# -*- coding: utf-8 -*-

"""
程序入口
"""

from flask import Flask, request
from handle import handle_auth, handle_chat

app = Flask(__name__)


@app.route('/')
def hello_world():
	return "Hello world!"


@app.route('/wx', methods=['POST', 'GET'])
def wechat():
	if request.method == 'GET':
		"""处理验证"""
		auth_data = request.args.to_dict()
		return handle_auth(auth_data)
	else:
		"""处理逻辑"""
		chat_data = request.data
		return handle_chat(chat_data)


if __name__ == '__main__':
	app.run(host='0.0.0.0', port=80, debug=True)
