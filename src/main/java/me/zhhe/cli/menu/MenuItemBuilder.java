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

import javax.annotation.Nonnull;

/**
 * @author zhhe.me@gmail.com.
 * @since 8/8/2018
 */
public class MenuItemBuilder {
    String argName;
    String longArgName;
    Supplier<?> value;
    String description;
    Consumer<String> inputChecker;

    /** argName name which doesn't start with any '-'. */
    public MenuItemBuilder argName(final String argument) {
        this.argName = argument;
        return this;
    }

    /** long argName name which doesn't start with any '-'. */
    public MenuItemBuilder longArgName(final String longArgName) {
        this.longArgName = longArgName;
        return this;
    }

    public MenuItemBuilder value(Supplier<?> value) {
        this.value = value;
        return this;
    }

    public MenuItemBuilder description(final String description) {
        this.description = description;
        return this;
    }

    /** specifiy input checker which should throw {@link IllegalArgumentException} in case of invalid input. */
    public MenuItemBuilder inputChecker(final Consumer<String> inputChecker) {
        this.inputChecker = inputChecker;
        return this;
    }

    public MenuItem build() {
        return new MenuItem(this);
    }
}
