"""
冒泡排序
Description

实现冒泡排序。


Input

输入的每一行表示一个元素为正整数的数组，所有值用空格隔开，第一个值为数值长度，其余为数组元素值。


Output

输出的每一行为排序结果，用空格隔开，末尾不要空格。
"""

import sys


def solve(nums):
    n = len(nums)
    for i in range(n, 0, -1):
        for j in range(1, i):
            if nums[j] < nums[j-1]:
                nums[j], nums[j-1] = nums[j-1], nums[j]
    print(" ".join([str(x) for x in nums]))


if __name__ == '__main__':
    while True:
        line = sys.stdin.readline()
        if not line:
            break
        nums = [int(x) for x in line.split()]
        solve(nums[1:])
