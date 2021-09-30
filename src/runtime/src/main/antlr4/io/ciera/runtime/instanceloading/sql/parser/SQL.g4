grammar SQL;

sql_file:          ( insert_statement | link_statement )*;

insert_statement:  'INSERT' 'INTO' table_name 'VALUES' LPAREN values RPAREN SEMI;

link_statement:    link rel_number 'IDS' LPAREN ids RPAREN SEMI;

link:              'LINK2' | 'LINK3';

table_name:        ID;

values:            value |
                   value COMMA values
                   ;

value:             STRING | REAL | INTEGER;

rel_number:        INTEGER;

ids:               id | id COMMA ids
                   ;

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
