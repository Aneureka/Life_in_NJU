# -*- coding: UTF-8 –*-

"""
A 95% confidence interval for a population mean, u, is given as (18.985, 21.015).
This confidence interval is based on a simple random samples of 36 observations.
Calculate the sample mean and standard deviation.
Assume that all conditions necessary for inference are satisfied.
Use the t-distribution in any calculations.
Output:[mean, standard deviation], accurate to the second decimal place
"""

import numpy as np
from scipy import stats

class Solution():
    def solve(self):
		alpha = 0.05
		left, right = 18.985, 21.015
		n = 36
		t = stats.t.ppf(1 - alpha/2, n-1)
		print t

		# calculate
		mean = (left + right) / 2
		std = (mean - left) * (n**0.5) / t
		return [round(mean, 2), round(std, 2)]

print Solution().solve()