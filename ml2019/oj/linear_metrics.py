#encoding=utf8
import numpy as np

#mse
def mse_score(y_predict,y_test):
    mse = np.mean((y_predict-y_test)**2)
    return mse

#r2
def r2_score(y_predict,y_test):
    '''
    input:y_predict(ndarray):预测值
          y_test(ndarray):真实值
    output:r2(float):r2值
    '''
    #********* Begin *********#
    r2 = 1 - np.sum((y_predict-y_test)**2) / np.sum((y_test-np.mean(y_test))**2)
    #********* End *********#
    return r2

class LinearRegression :
    def __init__(self):
        '''初始化线性回归模型'''
        self.theta = None
    def fit_normal(self,train_data,train_label):
        '''
        input:train_data(ndarray):训练样本
              train_label(ndarray):训练标签
        '''
        #********* Begin *********#
        train_data = self._add_ones(train_data)
        self.theta = np.linalg.inv(train_data.T.dot(train_data)).dot(train_data.T).dot(train_label.T)
        #********* End *********#
        return self.theta
    def predict(self,test_data):
        '''
        input:test_data(ndarray):测试样本
        '''
        #********* Begin *********#
        test_data = self._add_ones(test_data)
        return test_data.dot(self.theta.T)
        #********* End *********#
    def _add_ones(self,data):
        ones = np.ones((data.shape[0], 1))
        return np.hstack((data, ones))
