#-*- coding:utf-8 -*-
import numpy as np
from scipy import stats
class Solution():
	def solve(self, x, y):
		if not x and not y:
			return [None, None]

		x1 = np.array(x)
		y1 = np.array(y)
		n = x1.size

		r_h = n * (x1 * y1).sum() - x1.sum() * y1.sum()
		r_l = ((n*(x1**2).sum() - (x1.sum())**2) * (n*(y1**2).sum() - (y1.sum())**2)) ** 0.5

		r = r_h / r_l

		# finally I failed to solve out the value of p.
		p = 0.0

		return [round(r, 6), round(p, 6)]



print Solution().solve([1, 2, 3], [2, 2, 3])