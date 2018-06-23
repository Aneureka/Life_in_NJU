# coding: utf-8


def load_file(file):
    with open(file, 'r') as  f:
        res = f.read()
    return res


def save_to_file(file, tokens):
    with open(file, 'w') as f:
        for token in tokens:
            line = "<" + str(token[0]) + ", " + str(token[1]) + ">" + "\n"
            f.write(line)


