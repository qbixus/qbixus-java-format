/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.googlejavaformat.intellij;

import com.google.googlejavaformat.java.JavaFormatterOptions;
import com.google.googlejavaformat.java.JavaFormatterOptions.Style;
import java.util.Arrays;
import java.util.Objects;

/** Configuration options for the formatting style. */
public class UiFormatterStyle {

  private static UiFormatterStyle[] values;

  private final String description;
  private final JavaFormatterOptions.Style style;

  UiFormatterStyle(String description, JavaFormatterOptions.Style style) {
    this.description = description;
    this.style = style;
  }

  @Override
  public String toString() {
    return description;
  }

  public JavaFormatterOptions.Style convert() {
    return style;
  }

  private static void ensureValues() {
    if (values != null) {
      return;
    }
    values =
        Arrays.stream(Style.values())
            .map(
                i -> {
                  UiFormatterStyle result;
                  if (i == Style.GOOGLE) {
                    result = new UiFormatterStyle("Default Google Java style", Style.GOOGLE);
                  } else if (i == Style.AOSP) {
                    result =
                        new UiFormatterStyle(
                            "Android Open Source Project (AOSP) style", Style.AOSP);
                  } else {
                    result = new UiFormatterStyle(i.name() + " style", i);
                  }
                  return result;
                })
            .toArray(UiFormatterStyle[]::new);
  }

  static UiFormatterStyle convert(JavaFormatterOptions.Style style) {
    ensureValues();
    return Arrays.stream(values)
        .filter(value -> Objects.equals(value.style, style))
        .findFirst()
        .get();
  }

  static UiFormatterStyle[] values() {
    ensureValues();
    return values;
  }
}
