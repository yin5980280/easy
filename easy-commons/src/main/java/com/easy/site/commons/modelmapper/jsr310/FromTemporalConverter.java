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
package com.easy.site.commons.modelmapper.jsr310;

import org.modelmapper.Converter;
import org.modelmapper.internal.Errors;
import org.modelmapper.spi.ConditionalConverter;
import org.modelmapper.spi.MappingContext;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;

/**
 * Converts  {@link Temporal} to {@link Object}
 *
 * @author Chun Han Hsiao
 */
public class FromTemporalConverter implements ConditionalConverter<Temporal, Object> {

    private final Jsr310ModuleConfig config;
    private final LocalDateTimeConverter localDateTimeConverter = new LocalDateTimeConverter();
    private final LocalDateConverter localDateConverter = new LocalDateConverter();
    private final InstantConverter instantConverter = new InstantConverter();

    public FromTemporalConverter(Jsr310ModuleConfig config) {
        this.config = config;
    }

    @Override
    public MatchResult match(Class<?> sourceType, Class<?> destinationType) {
        return Temporal.class.isAssignableFrom(sourceType)
                ? MatchResult.FULL : MatchResult.NONE;
    }

    @Override
    public Object convert(MappingContext<Temporal, Object> mappingContext) {
        Class<?> sourceType = mappingContext.getSourceType();
        if (LocalDateTime.class.equals(sourceType))
            return localDateTimeConverter.convert(mappingContext);
        else if (LocalDate.class.equals(sourceType))
            return localDateConverter.convert(mappingContext);
        else if (Instant.class.equals(sourceType))
            return instantConverter.convert(mappingContext);
        else
            throw new Errors().addMessage("Unsupported mapping types[%s->%s]",
                    LocalDateTime.class.getName(), mappingContext.getDestinationType().getName())
                    .toMappingException();
    }

    private class LocalDateTimeConverter implements Converter<Temporal, Object> {

        @Override
        public Object convert(MappingContext<Temporal, Object> mappingContext) {
            LocalDateTime source = (LocalDateTime) mappingContext.getSource();
            return convertLocalDateTime(source, mappingContext);
        }
    }

    private class LocalDateConverter implements Converter<Temporal, Object> {

        @Override
        public Object convert(MappingContext<Temporal, Object> mappingContext) {
            LocalDate source = (LocalDate) mappingContext.getSource();
            Class<?> destinationType = mappingContext.getDestinationType();
            if (destinationType.equals(String.class))
                return DateTimeFormatter.ofPattern(config.getDatePattern())
                        .format(source);

            LocalDateTime localDateTime = source.atStartOfDay();
            return convertLocalDateTime(localDateTime, mappingContext);
        }
    }

    private class InstantConverter implements Converter<Temporal, Object> {

        @Override
        public Object convert(MappingContext<Temporal, Object> mappingContext) {
            Instant source = (Instant) mappingContext.getSource();
            return convertInstant(source, mappingContext);
        }
    }

    private Object convertLocalDateTime(LocalDateTime source, MappingContext<?, ?> mappingContext) {
        Class<?> destinationType = mappingContext.getDestinationType();
        if (destinationType.equals(String.class))
            return DateTimeFormatter.ofPattern(config.getDateTimePattern())
                    .format(source);

        Instant instant = source.atZone(config.getZoneId()).toInstant();
        return convertInstant(instant, mappingContext);
    }

    private Object convertInstant(Instant source, MappingContext<?, ?> mappingContext) {
        Class<?> destinationType = mappingContext.getDestinationType();
        if (destinationType.equals(String.class))
            return DateTimeFormatter.ofPattern(config.getDateTimePattern())
                    .withZone(config.getZoneId())
                    .format(source);
        else if (Date.class.isAssignableFrom(destinationType))
            return new Date(epochMilliOf(source));
        else if (Calendar.class.isAssignableFrom(destinationType))
            return calendarOf(source);
        else if (Long.class.equals(destinationType) || Long.TYPE.equals(destinationType))
            return epochMilliOf(source);
        else if (BigDecimal.class.equals(destinationType))
            return new BigDecimal(epochMilliOf(source));
        else if (BigInteger.class.equals(destinationType))
            return BigInteger.valueOf(epochMilliOf(source));
        else
            throw new Errors().addMessage("Unsupported mapping types[%s->%s]",
                    mappingContext.getSourceType().getName(), destinationType.getName())
                    .toMappingException();
    }

    private long epochMilliOf(Instant instant) {
        return instant.toEpochMilli();
    }

    private Calendar calendarOf(Instant instant) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(epochMilliOf(instant));
        return calendar;
    }
}
