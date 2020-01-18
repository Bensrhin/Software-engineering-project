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
            else:
                print("fichier pas encore traité")
                exit()


            if resultat==attendu:
                print("Tout va bien pour " + str(file))
            else:
                print("Tout ne pas va bien pour " + str(file))
                print("Le résultat obtenu :", resultat)
                print("Le résultat attendu :", attendu)

#os.system("rm " +  "../deca/codegen/valid" + "/"+ "*.ass")
valid_gencode()
os.system("rm " +  "../deca/codegen/valid" + "/"+ "*.ass")
