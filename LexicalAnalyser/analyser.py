# coding: utf-8

from automaton.fa import DFA
from automaton.regexp import RegExp
import regulation
import filehelper
import datetime

class Tokenizer(object):
    def __init__(self, res, src):
        self.dfas = dict()
        for (k, v) in res.items():
            self.dfas[DFA.from_re(RegExp.to_postfix(k))] = v
        self.src = src

    def tokenize(self):
        offset = 0
        file_size = len(self.src)
        tokens = []
        while offset < file_size:
            # find the first match
            ptr = 0
            cur_seq = ""
            cur_satisfied_tokens = []
            while len(cur_satisfied_tokens) == 0 and offset + ptr < file_size:
                cur_seq += self.src[offset + ptr]
                cur_satisfied_tokens = self._get_satisfied_tokens(cur_seq)
                ptr += 1
            # print("current seq:", cur_seq)
            # print(cur_satisfied_tokens)
            # find the longest match
            while offset + ptr < file_size:
                temp_seq = cur_seq + self.src[offset + ptr]
                new_satisfied_tokens = self._get_satisfied_tokens(temp_seq)
                # print(new_satisfied_tokens)
                if len(new_satisfied_tokens) > 0:
                    cur_satisfied_tokens = new_satisfied_tokens
                    cur_seq = temp_seq
                    ptr += 1
                else:
                    break
            if len(cur_satisfied_tokens) > 0:
                if cur_satisfied_tokens[0] != 'BLANK':
                    tokens.append([cur_satisfied_tokens[0], cur_seq])
                # print("tokens: " + str(tokens))
            offset += ptr
        return tokens

    def _get_satisfied_tokens(self, seq):
        res = []
        for (k, v) in self.dfas.items():
            if k.is_satisfied(seq):
                res.append(v)
        return res

if __name__ == '__main__':
    starttime = datetime.datetime.now()
    a = Tokenizer(regulation.get_re_and_token(), filehelper.load_file("input.txt"))
    endtime = datetime.datetime.now()
    print("Running time: " + str((endtime - starttime).seconds) + "s")
    print(a.tokenize())
    filehelper.save_to_file("output.txt", a.tokenize())
