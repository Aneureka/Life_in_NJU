import numpy as np
from sklearn import preprocessing, tree
from sklearn.model_selection import KFold
from sklearn.metrics import roc_auc_score
import pandas as pd


def load_data(mode):
    if mode == 'train':
        filepath = './dataset/adult.data'
        skiprows = 0
    elif mode == 'test':
        filepath = './dataset/adult.test'
        skiprows = 1
    else:
        raise ValueError('mode should be \'train\' or \'test\'')
    data = pd.read_csv(filepath, header=None, skiprows=skiprows, skipinitialspace=True, names=['age', 'workclass', 'fnlwgt', 'education', 'education-num', 'marital-status', 'occupation', 'relationship', 'race', 'sex', 'capital-gain', 'capital-loss', 'hours-per-week', 'native-country', 'income'])
    # encode categorical data to labels
    encode_data(data, ['workclass', 'education', 'marital-status', 'occupation', 'relationship', 'race', 'sex', 'native-country', 'income'])
    # print(data['income'].iloc[:100])
    x = data.iloc[:, :-1].values
    y = data.iloc[:, -1].values
    return x, y


def encode_data(df, columns):
    for c in columns:
        df[c] = preprocessing.LabelEncoder().fit_transform(df[c])
    df[columns[-1]] = df[columns[-1]].map({0: -1, 1: 1})

    
class BaseClassifier(object):
    def __init__(self):
        self.clf = tree.DecisionTreeClassifier(max_depth=7)
    
    def train(self, x, y, d=None):
        self.clf.fit(x, y, sample_weight=d)
    
    def predict(self, x):
        return self.clf.predict(x)
    

class AdaBoostClassifier(object):
    def __init__(self, base_classifier, num_iterations):
        self.base_classifier = base_classifier
        self.T = num_iterations
        self.alphas = []
        self.hypotheses = []

    def train(self, x, y):
        N = x.shape[0]
        d = np.ones(N) / N
        for t in range(self.T):
            h = self.base_classifier()
            h.train(x, y, d)
            y_pred = h.predict(x)
            eps = d.dot(y_pred != y)
            # print(eps)
            if eps > 0.5:
                break
            alpha = (np.log(1 - eps) - np.log(eps)) / 2
            d *= np.exp(- alpha * y * y_pred)
            d /= d.sum()
            self.alphas.append(alpha)
            self.hypotheses.append(h)
    
    def predict(self, x):
        y_pred = np.zeros(x.shape[0])
        for alpha, h in zip(self.alphas, self.hypotheses):
            y_pred += alpha * h.predict(x)
        y_pred = np.sign(y_pred)
        return y_pred

    def score(self, x, y):
        return np.mean(self.predict(x) == y)

    def auc_score(self, x, y):
        return roc_auc_score(y, self.predict(x))


def cross_validate():
    num_folds = 5
    # num_iterations_list = [10, 20, 50, 100, 200, 300]
    num_iterations_list = [60, 70, 80, 90, 100, 110]
    # num_iterations_list = [30, 40, 50, 60]
    x, y = load_data('train')
    kf = KFold(n_splits=num_folds)
    for num_iterations in num_iterations_list:
        ave_auc = 0.0
        for train_index, val_index in kf.split(x):
            x_train, x_val = x[train_index], x[val_index]
            y_train, y_val = y[train_index], y[val_index]
            clf = AdaBoostClassifier(BaseClassifier, num_iterations)
            clf.train(x_train, y_train)
            ave_auc += clf.auc_score(x_val, y_val)
        ave_auc /= num_folds
        print('num of iterations: %d, average auc: %f' % (num_iterations, ave_auc))
        

def test():
    best_num_iterations = 90
    x_train, y_train = load_data('train')
    x_test, y_test = load_data('test')
    clf = AdaBoostClassifier(BaseClassifier, best_num_iterations)
    clf.train(x_train, y_train)
    best_auc = clf.auc_score(x_test, y_test)
    print('num of iterations: %d, best auc: %f' % (best_num_iterations, best_auc))


if __name__ == "__main__":
    # cross_validate()
    test()