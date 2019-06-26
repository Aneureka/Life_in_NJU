import numpy as np
from scipy import stats


def em_single(init_values, observations):
    """
    模拟抛掷硬币实验并估计在一次迭代中，硬币A与硬币B正面朝上的概率
    :param init_values:硬币A与硬币B正面朝上的概率的初始值，类型为list，如[0.2, 0.7]代表硬币A正面朝上的概率为0.2，硬币B正面朝上的概率为0.7。
    :param observations:抛掷硬币的实验结果记录，类型为list。
    :return:将估计出来的硬币A和硬币B正面朝上的概率组成list返回。如[0.4, 0.6]表示你认为硬币A正面朝上的概率为0.4，硬币B正面朝上的概率为0.6。
    """

    #********* Begin *********#
    p_a, p_b = init_values[0], init_values[1]
    observations = np.array(observations)
    num_iter, num_each = observations.shape
    likelihoods = np.zeros((num_iter, 2))
    for i in range(num_iter):
        observation_i = observations[i]
        count_1 = np.sum(observation_i == 1)
        count_0 = np.sum(observation_i == 0)
        likelihoods[i, 0] = p_a**count_1 * (1-p_a)**count_0
        likelihoods[i, 1] = p_b**count_1 * (1-p_b)**count_0
    likelihoods /= np.sum(likelihoods, axis=1, keepdims=True)
    counts = np.zeros((2, 2))
    for i in range(num_iter):
        observation_i = observations[i]
        counts[0, 0] += likelihoods[i, 0] * np.sum(observation_i == 0)
        counts[0, 1] += likelihoods[i, 0] * np.sum(observation_i == 1)
        counts[1, 0] += likelihoods[i, 1] * np.sum(observation_i == 0)
        counts[1, 1] += likelihoods[i, 1] * np.sum(observation_i == 1)
    p_a = counts[0, 1] / (counts[0, 0] + counts[0, 1])
    p_b = counts[1, 1] / (counts[1, 0] + counts[1, 1])
    return [p_a, p_b]
    #********* End *********#


if __name__ == '__main__':
    init_values = [0.2, 0.7]
    observations = [[1, 1, 0, 1, 0], [0, 0, 1, 1, 0], [1, 0, 0, 0, 0], [1, 0, 0, 1, 1], [0, 1, 1, 0, 0]]
    print(em_single(init_values, observations)) # output = [0.346548, 0.528706]

