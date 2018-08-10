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

package me.zhhe.cli.menu;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author zhhe.me@gmail.com.
 * @since 7/8/2018
 */
public class MenuItem {

    final String argName;
    final String longArgName;
    final String format;
    final String header;
    final Supplier<?> value;
    final Supplier<String> description;
    final Consumer<String> inputChecker;

    MenuItem(final MenuItemBuilder builder) {
        this.argName = builder.argName;
        this.longArgName = builder.longArgName;
        this.format = builder.format;
        this.value = builder.value;
        this.header = builder.header;
        this.description = builder.description;
        this.inputChecker = builder.inputChecker;
    }

    void execute(final String input) {
        inputChecker.accept(input);
    }
}
