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

package me.zhhe.cli.menu.bean;

import com.google.common.base.MoreObjects;

import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

/**
 * @author zhhe.me@gmail.com.
 * @since 10/8/2018
 */
public class BeanItem {
    @Nonnull private final String argName;
    @Nonnull private final String longArgName;
    @Nonnull private final String description;
    private final Supplier<String> value;
    private final Consumer<String> executor;

    BeanItem(String argName, String longArgName, String description,
             Supplier<String> value, Consumer<String> executor) {
        this.argName = MoreObjects.firstNonNull(argName, "").trim();
        this.longArgName = MoreObjects.firstNonNull(longArgName, "").trim();
        this.description = MoreObjects.firstNonNull(description, "").trim();
        this.value = value;
        this.executor = executor;
    }

    public String getLongArgName() {
        return longArgName;
    }

    public String getArgName() {
        return argName;
    }

    public String getDescription() {
        return description;
    }

    public Supplier<String> getValue() {
        return value;
    }

    public Consumer<String> getExecutor() {
        return executor;
    }
}
