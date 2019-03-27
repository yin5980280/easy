package com.easy.site.spring.boot.interceptor;


import com.easy.site.spring.boot.validator.Validator;
import com.easy.site.spring.boot.validator.annotation.Validate;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

/**
 * @author shuw
 * @version 1.0
 * @date 2017/6/5 10:37
 */
@Component
@Aspect
@Slf4j
public class ValidateInterceptor {

    //接口请求参数校验器切面 Validate
    @Pointcut("@annotation(com.easy.site.spring.boot.validator.annotation.Validate) && @annotation(validate)")
    public void validateControllerAspect(Validate validate) {
    }

    /**
     * 验证请求
     * @param joinPoint
     * @throws Throwable
     */
    @Before("validateControllerAspect(validate)")
    public void validateRequest(JoinPoint joinPoint, Validate validate) throws Throwable {
        Object[] args = joinPoint.getArgs();
        if(args.length == 0){
            return;
        }
        if (null == validate) {
            validate = this.getValidate(joinPoint);
            if (null == validate) {
                return;
            }
        }
        int index = validate.reqParamIndex();
        if (0 > index || index >= args.length) {
            log.error(joinPoint.getSignature().getDeclaringTypeName() + " 校验器Validate参数reqParamIndex配置错误");
            return;
        }
        //验参
        Object validateArg = args[index];
        validate(validateArg, validate.groups());
    }

    private Validate getValidate(JoinPoint joinPoint){
        Class clazz = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        Method[] methods = clazz.getMethods();
        for(Method m:methods){
            if(!m.getName().equals(methodName)){
                continue;
            }
            if(m.getParameters().length != joinPoint.getArgs().length){
                continue;
            }
            Validate v = m.getDeclaredAnnotation(Validate.class);
            if(v != null){
                return v;
            }
        }
        return null;
    }

    /**
     * hibernate-validator验证
     * @param obj
     * @param groups
     */
    public static void validate(Object obj, Class<?>[] groups){
        Validator.validateRequest(obj, groups);
    }
}
