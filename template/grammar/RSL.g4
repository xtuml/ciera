grammar RSL;

@header {
package io.ciera.template.rsl.parser;
}

options { tokenVocab=RSLLexer; }

body:              ( line )*;

line:              buffer NEWLINE |
                   if_statement |
                   elif_statement |
                   endif_statement;
                   
buffer:            /* nothing */ |
                   BLOB buffer |
                   substitution_var buffer;
                   
substitution_var:  DOLLAR format_chars LCURLY ID RCURLY;

format_chars:      /* nothing */ |
                   FORMAT;

if_statement:      INITIAL_DOT 'if' expression NEWLINE;

elif_statement:    INITIAL_DOT 'elif' expression NEWLINE;

endif_statement:   INITIAL_DOT 'end' 'if';


expression:        ID;