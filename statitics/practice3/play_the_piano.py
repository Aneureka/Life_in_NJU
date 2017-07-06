# -*- coding: UTF-8 –*-

"""
Georgianna claims that in a small city renowned for its music school, the average child takes at least 5 years of piano lessons.
We have a random sample of 20 children from the city with a mean of 4.6 years of piano lessons
and a standard deviation of 2.2 years. Evaluate Georgianna's claims using a hypothesis test.
alpha is 0.05.
Extra:
(1) Construct a 95% confidence interval for the number of years students in this city takes piano lessons and interpret it in context of the data.
(2) Do your results from the hypothesis test and the confidence interval agree? Explain your reasoning.

[degree-of-freedom-of-distribution, statistical values, conclusion],
'degree-of-freedom-of-distribution' and 'statistical values' are accurate to the second decimal place,
'conclusion' is True, which means the H0 is accepted, or False
"""

import numpy as np
from scipy import stats

class Solution:
    def solve(self):
		n = 20.0
		mean = 4.6
		all_mean = 5.0
		s = 2.2
		alpha = 0.05
		df = n-1

		result = (mean - all_mean) / s * (n**0.5)
		t = stats.t.ppf(1-alpha, df)

		judge = True if result < t else False

		return [round(df, 2), round(result, 2), judge]


print Solution().solve()
