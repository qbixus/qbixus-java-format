package com.google.googlejavaformat.java;

import com.sun.source.tree.BlockTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.Modifier;

public class JavaClassArrangement {

  public static Map<Object, Integer> applyQbixusRanking(List<Object> items) {
    var result = new HashMap<Object, Integer>();
    for (var item : items) {
      Tree declaration;
      if (item instanceof Tree) {
        declaration = (Tree) item;
      } else if (item instanceof List<?> && ((List<?>) item).get(0) instanceof VariableTree) {
        declaration = (VariableTree) ((List<?>) item).get(0);
      } else {
        throw new AssertionError("#UNEXPECTED");
      }

      int rank;
      if (declaration instanceof VariableTree
          && ((VariableTree) declaration).getModifiers().getFlags().contains(Modifier.STATIC)) {
        var flags = ((VariableTree) declaration).getModifiers().getFlags();
        if (flags.contains(Modifier.FINAL)) {
          if (flags.contains(Modifier.PUBLIC)) {
            rank = 11;
          } else if (flags.contains(Modifier.PROTECTED)) {
            rank = 12;
          } else if (flags.contains(Modifier.PRIVATE)) {
            rank = 14;
          } else {
            rank = 13;
          }
        } else {
          if (flags.contains(Modifier.PUBLIC)) {
            rank = 21;
          } else if (flags.contains(Modifier.PROTECTED)) {
            rank = 22;
          } else if (flags.contains(Modifier.PRIVATE)) {
            rank = 24;
          } else {
            rank = 23;
          }
        }
      } else if (declaration instanceof BlockTree && ((BlockTree) declaration).isStatic()) {
        rank = 30;
      } else if (declaration instanceof VariableTree
          && !((VariableTree) declaration).getModifiers().getFlags().contains(Modifier.STATIC)) {
        var flags = ((VariableTree) declaration).getModifiers().getFlags();
        if (flags.contains(Modifier.PUBLIC)) {
          rank = 41;
        } else if (flags.contains(Modifier.PROTECTED)) {
          rank = 42;
        } else if (flags.contains(Modifier.PRIVATE)) {
          rank = 44;
        } else {
          rank = 43;
        }
      } else if (declaration instanceof BlockTree && !((BlockTree) declaration).isStatic()) {
        rank = 50;
      } else if (declaration instanceof MethodTree
          && !((MethodTree) declaration).getModifiers().getFlags().contains(Modifier.STATIC)) {
        var flags = ((MethodTree) declaration).getModifiers().getFlags();
        if (((MethodTree) declaration).getName().toString().equals("<init>")) {
          if (flags.contains(Modifier.PRIVATE)) {
            rank = 61;
          } else if (flags.contains(Modifier.PROTECTED)) {
            rank = 63;
          } else if (flags.contains(Modifier.PUBLIC)) {
            rank = 64;
          } else {
            rank = 62;
          }
        } else {
          if (flags.contains(Modifier.PRIVATE)) {
            rank = 71;
          } else if (flags.contains(Modifier.PROTECTED)) {
            rank = 73;
          } else if (flags.contains(Modifier.PUBLIC)) {
            rank = 74;
          } else {
            rank = 72;
          }
        }
      } else if (declaration instanceof MethodTree
          && ((MethodTree) declaration).getModifiers().getFlags().contains(Modifier.STATIC)) {
        var flags = ((MethodTree) declaration).getModifiers().getFlags();
        if (((MethodTree) declaration).getName().toString().equals("<init>")) {
          if (flags.contains(Modifier.PRIVATE)) {
            rank = 81;
          } else if (flags.contains(Modifier.PROTECTED)) {
            rank = 83;
          } else if (flags.contains(Modifier.PUBLIC)) {
            rank = 84;
          } else {
            rank = 82;
          }
        } else {
          if (flags.contains(Modifier.PRIVATE)) {
            rank = 91;
          } else if (flags.contains(Modifier.PROTECTED)) {
            rank = 93;
          } else if (flags.contains(Modifier.PUBLIC)) {
            rank = 94;
          } else {
            rank = 92;
          }
        }
      } else if (declaration instanceof ClassTree) {
        rank = 100;
      } else {
        throw new AssertionError("#UNEXPECTED");
      }

      result.put(item, rank);
    }
    return result;
  }
}
