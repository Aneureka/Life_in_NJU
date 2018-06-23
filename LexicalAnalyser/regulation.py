_regulations = {
    'KEYWORD': ["public", "private", "protected", "package", "abstract", "new", "assert", "default", "synchronized",
                "volatile", "if", "else", "do", "while", "for", "break", "continue", "switch", "case",
                "import", "throws", "throw", "instanceof", "return", "transient", "try", "catch", "finally", "final",
                "void", "int", "short", "char", "long", "boolean", "double", "byte", "class", "enum",
                "super", "this", "implements", "extends", "interface", "static", "const"],

    "OPERATOR": ["!", "\+\+", "\-\-", "\*", "\/", "%", "\+", "\-", "<<", ">>", "<=", "<", ">=", ">", "==", "!=",
                 "\&", "\|", "\&\&", "\|\|", "=", "\+=", "\-=", "\*=", "\/="],

    "DELIMITER": ["\(", "\)", "\[", "\]", "\{", "\}", ";", ",", "\.", "\?", ":", "~", "\^"],

    "BLANK": ["\s*"],

    "ID": ["[A-Za-z_][A-Za-z0-9_]*"],

    "STRING": ["\"[A-Za-z0-9_ :!\?\-\+\*\/]*\""],

    "CHAR": ["\'([A-Za-z0-9_!\?\-\+\*\/ ])\'"],

    "NUMBER": ["([0-9][0-9]*)|([0-9][0-9]*\.[0-9]*)"]

}


def get_re_and_token():
    rt = dict()
    for (token, res) in _regulations.items():
        for re in res:
            rt[re] = token
    return rt
