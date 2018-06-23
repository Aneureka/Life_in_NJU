# coding: utf-8

from automaton.util import is_legal_char, replace_square_bracket


class RegExp(object):
    @staticmethod
    def simplify(re):
        re = re.replace("\\d", "(0|1|2|3|4|5|6|7|8|9)") \
               .replace("\\s", "( |\t|\n)")
        index = 0
        while index < len(re):
            if re[index] == "[" and (index == 0 or re[index-1] != "\\"):
                cur_seq = ""
                offset = 0
                while index+offset < len(re) and re[index+offset] != "]":
                    cur_seq += re[index+offset]
                    offset += 1
                cur_seq += "]"
                re = re.replace(cur_seq, replace_square_bracket(cur_seq), 1)
                index += offset
            index += 1
        return re

    @staticmethod
    def add_connector(re):
        """ Pre-processing: add connector '.' to regexp"""
        ops = ['·', '|', '(']
        complete_re = []
        skip = False
        for i in range(len(re)):
            if skip:
                skip = False
                continue
            complete_re.append(re[i])
            if re[i] == '\\':
                i += 1
                complete_re[-1] += re[i]
                skip = True
            if re[i] not in ops and i + 1 < len(re) and (
                            is_legal_char(re[i + 1]) or re[i + 1] == '(' or re[i + 1] == '\\'):
                complete_re.append('·')
        complete_re.append('#')
        # print("COMPLETE:\t", complete_re)
        return complete_re

    @staticmethod
    def get_alpha_list(re):
        return RegExp.add_connector(re)

    @staticmethod
    def to_postfix(re):
        ops = {'·': 6, '|': 7, '(': 10}
        complete_re = RegExp.add_connector(RegExp.simplify(re))
        postfix_re = []
        symbols = []
        skip = False
        for i in range(len(complete_re)):
            if skip:
                skip = False
                continue
            ch = complete_re[i]
            if is_legal_char(ch):
                postfix_re.append(ch)
            elif ch == '\\':
                postfix_re.append(ch)
                i += 1
                postfix_re.append(complete_re[i])
                skip = True
            elif ch in ['*', '+', '?']:
                postfix_re.append(ch)
            elif ch == ')':
                while symbols[-1] != '(':
                    postfix_re.append(symbols.pop())
                symbols.pop()
            elif ch == '#':
                while len(symbols) > 0:
                    postfix_re.append(symbols.pop())
            elif ch in ops.keys():
                while len(symbols) > 0 and symbols[-1] != '(' and ops[symbols[-1]] >= ops[ch]:
                    postfix_re.append(symbols.pop())
                symbols.append(ch)
            else:
                raise Exception("Not supported character: " + str(ch) + "#")
        # print("POSTFIX:\t", postfix_re)
        return postfix_re


if __name__ == '__main__':
    re = "[A-Za-z0-9_!\?\-\+\*\/ \t\n]"
    complete = RegExp.simplify(re)
    print(complete)
    posfix = RegExp.to_postfix(complete)
    print(posfix)
