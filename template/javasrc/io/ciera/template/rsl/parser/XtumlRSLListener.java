package io.ciera.template.rsl.parser;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Stack;
import org.antlr.v4.runtime.ParserRuleContext;

public class XtumlRSLListener extends RSLBaseListener {

    private static int current_id = 1000;

    private Stack<Integer> parent_ids;
    private String filename;
    private PrintStream out;

    public XtumlRSLListener( String fn, OutputStream os ) {
        parent_ids = new Stack<>();
        filename = fn;
        out = new PrintStream( os );
    }
    
    @Override public void enterEveryRule( ParserRuleContext ctx ) {
        parent_ids.push( current_id++ );
    }

    @Override public void exitBody( RSLParser.BodyContext ctx ) {
        out.print( "INSERT INTO Y_Body VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "'%s'", filename );
        out.println( " );" );
        out.flush();
    }

    @Override public void exitLine( RSLParser.LineContext ctx) {
        out.print( "INSERT INTO Y_Line VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "%d", parent_ids.peek() );
        out.println( " );" );
    }

    @Override public void exitBuffer( RSLParser.BufferContext ctx ) {
        out.print( "INSERT INTO Y_Buffer VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "%d", parent_ids.peek() );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.NEWLINE() ? "" : ctx.NEWLINE().getText().replaceAll( "'", "''" ) );
        out.println( " );" );
    }

	@Override public void exitBuffer_element( RSLParser.Buffer_elementContext ctx ) {
        out.print( "INSERT INTO Y_BufferElement VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "%d", parent_ids.peek() );
        out.println( " );" );
	}

    @Override public void exitBlob( RSLParser.BlobContext ctx ) {
        out.print( "INSERT INTO Y_Blob VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "%d", parent_ids.peek() );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.BLOB() ? "" : ctx.BLOB().getText().replaceAll( "'", "''" ) );
        out.println( " );" );
    }

    @Override public void exitSubstitution_var( RSLParser.Substitution_varContext ctx ) {
        out.print( "INSERT INTO Y_SubstitutionVar VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "%d", parent_ids.peek() );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.DOLLAR() ? "" : ctx.DOLLAR().getText().replaceAll( "'", "''" ) );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.LCURLY() ? "" : ctx.LCURLY().getText().replaceAll( "'", "''" ) );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.RCURLY() ? "" : ctx.RCURLY().getText().replaceAll( "'", "''" ) );
        out.println( " );" );
    }

    @Override public void exitFormat_chars( RSLParser.Format_charsContext ctx ) {
        out.print( "INSERT INTO Y_FormatChars VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "%d", parent_ids.peek() );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.FORMAT() ? "" : ctx.FORMAT().getText().replaceAll( "'", "''" ) );
        out.println( " );" );
    }

    @Override public void exitIf_statement( RSLParser.If_statementContext ctx ) {
        out.print( "INSERT INTO Y_IfStatement VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "%d", parent_ids.peek() );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.IF() ? "" : ctx.IF().getText().replaceAll( "'", "''" ) );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.NEWLINE() ? "" : ctx.NEWLINE().getText().replaceAll( "'", "''" ) );
        out.println( " );" );
    }

    @Override public void exitElif_statement( RSLParser.Elif_statementContext ctx ) {
        out.print( "INSERT INTO Y_ElifStatement VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "%d", parent_ids.peek() );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.ELIF() ? "" : ctx.ELIF().getText().replaceAll( "'", "''" ) );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.NEWLINE() ? "" : ctx.NEWLINE().getText().replaceAll( "'", "''" ) );
        out.println( " );" );
    }

    @Override public void exitElse_statement( RSLParser.Else_statementContext ctx ) {
        out.print( "INSERT INTO Y_ElseStatement VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "%d", parent_ids.peek() );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.ELSE() ? "" : ctx.ELSE().getText().replaceAll( "'", "''" ) );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.NEWLINE() ? "" : ctx.NEWLINE().getText().replaceAll( "'", "''" ) );
        out.println( " );" );
    }

    @Override public void exitEndif_statement( RSLParser.Endif_statementContext ctx ) {
        out.print( "INSERT INTO Y_EndIfStatement VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "%d", parent_ids.peek() );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.END() ? "" : ctx.END().getText().replaceAll( "'", "''" ) );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.IF() ? "" : ctx.IF().getText().replaceAll( "'", "''" ) );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.NEWLINE() ? "" : ctx.NEWLINE().getText().replaceAll( "'", "''" ) );
        out.println( " );" );
    }

    @Override public void exitExpression( RSLParser.ExpressionContext ctx ) {
        out.print( "INSERT INTO Y_Expression VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "%d", parent_ids.peek() );
        out.println( " );" );
    }

    @Override public void exitDisjunction( RSLParser.DisjunctionContext ctx ) {
        out.print( "INSERT INTO Y_Disjunction VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "%d", parent_ids.peek() );
        out.println( " );" );
    }

	@Override public void exitDisjunction_operation( RSLParser.Disjunction_operationContext ctx ) {
        out.print( "INSERT INTO Y_DisjunctionOperation VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "%d", parent_ids.peek() );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.OR() ? "" : ctx.OR().getText().replaceAll( "'", "''" ) );
        out.println( " );" );
	}

    @Override public void exitConjunction( RSLParser.ConjunctionContext ctx ) {
        out.print( "INSERT INTO Y_Conjunction VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "%d", parent_ids.peek() );
        out.println( " );" );
    }

	@Override public void exitConjunction_operation( RSLParser.Conjunction_operationContext ctx ) {
        out.print( "INSERT INTO Y_ConjunctionOperation VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "%d", parent_ids.peek() );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.AND() ? "" : ctx.AND().getText().replaceAll( "'", "''" ) );
        out.println( " );" );
	}

    @Override public void exitComparison( RSLParser.ComparisonContext ctx ) {
        out.print( "INSERT INTO Y_Comparison VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "%d", parent_ids.peek() );
        out.println( " );" );
    }

	@Override public void exitComparison_operation( RSLParser.Comparison_operationContext ctx ) {
        out.print( "INSERT INTO Y_ComparisonOperation VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "%d", parent_ids.peek() );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.EQ() ? "" : ctx.EQ().getText().replaceAll( "'", "''" ) );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.NE() ? "" : ctx.NE().getText().replaceAll( "'", "''" ) );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.LT() ? "" : ctx.LT().getText().replaceAll( "'", "''" ) );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.GT() ? "" : ctx.GT().getText().replaceAll( "'", "''" ) );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.LTE() ? "" : ctx.LTE().getText().replaceAll( "'", "''" ) );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.GTE() ? "" : ctx.GTE().getText().replaceAll( "'", "''" ) );
        out.println( " );" );
	}

    @Override public void exitAddition( RSLParser.AdditionContext ctx ) {
        out.print( "INSERT INTO Y_Addition VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "%d", parent_ids.peek() );
        out.println( " );" );
    }

	@Override public void exitAddition_operation( RSLParser.Addition_operationContext ctx ) {
        out.print( "INSERT INTO Y_AdditionOperation VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "%d", parent_ids.peek() );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.PLUS() ? "" : ctx.PLUS().getText().replaceAll( "'", "''" ) );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.MINUS() ? "" : ctx.MINUS().getText().replaceAll( "'", "''" ) );
        out.println( " );" );
	}

    @Override public void exitMultiplication( RSLParser.MultiplicationContext ctx ) {
        out.print( "INSERT INTO Y_Multiplication VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "%d", parent_ids.peek() );
        out.println( " );" );
    }

	@Override public void exitMultiplication_operation( RSLParser.Multiplication_operationContext ctx ) {
        out.print( "INSERT INTO Y_MultiplicationOperation VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "%d", parent_ids.peek() );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.TIMES() ? "" : ctx.TIMES().getText().replaceAll( "'", "''" ) );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.DIVIDE() ? "" : ctx.DIVIDE().getText().replaceAll( "'", "''" ) );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.REM() ? "" : ctx.REM().getText().replaceAll( "'", "''" ) );
        out.println( " );" );
	}

    @Override public void exitNegation( RSLParser.NegationContext ctx ) {
        out.print( "INSERT INTO Y_Negation VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "%d", parent_ids.peek() );
        out.println( " );" );
    }

	@Override public void exitNegation_operation( RSLParser.Negation_operationContext ctx ) {
        out.print( "INSERT INTO Y_NegationOperation VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "%d", parent_ids.peek() );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.NOT() ? "" : ctx.NOT().getText().replaceAll( "'", "''" ) );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.MINUS() ? "" : ctx.MINUS().getText().replaceAll( "'", "''" ) );
        out.println( " );" );
	}

    @Override public void exitTerm( RSLParser.TermContext ctx ) {
        out.print( "INSERT INTO Y_Term VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "%d", parent_ids.peek() );
        out.println( " );" );
    }

    @Override public void exitParenthesis( RSLParser.ParenthesisContext ctx ) {
        out.print( "INSERT INTO Y_Parenthesis VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "%d", parent_ids.peek() );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.LPAREN() ? "" : ctx.LPAREN().getText().replaceAll( "'", "''" ) );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.RPAREN() ? "" : ctx.RPAREN().getText().replaceAll( "'", "''" ) );
        out.println( " );" );
    }

    @Override public void exitNamed_access( RSLParser.Named_accessContext ctx ) {
        out.print( "INSERT INTO Y_NamedAccess VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "%d", parent_ids.peek() );
        out.println( " );" );
    }

	@Override public void exitVariable_access( RSLParser.Variable_accessContext ctx ) {
        out.print( "INSERT INTO Y_VariableAccess VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "%d", parent_ids.peek() );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.ID() ? "" : ctx.ID().getText().replaceAll( "'", "''" ) );
        out.println( " );" );
	}

    @Override public void exitAttribute_access( RSLParser.Attribute_accessContext ctx ) {
        out.print( "INSERT INTO Y_AttributeAccess VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "%d", parent_ids.peek() );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.DOT() ? "" : ctx.DOT().getText().replaceAll( "'", "''" ) );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.ID() ? "" : ctx.ID().getText().replaceAll( "'", "''" ) );
        out.println( " );" );
    }

    @Override public void exitLiteral( RSLParser.LiteralContext ctx ) {
        out.print( "INSERT INTO Y_Literal VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "%d", parent_ids.peek() );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.BOOLEAN_LITERAL() ? "" : ctx.BOOLEAN_LITERAL().getText().replaceAll( "'", "''" ) );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.INTEGER_LITERAL() ? "" : ctx.INTEGER_LITERAL().getText().replaceAll( "'", "''" ) );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.REAL_LITERAL() ? "" : ctx.REAL_LITERAL().getText().replaceAll( "'", "''" ) );
        out.println( " );" );
    }

    @Override public void exitString_literal( RSLParser.String_literalContext ctx ) {
        out.print( "INSERT INTO Y_StringLiteral VALUES ( ");
        out.printf( "%d", parent_ids.pop() );
        out.print( ", " );
        out.printf( "%d", parent_ids.peek() );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.QUOTE(0) ? "" : ctx.QUOTE(0).getText().replaceAll( "'", "''" ) );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.BLOB() ? "" : ctx.BLOB().getText().replaceAll( "'", "''" ) );
        out.print( ", " );
        out.printf( "'%s'", null == ctx.QUOTE(1) ? "" : ctx.QUOTE(1).getText().replaceAll( "'", "''" ) );
        out.println( " );" );
    }

}
