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
fragment DIGIT : '0' .. '9';

fragment HEXA_MAJ : 'A' .. 'F';
fragment HEXA_MIN : 'a' .. 'f';
fragment POSITIVE_DIGITS : '1' .. '9';

LETTER : 'a' .. 'z'|'A' .. 'Z';

INT : '0' | POSITIVE_DIGITS*;
NUM : DIGIT+;
SIGN : '+' | '-' | '';
EXP : ('E' | 'e') SIGN NUM;

DEC : NUM '.' NUM;
FLOATDEC : (DEC | DEC EXP) ('F' | 'f' | '');
DIGITHEX : DIGIT | HEXA_MAJ| HEXA_MIN;
NUMHEX : DIGITHEX+
FLOATHEX : ('0x' | '0X') NUMHEX '.' NUMHEX ('P' | 'p') SIGN NUM ('F' | 'f' | '');
FLOAT : FLOATDEC | FLOATHEX;

STRING
