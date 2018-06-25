lexer grammar RSLLexer;

@header {
package io.ciera.template.rsl.parser;
}

fragment WS:               [ \t];

// THE 'INITIAL' MODE IS FOR MATCHING SEQUENCES AT THE BEGINNING OF A LINE

// keywords that occur at the beginning of a line
IF:                        WS* '.if' -> pushMode(CONTROL);
ELIF:                      WS* '.elif' -> pushMode(CONTROL);
ELSE:                      WS* '.else' -> pushMode(CONTROL);
END:                       WS* '.end' -> pushMode(CONTROL);

INITIAL_BLOB:              WS* ( ~( [$\r\n.] ) | '$$' | '..' ) ( ~( [$\r\n] ) | WS | '$$' )*  -> type(BLOB), pushMode(BUFFER);
INITIAL_DOLLAR:            DOLLAR -> type(DOLLAR), pushMode(BUFFER), pushMode(SUBVAR);
INITIAL_NEWLINE:           NEWLINE -> type(NEWLINE);

// THE 'BUFFER' MODE IS FOR MATCHING LITERAL TEXT WITHIN A LINE
mode BUFFER;

DOLLAR:                    '$' -> pushMode(SUBVAR);
BLOB:                      ( ~( [$\r\n] ) | WS | '$$' )+;
BUFFER_NEWLINE:            NEWLINE -> popMode, type(NEWLINE);

// THE 'STRING' MODE IS FOR MATCHING LITERAL TEXT WITHIN QUOTATION MARKS
mode STRING;

STRING_DOLLAR:             DOLLAR -> type(DOLLAR), pushMode(SUBVAR);
STRING_BLOB:               ( ~( [$\r\n"] ) | WS | '$$' | '""' )+ -> type(BLOB);
STRING_QUOTE:              QUOTE -> type(QUOTE), pushMode(STRING);

// THE 'SUBVAR' MODE IS FOR MATCHING FORMAT CHARACTERS IN A SUBSTITUTION
mode SUBVAR;

FORMAT:                    ( [ucl_ro] )+ ;
LCURLY:                    '{' -> pushMode(CONTROL);

// THE 'CONTROL' MODE IS FOR MATCHING RSL CONTROL STRUCTURES
mode CONTROL;

// literals
BOOLEAN_LITERAL:           'true' | 'false';
INTEGER_LITERAL:           '0' | '-'? [1-9][0-9]*;
REAL_LITERAL:              '-'? ( '0' | [1-9][0-9]* ) '.' [0-9]+;  // allows -0.0 but oh well

// other keyword
CONTROL_IF:                'if' -> type(IF);

// symbols
DOT:                       '.';
RCURLY:                    '}' -> popMode, popMode;
QUOTE:                     '"' -> pushMode(STRING);
NOT:                       'not';
MINUS:                     '-';
AND:                       'and';
OR:                        'or';
PLUS:                      '+';
TIMES:                     '*';
DIVIDE:                    '/';
REM:                       '%';
LT:                        '<';
LTE:                       '<=';
GT:                        '>';
GTE:                       '>=';
EQ:                        '==';
NE:                        '!=';
LPAREN:                    '(';
RPAREN:                    ')';
NEWLINE:                   ( '\r\n' | '\n' ) -> popMode;

ID:                        [a-zA-Z][a-zA-Z0-9_]* ;

CONTROL_WS:                WS -> skip;