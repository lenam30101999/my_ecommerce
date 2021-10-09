package com.demo.elk.util;

import com.google.common.base.CaseFormat;

public class CaseUtil {

  public static String toSnack(String camelCase) {
    return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, camelCase);
  }
}
