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

import org.modelmapper.ModelMapper;
import org.modelmapper.Module;

/**
 * Supports the JSR310 {@code java.time} objects with  ModelMapper
 *
 * @author Chun Han Hsiao
 */
public class Jsr310Module implements Module {

    private final Jsr310ModuleConfig config;

    public Jsr310Module() {
        this(new Jsr310ModuleConfig());
    }

    public Jsr310Module(Jsr310ModuleConfig config) {
        this.config = config;
    }

    @Override
    public void setupModule(ModelMapper modelMapper) {
        modelMapper.getConfiguration().getConverters().add(0, new FromTemporalConverter(config));
        modelMapper.getConfiguration().getConverters().add(0, new ToTemporalConverter(config));
        modelMapper.getConfiguration().getConverters().add(0, new TemporalToTemporalConverter());
    }
}
