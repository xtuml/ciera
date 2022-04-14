package io.ciera.tool.coremasl.ees.impl;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.json.JSONObject;

import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.util.Utility;
import io.ciera.tool.coremasl.ees.C_UTIL;

public class C_UTILImpl<C extends IComponent<C>> extends Utility<C> implements C_UTIL {

  private static final Pattern IMPORT_PATTERN =
      Pattern.compile("^import (([A-Za-z][A-Za-z_]+\\.)+[A-Za-z][A-Za-z_]+);$");

  public C_UTILImpl(C context) {
    super(context);
  }

  public String organizeImports(final String p_s) {
    // filter and sort imports
    return p_s.lines()
        .map(String::trim)
        .filter(Predicate.not(String::isEmpty))
        .sorted(
            (a, b) -> {
              Matcher m1 = IMPORT_PATTERN.matcher(a);
              Matcher m2 = IMPORT_PATTERN.matcher(b);
              if (m1.matches() && m2.matches()) {
                return m1.group(1).compareTo(m2.group(1));
              }
              return 0;
            })
        .collect(Collectors.joining("\n"));
  }

  public String stripTics(final String p_s) {
    return p_s.replaceAll("'", "");
  }

  public boolean isLong(final String p_int_string) {
    try {
      Integer.parseInt(p_int_string);
      return false;
    } catch (NumberFormatException e1) {
      try {
        Long.parseLong(p_int_string);
        return true;
      } catch (NumberFormatException e2) {
        return false;
      }
    }
  }

  // Hacky way to use the JSON library to cleanse a string
  public String cleanActionString(final String actions) {
    JSONObject obj = new JSONObject();
    obj.put("str", actions);
    String jsonString = obj.toString();
    return jsonString.substring("str".length() + 5, jsonString.length() - 2);
  }

  public int hashCode(final String s) {
    return s.hashCode();
  }
}
