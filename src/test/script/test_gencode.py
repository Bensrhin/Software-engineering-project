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


def valid_gencode():
    counter = 0
    tmp = 0
    test_gencode = "../../main/bin/decac"
    valid_gencode = "../deca/codegen/valid"
    print("======================================================")
    for file in files(valid_gencode):
        if (str(file))[len(str(file))-5:] == ".deca":
            tmp += 1
            execute = test_gencode + " " + valid_gencode + "/" + str(file)
            os.system(execute)
            if os.path.isfile(str(file)[:-4]+"ass"):
                print("Fichier {}ass non généré".format(str(file)[:-4]))
                exit()
            resultat = os.system("ima {}".format(valid_gencode + "/"+str(file)[:-4])+"ass" + "> resultat.log")
            fichier = open("resultat.log", "r")
            resultat = fichier.readlines()[0]
            resultat = resultat[:len(resultat)-1]
            os.system("rm resultat.log")
            if str(file) == "Cast.deca":
                attendu= "3.60000e+00 3 3 3.00000e+00 28"
            elif str(file) == "castIntFloat.deca":
                attendu = "(int) 2.50000e+00 = 2"
            elif str(file) == "Mult.deca":
                attendu= "1.51250e+03"
            elif str(file) == "ExempleDiapo.deca":
                attendu= "-4"
            elif str(file) == "Factoriel.deca":
                attendu = "3628800"
            elif str(file) == "FibonacciSuite.deca":
                attendu = "13"
            elif str(file) == "IfElseExpression.deca":
                attendu = "x est inf à 4"
            elif str(file) == "sommeFloatInt.deca":
                attendu = "1.98000e+01"
            elif str(file) == "conditions.deca":
                attendu = "ok2"
            elif str(file) == "ifImb.deca":
                attendu = "x est impair"
            elif str(file) == "multIntFloat.deca":
                attendu = "7.87500e+02"
            elif str(file) == "moyenne.deca":
                attendu = "-3.07500e+01"
            elif str(file) == "NotTest.deca":
                attendu = "not ok"
            elif str(file) == "pgcd.deca":
                attendu = "54"
            elif str(file) == "pingPong.deca":
                attendu = "pingpongpingpongpingpongpingpong"
            elif str(file) == "puiss34.deca":
                attendu = "81"
            elif str(file) == "mult.deca":
                attendu = "720"
            elif str(file) == "RegMin.deca":
                attendu = "528 528"
            elif str(file) == "testAssign.deca":
                attendu = "1.50000e+00"
            elif str(file) == "testDivision0.deca":
                attendu = "Error :Division par 0"
            elif str(file) == "testOpBinaire.deca":
                attendu = "4950"
            elif str(file) == "testPrintx.deca":
                attendu = "0x1.6p+3"
            elif str(file) == "unaryMinus.deca":
                attendu = "-3"
            elif str(file) == "whileOr.deca":
                attendu = "ok"
            elif str(file) == "division.deca":
                attendu = "25"
            elif str(file) == "modulo.deca":
                attendu = "2"
            elif str(file) == "min.deca":
                attendu = "45"
            elif str(file) == "max.deca":
                attendu = "4.58000e+01"
            else:
                print("fichier pas encore traité")
                exit()


            if resultat==attendu:
                print(str(file)+color.BOLD+color.HEADER+"  *** [TEST PASSED] *** "+color.ENDC)
                counter+=1
                #print("Tout va bien pour " + str(file))
            else:
                print(str(file)+color.BOLD+color.WARNING+"  *** [Test FAILED] *** "+color.ENDC)
                #print("Tout ne pas va bien pour " + str(file))
                print("Le résultat obtenu :", resultat)
                print("Le résultat attendu :", attendu)
        print("======================================================")
    return counter,tmp

#os.system("rm " +  "../deca/codegen/valid" + "/"+ "*.ass")
x = valid_gencode()
if (x[0] == x[1]):
    print(color.BOLD+ color.OKGREEN+"     .-~-.-~-.-~[{} TESTS GENCODE SUCCESS].-~-.-~-.-~".format(str(x[1]))+color.ENDC)
    print("~======================================================~")
else:
    print(color.BOLD+ color.FAIL+"     .-~-.-~-.-~[{} TESTS GENCODE ERROR].-~-.-~-.-~".format(str(x[1] - x[0]))+color.ENDC)
    print("~========================================================================~")
os.system("rm " +  "../deca/codegen/valid" + "/"+ "*.ass")
