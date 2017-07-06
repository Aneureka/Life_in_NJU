#-*- coding:utf-8 -*-
'''
log api example: log('output is: ' + str(output))
'''
# from log_api import log
from math import ceil, log

class Solution():
	def solve(self):
		return ceil(-log(0.02))

print Solution().solve()