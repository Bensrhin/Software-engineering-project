#! /bin/sh

# Auteur : gl53
# Version initiale : 01/01/2020

# Ce genre d'approche est bien sûr généralisable, en conservant le
# résultat attendu dans un fichier pour chaque fichier source.
cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:./src/main/bin:"$PATH"

# On ne teste qu'un fichier. Avec une boucle for appropriée, on
# pourrait faire bien mieux ...
rm -f ./test-etapeC/fibo.ass 2>/dev/null
decac ./test-etapeC/fibo.deca || exit 1
if [ ! -f ./test-etapeC/fibo.deca ]; then
    echo "Fichier fibo.ass non généré."
    exit 1
fi

resultat=$(ima ./test-etapeC/fibo.ass) || exit 1
rm -f ./test-etapeC/fibo.ass

# On code en dur la valeur attendue.
attendu=13

if [ "$resultat" = "$attendu" ]; then
    echo "Tout va bien fibo(7) = 13"
else
    echo "Résultat inattendu de ima:"
    echo "$resultat"
    exit 1
fi
rm -f ./test-etapeC/fact.ass 2>/dev/null
decac ./test-etapeC/fact.deca || exit 1
if [ ! -f ./test-etapeC/fibo.deca ]; then
    echo "Fichier fact.ass non généré."
    exit 1
fi

resultat=$(ima ./test-etapeC/fact.ass) || exit 1
rm -f ./test-etapeC/fact.ass

# On code en dur la valeur attendue.
attendu=3628800

if [ "$resultat" = "$attendu" ]; then
    echo "Tout va bien fact(10) = 3628800"
else
    echo "Résultat inattendu de ima:"
    echo "$resultat"
    exit 1
fi
rm -f ./test-etapeC/exemplediapo.ass 2>/dev/null
decac ./test-etapeC/exemplediapo.deca || exit 1
if [ ! -f ./test-etapeC/exemplediapo.deca ]; then
    echo "Fichier exemplediapo.ass non généré."
    exit 1
fi

resultat=$(ima ./test-etapeC/exemplediapo.ass) || exit 1
rm -f ./test-etapeC/exemplediapo.ass

# On code en dur la valeur attendue.
attendu=-4

if [ "$resultat" = "$attendu" ]; then
    echo "Tout va bien pour exemplediapo"
else
    echo "Résultat inattendu de ima:"
    echo "$resultat"
    exit 1
fi
