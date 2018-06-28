grammar RSL;

@header {
package io.ciera.template.rsl.parser;
}

options { tokenVocab=RSLLexer; }

body:                        ( line )*;

line:                        buffer |
                             if_statement |
                             elif_statement |
                             else_statement |
                             endif_statement;
                   
buffer:                      ( buffer_element )* NEWLINE;

buffer_element:              blob | substitution_var;

blob:                        BLOB;
                   
substitution_var:            DOLLAR format_chars LCURLY named_access RCURLY;

format_chars:                /* nothing */ |
                             FORMAT;

if_statement:                IF ( LPAREN expression RPAREN | expression ) NEWLINE;

elif_statement:              ELIF ( LPAREN expression RPAREN | expression ) NEWLINE;

else_statement:              ELSE NEWLINE;

endif_statement:             END IF NEWLINE;

expression:                  disjunction;

disjunction:                 conjunction ( disjunction_operation )*;

disjunction_operation:       OR conjunction;

conjunction:                 comparison ( conjunction_operation )*;

conjunction_operation:       AND comparison;

comparison:                  addition ( comparison_operation )*;

comparison_operation:        ( EQ | NE | LT | GT | LTE | GTE ) addition;
                   
addition:                    multiplication ( addition_operation )*;

addition_operation:          ( PLUS | MINUS ) multiplication;

multiplication:              negation ( multiplication_operation )*;

multiplication_operation:    ( TIMES | DIVIDE | REM ) negation;

negation:                    ( negation_operation )? term;

negation_operation:          NOT | MINUS;

term:                        parenthesis |
                             named_access;
                   
parenthesis:                 LPAREN expression RPAREN;

named_access:                variable_access ( attribute_access )?;
                   
variable_access:             ID;

attribute_access:            DOT ID ( attribute_access )?;
