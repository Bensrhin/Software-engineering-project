#!/usr/bin/env python
import os

while(1):
    print("Que Voulez-Vous Tester ? \n\n" +
    " [1 (Lexer)] \n [2 (Parser)] \n [3 (Context)] \n [4 (All)] \n [0 (Quitter)]\n")
    # x = int(input())
    try:
        x = int(input("Veuillez choisir un nombre entre 0 et 4: "))
    except SyntaxError:
        print("\nNot an integer! Try again.\n")
        continue
    except NameError:
        print("\nNot an integer! Try again.\n")
    else:
        if (x not in [0, 1, 2, 3, 4]):
            continue
        if x == 0:
            break
        if x == 1:
            os.system("./test_lexer.py")
        elif x == 2:
            os.system("./test_synt.py")
        elif x==3:
            os.system("./test_context.py")
        elif x==4:
            print("test_lexer")
            os.system("./test_lexer.py")
            print("test_synt")
            os.system("./test_synt.py")
            print("test_context")
            os.system("./test_context.py")
