# -*- coding: utf-8 -*-
"""
微信公众平台 消息回复模板
"""
import time

class Msg(object):
	def __init__(self):
		pass

	def render(self):
		return "success"

class TextMsg(Msg):
	def __init__(self, toUserName, fromUserName, content):
		self.__dict = dict()
		self.__dict['ToUserName'] = toUserName
		self.__dict['FromUserName'] = fromUserName
		self.__dict['CreateTime'] = int(time.time())
		self.__dict['Content'] = content

	def render(self):
		"""回复文本消息"""
		TEMPLATE = """
			<xml>
			<ToUserName><![CDATA[{ToUserName}]]></ToUserName>
			<FromUserName><![CDATA[{FromUserName}]]></FromUserName>
			<CreateTime>{CreateTime}</CreateTime>
			<MsgType><![CDATA[text]]></MsgType>
			<Content><![CDATA[{Content}]]></Content>
			</xml>
			"""
		return TEMPLATE.format(**self.__dict) # ** 表示将参数以字典的形式导入


class ImageMsg(Msg):
	def __init__(self, toUserName, fromUserName, mediaId):
		self.__dict = dict()
		self.__dict['ToUserName'] = toUserName
		self.__dict['FromUserName'] = fromUserName
		self.__dict['CreateTime'] = int(time.time())
		self.__dict['MediaId'] = mediaId

	def render(self):
		"""回复文本消息"""
		TEMPLATE = """
			<xml>
			<ToUserName><![CDATA[{ToUserName}]]></ToUserName>
			<FromUserName><![CDATA[{FromUserName}]]></FromUserName>
			<CreateTime>{CreateTime}</CreateTime>
			<MsgType><![CDATA[image]]></MsgType>
			<Image><MediaId><![CDATA[{MediaId}]]></MediaId></Image>
			</xml>
			"""
		return TEMPLATE.format(**self.__dict)



