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

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A builder for {@link Menu}.
 * @author zhhe.me@gmail.com.
 * @since 7/8/2018
 */
public class MenuBuilder {
    private final List<MenuItem> items = new ArrayList<>();
    private String title;
    private final Map<MenuItem, String[]> failedChecks = new HashMap<>();

    private final MenuContext context;
    private String[] args;

    /** default builder for CLI. */
    public static MenuBuilder defaultBuilder() {
        return new MenuBuilder(new MenuContext(new CliReader(), new CliWriter()));
    }

    public MenuBuilder(final MenuContext context) {
        this.context = context;
    }

    /** inject arguments from command. Must be called before injecting any menu item. */
    public MenuBuilder args(final String... args) {
        this.args = Arrays.copyOf(args, args.length);
        return this;
    }

    /** set menu's title. */
    public MenuBuilder menuTitle(final String title) {
        this.title = title;
        return this;
    }

    public MenuItemBuilder item() {
        return new MenuItemBuilder(this, context);
    }

    MenuBuilder item(final MenuItem item) {
        items.add(item);
        return this;
    }

    void logFailedCheck(final MenuItem item, final String[] msg) {
        failedChecks.put(item, msg);
    }

    /** build {@link Menu} instance. */
    public Menu build() {
        if (items.isEmpty())
            throw new IllegalStateException("No menu item.");

        injectArgumentToItems();

        return new Menu(context, title, items, failedChecks);
    }

    private void injectArgumentToItems() {
        if (ArrayUtils.isEmpty(args))
            return;

        final Options options = new Options();
        final Map<Option, MenuItem> itemsByOption = new HashMap<>();
        for (final MenuItem item : items) {
            final Option.Builder builder = Option.builder(item.argName).desc(item.header);
            if (StringUtils.isNotBlank(item.longArgName))
                builder.longOpt(item.longArgName);
            final Option option = builder.build();
            options.addOption(option);
            itemsByOption.put(option, item);
        }

        CommandLine cmd = null;
        try {
            cmd = new DefaultParser().parse(options, args);
        } catch (ParseException e) {
            throw new RuntimeException("parse arguments failed.", e);
        }

        final Option[] parsedOptions = cmd.getOptions();
        for (int i = 0; i < parsedOptions.length; i++) {
            final String argValue = cmd.getArgs()[i];
            final MenuItem item = itemsByOption.get(parsedOptions[i]);
            if (StringUtils.isNotEmpty(argValue)) {
                try {
                    item.execute(argValue);
                } catch (IllegalArgumentException e) {
                    logFailedCheck(item, new String[]{argValue, e.getMessage()});
                }
            }
        }

    }

}
