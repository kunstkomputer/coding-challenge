#!/usr/bin/env python
# -*- coding: UTF-8 -*-
from decimal import Decimal

import pytest

from product_list import app


@pytest.fixture
def mock_csv_data():
    return [
        "id|produktId|name|beschreibung|preis|bestand",
        "A-cVBTQHVF|P-cVBTQHVF|OBLAEDD|Gfaokn Ttefoa pfrnZ|58.77|38",
        "A-PnZZzJFO|P-cVBTQHVF|EDDBZM|fdlViTtefo fpfrnZj hva czIsmugweh|8.35|39"
    ]


@pytest.fixture
def mock_csv_file(tmp_path, mock_csv_data):
    datafile = tmp_path / "articles.csv"
    datafile.write_text("\n".join(mock_csv_data))
    return str(datafile)


def test_article_prices_as_float_gets_parsed_to_decimal():
    sample_one = app.Article("empty", "empty", "empty", "empty", 38.1, 1)
    assert sample_one.price == Decimal("38.1")
    sample_two = app.Article("empty", "empty", "empty", "empty", 38, 1)
    assert sample_two.price == Decimal("38.00")
    sample_three = app.Article("empty", "empty", "empty", "empty", "37", 1)
    assert sample_three.price == Decimal("37.00")


def test_read_articles_from_csv(mock_csv_file):
    mock_instances = [
        app.Article("A-cVBTQHVF", "P-cVBTQHVF", "OBLAEDD", "Gfaokn Ttefoa pfrnZ", 58.77,
                    38),
        app.Article("A-PnZZzJFO", "P-cVBTQHVF", "EDDBZM", "fdlViTtefo fpfrnZj hva czIsmugweh",
                    8.35, 39),

    ]
    assert app.read_articles_from_csv(mock_csv_file) == mock_instances


def test_products_with_stock_zero_are_filtered():
    zero_stock_art_one = app.Article("empty", "P-1", "empty", "empty", "38", "0")
    zero_stock_art_two = app.Article("empty", "P-2", "empty", "empty", "38", "0")
    product_list = [zero_stock_art_one, zero_stock_art_two]

    filtered_list = app.remove_articles_with_stock_zero(product_list)

    assert len(filtered_list) == 0


def test_accumulate_products():
    sample_one = app.Article("empty", "P-1", "sample_one", "empty", "32", "2")
    sample_two = app.Article("empty", "P-1", "sample_two", "empty", "38", "4")
    sample_three = app.Article("empty", "P-2", "sample_three", "empty", "3", "1")
    sample_four = app.Article("empty", "P-2", "sample_four", "empty", "2", "1")

    product_list = [sample_one, sample_two, sample_three, sample_four]

    accumulated_products = app.get_accumulated_stock_on_cheapest(product_list)

    assert accumulated_products[0].stock_count == 6
    assert accumulated_products[1].stock_count == 2


def test_picks_first_article_in_case_of_price_tie():
    sample_one = app.Article("empty", "P-1", "sample_one", "empty", "32", "2")
    sample_two = app.Article("empty", "P-1", "sample_two", "empty", "32", "4")

    product_list = [sample_one, sample_two]

    accumulated_products = app.get_accumulated_stock_on_cheapest(product_list)

    assert accumulated_products[0].name == "sample_one"
