package io.ciera.tool.templateengine.parser;

import java.util.Stack;

import org.antlr.v4.runtime.ParserRuleContext;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.UniqueId;
import io.ciera.tool.TemplateEngine;
import io.ciera.tool.templateengine.parser.RSLParser.Empty_checkContext;
import io.ciera.tool.templateengine.parser.RSLParser.Empty_check_operationContext;
import io.ciera.tool.templateengine.rsl.Y_Addition;
import io.ciera.tool.templateengine.rsl.Y_AdditionOperation;
import io.ciera.tool.templateengine.rsl.Y_AttributeAccess;
import io.ciera.tool.templateengine.rsl.Y_Blob;
import io.ciera.tool.templateengine.rsl.Y_Body;
import io.ciera.tool.templateengine.rsl.Y_Buffer;
import io.ciera.tool.templateengine.rsl.Y_BufferElement;
import io.ciera.tool.templateengine.rsl.Y_Comparison;
import io.ciera.tool.templateengine.rsl.Y_ComparisonOperation;
import io.ciera.tool.templateengine.rsl.Y_Conjunction;
import io.ciera.tool.templateengine.rsl.Y_ConjunctionOperation;
import io.ciera.tool.templateengine.rsl.Y_Disjunction;
import io.ciera.tool.templateengine.rsl.Y_DisjunctionOperation;
import io.ciera.tool.templateengine.rsl.Y_ElifStatement;
import io.ciera.tool.templateengine.rsl.Y_ElseStatement;
import io.ciera.tool.templateengine.rsl.Y_EmptyCheck;
import io.ciera.tool.templateengine.rsl.Y_EmptyCheckOperation;
import io.ciera.tool.templateengine.rsl.Y_EndIfStatement;
import io.ciera.tool.templateengine.rsl.Y_Expression;
import io.ciera.tool.templateengine.rsl.Y_FormatChars;
import io.ciera.tool.templateengine.rsl.Y_IfStatement;
import io.ciera.tool.templateengine.rsl.Y_Line;
import io.ciera.tool.templateengine.rsl.Y_Literal;
import io.ciera.tool.templateengine.rsl.Y_Multiplication;
import io.ciera.tool.templateengine.rsl.Y_MultiplicationOperation;
import io.ciera.tool.templateengine.rsl.Y_NamedAccess;
import io.ciera.tool.templateengine.rsl.Y_Negation;
import io.ciera.tool.templateengine.rsl.Y_NegationOperation;
import io.ciera.tool.templateengine.rsl.Y_Parenthesis;
import io.ciera.tool.templateengine.rsl.Y_StringLiteral;
import io.ciera.tool.templateengine.rsl.Y_SubstitutionVar;
import io.ciera.tool.templateengine.rsl.Y_Term;
import io.ciera.tool.templateengine.rsl.Y_VariableAccess;
import io.ciera.tool.templateengine.rsl.impl.Y_AdditionImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_AdditionOperationImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_AttributeAccessImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_BlobImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_BodyImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_BufferElementImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_BufferImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_ComparisonImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_ComparisonOperationImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_ConjunctionImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_ConjunctionOperationImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_DisjunctionImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_DisjunctionOperationImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_ElifStatementImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_ElseStatementImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_EmptyCheckImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_EmptyCheckOperationImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_EndIfStatementImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_ExpressionImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_FormatCharsImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_IfStatementImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_LineImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_LiteralImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_MultiplicationImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_MultiplicationOperationImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_NamedAccessImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_NegationImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_NegationOperationImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_ParenthesisImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_StringLiteralImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_SubstitutionVarImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_TermImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_VariableAccessImpl;

public class RSLPopulator extends RSLBaseListener {

    private static int currentId = 1000;

    private String filename;
    protected Stack<Integer> parentIds;
    protected TemplateEngine population;

    public RSLPopulator(String fn, TemplateEngine population) {
        parentIds = new Stack<>();
        filename = fn;
        this.population = population;
    }
    
    protected void incrementId() {
        parentIds.push(currentId++);
    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        incrementId();
    }

