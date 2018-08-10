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

/**
 * @author zhhe.me@gmail.com.
 * @since 10/8/2018
 */
public class BeanBuilderSample {

    private String grammar;
    private String startRule;

    public static void main(final String... args) {
        final BeanBuilderSample sample = new BeanBuilderSample();
        // mock arguments
        final String[] mockedArgs = {"--grammar", "Invalid_value", "--startRule", "token"};

        new BeanMenuBuilder().bean(sample).build(mockedArgs).render();

        System.out.println("\nBack to main, let's continue.");
    }


    public void setGrammar(final String value) {
        if (!"Csv".equals(value))
            throw new IllegalArgumentException("Only 'Csv' is acceptable!");

        grammar = value;
    }

    public void setStartRule(final String value) {
        startRule = value;
    }
}
