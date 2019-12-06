"""
按照要求保留数组元素使得和最大
Description

Given an array of N numbers, we need to maximize the sum of selected numbers. At each step, you need to select a number Ai, delete one occurrence of Ai-1 (if exists) and Ai each from the array. Repeat these steps until the array gets empty. The problem is to maximize the sum of selected numbers.


Input

The first line of the input contains T denoting the number of the test cases. For each test case, the first line contains an integer n denoting the size of the array. Next line contains n space separated integers denoting the elements of the array.

Constraints:1<=T<=100，1<=n<=50，1<=A[i]<=20

Note: Numbers need to be selected from maximum to minimum.


Output

For each test case, the output is an integer displaying the maximum sum of selected numbers.
"""


def solve(nums):
    n = len(nums)
    nums.sort()
    res = 0
    i = n - 1
    while i >= 0:
        res += nums[i]
        if i > 0 and nums[i] == nums[i-1] + 1:
            i -= 1
        i -= 1
    print(res)


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        n = int(input())
        nums = [int(x) for x in input().split()]
        solve(nums)
