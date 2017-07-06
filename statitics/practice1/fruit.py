#-*- coding:utf-8 -*-
'''
log api example: log('output is: ' + str(output))
'''
from log_api import log

class Solution():
	def solve(self, A):
		fruits = A
		uni_fruits = list(set(fruits))
		result = {}
		for fruit in uni_fruits:
			result[fruit] = fruits.count(fruit)
		return result