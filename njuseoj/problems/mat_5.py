"""
数字重组整除问题
Description

Babul’s favourite number is 17. He likes the numbers which are divisible by 17. This time what he does is that he takes a number N and tries to find the largest number which is divisible by 17, by rearranging the digits. As the number increases he gets puzzled with his own task. So you as a programmer have to help him to accomplish his task.Note: If the number is not divisible by rearranging the digits, then print “Not Possible”. N may have leading zeros.


Input

The first line of input contains an integer T denoting the no of test cases. Each of the next T lines contains the number N.


Output

For each test case in a new line print the desired output.
"""


def permute(num_list, num_str, res):
    if len(num_list) == 0:
        if int(num_str) % 17 == 0:
            res.append(int(num_str))
        return
    for i, num in enumerate(num_list):
        num_str += str(num)
        num_list.pop(i)
        permute(num_list, num_str, res)
        num_str = num_str[:-1]
        num_list.insert(0, num)


def solve(num):
    num_list = [int(x) for x in list(str(num))]
    res = []
    permute(num_list, "", res)
    if len(res) > 0 and max(res) > 0:
        print(max(res))
    else:
        print("Not Possible")


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        num = int(input())
        solve(num)
