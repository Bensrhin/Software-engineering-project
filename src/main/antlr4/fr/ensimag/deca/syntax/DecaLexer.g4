lexer grammar DecaLexer;

options {
   language=Java;
   // Tell ANTLR to make the generated lexer class extend the
   // the named class, which is where any supporting code and
   // variables will be placed.
   superClass = AbstractDecaLexer;
}

@members {
}

//Identificateur
fragment LETTER : 'a' .. 'z'|'A' .. 'Z';
fragment DIGIT : '0' .. '9';
INDENT : (LETTER | '$' | '_')(LETTER | DIGIT | '$' | '_')*;

//Symbole Speciaux

PLUS : '+' ;
MINUS : '-' ;
TIMES : '*' ;
LESS : '<';
GREAT : '>';
EQUAL : '=';
DIVISION : '/';
MOD : '%';
LEQ : '<=';
GEQ : '>=';
SKIP : (' ' | '\n' | '\t' | '\r');

//Litteraux entiers
fragment POSITIVE_DIGITS : '1' .. '9';
INT : '0' | POSITIVE_DIGITS*;

//Litteraux Flottant
fragment HEXA_MAJ : 'A' .. 'F';
fragment HEXA_MIN : 'a' .. 'f';

NUM : DIGIT+;
SIGN : '+' | '-' | '';
EXP : ('E' | 'e') SIGN NUM;
DEC : NUM '.' NUM;
FLOATDEC : (DEC | DEC EXP) ('F' | 'f' | '');
DIGITHEX : DIGIT | HEXA_MAJ| HEXA_MIN;
NUMHEX : DIGITHEX+
FLOATHEX : ('0x' | '0X') NUMHEX '.' NUMHEX ('P' | 'p') SIGN NUM ('F' | 'f' | '');
FLOAT : FLOATDEC | FLOATHEX;

//Chaine de caracteres
STRING_CAR : (LETTER | DIGIT)~('"' | '\\' | '\n');
STRING : '"' (STRING_CAR | '\\"' | '\\\\')* '"';
MULTI_LINE_STRING = '"' (STRING_CAR | '\n' | '\\"' | '\\\\')* '"';

//Commentaires


//Separateurs
WS : SKIP {skip();};

//Inclusion de Fichier
FILENAME : (LETTER | DIGIT | '.' | '-' | '_')+
INCLUDE : '#include' ('')* '"' FILENAME '"';
