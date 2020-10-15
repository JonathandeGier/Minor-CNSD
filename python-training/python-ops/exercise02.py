#!/usr/bin/env python3
"""Some description"""

import requests


def perform_post(baseurl):
    """Perform post request"""

    # implement this
    print(requests.post(baseurl, data={"course": "Python Training", "name":"Exersise02"}, headers={"User-Agent": "Exercise/0.0.02"}).json())


if __name__ == "__main__":
    """Run this when called directly"""
    url = 'https://httpbin.org/post'

    perform_post(url)
