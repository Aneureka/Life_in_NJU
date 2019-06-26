import numpy as np
from scipy.stats import multivariate_normal


class GMM(object):
    def __init__(self, n_components, max_iter=100):
        '''
        构造函数
        :param n_components: 想要划分成几个簇，类型为int
        :param max_iter: EM的最大迭代次数
        '''
        self.n_components = n_components
        self.max_iter = max_iter

    def fit(self, train_data):
        '''
        训练，将模型参数分别保存至self.alpha，self.mu，self.sigma中
        :param train_data: 训练数据集，类型为ndarray
        :return: 无返回
        '''
        row, col = train_data.shape
        # 初始化每个高斯分布的响应系数
        self.alpha = np.array([1.0 / self.n_components] * self.n_components)
        # 初始化每个高斯分布的均值向量
        self.mu = np.random.rand(self.n_components, col)
        # self.mu = np.array([train_data[5].tolist(), train_data[21].tolist(), train_data[26].tolist()])
        # 初始化每个高斯分布的协方差矩阵
        self.sigma = np.array([np.eye(col)] * self.n_components)
        # self.sigma = np.array([np.eye(col)*0.1] * self.n_components)
        # ********* Begin *******#
        # 执行 EM 算法
        pm = np.zeros((self.n_components, row))
        for i in range(self.n_components):
            pm[i] = multivariate_normal(mean=self.mu[i], cov=self.sigma[i]).pdf(train_data)
        pm *= self.alpha.reshape(-1, 1)
        pm /= np.sum(pm, axis=0)
        lld = np.sum(np.log(self.alpha.reshape(-1, 1) * pm))
        tol = 1e-4
        for iter in range(self.max_iter):
            # M步，更新 mu, sigma, alpha
            for i in range(self.n_components):
                pm_i_sum = np.sum(pm[i])
                if np.abs(pm_i_sum) < tol or np.abs(pm_i_sum-1) < tol:
                    break
                self.mu[i] = np.dot(pm[i].reshape(1, -1), train_data) / pm_i_sum
                self.sigma[i] = np.zeros((col, col))
                for j in range(row):
                    self.sigma[i] += pm[i, j] * np.dot((train_data[j] - self.mu[i]).reshape(-1, 1), (train_data[j] - self.mu[i]).reshape(1, -1))
                self.sigma[i] /= pm_i_sum
                self.alpha[i] = pm_i_sum / row
            # 判断是否收敛
            new_lld = np.sum(np.log(self.alpha.reshape(-1, 1) * pm))
            if np.abs(new_lld - lld) < tol:
               break
            lld = new_lld
            # E步，计算 p_m(z_j=i|x_j)
            for i in range(self.n_components):
                pm[i] = multivariate_normal(mean=self.mu[i], cov=self.sigma[i]).pdf(train_data)
            pm *= self.alpha.reshape(-1, 1)
            pm /= np.sum(pm, axis=0)
        # ********* End *********#

    def predict(self, test_data):
        '''
        预测，根据训练好的模型参数将test_data进行划分。
        注意：划分的标签的取值范围为[0,self.n_components-1]，即若self.n_components为3，则划分的标签的可能取值为0,1,2。
        :param test_data: 测试集数据，类型为ndarray
        :return: 划分结果，类型为你ndarray
        '''

        # ********* Begin *********#
        row = test_data.shape[0]
        pm = np.zeros((self.n_components, row))
        for i in range(self.n_components):
            pm[i] = multivariate_normal(mean=self.mu[i], cov=self.sigma[i]).pdf(test_data)
        pm /= np.mean(pm, axis=0)
        res = np.zeros(row)
        for i in range(row):
            res[i] = np.argmax(pm[:, i])
        res = res.astype(int)
        return res
        # ********* End *********#


if __name__ == '__main__':
    data = [[0.697, 0.460],
     [0.774, 0.376],
     [0.634, 0.264],
     [0.608, 0.318],
     [0.556, 0.215],
     [0.403, 0.237],
     [0.481, 0.149],
     [0.437, 0.211],
     [0.666, 0.091],
     [0.243, 0.267],
     [0.245, 0.057],
     [0.343, 0.099],
     [0.639, 0.161],
     [0.657, 0.198],
     [0.360, 0.370],
     [0.593, 0.042],
     [0.719, 0.103],
     [0.359, 0.188],
     [0.339, 0.241],
     [0.282, 0.257],
     [0.748, 0.232],
     [0.714, 0.346],
     [0.483, 0.312],
     [0.478, 0.437],
     [0.525, 0.369],
     [0.751, 0.489],
     [0.532, 0.472],
     [0.473, 0.376],
     [0.725, 0.445],
     [0.446, 0.459]]
    data = np.array(data)
    gmm = GMM(n_components=2)
    gmm.fit(data)
    print(gmm.predict(data))


