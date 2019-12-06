"""
计数排序
Description

实现计数排序，通过多次遍历数组，统计比每一个元素小的其它元素个数，根据该统计量对数据进行排序。


Input

输入的每一行表示一个元素为正整数的数组，所有值用空格隔开，第一个值为数值长度，其余为数组元素值。


Output

输出的每一行为排序结果，用空格隔开，末尾不要空格。
"""

import sys


def solve(nums):
    n = len(nums)
    counts = []
    for i in range(n):
        cnt = 0
        for j in range(n):
            if nums[i] > nums[j]:
                cnt += 1
        counts.append(cnt)
    cnt_to_nums = {}
    for i, c in enumerate(counts):
        cnt_to_nums[c] = ([] if c not in cnt_to_nums.keys() else cnt_to_nums[c]) + [nums[i]]
    sorted_nums = []
    for cnt in sorted(list(cnt_to_nums.keys())):
        sorted_nums += cnt_to_nums[cnt]
    print(" ".join(str(x) for x in sorted_nums))


if __name__ == '__main__':
    while True:
        line = sys.stdin.readline()
        if not line:
            break
        nums = [int(x) for x in line.split()]
        solve(nums[1:])
