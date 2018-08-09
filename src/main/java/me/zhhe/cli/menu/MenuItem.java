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
class MenuItem {

    final MenuContext context;
    final String argument;
    final String alias;
    final String format;
    final String header;
    final Supplier<?> value;
    final Supplier<String> description;
    final Consumer<String> inputChecker;

    MenuItem(final MenuItemBuilder builder) {
        this.context = builder.context;
        this.argument = builder.argument;
        this.alias = builder.alias;
        this.format = builder.format;
        this.value = builder.value;
        this.header = builder.header;
        this.description = builder.description;
        this.inputChecker = builder.inputChecker;
    }

    String getTitle() {
        return description.get();
    }

    void execute(final String input) {
        inputChecker.accept(input);
    }

    State getState() {
        return State.CONTINUE;
    }
}
