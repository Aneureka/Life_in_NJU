#-*- coding:utf-8 -*-
'''
log api example: log('output is: ' + str(output))
'''
from log_api import log
import numpy as np

class Solution():
	def solve(self, A):
		g = A
		f = np.array([2.0, 0.0, -1.0, 1.0])
		return np.poly1d(np.polymul(f, g))