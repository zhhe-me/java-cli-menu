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

import org.junit.Before;
import org.junit.Test;

import me.zhhe.cli.menu.util.ExceptionUtil;

import static org.assertj.core.api.Assertions.*;

/**
 * @author zhhe.me@gmail.com.
 * @since 10/8/2018
 */
public class ExceptionUtilTest {
    private ExceptionUtil test;

    @Before
    public void setUp() {
        test = ExceptionUtil.getInstance();
    }

    @Test
    public void getRootException() {
        final IllegalAccessException rootCause = new IllegalAccessException("root msg");
        final RuntimeException exception = new RuntimeException("outer msg", rootCause);

        final Throwable result = test.getRootException(exception);
        assertThat(result).isSameAs(rootCause);
    }
}