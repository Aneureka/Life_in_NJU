#-*- coding:utf-8 -*-
'''
log api example: log('output is: ' + str(output))
'''
from log_api import log

class Solution():
	def solve(self, A):
		num_strs = A

		numbers = []
		for num_str in num_strs:
			for num in list(num_str):
				numbers.append(int(num))

		results = {}
		for num in range(10):
			results[num] = numbers.count(num)

		return results