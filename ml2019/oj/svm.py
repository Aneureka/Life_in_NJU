#encoding=utf8
from sklearn.svm import SVC

def svc_predict(train_data,train_label,test_data,kernel):
    '''
    input:train_data(ndarray):训练数据
          train_label(ndarray):训练标签
          kernel(str):使用核函数类型:
              'linear':线性核函数
              'poly':多项式核函数
              'rbf':径像核函数/高斯核
    output:predict(ndarray):测试集预测标签
    '''
    #********* Begin *********#
    clf = SVC(kernel=kernel)
    clf.fit(train_data, train_label)
    predict = clf.predict(test_data)
    #********* End *********#
    return predict

