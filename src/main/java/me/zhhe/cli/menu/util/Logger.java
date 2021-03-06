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

package me.zhhe.cli.menu.util;

/**
 * @author zhhe.me@gmail.com.
 * @since 11/8/2018
 */
public class Logger {

    private static final Logger DEFAULT = new Logger();

    /** only for extension and unit test. */
    protected Logger() {}

    public static final Logger getInstance() {
        return DEFAULT;
    }

    public void debug(final String msg) {
        System.out.format("DEBUG %s%n", msg);
    }

    public void info(final String msg) {
        System.out.format("INFO %s%n", msg);
    }

    public void warning(final String msg) {
        System.out.format("WARN %s%n", msg);
    }

    public void error(final String msg) {
        System.out.format("ERROR %s%n", msg);
    }

}
