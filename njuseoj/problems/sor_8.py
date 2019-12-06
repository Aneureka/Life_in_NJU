"""
倒置个数
Description

有一个由N个实数构成的数组，如果一对元素A[i]和A[j]是倒序的，即i<j但是A[i]>A[j]则称它们是一个倒置，设计一个计算该数组中所有倒置数量的算法。要求算法复杂度为O(nlogn)


Input

输入有多行，第一行整数T表示为测试用例个数，后面是T个测试用例，每一个用例包括两行，第一行的一个整数是元素个数，第二行为用空格隔开的数组值。


Output

输出每一个用例的倒置个数，一行表示一个用例。
"""


def merge_sort_and_count(nums):
    n = len(nums)
    if n == 1:
        return nums, 0
    nums1, res1 = merge_sort_and_count(nums[:n//2])
    nums2, res2 = merge_sort_and_count(nums[n//2:])
    res = res1 + res2
    p, q = 0, 0
    new_nums = []
    while p < len(nums1) and q < len(nums2):
        if nums1[p] <= nums2[q]:
            new_nums.append(nums1[p])
            p += 1
        else:
            res += len(nums1) - p
            new_nums.append(nums2[q])
            q += 1
    new_nums += nums1[p:len(nums1)] + nums2[q:len(nums2)]
    return new_nums, res


def solve(nums):
    print(merge_sort_and_count(nums)[1])


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        n = int(input())
        nums = [int(x) for x in input().split()]
        solve(nums)
