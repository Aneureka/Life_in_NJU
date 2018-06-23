# -*- coding: utf-8 -*-
"""
处理UNIT的接口交互
"""
import requests
import json
import time
from configparser import ConfigParser

class UNITAccessToken(object):
	def __init__(self):
		self._access_token = "24.109be73bf3b1967ce872262090922b56.2592000.1510984950.282335-10091827"
		self._last_update = int(time.time())

	def _update_access_token(self):
		# client_id 为官网获取的AK， client_secret 为官网获取的SK
		ak = get_unit_config("AK")
		sk = get_unit_config("SK")
		url = 'https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=' + ak + '&client_secret=' + sk
		print(url)
		r = requests.get(url, headers={'Content-Type': 'application/json; charset=UTF-8'})
		rec_json = json.loads(r.content)
		self.access_token = rec_json.get('access_token')

	def get_access_token(self):
		if int(time.time()) - self._last_update > int(get_unit_config("EXPIRES_IN")):
			self._update_access_token()
		return self._access_token


class UNITHelper(object):
	def __init__(self, scene_id):
		self.auth = UNITAccessToken()
		self.scene_id = scene_id

	"""核心方法，处理用户提问，调用UNIT接口"""
	def query(self, question):
		access_token = self.auth.get_access_token()
		url = 'https://aip.baidubce.com/rpc/2.0/solution/v1/unit_utterance?access_token=' + access_token
		post_data = self._build_post_data(self.scene_id, question, " ")
		r = requests.post(url, headers={'Content-Type': 'application/json; charset=utf-8'}, data=post_data.encode('utf-8'),verify=False)
		r_json = json.loads(r.text)
		return self._extract_answer(r_json)

	def _build_post_data(self, scene_id, query, session_id):
		post_data = '{{ \"scene_id\":{}, \"query\":\"{}\", \"session_id\":\"{}\"}}'
		return post_data.format(scene_id, query, session_id)

	def _extract_answer(self, r_json):
		if r_json:
			if r_json.get('error_code'):
				print(r_json)
				return "小冰脑子短路了，可能得睡十几分钟..."
			else:
				say = r_json.get('result').get('action_list')[0].get('say')
				hint_list = r_json.get('result').get('action_list')[0].get('hint_list')
				res = [say]
				for hint in hint_list:
					res.append(hint.get('hint_query'))
				return "\n".join(res)
		else:
			return "我找不到小冰了..."

	def log(self):
		# TODO
		pass


def get_unit_config(key, sec="unit"):
	cf = ConfigParser()
	cf.read('config.conf')
	return cf.get(sec, key)


if __name__ == '__main__':
	print(UNITHelper(int(get_unit_config("scene_id"))).query("我要选课"))
	# res = json.loads(UNITHelper(int(get_unit_config("scene_id"))).query("教室"))
	# print res.get('result').get('action_list')[0].get('say')
