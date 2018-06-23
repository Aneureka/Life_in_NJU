# 微博个人主页：https://m.weibo.cn/u/1733950851
# 微博接口：https://m.weibo.cn/api/container/getIndex?containerid=1076031733950851[&page=2]
# 单个微博全文：https://m.weibo.cn/statuses/extend?id=4198221053585403
#
# 爬取内容：对象的微博及其评论（截至2015年）
# 思路：每次爬取一个对象的微博，翻页
#


import scrapy
import json
from WeiboSpider.items import WeiboItem


class WeiboSpider(scrapy.Spider):
    name = "weibo"
    id = "1076031733950851"
    start_url = "https://m.weibo.cn/api/container/getIndex?containerid={}&page=".format(id)
    cur_page = 0

    def start_requests(self):
        url = self.get_next_url()
        yield scrapy.Request(url=url, callback=self.parse)

    def parse(self, response):
        response_data = json.loads(response.body)
        if response_data["ok"] == 0:
            return
        cards = response_data["data"]["cards"]
        for card in cards:
            # if the card is not about mblog
            if "mblog" not in card.keys():
                continue
            yield self.get_weibo_info(card)
        next_url = self.get_next_url()
        yield scrapy.Request(url=next_url, callback=self.parse)

    def get_next_url(self):
        self.cur_page += 1
        next_url = self.start_url + str(self.cur_page)
        # print(next_url)
        return next_url

    @staticmethod
    def get_weibo_info(card):
        weibo_info = WeiboItem()
        blog = card["mblog"]
        weibo_info["id"] = blog["id"]
        weibo_info["uid"] = blog["user"]["id"]
        weibo_info["username"] = blog["user"]["screen_name"]
        weibo_info["content"] = blog["text"]
        weibo_info["raw_content"] = ""
        if "raw_text" in blog.keys():
            weibo_info["raw_content"] = blog["raw_text"]
        weibo_info["created_at"] = blog["created_at"]
        weibo_info["attitudes_count"] = blog["attitudes_count"]
        weibo_info["comments_count"] = blog["comments_count"]
        weibo_info["reposts_count"] = blog["reposts_count"]
        return weibo_info