    @Override
    public void exitBody(RSLParser.BodyContext ctx) {
        try {
            Y_Body body = Y_BodyImpl.create(population, UniqueId.random(), parentIds.pop(), filename);
            for (Y_Line line : population.Y_Line_instances()
                    .where((selected) -> selected.getParent_node_id() == body.getNode_id()))
                population.relate_R3054_Y_Line_Y_Body(line, body);
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

    @Override
    public void exitLine(RSLParser.LineContext ctx) {
        try {
            Y_Line line = Y_LineImpl.create(population, UniqueId.random(), parentIds.pop(), parentIds.peek());
            for (Y_Buffer buffer : population.Y_Buffer_instances()
                    .where((selected) -> selected.getParent_node_id() == line.getNode_id()))
                population.relate_R3055_Y_Buffer_is_a_Y_Line(buffer, line);
            for (Y_IfStatement if_statement : population.Y_IfStatement_instances()
                    .where((selected) -> selected.getParent_node_id() == line.getNode_id()))
                population.relate_R3055_Y_IfStatement_is_a_Y_Line(if_statement, line);
            for (Y_ElifStatement elif_statement : population.Y_ElifStatement_instances()
                    .where((selected) -> selected.getParent_node_id() == line.getNode_id()))
                population.relate_R3055_Y_ElifStatement_is_a_Y_Line(elif_statement, line);
            for (Y_ElseStatement else_statement : population.Y_ElseStatement_instances()
                    .where((selected) -> selected.getParent_node_id() == line.getNode_id()))
                population.relate_R3055_Y_ElseStatement_is_a_Y_Line(else_statement, line);
            for (Y_EndIfStatement endif_statement : population.Y_EndIfStatement_instances()
                    .where((selected) -> selected.getParent_node_id() == line.getNode_id()))
                population.relate_R3055_Y_EndIfStatement_is_a_Y_Line(endif_statement, line);
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

    @Override
    public void exitBuffer(RSLParser.BufferContext ctx) {
        try {
            Y_Buffer buffer = Y_BufferImpl.create(population, UniqueId.random(), parentIds.pop(), parentIds.peek(),
                    null == ctx.NEWLINE() ? "" : ctx.NEWLINE().getText());
            for (Y_BufferElement buffer_element : population.Y_BufferElement_instances()
                    .where((selected) -> selected.getParent_node_id() == buffer.getNode_id()))
                population.relate_R3056_Y_BufferElement_Y_Buffer(buffer_element, buffer);
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

    @Override
    public void exitBuffer_element(RSLParser.Buffer_elementContext ctx) {
        try {
            Y_BufferElement buffer_element = Y_BufferElementImpl.create(population, UniqueId.random(), parentIds.pop(), parentIds.peek());
            for (Y_Blob blob : population.Y_Blob_instances()
                    .where((selected) -> selected.getParent_node_id() == buffer_element.getNode_id()))
                population.relate_R3057_Y_Blob_is_a_Y_BufferElement(blob, buffer_element);
            for (Y_SubstitutionVar substitution_var : population.Y_SubstitutionVar_instances()
                    .where((selected) -> selected.getParent_node_id() == buffer_element.getNode_id()))
                population.relate_R3057_Y_SubstitutionVar_is_a_Y_BufferElement(substitution_var, buffer_element);
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

    @Override
    public void exitBlob(RSLParser.BlobContext ctx) {
        try {
            Y_BlobImpl.create(population, UniqueId.random(), parentIds.pop(), parentIds.peek(),
                    null == ctx.BLOB() ? "" : ctx.BLOB().getText());
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

    @Override
    public void exitSubstitution_var(RSLParser.Substitution_varContext ctx) {
        try {
            Y_SubstitutionVar substitution_var = Y_SubstitutionVarImpl.create(population, UniqueId.random(), parentIds.pop(),
                    parentIds.peek(), null == ctx.DOLLAR() ? "" : ctx.DOLLAR().getText(),
                    null == ctx.LCURLY() ? "" : ctx.LCURLY().getText(),
                    null == ctx.RCURLY() ? "" : ctx.RCURLY().getText());
            for (Y_FormatChars format_chars : population.Y_FormatChars_instances()
                    .where((selected) -> selected.getParent_node_id() == substitution_var.getNode_id()))
                population.relate_R3058_Y_FormatChars_Y_SubstitutionVar(format_chars, substitution_var);
            for (Y_NamedAccess named_access : population.Y_NamedAccess_instances()
                    .where((selected) -> selected.getParent_node_id() == substitution_var.getNode_id()))
                population.relate_R3085_Y_NamedAccess_Y_SubstitutionVar(named_access, substitution_var);
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

    @Override
    public void exitFormat_chars(RSLParser.Format_charsContext ctx) {
        try {
            Y_FormatCharsImpl.create(population, UniqueId.random(), parentIds.pop(), parentIds.peek(),
                    null == ctx.FORMAT() ? "" : ctx.FORMAT().getText());
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

    @Override
    public void exitIf_statement(RSLParser.If_statementContext ctx) {
        try {
            Y_IfStatement if_statement = Y_IfStatementImpl.create(population, UniqueId.random(), parentIds.pop(), parentIds.peek(),
                    null == ctx.IF() ? "" : ctx.IF().getText(), null == ctx.NEWLINE() ? "" : ctx.NEWLINE().getText());
            for (Y_Expression expression : population.Y_Expression_instances()
                    .where((selected) -> selected.getParent_node_id() == if_statement.getNode_id()))
                population.relate_R3059_Y_Expression_Y_IfStatement(expression, if_statement);
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

    @Override
    public void exitElif_statement(RSLParser.Elif_statementContext ctx) {
        try {
            Y_ElifStatement elif_statement = Y_ElifStatementImpl.create(population, UniqueId.random(), parentIds.pop(), parentIds.peek(),
                    null == ctx.ELIF() ? "" : ctx.ELIF().getText(),
                    null == ctx.NEWLINE() ? "" : ctx.NEWLINE().getText());
            for (Y_Expression expression : population.Y_Expression_instances()
                    .where((selected) -> selected.getParent_node_id() == elif_statement.getNode_id()))
                population.relate_R3060_Y_Expression_Y_ElifStatement(expression, elif_statement);
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

    @Override
    public void exitElse_statement(RSLParser.Else_statementContext ctx) {
        try {
            Y_ElseStatementImpl.create(population, UniqueId.random(), parentIds.pop(), parentIds.peek(),
                    null == ctx.ELSE() ? "" : ctx.ELSE().getText(),
                    null == ctx.NEWLINE() ? "" : ctx.NEWLINE().getText());
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

    @Override
    public void exitEndif_statement(RSLParser.Endif_statementContext ctx) {
        try {
            Y_EndIfStatementImpl.create(population, UniqueId.random(), parentIds.pop(), parentIds.peek(),
                    null == ctx.END() ? "" : ctx.END().getText(), null == ctx.IF() ? "" : ctx.IF().getText(),
                    null == ctx.NEWLINE() ? "" : ctx.NEWLINE().getText());
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

    @Override
    public void exitExpression(RSLParser.ExpressionContext ctx) {
        try {
            Y_Expression expression = Y_ExpressionImpl.create(population, UniqueId.random(), parentIds.pop(), parentIds.peek());
            for (Y_Disjunction disjunction : population.Y_Disjunction_instances()
                    .where((selected) -> selected.getParent_node_id() == expression.getNode_id()))
                population.relate_R3061_Y_Disjunction_Y_Expression(disjunction, expression);
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

    @Override
    public void exitDisjunction(RSLParser.DisjunctionContext ctx) {
        try {
            Y_Disjunction disjunction = Y_DisjunctionImpl.create(population, UniqueId.random(), parentIds.pop(), parentIds.peek());
            for (Y_DisjunctionOperation disjunction_operation : population.Y_DisjunctionOperation_instances()
                    .where((selected) -> selected.getParent_node_id() == disjunction.getNode_id()))
                population.relate_R3068_Y_DisjunctionOperation_Y_Disjunction(disjunction_operation, disjunction);
            for (Y_Conjunction conjunction : population.Y_Conjunction_instances()
                    .where((selected) -> selected.getParent_node_id() == disjunction.getNode_id()))
                population.relate_R3062_Y_Conjunction_Y_Disjunction(conjunction, disjunction);
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

    @Override
    public void exitDisjunction_operation(RSLParser.Disjunction_operationContext ctx) {
        try {
            Y_DisjunctionOperation disjunction_operation = Y_DisjunctionOperationImpl.create(population, UniqueId.random(),
                    parentIds.pop(), parentIds.peek(), null == ctx.OR() ? "" : ctx.OR().getText());
            for (Y_Conjunction conjunction : population.Y_Conjunction_instances()
                    .where((selected) -> selected.getParent_node_id() == disjunction_operation.getNode_id()))
                population.relate_R3069_Y_Conjunction_Y_DisjunctionOperation(conjunction, disjunction_operation);
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

    @Override
    public void exitConjunction(RSLParser.ConjunctionContext ctx) {
        try {
            Y_Conjunction conjunction = Y_ConjunctionImpl.create(population, UniqueId.random(), parentIds.pop(), parentIds.peek());
            for (Y_ConjunctionOperation conjunction_operation : population.Y_ConjunctionOperation_instances()
                    .where((selected) -> selected.getParent_node_id() == conjunction.getNode_id()))
                population.relate_R3070_Y_ConjunctionOperation_Y_Conjunction(conjunction_operation, conjunction);
            for (Y_Comparison comparison : population.Y_Comparison_instances()
                    .where((selected) -> selected.getParent_node_id() == conjunction.getNode_id()))
                population.relate_R3063_Y_Comparison_Y_Conjunction(comparison, conjunction);
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

    @Override
    public void exitConjunction_operation(RSLParser.Conjunction_operationContext ctx) {
        try {
            Y_ConjunctionOperation conjunction_operation = Y_ConjunctionOperationImpl.create(population, UniqueId.random(),
                    parentIds.pop(), parentIds.peek(), null == ctx.AND() ? "" : ctx.AND().getText());
            for (Y_Comparison comparison : population.Y_Comparison_instances()
                    .where((selected) -> selected.getParent_node_id() == conjunction_operation.getNode_id()))
                population.relate_R3071_Y_Comparison_Y_ConjunctionOperation(comparison, conjunction_operation);
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

    @Override
    public void exitComparison(RSLParser.ComparisonContext ctx) {
        try {
            Y_Comparison comparison = Y_ComparisonImpl.create(population, UniqueId.random(), parentIds.pop(), parentIds.peek());
            for (Y_ComparisonOperation comparison_operation : population.Y_ComparisonOperation_instances()
                    .where((selected) -> selected.getParent_node_id() == comparison.getNode_id()))
                population.relate_R3072_Y_ComparisonOperation_Y_Comparison(comparison_operation, comparison);
            for (Y_Addition addition : population.Y_Addition_instances()
                    .where((selected) -> selected.getParent_node_id() == comparison.getNode_id()))
                population.relate_R3064_Y_Addition_Y_Comparison(addition, comparison);
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

    @Override
    public void exitComparison_operation(RSLParser.Comparison_operationContext ctx) {
        try {
            Y_ComparisonOperation comparison_operation = Y_ComparisonOperationImpl.create(population, UniqueId.random(), parentIds.pop(),
                    parentIds.peek(), null == ctx.EQ() ? "" : ctx.EQ().getText(),
                    null == ctx.NE() ? "" : ctx.NE().getText(), null == ctx.LT() ? "" : ctx.LT().getText(),
                    null == ctx.GT() ? "" : ctx.GT().getText(), null == ctx.LTE() ? "" : ctx.LTE().getText(),
                    null == ctx.GTE() ? "" : ctx.GTE().getText());
            for (Y_Addition addition : population.Y_Addition_instances()
                    .where((selected) -> selected.getParent_node_id() == comparison_operation.getNode_id()))
                population.relate_R3073_Y_Addition_Y_ComparisonOperation(addition, comparison_operation);
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

    @Override
    public void exitAddition(RSLParser.AdditionContext ctx) {
        try {
            Y_Addition addition = Y_AdditionImpl.create(population, UniqueId.random(), parentIds.pop(), parentIds.peek());
            for (Y_AdditionOperation addition_operation : population.Y_AdditionOperation_instances()
                    .where((selected) -> selected.getParent_node_id() == addition.getNode_id()))
                population.relate_R3074_Y_AdditionOperation_Y_Addition(addition_operation, addition);
            for (Y_Multiplication multiplication : population.Y_Multiplication_instances()
                    .where((selected) -> selected.getParent_node_id() == addition.getNode_id()))
                population.relate_R3065_Y_Multiplication_Y_Addition(multiplication, addition);
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

    @Override
    public void exitAddition_operation(RSLParser.Addition_operationContext ctx) {
        try {
            Y_AdditionOperation addition_operation = Y_AdditionOperationImpl.create(population, UniqueId.random(), parentIds.pop(),
                    parentIds.peek(), null == ctx.PLUS() ? "" : ctx.PLUS().getText(),
                    null == ctx.MINUS() ? "" : ctx.MINUS().getText());
            for (Y_Multiplication multiplication : population.Y_Multiplication_instances()
                    .where((selected) -> selected.getParent_node_id() == addition_operation.getNode_id()))
                population.relate_R3075_Y_Multiplication_Y_AdditionOperation(multiplication, addition_operation);
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

    @Override
    public void exitMultiplication(RSLParser.MultiplicationContext ctx) {
        try {
            Y_Multiplication multiplication = Y_MultiplicationImpl.create(population, UniqueId.random(), parentIds.pop(),
                    parentIds.peek());
            for (Y_MultiplicationOperation multiplication_operation : population.Y_MultiplicationOperation_instances()
                    .where((selected) -> selected.getParent_node_id() == multiplication.getNode_id()))
                population.relate_R3076_Y_MultiplicationOperation_Y_Multiplication(multiplication_operation,
                        multiplication);
            for (Y_Negation negation : population.Y_Negation_instances()
                    .where((selected) -> selected.getParent_node_id() == multiplication.getNode_id()))
                population.relate_R3066_Y_Negation_Y_Multiplication(negation, multiplication);
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

    @Override
    public void exitMultiplication_operation(RSLParser.Multiplication_operationContext ctx) {
        try {
            Y_MultiplicationOperation multiplication_operation = Y_MultiplicationOperationImpl.create(population, UniqueId.random(),
                    parentIds.pop(), parentIds.peek(), null == ctx.TIMES() ? "" : ctx.TIMES().getText(),
                    null == ctx.DIVIDE() ? "" : ctx.DIVIDE().getText(), null == ctx.REM() ? "" : ctx.REM().getText());
            for (Y_Negation negation : population.Y_Negation_instances()
                    .where((selected) -> selected.getParent_node_id() == multiplication_operation.getNode_id()))
                population.relate_R3077_Y_Negation_Y_MultiplicationOperation(negation, multiplication_operation);
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }
    
    @Override
    public void exitNegation(RSLParser.NegationContext ctx) {
        try {
            Y_Negation negation = Y_NegationImpl.create(population, UniqueId.random(), parentIds.pop(), parentIds.peek());
            for (Y_NegationOperation negation_operation : population.Y_NegationOperation_instances()
                    .where((selected) -> selected.getParent_node_id() == negation.getNode_id()))
                population.relate_R3078_Y_NegationOperation_Y_Negation(negation_operation, negation);
            for (Y_EmptyCheck emptyCheck : population.Y_EmptyCheck_instances()
                    .where((selected) -> selected.getParent_node_id() == negation.getNode_id()))
                population.relate_R3090_Y_EmptyCheck_Y_Negation(emptyCheck, negation);
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

    @Override
    public void exitNegation_operation(RSLParser.Negation_operationContext ctx) {
        try {
            Y_NegationOperationImpl.create(population, UniqueId.random(), parentIds.pop(), parentIds.peek(),
                    null == ctx.NOT() ? "" : ctx.NOT().getText(), null == ctx.MINUS() ? "" : ctx.MINUS().getText());
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

    @Override
    public void exitEmpty_check(Empty_checkContext ctx) {
        try {
            Y_EmptyCheck emptyCheck = Y_EmptyCheckImpl.create(population, UniqueId.random(), parentIds.pop(), parentIds.peek());
            for (Y_EmptyCheckOperation emptyCheckOperation : population.Y_EmptyCheckOperation_instances()
                    .where((selected) -> selected.getParent_node_id() == emptyCheck.getNode_id()))
                population.relate_R3091_Y_EmptyCheckOperation_Y_EmptyCheck(emptyCheckOperation, emptyCheck);
            for (Y_Term term : population.Y_Term_instances()
                    .where((selected) -> selected.getParent_node_id() == emptyCheck.getNode_id()))
                population.relate_R3067_Y_Term_Y_EmptyCheck(term, emptyCheck);
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }
    
    @Override
    public void exitEmpty_check_operation(Empty_check_operationContext ctx) {
        try {
            Y_EmptyCheckOperationImpl.create(population, UniqueId.random(), parentIds.pop(), parentIds.peek(),
                    null == ctx.EMPTY() ? "" : ctx.EMPTY().getText(), null == ctx.NOT_EMPTY() ? "" : ctx.NOT_EMPTY().getText());
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

    @Override
    public void exitTerm(RSLParser.TermContext ctx) {
        try {
            Y_Term term = Y_TermImpl.create(population, UniqueId.random(), parentIds.pop(), parentIds.peek());
            for (Y_Parenthesis parenthesis : population.Y_Parenthesis_instances()
                    .where((selected) -> selected.getParent_node_id() == term.getNode_id()))
                population.relate_R3079_Y_Parenthesis_Y_Term(parenthesis, term);
            for (Y_NamedAccess named_access : population.Y_NamedAccess_instances()
                    .where((selected) -> selected.getParent_node_id() == term.getNode_id()))
                population.relate_R3081_Y_NamedAccess_Y_Term(named_access, term);
            for (Y_Literal parenthesis : population.Y_Literal_instances()
                    .where((selected) -> selected.getParent_node_id() == term.getNode_id()))
                population.relate_R3086_Y_Literal_Y_Term(parenthesis, term);
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

    @Override
    public void exitParenthesis(RSLParser.ParenthesisContext ctx) {
        try {
            Y_Parenthesis parenthesis = Y_ParenthesisImpl.create(population, UniqueId.random(), parentIds.pop(), parentIds.peek(),
                    null == ctx.LPAREN() ? "" : ctx.LPAREN().getText(),
                    null == ctx.RPAREN() ? "" : ctx.RPAREN().getText());
            for (Y_Expression expression : population.Y_Expression_instances()
                    .where((selected) -> selected.getParent_node_id() == parenthesis.getNode_id()))
                population.relate_R3080_Y_Expression_Y_Parenthesis(expression, parenthesis);
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

    @Override
    public void exitNamed_access(RSLParser.Named_accessContext ctx) {
        try {
            Y_NamedAccess named_access = Y_NamedAccessImpl.create(population, UniqueId.random(), parentIds.pop(), parentIds.peek());
            for (Y_VariableAccess variable_access : population.Y_VariableAccess_instances()
                    .where((selected) -> selected.getParent_node_id() == named_access.getNode_id()))
                population.relate_R3082_Y_VariableAccess_Y_NamedAccess(variable_access, named_access);
            for (Y_AttributeAccess attribute_access : population.Y_AttributeAccess_instances()
                    .where((selected) -> selected.getParent_node_id() == named_access.getNode_id()))
                population.relate_R3083_Y_AttributeAccess_Y_NamedAccess(attribute_access, named_access);
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

    @Override
    public void exitVariable_access(RSLParser.Variable_accessContext ctx) {
        try {
            Y_VariableAccessImpl.create(population, UniqueId.random(), parentIds.pop(), parentIds.peek(),
                    null == ctx.ID() ? "" : ctx.ID().getText());
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

    @Override
    public void exitAttribute_access(RSLParser.Attribute_accessContext ctx) {
        try {
            Y_AttributeAccess attribute_access1 = Y_AttributeAccessImpl.create(population, UniqueId.random(), parentIds.pop(),
                    parentIds.peek(), null == ctx.DOT() ? "" : ctx.DOT().getText(),
                    null == ctx.ID() ? "" : ctx.ID().getText());
            for (Y_AttributeAccess attribute_access2 : population.Y_AttributeAccess_instances()
                    .where((selected) -> selected.getParent_node_id() == attribute_access1.getNode_id()))
                population.relate_R3084_Y_AttributeAccess_contained_by_Y_AttributeAccess(attribute_access2,
                        attribute_access1);
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

    @Override
    public void exitLiteral(RSLParser.LiteralContext ctx) {
        try {
            Y_Literal literal = Y_LiteralImpl.create(population, UniqueId.random(), parentIds.pop(), parentIds.peek(),
                    null == ctx.BOOLEAN_LITERAL() ? "" : ctx.BOOLEAN_LITERAL().getText(),
                    null == ctx.INTEGER_LITERAL() ? "" : ctx.INTEGER_LITERAL().getText(),
                    null == ctx.REAL_LITERAL() ? "" : ctx.REAL_LITERAL().getText());
            for (Y_StringLiteral string_literal : population.Y_StringLiteral_instances()
                    .where((selected) -> selected.getParent_node_id() == literal.getNode_id()))
                population.relate_R3087_Y_StringLiteral_Y_Literal(string_literal, literal);
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

    @Override
    public void exitString_literal(RSLParser.String_literalContext ctx) {
        try {
            Y_StringLiteral string_literal = Y_StringLiteralImpl.create(population, UniqueId.random(), parentIds.pop(),
                    parentIds.peek(), null == ctx.QUOTE(0) ? "" : ctx.QUOTE(0).getText(),
                    null == ctx.QUOTE(1) ? "" : ctx.QUOTE(1).getText());
            for (Y_BufferElement buffer_element : population.Y_BufferElement_instances()
                    .where((selected) -> selected.getParent_node_id() == string_literal.getNode_id()))
                population.relate_R3088_Y_BufferElement_Y_StringLiteral(buffer_element, string_literal);
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

}
