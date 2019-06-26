# encoding=utf8
import numpy as np
from sklearn.tree import DecisionTreeClassifier
from sklearn.ensemble import AdaBoostClassifier

# adaboost算法
class AdaBoost:
    def __init__(self, n_estimators=50, learning_rate=1.0):
        self.clf_num = n_estimators
        self.learning_rate = learning_rate
        self.clf = AdaBoostClassifier(n_estimators=50, learning_rate=1e-2)

    def fit(self, X, y):
        self.clf.fit(X, y)

    def predict(self, data):
        if len(data.shape) != 2:
            data = data[np.newaxis, :]
        return self.clf.predict(data)[0]

