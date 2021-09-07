#!/usr/bin/env python
# -*- coding: UTF-8 -*-
import csv
from decimal import *



class Article:
    def __init__(self, article_id, prod_id, name, description, price, amount):
        self.article_id = article_id
        self.prod_id = prod_id
        self.name = name
        self.description = description
        self.price = price
        self.amount = amount

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
            Article(row[0], row[1], row[2], row[3], Decimal(row[4]), int(row[5])))

    return article_instances


if __name__ == '__main__':
    read_articles_from_csv('../tests/data/a.csv')
