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
