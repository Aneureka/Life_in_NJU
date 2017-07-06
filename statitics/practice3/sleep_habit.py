# -*- coding: UTF-8 –*-

"""
New York is known as "the city that never sleeps".
A random sample of 25 New Yorkers were asked how much sleep they get per night.
Statistical summaries of these data are shown below.
Do these data provide strong evidence that New Yorkers sleep less than 8 hours per night on average?
Null-hypothesis is H0: u=8, and alpha is 0.05

n    mean    stand-variance    min    max
25   7.73        0.77         6.17    9.7

output
[degree-of-freedom-of-distribution, statistical values, conclusion],
'degree-of-freedom-of-distribution' and 'statistical values' are accurate to the second decimal place,
'conclusion' is True, which means the H0 is accepted, or False

If you were to construct a 90% confidence interval that corresponded to this hypothesis test,
would you expect 8 hours to be in the interval? Explain your reasoning.
"""

import numpy as np
from scipy import stats

class Solution:
    def solve(self):
		n = 25.0
		mean = 7.73
		all_mean = 8.0
		s = 0.77
		alpha = 0.05
		df = n - 1

		t = stats.t.ppf(1 - alpha, df)
		result = (mean - all_mean) / s * (n**0.5)

		judge = True if result > -t else False

		return [round(df, 2), round(result, 2), judge]