# -*- coding: utf-8 -*-
"""
微信公众平台 负责解析消息
"""
import hashlib
import json
import receive
import reply
from unit_helper import UNITHelper, get_unit_config

token = "aneureka"

def handle_auth(data):
	"""验证token"""
	if len(data) < 4: 	# 如果请求不是来自微信，返回提示语
		return "GET Request not from wechat!"
	# 核心验证过程
	signature = data.get('signature')
	timestamp = data.get('timestamp')
	nonce = data.get('nonce')
	echostr = data.get('echostr')
	arr = [token, timestamp, nonce]
	arr.sort()
	hashcode = hashlib.sha1(''.join(arr).encode('utf-8')).hexdigest()
	# print("handle/GET func: hashcode, signature: ", hashcode, signature)
	if hashcode == signature:
		return echostr
	else:
		return "Failed to check!"

def handle_chat(data):
	"""处理逻辑"""
	rec_msg = receive.parse_xml(data)
	t = rec_msg.MsgType
	if isinstance(rec_msg, receive.Msg):
		if t == 'text':
			return handle_text(rec_msg)
		elif t == 'event':
			return handle_event(rec_msg)
		elif t == 'image':
			return handle_image(rec_msg)
		else:
			return handle_default(rec_msg)
	else:
		return "success"


def handle_text(rec_msg):
	rec_content = rec_msg.Content.strip().replace('\n', ' ')
	reply_content = UNITHelper(int(get_unit_config("scene_id"))).query(rec_content)
	reply_msg = reply.TextMsg(rec_msg.FromUserName, rec_msg.ToUserName, reply_content)
	return reply_msg.render()

def handle_event(rec_msg):
	rec_content = rec_msg.Event
	if rec_content == 'subscribe':
		reply_content = u'谢谢关注小冰，关于NJU的校园学习生活问题都可以向我提问哦，我会尽量回答的~'
		return reply.TextMsg(rec_msg.FromUserName, rec_msg.ToUserName, reply_content).render()
	else:
		reply_content = u'好生气哦！'
		return reply.TextMsg(rec_msg.FromUserName, rec_msg.ToUserName, reply_content).render()

def handle_image(rec_msg):
	reply_content = u'暂时还没有图片处理功能，请期待哦^_^'
	return reply.TextMsg(rec_msg.FromUserName, rec_msg.ToUserName, reply_content).render()

def handle_default(rec_msg):
	reply_content = u'暂时还没有其他功能，请期待哦^_^'
	return reply.TextMsg(rec_msg.FromUserName, rec_msg.ToUserName, reply_content).render()
