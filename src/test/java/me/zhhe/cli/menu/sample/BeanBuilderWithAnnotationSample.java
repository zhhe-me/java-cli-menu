/*
 * Copyright (C) 2018 The java-cli-menu Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package me.zhhe.cli.menu.sample;

import me.zhhe.cli.menu.BeanMenuBuilder;
import me.zhhe.cli.menu.Setter;
import me.zhhe.cli.menu.Option;

/**
 * @author zhhe.me@gmail.com.
 * @since 12/8/2018
 */
public class BeanBuilderWithAnnotationSample {

    // Annotation can help supply extra info, like arg name, description
    @Option(name = "g", description = "Grammar name")
    private String grammar;

    // use field name when neither name nor longArgName is specified.
    @Option(description = "Start rule")
    private String startRule;

    // matched by naming convention
    public void setGrammar(final String value) {
        if (!"Csv".equals(value))
            throw new IllegalArgumentException("Only 'Csv' is acceptable!");
        grammar = value;
    }

    // ignored due to setStartRule2
    public void setStartRule(final String value) {
        startRule = value;
    }

    // Annotation is preferred to reflection/naming convention
    @Setter("startRule")
    public void setStartRule2(final String value) {
        startRule = "2-" + value;
    }

    public static void main(final String... args) {
        final BeanBuilderWithAnnotationSample sample = new BeanBuilderWithAnnotationSample();
        // mock arguments
        final String[] mockedArgs = {"-g", "Invalid_value", "--startRule", "token"};

        new BeanMenuBuilder().bean(sample).build(mockedArgs).render();

        // continue your business after all setting is done.
        System.out.println("\nBack to main, let's continue.");

    }
}
