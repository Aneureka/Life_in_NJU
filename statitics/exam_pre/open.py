#encoding=utf-8
import urllib2
import pandas as pd
import numpy as np
from pandas import Series, DataFrame

url_old = "http://py.mooctest.net:8081/dataset/population/population_old.csv"
url_total = "http://py.mooctest.net:8081/dataset/population/population_total.csv"
print urllib2.urlopen(url_total).read().decode("GBK")

# header = ["city", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
# year10 = np.array(pd.read_csv(url10, skiprows=range(6), skipfooter=3, names=header)[7].values)
# year14 = np.array(pd.read_csv(url1, skiprows=range(6), skipfooter=3, names=header)[7].values)
print pd.read_csv(url_old, skiprows=range(3), names=range(16))[1]
old = np.array(pd.read_csv(url_old, skiprows=range(3), names = range(16))[1].values, dtype=np.float64)
total = np.array(pd.read_csv(url_total, skiprows=range(5), names = range(17))[4].values, dtype=np.float64)
print old

# print frame
# print res
#