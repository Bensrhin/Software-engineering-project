#!/usr/bin/env python
import os


print(" Que Voulez-Vous Tester ? [0 (Lexer) / 1 (Parser)] / [2 (Context)] / [3 (All), mais bon y'a bcp !!]")
x = int(input())
if x == 0:
    os.system("./test_lexer.py")
elif x == 1:
    os.system("./test_synt.py")
elif x==2:
    os.system("./test_context.py")
elif x==3:
    print("test_lexer")
    os.system("./test_lexer.py")
    print("test_synt")
    os.system("./test_synt.py")
    print("test_context")
    os.system("./test_context.py")



