#!/usr/bin/env python
# -*- coding: UTF-8 -*-
import csv
from decimal import *

product_list = []


class Article:
    def __init__(self, article_id: str, prod_id: str, name: str, description: str, price: str,
                 amount: str):
        self.article_id = str(article_id)
        self.prod_id = str(prod_id)
        self.name = str(name)
        self.description = str(description)
        self.price = Decimal(str(price))
        self.amount = int(amount)

    def __eq__(self, other):
        if not isinstance(self, other.__class__):
            return False

        return self.article_id == other.article_id and self.prod_id == other.prod_id and \
            self.name == other.name and self.description == other.description and \
            self.price == other.price and self.amount == other.amount


def read_articles_from_csv(csv_file_path):
    data = list(csv.reader(open(csv_file_path), delimiter='|', lineterminator='\n'))
    print(data)
    article_instances = []
    for row in data[1:]:
        article_instances.append(
            Article(row[0], row[1], row[2], row[3], row[4], row[5]))

    return article_instances


def remove_products_with_stock_zero(products):
    if all(isinstance(x, Article) for x in products):
        return list(filter(lambda x: x.amount > 0, products))



if __name__ == '__main__':
    product_list.extend(read_articles_from_csv('../tests/data/a.csv'))
