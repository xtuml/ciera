grammar SQL;

@header {
package io.ciera.runtime.instanceloading.sql.parser;
}

sql_file:          ( insert_statement )*;

insert_statement:  'INSERT' 'INTO' table_name 'VALUES' LPAREN values RPAREN SEMI;

table_name:        ID;

values:            value |
                   value COMMA values
                   ;

value:             STRING | REAL | INTEGER;

COMMENT:           '--' ~( '\n' )* '\n' -> skip;
ID:                [a-zA-Z][a-zA-Z0-9_]*;
INTEGER:           '-'? [0-9]+;
REAL:              '-'? [0-9]+ '.' [0-9]+;
STRING:            ( '\'' ( ~( '\'' ) | '\'' '\'' )* '\'' ) | ( '"' ( ~( '"' ) )* '"' );

LPAREN:            '(';
RPAREN:            ')';
SEMI:              ';';
COMMA:             ',';

WS:                [ \t\r\n]+ -> skip;
