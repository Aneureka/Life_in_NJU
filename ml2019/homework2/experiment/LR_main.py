import numpy as np
import pandas as pd
import os
import math
import random
import sys
from itertools import product

DATA_DIR = os.path.join('.', 'dataset')


def load_data(val_prop=0):
  data = {}
  for mode in ['train', 'test']:
    file_path = os.path.join(DATA_DIR, '%s_set.csv' % mode)
    print('reading data from %s' % file_path)
    df = pd.read_csv(file_path)
    X = df.iloc[:, :-1]
    y = df.iloc[:, -1]
    data['X_%s' % mode] = X.values
    data['y_%s' % mode] = y.values
  # split training set to validation set
  data['X_train'], data['y_train'], data['X_val'], data['y_val'] = split_train_set(data['X_train'], data['y_train'], prop=val_prop)
  return data


def split_train_set(X, y, prop):
  div = int((1-prop) * X.shape[0])
  X_train, y_train = X[:div, :], y[:div]
  X_val, y_val = X[div:, :], y[div:]
  return X_train, y_train, X_val, y_val


def add_ones(X):
  return np.hstack([X, np.ones([X.shape[0], 1], dtype=np.int32)])


def process_y_for_class(y, clazz):
  y_p = np.copy(y)
  y_p[y_p != clazz] = 0
  y_p[y_p == clazz] = 1
  return y_p


def sgd(w, dw, config=None):
    if config is None: config = {}
    config.setdefault('learning_rate', 1e-3)
    w -= config['learning_rate'] * dw
    return w, config


def sgd_momentum(w, dw, config=None):
    if config is None: config = {}
    config.setdefault('learning_rate', 1e-2)
    config.setdefault('momentum', 0.9)
    v = config.get('velocity', np.zeros_like(w))

    next_w = None
    v = config['momentum'] * v - config['learning_rate'] * dw
    next_w = w + v
    config['velocity'] = v

    return next_w, config


class LogisticClassifier(object):
  def __init__(self, input_dim=16+1, weight_scale=1e-3, reg=0.0):
    self.reg = reg
    self.W = np.random.normal(0, weight_scale, (input_dim,))
    # self.W = np.ones(input_dim) * weight_scale
  
  def loss(self, X, y=None):
    """ return score in test mode and (loss,grad) in train mode """
    sigmoid = lambda x : 1 / (1 + np.exp(-x))
    scores = sigmoid(X.dot(self.W))
    if y is None:
      return scores
    
    loss = - np.mean(y * np.log(scores) + (1-y) * np.log(1-scores))
    grad = (scores - y).dot(X) / y.size
    # use regularization
    l2 = lambda x : 0.5 * np.sum(x**2)
    loss += self.reg * l2(self.W)
    grad += self.reg * self.W
    return loss, grad
  

