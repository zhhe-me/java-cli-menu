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
 * @since 10/8/2018
 */
public class ExceptionUtil {

    private static final ExceptionUtil DEFAULT = new ExceptionUtil();

    /** only for extension and unit test. */
    protected ExceptionUtil() {}

    public static ExceptionUtil getInstance() {
        return DEFAULT;
    }

    public Throwable getRootException(final Throwable e) {
        final int maxLevel = 9;
        int i = 0;
        Throwable ce = e;
        while (i < maxLevel) {
            final Throwable root = ce.getCause();
            if (root == null)
                return ce;

            ce = root;
            i++;
        }

        return ce;
    }

}
