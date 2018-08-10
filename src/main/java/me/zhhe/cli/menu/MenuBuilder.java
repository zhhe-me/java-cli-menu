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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhhe.me@gmail.com.
 * @since 10/8/2018
 */
public abstract class MenuBuilder {
    protected final MenuContext context = new MenuContext(new CliReader(), new CliWriter());

    private List<MenuBuilder> chainedBuilders = new ArrayList<>();
    private final List<MenuItem> items = new ArrayList<>();
    private final Map<MenuItem, String[]> failedChecks = new HashMap<>();

    private String[] args;


    public MenuBuilder with(MenuBuilder chainedBuilder) {
        chainedBuilders.add(chainedBuilder);
        return this;
    }

    public MenuBuilder item(MenuItem item) {
        items.add(item);
        return this;
    }

    /** build {@link Menu} instance. */
    public Menu build(String... args) {
        if (items.isEmpty())
            throw new IllegalStateException("No menu item.");

        this.args = Arrays.copyOf(args, args.length);

        injectArgumentToItems();

        return new Menu(context,items, failedChecks);
    }

    private void injectArgumentToItems() {
        if (ArrayUtils.isEmpty(args))
            return;

        final Options options = new Options();
        final Map<Option, MenuItem> itemsByOption = new HashMap<>();
        for (final MenuItem item : items) {
            final Option.Builder builder = Option.builder(item.argName).desc(item.header).hasArg();
            if (StringUtils.isNotBlank(item.longArgName))
                builder.longOpt(item.longArgName);
            final Option option = builder.build();
            options.addOption(option);
            itemsByOption.put(option, item);
        }

        CommandLine cmd = null;
        try {
            cmd = new DefaultParser().parse(options, args, true);
        } catch (ParseException e) {
            throw new RuntimeException("parse arguments failed.", e);
        }

        for (final Option option : cmd.getOptions()) {
            final MenuItem item = itemsByOption.get(option);
            final String argValue = option.getValue();
            if (StringUtils.isNotEmpty(argValue)) {
                try {
                    item.execute(argValue);
                } catch (IllegalArgumentException e) {
                    failedChecks.put(item, new String[]{argValue, e.getMessage()});
                }
            }
        }
    }
}
