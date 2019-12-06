"""
无重复字符子集问题
Description

Mike is a lawyer with the gift of photographic memory. He is so good with it that he can tell you all the numbers on a sheet of paper by having a look at it without any mistake. Mike is also brilliant with subsets so he thought of giving a challange based on his skill and knowledge to Rachael. Mike knows how many subset are possible in an array of N integers. The subsets may or may not have the different sum. The challenge is to find the maximum sum produced by any subset under the condition:

The elements present in the subset should not have any digit in common.

Note: Subset {12, 36, 45} does not have any digit in common and Subset {12, 22, 35} have digits in common.Rachael find it difficult to win the challenge and is asking your help. Can youhelp her out in winning this challenge?


Input

First Line of the input consist of an integer T denoting the number of test cases. Then T test cases follow. Each test case consist of a numbe N denoting the length of the array. Second line of each test case consist of N space separated integers denoting the array elements.

Constraints:

1 <= T <= 100

1 <= N <= 100

1 <= array elements <= 100000


Output

Corresponding to each test case, print output in the new line.
"""


def encode(num):
    res = 0
    for s_digit in set(list(str(num))):
        res += 1 << int(s_digit)
    return res


def solve(nums):
    sum_map = {}
    for num in nums:
        encoded_num = encode(num)
        cur_map = {encoded_num: num}
        for k, v in sum_map.items():
            if k & encoded_num == 0:
                cur_map[k | encoded_num] = v + num
        for k, v in cur_map.items():
            if k not in sum_map.keys():
                sum_map[k] = v
            else:
                sum_map[k] = max(v, sum_map[k])
    print(max(list(sum_map.values())))


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        n = int(input())
        nums = [int(x) for x in input().split()]
        solve(nums)
