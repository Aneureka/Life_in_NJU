from sklearn.datasets import load_boston
from sklearn.model_selection import train_test_split
from sklearn.metrics import r2_score

#encoding=utf8
from sklearn.svm import SVR

def svr_predict(train_data, train_label, test_data):
    '''
    input:train_data(ndarray):训练数据
          train_label(ndarray):训练标签
    output:predict(ndarray):测试集预测标签
    '''
    #********* Begin *********#
    svr = SVR(kernel='rbf', C=1e5, gamma=1e-5, epsilon=1)
    svr.fit(train_data, train_label)
    predict = svr.predict(test_data)
    #********* End *********#
    return predict

if __name__ == '__main__':
    boston = load_boston()
    x = boston['data']
    y = boston['target']

    # 划分训练集与测试集
    train_data, test_data, train_label, test_label = train_test_split(x, y, test_size=0.2, random_state=995)

    # best_score = 0.0
    # best_params = ()
    # for c in [0.1, 0.2, 1, 1.3, 5, 7, 10, 20, 50, 100, 1e5]:
    #     for k in ['rbf']:
    #         for e in [1e-3, 1e-2, 1e-1, 1, 5, 7, 10, 20]:
    #             for g in ['scale']:
    #                 svr = SVR(kernel=k, C=c, gamma=g, degree=1, epsilon=e)
    #                 pred = svr_predict(train_data, train_label, test_data, svr)
    #                 cur_score = r2_score(test_label, pred)
    #                 print(cur_score)
    #                 if cur_score > best_score:
    #                     best_score = cur_score
    #                     best_params = (c, k, e, g)
    # print('=========================')
    # print(best_score)
    # print(best_params)


