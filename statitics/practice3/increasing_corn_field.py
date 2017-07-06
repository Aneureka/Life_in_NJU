# -*- coding: UTF-8 –*-

"""
A large farm wants to try out a new type of fertilizer to evaluate whether it will improve the farm's corn production.
The land is broken into plots that produce an average of 1.215 pounds of corn with a standard deviation of 94 pounds per plot.
The owner is interested in detecting any average difference of at least 40 pounds per plot.
How many plots of land would be needed for the experiment if the desired power level is 90%?
Assume each plot of land gets treated with either the current fertilizer or the new fertilizer.
output:
plot_num, int type
"""

import numpy as np
import math
from scipy import stats

class Solution():
	def solve(self):
		mean = 1215.0
		std = 94.0
		wrong_limit = 40.0
		za = 1.96
		zb = 1.28

		n = math.ceil((((za + zb) * std / wrong_limit) ** 2) * 2)
		return n

Solution().solve()