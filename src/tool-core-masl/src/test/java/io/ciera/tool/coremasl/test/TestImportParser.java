package io.ciera.tool.coremasl.test;

import java.io.IOException;
import java.util.stream.Stream;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.xtuml.stratus.parser.MaslLexer;
import org.xtuml.stratus.parser.MaslParser;
import org.xtuml.stratus.parser.MaslPopulator;

import io.ciera.runtime.instanceloading.generic.IGenericLoader;
import io.ciera.runtime.instanceloading.generic.util.LOAD;

public class TestImportParser implements IGenericLoader {

  // private fields
  private LOAD loader; // OOA API
  private Stream<String> resources;

  public TestImportParser(Stream<String> resources) {
    this.resources = resources;
  }

  // parse a MASL resource
  private void parse(String resource) {
    try {
      // Tokenize the file
      CharStream input = CharStreams.fromStream(getClass().getResourceAsStream(resource));
      MaslLexer lexer = new MaslLexer(input);
      MaslParser parser = new MaslParser(new CommonTokenStream(lexer));

      // Parse the file
      ParserRuleContext ctx = parser.target();

      // Walk the parse tree
      MaslPopulator listener = new MaslPopulator(this, loader, input, resource);
      listener.visit(ctx);

    } catch (IOException e) {
      // TODO error handling
      e.printStackTrace();
    }
  }

  // main method
  @Override
  public void load(LOAD loader, String args[]) {
    this.loader = loader;

    // parse each resource
    resources.forEach(this::parse);
  }
}
