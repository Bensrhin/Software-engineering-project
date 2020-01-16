#!/usr/bin/env python
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
    counter = 0;
    test_synt = "src/test/script/launchers/test_synt"
    valid_synt = "src/test/deca/syntax/valid"
    print("Details d'execution [1/0] ?")
    x = input()
    print("======================================================")
    #for file in os.listdir(valid_synt):
    for file in files(valid_synt):
        execute = test_synt + " " + valid_synt + "/" + str(file) + " " +"2> {}.log".format(str(file))
        if x == 1:
            os.system(execute)
        if x == 0:
            os.system(execute + "> synt.txt")
            os.system("rm synt.txt")
        if os.stat("{}.log".format(str(file))).st_size != 0:
            print(file+color.BOLD+color.WARNING+"  *** [Test FAILED UNEXPECTED] *** "+color.ENDC)
            counter+=1
        else:
            print(file+color.BOLD+color.HEADER+"  *** [TEST PASSED EXPECTED] *** "+color.ENDC)
        print("======================================================")
    return counter


def invalid_synt():
    counter = 0;
    test_synt = "src/test/script/launchers/test_synt"
    invalid_synt = "src/test/deca/syntax/invalid"
    print("Details d'execution [1/0] ?")
    x = input()
    print("~======================================================~")
    #for file in os.listdir(invalid_synt):
    for file in files (invalid_synt):
        execute = test_synt + " " + invalid_synt + "/" + str(file) + " " +"2> {}.log".format(str(file))
        #print(execute)
        if x == 1:
            os.system(execute)
            #os.open(format(str(file))+".log",os.O_RDWR)

        if x == 0:
            os.system(execute + "> synt.txt")
            os.system("rm synt.txt")
        if os.stat("{}.log".format(str(file))).st_size != 0:
            print(file+color.BOLD+color.HEADER +"  *** [Test FAILED EXPECTED] ***"+color.ENDC)   
        else:
            print(file+color.BOLD+color.WARNING+"  *** [TEST PASSED UNEXPECTED] ***"+color.ENDC)
            counter+=1
        print("~======================================================~")
    return counter



y = valid_synt()
if (y==0):
    print(color.BOLD+ color.OKGREEN+"     .-~-.-~-.-~[TEST VALID SUCCESS].-~-.-~-.-~"+color.ENDC) 
    print("~======================================================~")
if (y!=0):
    print(color.BOLD+ color.FAIL+"     .-~-.-~-.-~[TEST VALID ERROR].-~-.-~-.-~"+color.ENDC) 
    print("~========================================================================~")
x = invalid_synt()
if (x==0):
    print(color.BOLD+ color.OKGREEN+"     .-~-.-~-.-~[TEST INVALID SUCCESS].-~-.-~-.-~"+color.ENDC) 
    print("~======================================================~")
if (x!=0):
    print(color.BOLD+ color.FAIL+"     .-~-.-~-.-~[TEST INVALID ERROR].-~-.-~-.-~"+color.ENDC) 
    print("~========================================================================~")

os.system("rm *.log")

