"""
插入排序
Description

实现插入排序。


Input

输入第一行为用例个数， 每个测试用例输入的每一行代表一个数组，其中的值用空格隔开，第一个值表示数组的长度。


Output

输出排序的数组，用空格隔开，末尾不要空格。
"""


def solve(nums):
    n = len(nums)
    for i in range(1, n):
        for j in range(i, 0, -1):
            if nums[j] < nums[j-1]:
                nums[j], nums[j-1] = nums[j-1], nums[j]
            else:
                break
    print(" ".join([str(x) for x in nums]))


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        nums = [int(x) for x in input().split()]
        solve(nums[1:])
