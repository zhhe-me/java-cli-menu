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

import com.google.common.base.MoreObjects;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author zhhe.me@gmail.com.
 * @since 7/8/2018
 */
public class MenuItem {

    String argName;
    String longArgName;
    String description;
    Supplier<?> value;
    Consumer<String> inputChecker;

    MenuItem(final MenuItemBuilder builder) {
        this.argName = MoreObjects.firstNonNull(builder.argName, "").trim();
        this.longArgName = MoreObjects.firstNonNull(builder.longArgName, "").trim();
        this.description = MoreObjects.firstNonNull(builder.description, "").trim();
        this.value = builder.value;
        this.inputChecker = builder.inputChecker;
    }

    void execute(final String input) {
        inputChecker.accept(input);
    }
}
