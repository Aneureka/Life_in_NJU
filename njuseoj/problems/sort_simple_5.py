"""
非递归合并排序
Description

合并（归并）排序的核心思想是：每次从中间位置将数组分组再分别排序。请实现其非递归方案。


Input

输入的每一行表示一个元素为正整数的数组，所有值用空格隔开，第一个值为数值长度，其余为数组元素值。


Output

输出的每一行为排序结果，用空格隔开，末尾不要空格。
"""


import sys


def merge_sort(nums, low, high):
    if high - low <= 1:
        return
    mid = low + (high - low) // 2
    merge_sort(nums, low, mid)
    merge_sort(nums, mid, high)
    sorted_nums = []
    p, q = low, mid
    while p < mid and q < high:
        if nums[p] < nums[q]:
            sorted_nums.append(nums[p])
            p += 1
        elif nums[p] > nums[q]:
            sorted_nums.append(nums[q])
            q += 1
        else:
            sorted_nums.append(nums[p])
            sorted_nums.append(nums[q])
            p += 1
            q += 1
    sorted_nums += nums[p:mid] + nums[q:high]
    nums[low:high] = sorted_nums


def solve(nums):
    merge_sort(nums, 0, len(nums))
    print(" ".join([str(x) for x in nums]))


if __name__ == '__main__':
    while True:
        line = sys.stdin.readline()
        if not line:
            break
        nums = [int(x) for x in line.split()]
        solve(nums[1:])
