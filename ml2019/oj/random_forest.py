import numpy as np
from sklearn.tree import DecisionTreeClassifier

class RandomForestClassifier():
    def __init__(self, n_model=10):
        '''
        初始化函数
        '''
        #分类器的数量，默认为10
        self.n_model = n_model
        #用于保存模型的列表，训练好分类器后将对象append进去即可
        self.models = []
        #用于保存决策树训练时随机选取的列的索引
        self.col_indexs = []


    def fit(self, feature, label):
        '''
        训练模型
        :param feature: 训练集数据，类型为ndarray
        :param label: 训练集标签，类型为ndarray
        :return: None
        '''

        #************* Begin ************#
        N = feature.shape[0]
        for i in range(self.n_model):
            model = DecisionTreeClassifier(max_depth=10, max_features='log2')
            mask = np.random.choice(N, size=N, replace=True)
            model.fit(feature[mask], label[mask])
            self.models.append(model)
        #************* End **************#


    def predict(self, feature):
        '''
        :param feature:测试集数据，类型为ndarray
        :return:预测结果，类型为ndarray，如np.array([0, 1, 2, 2, 1, 0])
        '''
        #************* Begin ************#
        pred_all = None
        for i in range(self.n_model):
            pred_cur = self.models[i].predict(feature).reshape(-1, 1)
            pred_all = pred_cur if pred_all is None else np.hstack((pred_all, pred_cur))
        N = feature.shape[0]
        pred = np.zeros(N)
        for i in range(N):
            pred[i] = np.argmax(np.bincount(pred_all[i]))
        return pred
        #************* End **************#
