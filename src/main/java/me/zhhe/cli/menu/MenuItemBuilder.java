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

import org.apache.commons.lang3.StringUtils;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

/**
 * @author zhhe.me@gmail.com.
 * @since 8/8/2018
 */
public class MenuItemBuilder {

    private final MenuBuilder menuBuilder;

    final MenuContext context;
    String argument;
    String argValue;
    String alias;
    String format;
    String header;
    Supplier<?> value;
    Supplier<String> description;
    Consumer<String> inputChecker;

    MenuItemBuilder(@Nonnull final MenuBuilder menuBuilder, @Nonnull final MenuContext context) {
        this.menuBuilder = menuBuilder;
        this.context = context;
    }

    public MenuItemBuilder argument(final String argument) {
        this.argument = argument;
        return this;
    }

    public MenuItemBuilder argument(final String argument, final String argValue) {
        this.argument = argument;
        this.argValue = argValue;
        return this;
    }

    public MenuItemBuilder alias(final String alias) {
        this.alias = alias;
        return this;
    }

    public MenuItemBuilder format(final String format) {
        this.format = format;
        return this;
    }

    public MenuItemBuilder value(String value) {
        this.value = () -> value;
        return this;
    }

    public MenuItemBuilder value(Supplier<?> value) {
        this.value = value;
        return this;
    }

    public MenuItemBuilder header(final String header) {
        this.header = header;
        return this;
    }

    public MenuItemBuilder description(final Supplier<String> description) {
        this.description = description;
        return this;
    }

    /** specifiy input checker which should throw {@link IllegalArgumentException} in case of invalid input. */
    public MenuItemBuilder inputChecker(final Consumer<String> inputChecker) {
        this.inputChecker = inputChecker;
        return this;
    }

    public MenuBuilder done() {
        final MenuItem item = new MenuItem(this);
        menuBuilder.item(item);

        if (StringUtils.isNotBlank(argValue)) {
            try {
                item.execute(argValue);
            } catch (IllegalArgumentException e) {
                menuBuilder.logFailedCheck(item, new String[]{argValue, e.getMessage()});
            }
        }

        return menuBuilder;
    }
}
