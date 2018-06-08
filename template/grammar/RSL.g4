grammar RSL;

@header {
package io.ciera.template.rsl.parser;
}

sql_file:          ( comment )* ( insert_statement )+;

insert_statement:  'INSERT' 'INTO' table_name 'VALUES' LPAREN values RPAREN SEMI;

table_name:        ID;

values:            value |
                   value COMMA values
                   ;

value:             STRING | REAL | INTEGER | UUID;

comment:           COMMENT;

ID:                [a-zA-Z][a-zA-Z0-9_]*;
INTEGER:           '-'? [0-9]+;
REAL:              '-'? [0-9]+ '.' [0-9]+;
fragment HB:       [0-9a-f][0-9a-f];  // single hex byte
UUID:              '"' HB HB HB HB '-' HB HB '-' HB HB '-' HB HB '-' HB HB HB HB HB HB '"';
STRING:            '\'' ( ~( '\'' ) | '\'' '\'' )* '\'';
COMMENT:           '--' [^\n]* '\n';

LPAREN:            '(';
RPAREN:            ')';
SEMI:              ';';
COMMA:             ',';

WS:                [ \t\r\n]+ -> skip;