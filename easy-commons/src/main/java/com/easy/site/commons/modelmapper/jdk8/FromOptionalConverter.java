/*
 * Copyright (c) 2018-2022 Caratacus, (caratacus@qq.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.easy.site.commons.modelmapper.jdk8;

import org.modelmapper.spi.ConditionalConverter;
import org.modelmapper.spi.MappingContext;

import java.util.Optional;

/**
 * Converts  {@link Optional} to {@link Object}
 *
 * @author Chun Han Hsiao
 */
public class FromOptionalConverter implements ConditionalConverter<Optional<Object>, Object> {

    @Override
    public MatchResult match(Class<?> sourceType, Class<?> destinationType) {
        return (Optional.class.equals(sourceType) && !Optional.class.equals(destinationType))
                ? MatchResult.FULL
                : MatchResult.NONE;
    }

    @Override
    public Object convert(MappingContext<Optional<Object>, Object> mappingContext) {
        if (mappingContext.getSource() == null || !mappingContext.getSource().isPresent()) {
            return null;
        }

        MappingContext<Object, Object> propertyContext = mappingContext.create(
                mappingContext.getSource().get(), mappingContext.getDestinationType());
        return mappingContext.getMappingEngine().map(propertyContext);
    }
}
