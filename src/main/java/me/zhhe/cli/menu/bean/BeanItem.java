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

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author zhhe.me@gmail.com.
 * @since 10/8/2018
 */
public class BeanItem {
    private final String name;
    private final Supplier<String> value;
    private final Consumer<String> executor;

    BeanItem(String name, Supplier<String> value, Consumer<String> executor) {
        this.name = name;
        this.value = value;
        this.executor = executor;
    }

    public String getName() {
        return name;
    }

    public Supplier<String> getValue() {
        return value;
    }

    public Consumer<String> getExecutor() {
        return executor;
    }
}
