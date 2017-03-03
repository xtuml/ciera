grammar SqlInsert;

@header {
package sqlinsert;
}
@annotations {
@SuppressWarnings("all")
}

sql_file:           ( insert_statement )+ EOF;

insert_statement:   'insert' 'into' table_name 'values' '(' 
                        data_value ( ',' data_value )*
                    ')' ';'
                    ;

table_name:         ID;

data_value:         STRING
                    | NUMBER
                    ;

ID:         ( 'a'..'z' | '_' )+;
NUMBER:     ('-')? ( '0'..'9' | '.' )+;
STRING:     '\'' ( '\'\'' | '\r' | '\n' | ~('\r' | '\'' | '\n') )* '\'';
SL_COMMENT: '--' (~'\n')* ('\n' ) -> skip; 
WS:         ( ( ' ' | '\t' ) | ( ('\r\n') | '\n' | '\r' ) )+ -> skip;
