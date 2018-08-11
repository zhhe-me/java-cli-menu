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
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A builder for {@link Menu}.
 * @author zhhe.me@gmail.com.
 * @since 7/8/2018
 */
public class BasicMenuBuilder extends MenuBuilder {

    public final BasicMenuBuilder item(final String arg, final String desc) {
        return item(arg, null, desc);
    }

    public final BasicMenuBuilder item(final String arg, final String longArg, final String desc) {
        return item(arg, longArg, desc, null, null);
    }

    public final BasicMenuBuilder item(final String arg, final String desc,
                                 final Supplier<String> value, final Consumer<String> executor) {
        return item(arg, null, desc, value, executor);
    }

    public final BasicMenuBuilder item(final String arg, final String longArg, final String desc,
                                 final Supplier<String> value, final Consumer<String> executor) {
        final MenuItem item = new MenuItemBuilder().argName(arg).longArgName(longArg)
                .value(value).inputChecker(executor).description(desc)
                .build();
        item(item);
        return this;
    }

}
