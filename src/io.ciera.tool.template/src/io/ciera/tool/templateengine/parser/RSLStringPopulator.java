package io.ciera.tool.templateengine.parser;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.UniqueId;
import io.ciera.tool.TemplateEngine;
import io.ciera.tool.templateengine.rsl.NonTemplateStringLiteral;
import io.ciera.tool.templateengine.rsl.Y_BufferElement;
import io.ciera.tool.templateengine.rsl.Y_StringLiteral;
import io.ciera.tool.templateengine.rsl.impl.NonTemplateStringLiteralImpl;
import io.ciera.tool.templateengine.rsl.impl.Y_StringLiteralImpl;

public class RSLStringPopulator extends RSLPopulator {

    private NonTemplateStringLiteral non_template_string_literal;

    public RSLStringPopulator(String parent_name, String parent_package, String body_name, String block_number,
            String statement_number, String expression_number, TemplateEngine population) {
        super("", population);
        try {
            incrementId();
            non_template_string_literal = NonTemplateStringLiteralImpl.create(population, UniqueId.random(),
                    parentIds.peek(), parent_name, parent_package, body_name, block_number, statement_number,
                    expression_number);
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
            population.relate_R3089_Y_StringLiteral_NonTemplateStringLiteral(string_literal,
                    non_template_string_literal);
        } catch (XtumlException e) {
            population.getRunContext().getLog().error(e);
        }
    }

}