class Solver(object):
  def __init__(self, data, **kwargs):
    self.X_train = data['X_train']
    self.y_train = data['y_train']
    
    self.update_rule = kwargs.pop('update_rule', 'sgd')
    self.optim_config = kwargs.pop('optim_config', { 'learning_rate': 1e-3 })
    self.reg = kwargs.pop('reg', 0.0)
    self.batch_size = kwargs.pop('batch_size', 10)
    self.num_epochs = kwargs.pop('num_epochs', 30)
    self.verbose = kwargs.pop('verbose', False)
    self.num_classes = kwargs.pop('num_classes', 26)
    self.print_every = kwargs.pop('print_every', 200)
    self.normalize = kwargs.pop('normalize', False)
    
    # throw an error if there are extra keyword arguments
    if len(kwargs) > 0:
      extra = ', '.join('"%s"' % k for k in list(kwargs.keys()))
      raise ValueError('Unrecognized arguments %s' % extra)

    cur_module = sys.modules[__name__]
    if not getattr(cur_module, self.update_rule):
      raise ValueError('Invalid update_rule "%s"' % self.update_rule)
    self.update_rule = getattr(cur_module, self.update_rule)
    self._reset()

  def _reset(self):
    self.epoch = 0
    self.models = []
    for _ in range(self.num_classes):
      self.models.append(LogisticClassifier(reg=self.reg))
    # make a deep copy of the optim_config for each class
    self.optim_configs = [{k: v for k, v in self.optim_config.items()}] * self.num_classes
    # normalize training set
    self.normalize_config = {}
    if self.normalize:
      self.X_train = self._normalize_features(self.X_train)

  def _step(self, X_batch, y_batch, clazz):
    # compute loss and gradient
    loss, dw = self.models[clazz-1].loss(X_batch, y_batch)

    # perform parameter update
    next_w, next_config = self.update_rule(self.models[clazz-1].W, dw, self.optim_configs[clazz-1])
    self.models[clazz-1].W = next_w
    self.optim_configs[clazz-1] = next_config
    return loss

  def train(self, clazz):
    # preprocess input and labels
    X_train, y_train = add_ones(self.X_train), process_y_for_class(self.y_train, clazz)
    num_train = X_train.shape[0]
    for epoch in range(self.num_epochs):
      num_batch = num_train // self.batch_size
      for i in range(num_batch):
        batch_mask = range(i*self.batch_size, (i+1)*self.batch_size)
        X_batch = X_train[batch_mask, :]
        y_batch = y_train[batch_mask]
        loss = self._step(X_batch, y_batch, clazz)
        if self.verbose and i % self.print_every == 0:
          print('epoch %d, batch %d: loss %f for class %d' % (epoch+1, i, loss, clazz))

  def train_all(self):
    for clazz in range(1, self.num_classes+1):
      self.train(clazz)

  def check_accuracy(self, X, y, mode='test'):
    X_check = self._normalize_features(X, mode=mode) if self.normalize else np.copy(X)
    X_check = add_ones(X_check)
    y_pred_accumulate = None
    result = None
    for clazz in range(1, self.num_classes+1):
      y_check = process_y_for_class(y, clazz)
      y_pred_one = self.models[clazz-1].loss(X_check)
      y_pred_accumulate = np.hstack([y_pred_accumulate, y_pred_one.reshape(y_check.shape[0], 1)]) if y_pred_accumulate is not None else y_pred_one.reshape(y_check.shape[0], 1)
      result_one = self._get_test_result_one(y_check, y_pred_one)
      result = np.vstack([result, result_one]) if result is not None else result_one
    y_pred = np.argmax(y_pred_accumulate, axis=1) + 1
    acc = np.mean(y_pred == y)
    print('%s accuracy: %f with lr=%f, reg=%f, epoch=%d, batch_size=%d, normalize=%r' % (mode, acc, self.optim_config['learning_rate'], self.reg, self.num_epochs, self.batch_size, self.normalize))
    self._print_performance(result)
    return acc

  def _get_test_result_one(self, y, y_pred, threshold=0.3):
    # print
    # print(y_pred[y == 1])
    # print(y_pred[y == 0][:20])
    # print('========================================================')
    # set threshold
    y_pred_th = np.copy(y_pred)
    y_pred_th[y_pred_th >= threshold] = 1
    y_pred_th[y_pred_th < 1] = 0
    # print(y, y_pred_th)
    tp = np.logical_and(y==1, y_pred_th==1).sum()
    fp = np.logical_and(y==0, y_pred_th==1).sum()
    tn = np.logical_and(y==0, y_pred_th==0).sum()
    fn = np.logical_and(y==1, y_pred_th==0).sum()
    return np.array([tp, fp, tn, fn])

  def _print_performance(self, result):
    # print(result)
    micro_p = np.mean(result[:, 0]) / (np.mean(result[:, 0]) + np.mean(result[:, 1]))
    micro_r = np.mean(result[:, 0]) / (np.mean(result[:, 0]) + np.mean(result[:, 3]))
    micro_f1 = 2 * micro_p * micro_r / (micro_p + micro_r)
    macro_p = np.mean(result[:, 0] / (result[:, 0] + result[:, 1]))
    macro_r = np.mean(result[:, 0] / (result[:, 0] + result[:, 3]))
    macro_f1 = 2 * macro_p * macro_r / (macro_p + macro_r)
    print('micro_p: %.4f, micro_r: %.4f, micro_f1: %.4f, macro_p: %.4f, macro_r: %.4f, macro_f1: %.4f' % (micro_p, micro_r, micro_f1, macro_p, macro_r, macro_f1))

  def _normalize_features(self, X, mode='train'):
    if mode == 'train':
      mean = np.mean(X, axis=0)
      std = np.std(X, axis=0)
      self.normalize_config['mean'] = mean
      self.normalize_config['std'] = std
      return (X - mean) / std
    else:
      return (X - self.normalize_config['mean']) / self.normalize_config['std'] 


def run_validation(data):
  # set hyper-parameters
  learning_rate_list = [1e-5, 1e-3, 1e-2]
  reg_list = [0, 1e-5, 1e-3]
  num_epochs_list = [10, 30, 70, 100]
  batch_size_list = [10, 50, 100]
  normalize_list = [True, False]
  learning_rate_list = [1e-2]
  # reg_list = [1e-3]
  num_epochs_list = [100]
  batch_size_list = [10]
  normalize_list = [True]
  # initialization
  best_val_acc = 0.0
  best_param = None
  results = {}
  for lr, reg, num_epochs, batch_size, normalize in product(learning_rate_list, reg_list, num_epochs_list, batch_size_list, normalize_list):
    acc = run_test(data, lr, reg, num_epochs, batch_size, normalize, mode='val')
    results[(lr, reg, num_epochs, batch_size, normalize)] = acc
    if acc > best_val_acc:
      best_val_acc = acc
      best_param = (lr, reg, num_epochs, batch_size, normalize)
  print('best val accuracy: %f with lr=%f, reg=%f, num_epochs=%d, batch_size=%d, normalize=%r' % (best_val_acc, best_param[0], best_param[1], best_param[2], best_param[3], best_param[4]))
  for lr, reg, num_epochs, batch_size, normalize in sorted(results):
    val_acc = results[(lr, reg, num_epochs, batch_size, normalize)]
    print('val accuracy: %f with lr=%f, reg=%f, num_epochs=%d, batch_size=%d, normalize=%r' % (val_acc, lr, reg, num_epochs, batch_size, normalize))
  return best_param


def run_test(data, lr, reg, num_epochs, batch_size, normalize, mode='test', verbose=False):
  solver = Solver(data, reg=reg, update_rule='sgd', optim_config={'learning_rate': lr}, num_epochs=num_epochs, batch_size=batch_size, normalize=normalize, verbose=verbose)
  if verbose:
    print('training all classifiers...')
  solver.train_all()
  acc = solver.check_accuracy(data['X_%s' % mode], data['y_%s' % mode], mode=mode)
  return acc


if __name__ == "__main__":
  # test result recurrence
  data = load_data()
  run_test(data, lr=1e-2, reg=0, num_epochs=100, batch_size=10, normalize=True)
  
  # run validation to select parameters
  # data = load_data(val_prop=0.1)
  # run_validation(data)
