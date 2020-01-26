#!/usr/bin/env python3
import os

class color:
    HEADER = '\033[95m'
    OKBLUE = '\033[94m'
    OKGREEN = '\033[92m'
    WARNING = '\033[93m'
    FAIL = '\033[91m'
    ENDC = '\033[0m'
    BOLD = '\033[1m'

def files(path):
    for file in os.listdir(path):
        if os.path.isfile(os.path.join(path, file)):
            yield file


def valid_context():
    counter = 0
    tmp = 0
    test_context = "launchers/test_context"
    valid_context = "../deca/context/valid/Objet"
    # print("Details d'execution [1/0] ?")
    # x = int(input())
    x = 0
    print("======================================================")

    for file in files(valid_context):
        tmp += 1
        execute = test_context + " " + valid_context + "/" + str(file) + " " +"2> {}.log".format(str(file))
        if x == 1:
            os.system(execute)
        if x == 0:
            os.system(execute + "> synt.txt")
            os.system("rm synt.txt")
        if os.stat("{}.log".format(str(file))).st_size != 0:
            print(file+color.BOLD+color.WARNING+"  *** [Test FAILED UNEXPECTED] *** "+color.ENDC)

        else:
            print(file+color.BOLD+color.HEADER+"  *** [TEST PASSED EXPECTED] *** "+color.ENDC)
            counter+=1
        print("======================================================")
    return counter, tmp


def invalid_context():
    counter = 0
    tmp = 0
    test_context = "launchers/test_context"
    invalid_context = "../deca/context/invalid/object"
    print("~======================================================~")
    for file in files(invalid_context):
        tmp += 1
        execute = test_context + " " + invalid_context + "/" + str(file) + " " +"2> {}.log".format(str(file))
        os.system(execute)
        if os.stat("{}.log".format(str(file))).st_size != 0:
            counter += 1
            fichier = open("{}.log".format(str(file)), "r")
            read = fichier.readlines()[0]
            if read[0] == '.':
                new_read = read[len(invalid_context) + 1:]
                print(new_read+color.BOLD+color.HEADER +"  *** [Test FAILED EXPECTED] ***"+color.ENDC)
            else:
                new_read = read
                print(file+": "+new_read+color.BOLD+color.HEADER +"  *** [Test FAILED EXPECTED] ***"+color.ENDC)

        else:
            print(file+color.BOLD+color.WARNING+"  *** [TEST PASSED UNEXPECTED] ***"+color.ENDC)

        print("~======================================================~")
    return counter, tmp


# print("Tester les valides ? [1/0]")
# val = int(input())
val = 1
if val == 1:
    y = valid_context()
    if (y[0] == y[1]):
        print(color.BOLD+ color.OKGREEN+"     .-~-.-~-.-~[{} TESTS VALID CONTEXT SUCCESS].-~-.-~-.-~".format(str(y[1]))+color.ENDC)
        print("~======================================================~")
    else:
        print(color.BOLD+ color.FAIL+"     .-~-.-~-.-~[{} TESTS VALID CONTEXT ERROR].-~-.-~-.-~".format(str(y[1] -y[0]))+color.ENDC)
        print("~========================================================================~")
# print("Tester les invalides ? [1/0]")
# inval = int(input())
inval = 1
if inval == 1:
    x = invalid_context()
    if (x[0] == x[1]):
        print(color.BOLD+ color.OKGREEN+"     .-~-.-~-.-~[{} TESTS INVALID CONTEXT SUCCESS].-~-.-~-.-~".format(str(x[1]))+color.ENDC)
        print("~======================================================~")
    else:
        print(color.BOLD+ color.FAIL+"     .-~-.-~-.-~[{} TESTS INVALID CONTEXT ERROR].-~-.-~-.-~".format(str(x[1] - x[0]))+color.ENDC)
        print("~========================================================================~")

os.system("rm *.log")
