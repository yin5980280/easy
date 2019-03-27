package com.easy.site.spring.boot.validator;

import com.easy.site.framework.exception.BaseException;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

/**
 * 基于hibernate validator的参数校验
 * @date
 */
public class Validator {
  private static final javax.validation.Validator vf = Validation.buildDefaultValidatorFactory().getValidator();
  // 连接多个错误信息的分隔符
  private static final char ERROR_INFO_LINK = ';';
  private static final char KEY_PREFIX = '[';
  private static final char KEY_SUFFIX = ']';
  private static final String KEY_VALIDATE_ERROR = "validate_error";

  /**
   * 校验请求参数
   * @param request
   * @param groups
   * @throws BaseException
   */
  public static void validateRequest(Object request, Class<?>[] groups) throws BaseException {
    Set<ConstraintViolation<Object>> rsList;
    if (groups == null || groups.length == 0) {
      validateParam(request);
    } else {
      validate(vf.validate(request, groups));
    }

  }

  private static void validate(Set<ConstraintViolation<Object>> rsList) {
    if (rsList.isEmpty()) {
      return;
    }
    StringBuilder sb = new StringBuilder();
    for (ConstraintViolation<Object> rs : rsList) {
      sb.append(ERROR_INFO_LINK).append(KEY_PREFIX).append(rs.getPropertyPath()).append(KEY_SUFFIX)
              .append(rs.getMessage());
    }
    sb.deleteCharAt(0);
    throw new BaseException(1003, sb.toString());
  }

  /**
   * 校验业务参数
   *
   * @param request
   * @throws BaseException
   */
  public static void validateParam(Object request) throws BaseException {
    validate(vf.validate(request));
  }
}
