
import requests
import pytest
from assertpy import assert_that

class Test():
    def test_ok(self):
        res = requests.get('http://web-server:8080/users/')
        assert_that(res.ok).is_true()

    def test_user(self):
        res = requests.get('http://web-server:8080/users/')
        data = res.json()
        assert isinstance(data[0].get("id"), int)