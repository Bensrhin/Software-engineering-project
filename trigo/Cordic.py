#!/usr/bin/env python3
import matplotlib.pyplot as plt
import numpy as np
from math import *
from pylab import *
import AtanValues

def Cordic2(x,y,z,mode,val):
    t=1.0
    for  i in range(32):
        if ((mode >=0 and y< mode) or( mode <0 and z>=0)):
            x1=x-(y*t)
            y=y+(x*t)
            z = z- AtanValues.Valeur(i)
        else:
            x1=x+(y*t)
            y=y-(x*t)
            z=z+ AtanValues.Valeur(i)
        x=x1
        t=t/2
    if (val == 1):
        return x
    if (val == 2):
        return y
    if (val == 3):
        return z
    else:
        return -9999.0

def atan(a):
    z=Cordic2(1,a,0,0,3)
    return z

def gain1Cordic():
    return Cordic2(1,0,0,-1,1)
    
def asin(a):
        x = 1/gain1Cordic()
        z=0
        f =1
        if (a<0):
            a = -a
            f=0
        z = Cordic2(x, 0, 0, a,3)
        if (f):
            z= -z
        return z
        
rad = np.linspace(-20,20)
atanList =[]
for i in rad:
    atanList.append(atan(i))

asinList= []
rad2= np.linspace(-0.98,0.98,100000)
for i in rad2:
    asinList.append(asin(i))
    
    

figure = plt.figure(figsize = (10, 10))
axes = figure.add_subplot(2, 2, 1)
axes.set_xlabel('x in rad')
axes.set_ylabel('arctan(x)')
axes.set_title('Représentation de arctan(x) avec Cordic')
plot(rad,atanList,color="red",label="atanCordic")
axes.legend(loc='upper left', bbox_to_anchor=(0,1),fancybox=True, shadow=True)


axes = figure.add_subplot(2, 2, 2)
axes.set_xlabel('x in rad')
axes.set_ylabel('arctan(x)')
axes.set_title('Représentation de arctan(x) avec Python')
plot(rad,np.arctan(rad),color="blue",label="atanPython")
axes.legend(loc='upper left', bbox_to_anchor=(0,1),fancybox=True, shadow=True)


axes = figure.add_subplot(2, 2, 3)
axes.set_xlabel('x in rad')
axes.set_ylabel('arcsin(x)')
axes.set_title('Représentation de arcSin(x) avec Cordic')
plot(rad2,asinList,color="red",label="asinCordic")
axes.legend(loc='upper left', bbox_to_anchor=(0,1),fancybox=True, shadow=True)

axes = figure.add_subplot(2, 2, 4)
axes.set_xlabel('x in rad')
axes.set_ylabel('arcsin(x)')
axes.set_title('Représentation de arcSin(x) avec Python')
plot(rad2,np.arcsin(rad2),color="blue",label="asinPython")
axes.legend(loc='upper left', bbox_to_anchor=(0,1),fancybox=True, shadow=True)
savefig("figuredivisee.pdf")

figure = plt.figure(figsize = (10, 10))
plt.gcf().subplots_adjust(left = 0.1, bottom = 0.1,
                       right = 0.9, top = 0.9, wspace = 0, hspace = 0.3)
axes = figure.add_subplot(2, 1, 1)
axes.set_xlabel('x in rad')
axes.set_ylabel('arctan(x)')
axes.set_title('Représentation de arctan(x)')
plot(rad,atanList,color="red",label="atanCordic")
plot(rad,np.arctan(rad),color="blue",label="atanPython")
axes.legend(loc='upper left', bbox_to_anchor=(0,1),fancybox=True, shadow=True)


axes = figure.add_subplot(2, 1, 2)
axes.set_xlabel('x in rad')
axes.set_ylabel('arcsin(x)')
axes.set_title('Représentation de arcSin(x)')
plot(rad2,asinList,color="red",label="asinCordic")
plot(rad2,np.arcsin(rad2),color="blue",label="asinPython")
axes.legend(loc='upper left', bbox_to_anchor=(0,1),fancybox=True, shadow=True)

plt.show()
savefig("figuretolal.pdf")
