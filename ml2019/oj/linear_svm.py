#encoding=utf8
from sklearn.svm import LinearSVC

def linearsvc_predict(train_data,train_label,test_data):
    '''
    input:train_data(ndarray):训练数据
          train_label(ndarray):训练标签
    output:predict(ndarray):测试集预测标签
    '''
    #********* Begin *********#
    clf = LinearSVC(dual=False, max_iter=1000)
    clf.fit(train_data, train_label)
    predict = clf.predict(test_data)
    #********* End *********#
    return predict