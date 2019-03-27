package com.easy.site.spring.boot.distributed.lock.core.provider.impl;

import com.easy.site.spring.boot.distributed.lock.annotation.DLock;
import com.easy.site.spring.boot.distributed.lock.annotation.DLockKey;
import com.easy.site.spring.boot.distributed.lock.core.provider.api.BusinessKeyProvider;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by panda on 2018/1/24.
 * Content :获取用户定义业务key
 */
@Slf4j
public class DefaultBusinessKeyProvider implements BusinessKeyProvider {

    private ParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    private ExpressionParser parser = new SpelExpressionParser();

    @Override
    public String getKeyName(ProceedingJoinPoint joinPoint, DLock lock) {

        Method method = getMethod(joinPoint);
        List<String> definitionKeys = getSpelDefinitionKey(lock.keys(), method, joinPoint.getArgs());
        List<String> keyList = new ArrayList<>(definitionKeys);
        List<String> parameterKeys = getParameterKey(method.getParameters(), joinPoint.getArgs());
        keyList.addAll(parameterKeys);
        return StringUtils.collectionToDelimitedString(keyList, "", "-", "");
    }

    private Method getMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (method.getDeclaringClass().isInterface()) {
            try {
                method = joinPoint.getTarget().getClass().getDeclaredMethod(signature.getName(), method.getParameterTypes());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return method;
    }

    private List<String> getSpelDefinitionKey(String[] definitionKeys, Method method, Object[] parameterValues) {
        List<String> definitionKeyList = new ArrayList<>();
        for (String definitionKey : definitionKeys) {
            if (definitionKey != null && !definitionKey.isEmpty()) {
                EvaluationContext context = new MethodBasedEvaluationContext(null, method, parameterValues, nameDiscoverer);
                Object key = parser.parseExpression(definitionKey).getValue(context);
                definitionKeyList.add(key == null ? "" : key.toString());
            }
        }
        return definitionKeyList;
    }

    private List<String> getParameterKey(Parameter[] parameters, Object[] parameterValues) {
        List<String> parameterKey = new ArrayList<>();
        for (int i = 0; i< parameters.length; i++) {
            if (parameters[i].getAnnotation(DLockKey.class) != null) {
                DLockKey keyAnnotation = parameters[i].getAnnotation(DLockKey.class);
                if (keyAnnotation.value().isEmpty()) {
                    parameterKey.add(parameterValues[i].toString());
                } else {
                    StandardEvaluationContext context = new StandardEvaluationContext(parameterValues[i]);
                    Object key = parser.parseExpression(keyAnnotation.value()).getValue(context);
                    parameterKey.add(key == null ? "" : key.toString());
                }
            }
        }
        return parameterKey;
    }
}
