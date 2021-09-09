#!/usr/bin/env python
# -*- coding: UTF-8 -*-
import argparse
import csv
import logging
import operator
import os
import shutil
import tempfile
from decimal import *
from itertools import groupby

import requests


class Article:
    def __init__(self, article_id: str, prod_id: str, name: str, description: str, price: str,
                 amount: str):
        self.article_id = str(article_id)
        self.prod_id = str(prod_id)
        self.name = str(name)
        self.description = str(description)
        self.price = Decimal(str(price)).quantize(Decimal(10) ** -2)
        self.stock_count = int(amount)

    def __eq__(self, other):
        if not isinstance(self, other.__class__):
            return False

        return self.article_id == other.article_id and self.prod_id == other.prod_id and \
            self.name == other.name and self.description == other.description and \
            self.price == other.price and self.stock_count == other.stock_count


def download_articles_csv(fh, url, num_of_products):
    remote_url = url + '/articles/' + str(num_of_products)
    logging.info(f'Fetching csv from: {remote_url}')
    logging.debug(f'Tempfile download: {fh}')

    r = requests.get(remote_url, stream=True)
    if r.status_code == 200:
        with open(fh, 'wb') as f:
            r.raw.decode_content = True
            shutil.copyfileobj(r.raw, f)
    else:
        logging.error(f'Unable to fetch csv from remote. Response from Server: {r.text}')


def parse_articles_from_csv(csv_file_path):
    data = list(csv.reader(open(csv_file_path), delimiter='|', lineterminator='\n'))
    logging.debug(f'Data read from csv: {data}')

    article_instances = []
    for row in data[1:]:
        article_instances.append(
            Article(row[0], row[1], row[2], row[3], row[4], row[5]))

    return article_instances


def remove_articles_with_stock_zero(articles_list):
    if all(isinstance(x, Article) for x in articles_list):
        return list(i for i in articles_list if i.stock_count > 0)


def get_accumulated_product_stock_on_cheapest(products_list):
    result = []
    product_groups = []
    for key, group in groupby(products_list, key=operator.attrgetter('prod_id')):
        product_groups.append(list(group))

    for article in product_groups:
        cheapest_article = min(article, key=operator.attrgetter('price'))
        amount_accumulated = sum(i.stock_count for i in article)

        logging.debug(
            f'product: {cheapest_article.prod_id} cheapest article: {cheapest_article.name} '
            f'amount_accumulated:{amount_accumulated}')

        cheapest_article.stock_count = amount_accumulated
        result.append(cheapest_article)
    return result


def write_product_list_to_csv(products_list):
    with os.fdopen(tmp_file_upload, 'w') as tmp:
        csv_writer = csv.writer(tmp, delimiter='|', lineterminator='\n')
        csv_writer.writerow(['produktId', 'name', 'beschreibung', 'preis', 'summeBestand'])
        for article in products_list:
            csv_writer.writerow(
                [article.prod_id, article.name, article.description, article.price,
                 article.stock_count])


def upload_file_to_webserver(fh, url, num_of_products):
    remote_url = url + '/products/' + str(num_of_products)
    logging.info(f'Uploading csv to: {remote_url}')
    logging.debug(f'Tempfile Upload: {fh}')

    data = open(fh, 'rb').read()
    headers = {'Content-Type': 'text/csv'}

    r = requests.put(remote_url, data=data, headers=headers)
    logging.info(f'Remote responded: HTTP status: {r.status_code} and message: {r.text}')


if __name__ == '__main__':
    parser = argparse.ArgumentParser(
        description='Fetches a list of articles from a remote server, process them according to '
                    'spec and uploads a list of products as csv back to remote.'
                    'The cheapest articles per product with non zero stock is returned. All '
                    'corresponding products\' article stock count are summed and returned.')

    parser.add_argument('--download_url',
                        metavar='dl_url',
                        type=str,
                        nargs='?',
                        default='http://localhost:8080',
                        help='the Url to the server holding the article list. e.g. '
                             'http://localhost:8080')

    parser.add_argument('--upload_url',
                        metavar='upl_url',
                        type=str,
                        nargs='?',
                        default='http://localhost:8080',
                        help='the Url to the server to upload the processed products list. '
                             'http://localhost:8080')

    parser.add_argument('--lines',
                        metavar='lines',
                        type=int,
                        nargs='?',
                        default=100,
                        help='the number of articles to fetch from remote ')

    parser.add_argument('--debug',
                        action='store_true',
                        help='activate debug mode and don\'t remove tmp files')
    args = parser.parse_args()

    # parse args
    url_download = args.download_url
    url_upload = args.upload_url
    lines_to_fetch = args.lines
    if args.debug:
        logging.basicConfig(level=logging.DEBUG)
        logging.debug('Debug mode enabled. Tempfiles not automatically removed.')
    else:
        logging.basicConfig(level=logging.INFO)

    # setup
    articles = []
    products = []
    tmp_file_download, path_download = tempfile.mkstemp()
    tmp_file_upload, path_upload = tempfile.mkstemp()

    try:
        logging.info(f'Fetching {lines_to_fetch} articles')

        download_articles_csv(tmp_file_download, url_download, lines_to_fetch)

        articles.extend(parse_articles_from_csv(path_download))

        cleared_article_list = remove_articles_with_stock_zero(articles)

        products.extend(get_accumulated_product_stock_on_cheapest(cleared_article_list))

        write_product_list_to_csv(products)

        upload_file_to_webserver(path_upload, url_upload, lines_to_fetch)
    finally:
        if not args.debug:
            os.remove(path_download)
            os.remove(path_upload)
