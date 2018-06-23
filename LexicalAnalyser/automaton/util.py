# coding: utf-8

import string

"""
Provide some tool function.
"""


def is_legal_char(ch):
    trans = ["\\+", "\\-", "\\*", "\\/", "\\?", "\\(", "\\)", "\\[", "\\]", "\\{", "\\}", "\\|", "\\.", "\\^", "\\&", "\?", "\"", "\'", "\\n", "\\t"]
    delimeter = [" ", "\t", "\n"]
    other_symbols = ["!", "=", "%", ";", ",", ":", "~", "<", ">"]
    legal_symbols = trans + delimeter + other_symbols
    return str.isidentifier(ch) or str.isalnum(ch) or ch in legal_symbols


# eliminate the symbol "\"
def eliminate_slash(seq):
    if seq[0] == '\\':
        return seq[1:]
    return seq


def eliminate_slash_of_list(seqs):
    for i in range(len(seqs)):
        if seqs[i][0] == '\\':
            seqs[i] = seqs[i][1:]


def replace_square_bracket(re):
    to_dispose = re[1:len(re)-1]
    l = len(to_dispose)
    ctn = []
    index = 0
    while index < l:
        item = to_dispose[index]
        if item == "\\" and index+1 < l:
            item += to_dispose[index+1]
            index += 1
        elif index+2 < l and to_dispose[index+1] == "-":
            item += to_dispose[index+1] + to_dispose[index+2]
            index += 2
        index += 1
        ctn.append(item)
    chars = []
    for item in ctn:
        if "-" in item and "\\" not in item:
            for i in range(ord(item[0]), ord(item[-1])+1):
                chars.append(chr(i))
        else:
            chars.append(item)
    res = "|".join(chars)
    return "(" + res + ")"


def get_alphas_of_list(s):
    ctn = [ch for ch in s if is_legal_char(ch)]
    # eliminate_slash(ctn)
    return sorted(set(ctn))


if __name__ == '__main__':
    print(replace_square_bracket("[0-9h-z\d]"))
