#!/usr/bin/env python3
import random
recursive = 4
bool = ["false", "true"]
dec = ["1", "2", "3", "4", "5", "6", "7", "8", "9"]
deca = dec + ["0"]
flaot = ["0.6", "1.4", "2.5", "33.4", "40.1", "5.7", "6.9", "7.1", "8.1", "9.0"]
var = ["a", "b", "c", "d", "x", "y", "z", "x1", "x2", "x3", "x4", "x5"]
T = ["int",  "float", "boolean"]
string = ["Bonjour", "projetGL", "groupe 53", "test auto"]
arith = [" + ", " / ", " * ", " - "]
cmp = [" < ", " <= ", " > ", " >= ", " == ", " != "]
bl = [" && ", " || "]
usedVar = {}
notusedVar = {}
tVar = {}
for i in T:
    tVar[i] = []
code = ""

def excepting(tab1, tab2):
    tab = []
    for i in tab1:
        if i not in tab2:
            tab += [i]
    return tab
def double(t):
    ini = random.choice(dec)
    comma = ""
    inif = ""
    for _ in range(random.randint(1, 4)):
        if (t == "int"):
            ini += random.choice(deca)
        if (t == "float"):
            comma = "."
            ini += random.choice(deca)
            inif += random.choice(deca)
    return (ini + comma + inif)

doubles = [double("float") for _ in range(10)]
ints = [double("int") for _ in range(10)]
"""
/*****************************************************************/
"""
def opCmp(a, b):
    return ("(" + a + random.choice(cmp) + b + ")")
def opsCmp(Tab):
    s = opCmp(random.choice(Tab), random.choice(Tab))
    for _ in range(random.randint(0,4)):
        s += random.choice(bl) + opCmp(random.choice(Tab), random.choice(Tab))
    return s
"""
/*****************************************************************/
"""
def opArith(a, b, bool):
    add = []
    if bool : add = [" % "]
    return ("(" + a + random.choice(arith + add) + b + ")")
def opsAriths(Tab, bool):
    s = opArith(random.choice(Tab), random.choice(Tab), bool)
    for _ in range(random.randint(0, 4)):
        s += random.choice(arith) + opArith(random.choice(Tab), random.choice(Tab), bool)
    return s
"""
/*****************************************************************/
"""
def opBool(a, b):
    return ("(" + a + random.choice(bl) + b + ")")
def opsBools(Tab):
    tab1 = deca + ints + doubles + tVar["int"] + tVar["float"]
    s = random.choice(
                      [opBool(random.choice(Tab), random.choice(Tab)),
                      opCmp(random.choice(tab1), random.choice(tab1))] )
    for _ in range(random.randint(0, 4)):
        s += random.choice(bl) + random.choice(
                          [opBool(random.choice(Tab), random.choice(Tab)),
                          opCmp(random.choice(tab1), random.choice(tab1))] )
    return s


"""
/*****************************************************************/
"""
def Type():
     return random.choice(T)

def identifier(T):
    return random.choice(T)

def initialiser(t):
    add = tVar[t]
    if (t == "boolean"):
        return opsBools(bool + add)

    if (t == "int"):
        return opsAriths(deca + ints + add, True)
    elif (t == "float"):
        return opsAriths(deca + ints + doubles + add + tVar["int"], False)
    # return opsAriths(deca + ints)

def declVar():
    global code
    global tVar
    global usedVar
    t  = Type()
    id = identifier(excepting(var,list(usedVar.keys())))
    if (random.randint(0, 2)):
        init = initialiser(t)
        code += (t + " " + id + " = " + init + ";\n")
        tVar[t] += [id]
        usedVar[id] = t
    else:
        code += (t + " " + id + ";\n")
        tVar[t] += [id]
        notusedVar[id] = t
def listDeclVar():
    for _ in range(random.randint(3, len(excepting(var,list(usedVar.keys()))))):
        declVar()

def assign():

    id = identifier(list(notusedVar.keys()))
    rvalue = initialiser(notusedVar[id])
    usedVar[id] = notusedVar[id]
    del notusedVar[id]
    return (id + " = " + rvalue + ";\n")

def assigns():

    s = ""
    for _ in range(random.randint(0, len(notusedVar.keys()))):
        s +=  assign()
    return s

"""
/*****************************************************************/
"""
def ifthen(isElse):
    cond = initialiser("boolean")
    listinst = listInst(True)
    if (isElse):
        return ("{\n" + listinst + "}\n")
    return (" (" + cond + ")\n" + "{\n" + listinst + "}\n")

"""
/*****************************************************************/
"""
def ifthenelse():
    global recursive
    s = "if" + ifthen(False)
    for _ in range(random.randint(0, 3)):
        s += "else if" + ifthen(False)
    s += "else\n" + ifthen(True)
    recursive -= 1
    return s
"""
/*****************************************************************/
"""

def inst(identation):
    ident = ""
    if identation: ident = "\t"
    tab = [ident + "print(\"HELLO\")\n"]
    s = assigns()
    if s: tab.append(ident + s)
    s = ifthenelse()
    if (s!="") & (recursive!=0): tab.append(ident + s)
    return random.choice(tab)
def listInst(identation):
    s = ""
    for _ in range(random.randint(0, 4)):
        s += inst(identation)
    return s
"""
/*****************************************************************/
"""

listDeclVar()
code += ifthenelse()
print(code, tVar)
