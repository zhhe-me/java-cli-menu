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

import me.zhhe.cli.menu.BasicMenuBuilder;
import me.zhhe.cli.menu.MenuItemBuilder;

/**
 * @author zhhe.me@gmail.com.
 * @since 7/8/2018
 */
class BasicBuilderSample {

    private String grammar;
    private String startRule;

    public static void main(final String... args) {
        final BasicBuilderSample sample = new BasicBuilderSample();

        // mock arguments
        final String[] mockedArgs = {"-g", "Invalid_value", "--startRule", "token"};

        final BasicMenuBuilder builder = new BasicMenuBuilder();
        builder
            .item(new MenuItemBuilder()
                    .argName("g")
                    .longArgName("grammar")
                    .value(() -> sample.grammar)
                    .description("It should be same with file name of your .g4")
                    .inputChecker(sample::setGrammar)
                    .build()
            )
            .item(new MenuItemBuilder()
                    .argName("r").longArgName("startRule")
                    .value(() -> sample.startRule)
                    .inputChecker(sample::setStartRule)
                    .build()
            )
            .build(mockedArgs)
            .render();

        System.out.println("\nBack to main, let's continue.");
    }

    void setGrammar(final String value) {
        if (!"Csv".equals(value))
            throw new IllegalArgumentException("Only 'Csv' is acceptable!");

        grammar = value;
    }

    void setStartRule(final String value) {
        startRule = value;
    }
}
