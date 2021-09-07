#!/usr/bin/env python
# -*- coding: UTF-8 -*-

from product_list import app


def test_read_csv():
    assert app.read_csv(1, 2) == 3
