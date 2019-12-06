"""
最小交换次数
Description

Given an array of N distinct elementsA[ ], find the minimum number of swaps required to sort the array.Your are required to complete the function which returns an integer denoting the minimum number of swaps, required to sort the array.


Input

The first line of input contains an integer T denoting the no of test cases . Then T test cases follow . Each test case contains an integer N denoting the no of element of the array A[ ]. In the next line are N space separated values of the array A[ ] .(1<=T<=100;1<=N<=100;1<=A[] <=1000)


Output

For each test case in a new line output will be an integer denoting minimum umber of swaps that are required to sort the array.
"""


def solve(nums):
    n = len(nums)
    indices = {}
    for i, num in enumerate(nums):
        indices[num] = i
    cnt = 0
    sorted_nums = sorted(nums)
    for i in range(n):
        if nums[i] != sorted_nums[i]:
            tmp = nums[i]
            to_swap_index = indices[sorted_nums[i]]
            nums[i] = nums[to_swap_index]
            nums[to_swap_index] = tmp
            indices[sorted_nums[i]] = i
            indices[tmp] = to_swap_index
            cnt += 1
    print(cnt)


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        n = int(input())
        nums = [int(x) for x in input().split()]
        solve(nums)
