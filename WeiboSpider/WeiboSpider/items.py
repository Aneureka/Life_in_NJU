# -*- coding: utf-8 -*-

import scrapy


class WeiboItem(scrapy.Item):
    id = scrapy.Field()
    uid = scrapy.Field()
    username = scrapy.Field()
    content = scrapy.Field()
    raw_content = scrapy.Field()
    created_at = scrapy.Field()
    attitudes_count = scrapy.Field()
    comments_count = scrapy.Field()
    reposts_count = scrapy.Field()
