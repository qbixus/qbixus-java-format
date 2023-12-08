/*
 * Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.googlejavaformat.java;

import com.google.auto.value.AutoValue;
import com.google.errorprone.annotations.Immutable;
import com.google.googlejavaformat.MaxWidth;
import java.util.Comparator;
import java.util.function.BiFunction;

/**
 * Options for a google-java-format invocation.
 *
 * <p>Like gofmt, the google-java-format CLI exposes <em>no</em> configuration options (aside from
 * {@code --aosp}).
 *
 * <p>The goal of google-java-format is to provide consistent formatting, and to free developers
 * from arguments over style choices. It is an explicit non-goal to support developers' individual
 * preferences, and in fact it would work directly against our primary goals.
 */
@Immutable
@AutoValue
public abstract class JavaFormatterOptions {

  public enum Style {

    /** The default Google Java Style configuration. */
    GOOGLE(
        1,
        false,
        false,
        new MaxWidth(100, Integer.MAX_VALUE),
        ImportOrderer.GOOGLE_IMPORT_COMPARATOR,
        ImportOrderer::shouldInsertBlankLineGoogle),

    /** The AOSP-compliant configuration. */
    AOSP(
        2,
        false,
        false,
        new MaxWidth(100, Integer.MAX_VALUE),
        ImportOrderer.AOSP_IMPORT_COMPARATOR,
        ImportOrderer::shouldInsertBlankLineAosp),

    QBIXUS(
        1,
        false,
        true,
        new MaxWidth(100, 80),
        ImportOrderer.GOOGLE_IMPORT_COMPARATOR,
        ImportOrderer::shouldInsertBlankLineGoogle),

    QBIXUS_GOOGLE(
        1,
        false,
        true,
        new MaxWidth(100, Integer.MAX_VALUE),
        ImportOrderer.GOOGLE_IMPORT_COMPARATOR,
        ImportOrderer::shouldInsertBlankLineGoogle);

    public final int indentationMultiplier;
    public final boolean unifiedReturns;
    public final boolean optimizeArgs;
    private final MaxWidth maxWidth;
    public final Comparator<ImportOrderer.Import> importComparator;
    public final BiFunction<ImportOrderer.Import, ImportOrderer.Import, Boolean>
        shouldInsertBlankLineFn;

    Style(
        int indentationMultiplier,
        boolean unifiedReturns,
        boolean optimizeArgs,
        MaxWidth maxWidth,
        Comparator<ImportOrderer.Import> importComparator,
        BiFunction<ImportOrderer.Import, ImportOrderer.Import, Boolean> shouldInsertBlankLineFn) {
      this.indentationMultiplier = indentationMultiplier;
      this.unifiedReturns = unifiedReturns;
      this.optimizeArgs = optimizeArgs;
      this.maxWidth = maxWidth;
      this.importComparator = importComparator;
      this.shouldInsertBlankLineFn = shouldInsertBlankLineFn;
    }

    int indentationMultiplier() {
      return indentationMultiplier;
    }

    MaxWidth maxWidth() {
      return maxWidth;
    }
  }

  /** Returns the multiplier for the unit of indent. */
  public int indentationMultiplier() {
    return style().indentationMultiplier();
  }

  public MaxWidth maxWidth() {
    return style().maxWidth();
  }

  public abstract boolean formatJavadoc();

  public abstract boolean reorderModifiers();

  /** Returns the code style. */
  public abstract Style style();

  /** Returns the default formatting options. */
  public static JavaFormatterOptions defaultOptions() {
    return builder().build();
  }

  /** Returns a builder for {@link JavaFormatterOptions}. */
  public static Builder builder() {
    return new AutoValue_JavaFormatterOptions.Builder()
        .style(Style.GOOGLE)
        .formatJavadoc(true)
        .reorderModifiers(true);
  }

  /** A builder for {@link JavaFormatterOptions}. */
  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder style(Style style);

    public abstract Builder formatJavadoc(boolean formatJavadoc);

    public abstract Builder reorderModifiers(boolean reorderModifiers);

    public abstract JavaFormatterOptions build();
  }
}
