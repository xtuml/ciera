grammar SQL;

@header {
package io.ciera.sql.parser;
}

insert_statement: 'insert' 'into' table_name 'values' LPAREN values RPAREN;

table_name: ID;

values: ID;

ID :         [a-z]+;
WS :         [ \t\r\n]+ -> skip;
LPAREN :     '(';
RPAREN :     ')';