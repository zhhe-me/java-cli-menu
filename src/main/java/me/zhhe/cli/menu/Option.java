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

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author zhhe.me@gmail.com.
 * @since 12/8/2018
 */
@Retention(RUNTIME)
@Target({FIELD})
public @interface Option {
    /** argument name. */
    String name() default "";

    /** long argument name.
     * when neither {@link #name()} nor long arg name is specified,
     * field name will be used as long arg name. */
    String longArgName() default "";

    /** description. */
    String description() default "";
}
