lexer grammar RSLLexer;

// THE 'INITIAL' MODE IS FOR MATCHING SEQUENCES AT THE BEGINNING OF A LINE

COMMENT:                   [ \t]* '.' '/' '/'  ( ~( [\r\n] ) )+ ( '\r\n' | '\n' ) -> skip;
INITIAL_DOT:               [ \t]* '.' -> skip, pushMode(CONTROL);
INITIAL_BLOB:              ( [ \t]+ | [ \t]* ( ~( [$ \t\r\n.] ) | '$$' | '..' ) ( ~( [$\r\n] ) | '$$' )* ) -> type(BLOB), pushMode(BUFFER);
INITIAL_DOLLAR:            DOLLAR -> type(DOLLAR), pushMode(BUFFER), pushMode(SUBVAR);
INITIAL_NEWLINE:           NEWLINE -> type(NEWLINE);

// THE 'BUFFER' MODE IS FOR MATCHING LITERAL TEXT WITHIN A LINE
mode BUFFER;

DOLLAR:                    '$' -> pushMode(SUBVAR);
BLOB:                      ( ~( [$\r\n] ) | [ \t] | '$$' )+;
BUFFER_NEWLINE:            NEWLINE -> popMode, type(NEWLINE);

// THE 'STRING' MODE IS FOR MATCHING LITERAL TEXT WITHIN QUOTATION MARKS
mode STRING;

STRING_DOLLAR:             DOLLAR -> type(DOLLAR), pushMode(SUBVAR);
STRING_BLOB:               ( ~( [$\r\n"] ) | [ \t] | '$$' | '""' )+ -> type(BLOB);
STRING_QUOTE:              QUOTE -> type(QUOTE), popMode;

// THE 'SUBVAR' MODE IS FOR MATCHING FORMAT CHARACTERS IN A SUBSTITUTION
mode SUBVAR;

FORMAT:                    ( [ucl_rot] )+ ;
LCURLY:                    '{' -> pushMode(CONTROL);

// THE 'CONTROL' MODE IS FOR MATCHING RSL CONTROL STRUCTURES
mode CONTROL;

// literals
BOOLEAN_LITERAL:           'true' | 'false';
INTEGER_LITERAL:           '0' | '-'? [1-9][0-9]*;
REAL_LITERAL:              '-'? ( '0' | [1-9][0-9]* ) '.' [0-9]+;  // allows -0.0 but oh well

// keywords
IF:                        'if';
ELIF:                      'elif';
ELSE:                      'else';
END:                       'end';

// symbols
DOT:                       '.';
RCURLY:                    '}' -> popMode, popMode;
QUOTE:                     '"' -> pushMode(STRING);
EMPTY:                     'empty';
NOT_EMPTY:                 'not_empty';
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

WS:                        [ \t] -> skip;
