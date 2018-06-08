T_b("        ");
T_b("for ( ");
T_b(self->form_name);
T_b(" form : population.");
T_b(self->form_name);
T_b("_instances() ) {");
T_b("\n");
if ( self->unconditional ) {
T_b("            ");
T_b("population.relate_");
T_b(self->rel_name);
T_b("( form, population.");
T_b(self->part_name);
T_b("_instances().anyWhere( selected -> ");
T_b(attribute_comparisons);
T_b(" ) );");
T_b("\n");
} else {
T_b("            ");
T_b(self->part_name);
T_b(" part = population.");
T_b(self->part_name);
T_b("_instances().anyWhere( selected -> ");
T_b(attribute_comparisons);
T_b(" );");
T_b("\n");
T_b("            ");
T_b("if ( !part.isEmpty() ) population.relate_");
T_b(self->rel_name);
T_b("( form, part );");
T_b("\n");
}
T_b("        ");
T_b("}");
T_b("\n");
