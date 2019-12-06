"""
非递归快排
Description

快速排序的核心思想是使用元素的值对数组进行划分。实现其非递归方案。


Input

输入的每一行表示一个元素为正整数的数组，所有值用空格隔开，第一个值为数值长度，其余为数组元素值。


Output

输出的每一行为排序结果，用空格隔开，末尾不要空格。
"""


import sys


def quick_sort(nums, low, high):
    if high - low <= 1:
        return
    pivot = nums[low]
    left = [x for x in nums[low+1:high] if x <= pivot]
    right = [x for x in nums[low+1:high] if x > pivot]
    nums[low:high] = left + [pivot] + right
    pivot_index = len(left)
    quick_sort(nums, low, low + pivot_index)
    quick_sort(nums, low + pivot_index + 1, high)


def solve(nums):
    quick_sort(nums, 0, len(nums))
    print(" ".join([str(x) for x in nums]))


if __name__ == '__main__':
    while True:
        line = sys.stdin.readline()
        if not line:
            break
        nums = [int(x) for x in line.split()]
        solve(nums[1:])
