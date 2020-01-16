#!/usr/bin/env python
import os

def main():
    print(" Que Voulez-Vous Tester ? [0 (Lexer) / 1 (Parser)]")
    x = int(input())
    if x == 0:
        os.system("./test_lexer.py")
    elif x == 1:
        os.system("./test_synt.py")


main()
