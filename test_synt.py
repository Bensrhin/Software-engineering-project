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


def valid_synt():
    counter = 0
    tmp = 0
    test_synt = "src/test/script/launchers/test_synt"
    valid_synt = "src/test/deca/syntax/valid"
    print("Details d'execution des tests valides [1/0] ?")
    x = int(input())
    print("======================================================")

    for file in files(valid_synt):
        tmp += 1
        execute = test_synt + " " + valid_synt + "/" + str(file) + " " +"2> {}.log".format(str(file))
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


def invalid_synt():
    counter = 0
    tmp = 0
    test_synt = "src/test/script/launchers/test_synt"
    invalid_synt = "src/test/deca/syntax/invalid"
    print("~======================================================~")
    #for file in os.listdir(invalid_synt):
    for file in files(invalid_synt):
        tmp += 1
        execute = test_synt + " " + invalid_synt + "/" + str(file) + " " +"2> {}.log".format(str(file))
        #print(execute)
        os.system(execute)
        #os.open(format(str(file))+".log",os.O_RDWR)
        if os.stat("{}.log".format(str(file))).st_size != 0:
            counter += 1
            fichier = open("{}.log".format(str(file)), "r")
            read = fichier.readlines()[0]
            if read[0] == 's':
                new_read = read[len(invalid_synt) + 1:]
                print(new_read+color.BOLD+color.HEADER +"  *** [Test FAILED EXPECTED] ***"+color.ENDC)

            else:
                new_read = read
                print(file+": "+new_read+color.BOLD+color.HEADER +"  *** [Test FAILED EXPECTED] ***"+color.ENDC)

        else:
            print(file+color.BOLD+color.WARNING+"  *** [TEST PASSED UNEXPECTED] ***"+color.ENDC)

        print("~======================================================~")
    return counter, tmp



y = valid_synt()
if (y[0] == y[1]):
    print(color.BOLD+ color.OKGREEN+"     .-~-.-~-.-~[{} TESTS VALID SUCCESS].-~-.-~-.-~".format(str(y[1]))+color.ENDC)
    print("~======================================================~")
else:
    print(color.BOLD+ color.FAIL+"     .-~-.-~-.-~[{} TESTS VALID ERROR].-~-.-~-.-~".format(str(y[1] -y[0]))+color.ENDC)
    print("~========================================================================~")
x = invalid_synt()
if (x[0] == x[1]):
    print(color.BOLD+ color.OKGREEN+"     .-~-.-~-.-~[{} TESTS INVALID SUCCESS].-~-.-~-.-~".format(str(x[1]))+color.ENDC)
    print("~======================================================~")
else:
    print(color.BOLD+ color.FAIL+"     .-~-.-~-.-~[{} TESTS INVALID ERROR].-~-.-~-.-~".format(str(x[1] - x[0]))+color.ENDC)
    print("~========================================================================~")
#
os.system("rm *.log")
