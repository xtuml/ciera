grammar SQL;

@header {
package io.ciera.runtime.instanceloading.sql.parser;
}

sql_file:          ( insert_statement | link2_statement | link3_statement )*;

insert_statement:  'INSERT' 'INTO' table_name 'VALUES' LPAREN values RPAREN SEMI;

link2_statement:   'LINK2' rel_number 'IDS' LPAREN id COMMA id RPAREN SEMI;

link3_statement:   'LINK3' rel_number 'IDS' LPAREN id COMMA id COMMA id RPAREN SEMI;

table_name:        ID;

values:            value |
                   value COMMA values
                   ;

value:             STRING | REAL | INTEGER;

rel_number:        INTEGER;

id:                STRING;

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
