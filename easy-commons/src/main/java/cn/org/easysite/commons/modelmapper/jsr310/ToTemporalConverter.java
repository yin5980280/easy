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
package cn.org.easysite.commons.modelmapper.jsr310;

import org.modelmapper.Converter;
import org.modelmapper.internal.Errors;
import org.modelmapper.spi.ConditionalConverter;
import org.modelmapper.spi.MappingContext;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;

/**
 * Converts  {@link Object} to {@link Temporal}
 *
 * @author Chun Han Hsiao
 */
public class ToTemporalConverter implements ConditionalConverter<Object, Temporal> {

    private final Jsr310ModuleConfig config;
    private final LocalDateTimeConverter localDateTimeConverter = new LocalDateTimeConverter();
    private final LocalDateConverter localDateConverter = new LocalDateConverter();
    private final InstantConverter instantConverter = new InstantConverter();

    public ToTemporalConverter(Jsr310ModuleConfig config) {
        this.config = config;
    }

    @Override
    public MatchResult match(Class<?> sourceType, Class<?> destinationType) {
        return Temporal.class.isAssignableFrom(destinationType)
                ? MatchResult.FULL : MatchResult.NONE;
    }

    @Override
    public Temporal convert(MappingContext<Object, Temporal> mappingContext) {
        Class<?> destinationType = mappingContext.getDestinationType();
        if (LocalDateTime.class.equals(destinationType)) {
            return localDateTimeConverter.convert(mappingContext);
        }
        else if (LocalDate.class.equals(destinationType)) {
            return localDateConverter.convert(mappingContext);
        }
        else if (Instant.class.equals(destinationType)) {
            return instantConverter.convert(mappingContext);
        } else {
            throw new Errors().addMessage("Unsupported mapping types[%s->%s]",
                    mappingContext.getSourceType().getName(), destinationType.getName())
                    .toMappingException();
        }
    }

    private class LocalDateTimeConverter implements Converter<Object, Temporal> {

        @Override
        public Temporal convert(MappingContext<Object, Temporal> mappingContext) {
            return convertLocalDateTime(mappingContext);
        }
    }

    private class LocalDateConverter implements Converter<Object, Temporal> {

        @Override
        public Temporal convert(MappingContext<Object, Temporal> mappingContext) {
            return convertLocalDate(mappingContext);
        }
    }

    private class InstantConverter implements Converter<Object, Temporal> {

        @Override
        public Temporal convert(MappingContext<Object, Temporal> mappingContext) {
            return convertInstant(mappingContext);
        }
    }

    private LocalDate convertLocalDate(MappingContext<?, ?> mappingContext) {
        Object source = mappingContext.getSource();
        Class<?> sourceType = source.getClass();
        if (sourceType.equals(String.class)) {
            return LocalDate.parse((String) source,
                    DateTimeFormatter.ofPattern(config.getDatePattern()));
        }
        return convertInstant(mappingContext).atZone(config.getZoneId()).toLocalDate();
    }

    private LocalDateTime convertLocalDateTime(MappingContext<?, ?> mappingContext) {
        Object source = mappingContext.getSource();
        Class<?> sourceType = source.getClass();
        if (sourceType.equals(String.class)) {
            return LocalDateTime.parse((String) source,
                    DateTimeFormatter.ofPattern(config.getDateTimePattern()));
        }
        return convertInstant(mappingContext).atZone(config.getZoneId()).toLocalDateTime();
    }

    private Instant convertInstant(MappingContext<?, ?> mappingContext) {
        Object source = mappingContext.getSource();
        Class<?> sourceType = source.getClass();
        if (sourceType.equals(String.class)) {
            return LocalDateTime.parse((String) source,
                    DateTimeFormatter.ofPattern(config.getDateTimePattern()))
                    .atZone(config.getZoneId()).toInstant();
        } else if (Date.class.isAssignableFrom(sourceType)) {
            return Instant.ofEpochMilli(((Date) source).getTime());
        } else if (Calendar.class.isAssignableFrom(sourceType)) {
            return Instant.ofEpochMilli(((Calendar) source).getTime().getTime());
        } else if (Number.class.isAssignableFrom(sourceType)) {
            return Instant.ofEpochMilli(((Number) source).longValue());
        } else {
            throw new Errors().addMessage("Unsupported mapping types[%s->%s]",
                    sourceType.getName(), mappingContext.getDestinationType().getName())
                    .toMappingException();
        }
    }
}
