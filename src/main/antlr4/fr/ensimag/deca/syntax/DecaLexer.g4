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

//Reserved
ASM : 'asm';
INSTANCEOF : 'instanceof';
PRINTLN : 'println';
TRUE : 'true';
CLASS : 'class';
NEW : 'new';
PRINTLNX : 'printlnx';
WHILE : 'while';
EXTENDS : 'extends';
NULL : 'null';
PRINTX : 'printx';
ELSE : 'else';
READINT : 'readInt';
PROTECTED : 'protected';
FALSE : 'false';
READFLOAT : 'readFloat';
RETURN : 'return';
IF : 'if';
PRINT : 'print';
THIS : 'this';


// Mots reserves
//RESERV : ('asm'|'class'|'extends'|'else'|'false'|'if'|'instanceof'|'new'
//      |'null'|'readInt'|'readFloat'|'print'|'println'|'printlnx'
//    |'printx'|'protected'|'return'|'this'|'true'|'while');

//Identificateur
fragment LETTER : 'a' .. 'z'|'A' .. 'Z';
fragment DIGIT : '0' .. '9';
INDENT : (LETTER |'$' |'_')(LETTER |DIGIT |'$' |'_')*(~'aa');

//Symbole Speciaux
PLUS : '+' ;
MINUS : '-' ;
TIMES : '*' ;
SLASH : '/';
PERCENT : '%';

LEQ : '<=';
GEQ : '>=';
LT : '<';
GT : '>';
EQUALS : '=';
EQEQ : '==';
NEQ : '!=';

DOT : '.';
COMMA : ',';

OPARENT : '(';
CPARENT : ')';
OBRACE : '{';
CBRACE : '}';

OR : '||';
AND : '&&';

EOL : '\n';
TAB : '\t';
//EMPTY :

SKIPCAR : (' ' |EOL |TAB |'\r');

//Litteraux entiers
fragment POSITIVE_DIGITS : '1' .. '9';
INT : '0' | POSITIVE_DIGITS*;

//Litteraux Flottant
fragment HEXA_MAJ : 'A' .. 'F';
fragment HEXA_MIN : 'a' .. 'f';

NUM : DIGIT+;
SIGN : ('+' |'-')?;
EXP : ('E' | 'e') SIGN NUM;
DEC : NUM '.' NUM;
FLOATDEC : (DEC | DEC EXP) (('F' | 'f')?);
DIGITHEX : DIGIT | HEXA_MAJ| HEXA_MIN;
NUMHEX : DIGITHEX+;
FLOATHEX : ('0x' | '0X') NUMHEX '.' NUMHEX ('P' | 'p') SIGN NUM (('F' | 'f')?);
FLOAT : FLOATDEC | FLOATHEX;

//Chaine de caracteres
STRING_CAR : ~('"' | '\\' | '\n');
STRING : '"' (STRING_CAR | '\\"' | '\\\\')* '"';
MULTI_LINE_STRING : '"' (STRING_CAR | EOL | '\\"' | '\\\\')* '"';
//
//Commentaires
COMMENT : ('/*' .*? '*/') | ('//' .*? ('\n' | EOF));

//Separateurs
WS : (SKIPCAR | COMMENT) {skip();};

//Inclusion de Fichier
FILENAME : (LETTER | DIGIT | '.' | '-' | '_')+;
INCLUDE : '#include' (' ')* '"' FILENAME '"';
