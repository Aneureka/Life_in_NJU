"""
数组查询
Description

Given an array, the task is to complete the function which finds the maximum sum subarray, where you may remove at most one element to get the maximum sum.


Input

第一行为测试用例个数T；后面每两行表示一个用例，第一行为用例中数组长度N，第二行为数组具体内容。


Output

每一行表示对应用例的结果。
"""


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        n = int(input())
        nums = [int(x) for x in input().split()]
        res = 0
        for i in range(0, n):
            cur = 0
            cur_min_num = 0
            for j in range(i, n):
                cur += nums[j]
                cur_min_num = min(cur_min_num, nums[j])
                res = max(res, cur - cur_min_num)
        if res == 0:
            res = max(nums)
        print(res)
