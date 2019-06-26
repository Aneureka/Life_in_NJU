#encoding=utf8
import numpy as np
from sklearn.linear_model import LogisticRegression
import pandas as pd
from sklearn.datasets import load_iris

#adaboost算法
class AdaBoost:
    '''
    input   :n_estimators(int):迭代轮数
          learning_rate(float):弱分类器权重缩减系数
    '''
    def __init__(self, n_estimators=50, learning_rate=1.0):
        self.clf_num = n_estimators
        self.learning_rate = learning_rate

    def init_args(self, datasets, labels):
        self.X = datasets
        self.Y = labels
        self.M, self.N = datasets.shape
        # 弱分类器数目和集合
        self.clf_sets = []
        # 初始化weights
        self.weights = np.array([1.0/self.M]*self.M)
        # G(x)系数 alpha
        self.alpha = []

    #********* Begin *********#
    # 计算alpha
    def _alpha(self, error):
        return 0.5 * (np.log(1 - error) - np.log(error))

    # 权值更新
    def _w(self, a, y_pred, y):
        weight_sum = 0.0
        for i in range(self.M):
            self.weights[i] *= np.exp(- a * y_pred[i] * y[i])
            weight_sum += self.weights[i]
        for i in range(self.M):
            self.weights[i] /= weight_sum

    def fit(self, X, y):
        '''
        X(ndarray):训练数据
        y(ndarray):训练标签
        '''
        self.init_args(X, y)

        for i in range(self.clf_num):
            # 计算G(x)系数a
            self.clf_sets.append(LogisticRegression(solver='newton-cg', max_iter=10, C=0.8))
            self.clf_sets[i].fit(X, y, sample_weight=self.weights)
            y_pred = self.clf_sets[i].predict(X)
            error = np.array(self.weights).dot(y_pred != y)
            print(error)
            if error > 0.5:
                break
            # 更新 alpha
            a = self._alpha(error)
            self.alpha.append(a)
            # 更新权值
            # print(self.weights)
            self._w(a, y_pred, y)
            # print(y_pred == y)
            # print(self.weights[y_pred != y])
            # print(self.weights[y_pred == y])

    def predict(self, data):
        '''
        input:data(ndarray):单个样本
        output:预测为正样本返回+1，负样本返回-1
        '''
        if len(data.shape) != 2:
            data = data[np.newaxis, :]
        pred = np.zeros(data.shape[0])
        for i, clf in enumerate(self.clf_sets):
            pred += self.alpha[i] * clf.predict(data)
        pred = np.sign(pred)
        print(pred)

        return pred[0]

    #********* End *********#

#获取并处理鸢尾花数据
def create_data():
    iris = load_iris()
    df = pd.DataFrame(iris.data, columns=iris.feature_names)
    df['label'] = iris.target
    df.columns = ['sepal length', 'sepal width', 'petal length', 'petal width', 'label']
    data = np.array(df.iloc[:100, [0, 1, -1]])
    #将标签为0的数据标签改为-1
    for i in range(len(data)):
        if data[i,-1] == 0:
            data[i,-1] = -1
    return data[:,:2], data[:,-1]


if __name__ == '__main__':
    X_train, y_train = create_data()
    ada = AdaBoost()
    ada.fit(X_train, y_train)
