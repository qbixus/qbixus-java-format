package com.google.googlejavaformat;

public class MaxWidth {

  public final int maxLineLength;

  public final int maxRunWidth;

  public MaxWidth(int maxLineLength, int maxRunWidth) {
    this.maxLineLength = maxLineLength;
    this.maxRunWidth = maxRunWidth;
  }

  public MaxWidth withExtraIndent(int delta) {
    if (this.maxRunWidth == Integer.MAX_VALUE) {
      return this;
    }
    return new MaxWidth(maxLineLength, maxRunWidth + delta);
  }

  public int eval() {
    return Math.min(maxLineLength, maxRunWidth);
  }
}
