#!/usr/bin/env python
# -*- coding: UTF-8 -*-
import csv
import operator
import os
import shutil
import tempfile
import requests
from decimal import *
from itertools import groupby

article_list = []
product_list = []
temp_file_upload, path_upload = tempfile.mkstemp()
temp_file_download, path_download = tempfile.mkstemp()


class Article:
    def __init__(self, article_id: str, prod_id: str, name: str, description: str, price: str,
                 amount: str):
        self.article_id = str(article_id)
        self.prod_id = str(prod_id)
        self.name = str(name)
        self.description = str(description)
        self.price = Decimal(str(price))
        self.stock_count = int(amount)

    def __eq__(self, other):
        if not isinstance(self, other.__class__):
            return False

        return self.article_id == other.article_id and self.prod_id == other.prod_id and \
            self.name == other.name and self.description == other.description and \
            self.price == other.price and self.stock_count == other.stock_count


def download_articles_csv_webserver(fh, num_of_products):
    url = "http://localhost:8080/articles/"
    r = requests.get(url + str(num_of_products), stream=True)
    if r.status_code == 200:
        with open(fh, 'wb') as f:
            r.raw.decode_content = True
            shutil.copyfileobj(r.raw, f)


def read_articles_from_csv(csv_file_path):
    data = list(csv.reader(open(csv_file_path), delimiter='|', lineterminator='\n'))
    print(data)
    article_instances = []
    for row in data[1:]:
        article_instances.append(
            Article(row[0], row[1], row[2], row[3], row[4], row[5]))

    return article_instances


def remove_articles_with_stock_zero(articles):
    if all(isinstance(x, Article) for x in articles):
        return list(i for i in articles if i.stock_count > 0)


def get_accumulated_stock_on_cheapest(products):
    result = []
    print(products)
    product_groups = []
    for key, group in groupby(products, key=operator.attrgetter('prod_id')):
        product_groups.append(list(group))

    for article in product_groups:
        cheapest_article = min(article, key=operator.attrgetter('price'))
        amount_accumulated = sum(i.stock_count for i in article)
        print(cheapest_article.name)
        print(f"ammount accumulated:{amount_accumulated}")
        cheapest_article.stock_count = amount_accumulated
        result.append(cheapest_article)
    print(f"Group_content:{list(product_groups)}")
    return result


def write_product_list_to_csv(products):
    try:
        with os.fdopen(temp_file_upload, 'w') as tmp:
            csv_writer = csv.writer(tmp, delimiter='|', lineterminator='\n')
            csv_writer.writerow(['produktId', 'name', 'beschreibung', 'preis', 'summeBestand'])
            for article in products:
                csv_writer.writerow(
                    [article.prod_id, article.name, article.description, article.price,
                     article.stock_count])
    finally:
        pass
        # os.remove(path_upload)


def upload_file_to_webserver(fh, num_of_products):
    headers = {'Content-Type': 'text/csv'}

    url = "http://localhost:8080/products/" + str(num_of_products)
    data = open(fh, 'rb').read()

    r = requests.put(url, data=data, headers=headers)
    print(r)
    print(r.text)


pass

if __name__ == '__main__':
    articles_to_fetch = 12
    download_articles_csv_webserver(temp_file_download, articles_to_fetch)

    article_list.extend(read_articles_from_csv(path_download))
    cleared_list = remove_articles_with_stock_zero(article_list)
    product_list.extend(get_accumulated_stock_on_cheapest(cleared_list))

    write_product_list_to_csv(product_list)
    upload_file_to_webserver(path_upload, articles_to_fetch)

    os.remove(path_download)
    os.remove(path_upload)
