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

import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.zhhe.cli.menu.util.Logger;

/**
 * @author zhhe.me@gmail.com.
 * @since 10/8/2018
 */
public abstract class MenuBuilder {
    protected final Logger logger = Logger.getInstance();
    protected final MenuContext context = new MenuContext(new CliReader(), new CliWriter());

    private final List<MenuItem> items = new ArrayList<>();
    private final Map<MenuItem, String[]> failedChecks = new HashMap<>();

    private String[] args;

    private final Map<String, MenuItem> itemsByArg = new HashMap<>();
    private final Map<String, MenuItem> itemsByLongArg = new HashMap<>();


    /**
     * get assistant from other builder. If same arg's item alread exists,
     * the one in assistant builder will be merged into current one. Current one will win if conflict.
     */
    public final MenuBuilder with(MenuBuilder builder) {
        final Collection<MenuItem> augItems = MoreObjects
                .firstNonNull(builder.items, Collections.emptyList());
        for (final MenuItem item : augItems) {
            final String argName = item.argName;
            if (itemsByArg.containsKey(argName)) {
                copy(item, itemsByArg.get(argName));
                continue;
            }
            final String longArgName = item.longArgName;
            if (itemsByLongArg.containsKey(longArgName)) {
                copy(item, itemsByLongArg.get(longArgName));
                continue;
            }
            item(item);
        }

        return this;
    }

    private void copy(final MenuItem from, final MenuItem to) {
        if (StringUtils.isBlank(to.argName) && StringUtils.isNotBlank(from.argName)) {
            to.argName = from.argName;
        }
        if (StringUtils.isBlank(to.longArgName) && StringUtils.isNotBlank(from.longArgName)) {
            to.longArgName = from.longArgName;
        }
        if (StringUtils.isBlank(to.description) && StringUtils.isNotBlank(from.description)) {
            to.description = from.description;
        }
        if (to.value == null && from.value != null) {
            to.value = from.value;
        }
        if (to.inputChecker == null && from.inputChecker != null) {
            to.inputChecker = from.inputChecker;
        }
    }

    public final MenuBuilder item(MenuItem item) {
        final String argName = item.argName;
        if (itemsByArg.containsKey(argName)) {
            logger.warning(String.format("duplicated arg: %s", argName));
            return this;
        }
        final String longArgName = item.longArgName;
        if (itemsByLongArg.containsKey(longArgName)) {
            logger.warning(String.format("duplicated arg: %s", longArgName));
            return this;
        }
        if (StringUtils.isBlank(argName) && StringUtils.isBlank(longArgName))
            throw new IllegalArgumentException("arg or longArg must be supplied.");

        items.add(item);
        if (StringUtils.isNotBlank(argName)) {
            itemsByArg.put(argName, item);
        }
        if (StringUtils.isNotBlank(longArgName)) {
            itemsByLongArg.put(longArgName, item);
        }
        return this;
    }

    /** onlu for unit test. */
    Collection<? extends MenuItem> getItems() {
        return items;
    }

    /** build {@link Menu} instance. */
    public final Menu build(String... args) {
        if (items.isEmpty())
            throw new IllegalStateException("No menu item.");

        this.args = Arrays.copyOf(args, args.length);

        injectArgumentToItems();

        return new Menu(context,items, failedChecks);
    }

    private void injectArgumentToItems() {
        if (ArrayUtils.isEmpty(args))
            return;

        logger.info(String.format("args: %s", Joiner.on(' ').join(args)));

        final Options options = new Options();
        final Map<Option, MenuItem> itemsByOption = new HashMap<>();
        for (final MenuItem item : items) {
            final Option.Builder builder = Option.builder(item.argName).desc(item.description).hasArg();
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
