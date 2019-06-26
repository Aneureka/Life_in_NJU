import numpy as np

# 逻辑回归
class tiny_logistic_regression(object):
    def __init__(self):
        #W
        self.coef_ = None
        #b
        self.intercept_ = None
        #所有的W和b
        self._theta = None


    def _sigmoid(self, x):
        return 1. / (1. + np.exp(-x))


    #训练，train_labels中的值只能是0或者1
    def fit(self, train_datas, train_labels, learning_rate=1e-4, n_iters=1e3):
        #loss
        def J(theta, X_b, y):
            y_hat = self._sigmoid(X_b.dot(theta))
            try:
                return -np.sum(y*np.log(y_hat)+(1-y)*np.log(1-y_hat)) / len(y)
            except:
                return float('inf')

        # 算theta对loss的偏导
        def dJ(theta, X_b, y):
            return X_b.T.dot(self._sigmoid(X_b.dot(theta)) - y) / len(y)

        # 批量梯度下降
        def gradient_descent(X_b, y, initial_theta, leraning_rate, n_iters=1e2, epsilon=1e-6):
            theta = initial_theta
            cur_iter = 0
            while cur_iter < n_iters:
                gradient = dJ(theta, X_b, y)
                last_theta = theta
                theta = theta - leraning_rate * gradient
                if (abs(J(theta, X_b, y) - J(last_theta, X_b, y)) < epsilon):
                    break
                cur_iter += 1
            return theta

        X_b = np.hstack([np.ones((len(train_datas), 1)), train_datas])
        initial_theta = np.zeros(X_b.shape[1])
        self._theta = gradient_descent(X_b, train_labels, initial_theta, learning_rate, n_iters)

        self.intercept_ = self._theta[0]
        self.coef_ = self._theta[1:]

        return self


    #预测X中每个样本label为1的概率
    def predict_proba(self, X):
        X_b = np.hstack([np.ones((len(X), 1)), X])
        return self._sigmoid(X_b.dot(self._theta))

    #预测
    def predict(self, X):
        proba = self.predict_proba(X)
        result = np.array(proba >= 0.5, dtype='int')
        return result



class OvR(object):
    def __init__(self):
        # 用于保存训练时各种模型的list
        self.models = []
        # 用于保存models中对应的正例的真实标签
        # 例如第1个模型的正例是2，则real_label[0]=2
        self.real_label = []


    def fit(self, train_datas, train_labels):
        '''
        OvR的训练阶段，将模型保存到self.models中
        :param train_datas: 训练集数据，类型为ndarray
        :param train_labels: 训练集标签，标签值为0,1,2之类的整数，类型为ndarray，shape为(-1,)
        :return:None
        '''

        #********* Begin *********#
        for i in range(3):
            train_labels_i = np.copy(train_labels)
            train_labels_i[train_labels_i != i] = -1
            train_labels_i[train_labels_i == i] = 1
            train_labels_i[train_labels_i == -1] = 0
            model = tiny_logistic_regression()
            model.fit(train_datas, train_labels_i)
            self.models.append(model)
        #********* End *********#


    def predict(self, test_datas):
        '''
        OvR的预测阶段
        :param test_datas:测试集数据，类型为ndarray
        :return:预测结果，类型为ndarray
        '''

        #********* Begin *********#
        N = test_datas.shape[0]
        scores = np.zeros((N, 3))
        for i, model in enumerate(self.models):
            result_i = model.predict_proba(test_datas)
            scores[:, i] = result_i
        return np.argmax(scores, axis=1)
        #********* End *********#
