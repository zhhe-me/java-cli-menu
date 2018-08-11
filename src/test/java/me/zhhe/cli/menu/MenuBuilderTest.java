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

import com.google.common.base.Supplier;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.*;

/**
 * @author zhhe.me@gmail.com.
 * @since 11/8/2018
 */
public class MenuBuilderTest {
    private MenuBuilder test;
    final Supplier<String> value1 = this::getArg1;
    final Consumer<String> inputChecker1 = this::setArg1;
    final Supplier<String> value2 = this::getArg2;
    final Consumer<String> inputChecker2 = this::setArg2;

    @Before
    public void setUp() throws Exception {
        test = new MyMenuBuilder();
    }

    @Test
    public void item_normal_case() {
        test.item(
                new MenuItemBuilder().argName("arg1").longArgName("longArg1")
                        .description("it's description1")
                        .value(value1)
                        .inputChecker(inputChecker1)
                        .build()
        ).item(
                new MenuItemBuilder().argName("arg2")
                        .inputChecker(inputChecker2)
                        .build()
        ).item(
                new MenuItemBuilder().longArgName("longArg3")
                        .description("it's description3")
                        .build()
        );

        final Collection<? extends MenuItem> items = test.getItems();

        assertThat(items).hasSize(3)
                .extracting("argName", "longArgName", "description")
                .containsExactly(
                        tuple("arg1", "longArg1", "it's description1"),
                        tuple("arg2", "", ""),
                        tuple("", "longArg3", "it's description3")
                );
        assertThat(items).extracting("value", "inputChecker")
                .containsExactly(
                        tuple(value1, inputChecker1),
                        tuple(null, inputChecker2),
                        tuple(null, null)
                );

    }

    @Test(expected = IllegalArgumentException.class)
    public void item_must_have_arg_or_longArg() {
        test.item(new MenuItemBuilder().description("desc").build());
    }

    @Test
    public void item_same_arg_can_not_override() {
        final String arg = "arg1";
        test.item(
                new MenuItemBuilder().argName(arg).description("desc1").build()
        ).item(
                new MenuItemBuilder().argName(arg).longArgName("longArg1")
                        .description("desc1111").value(this::getArg1)
                        .inputChecker(this::setArg1).build()
        );

        final Collection<? extends MenuItem> result = test.getItems();
        assertThat(result).hasSize(1);
        final MenuItem item = result.iterator().next();
        assertBasic(item, arg, "", "desc1");
        assertThat(item).extracting("value", "inputChecker")
                .containsExactly(null, null);
    }

    @Test
    public void item_same_longArg_can_not_override() {
        final String longArg = "longArg1";
        test.item(
                new MenuItemBuilder().longArgName(longArg).description("desc1").build()
        ).item(
                new MenuItemBuilder().argName("arg1").longArgName(longArg)
                        .description("desc1111").value(this::getArg1)
                        .inputChecker(this::setArg1).build()
        );

        final Collection<? extends MenuItem> result = test.getItems();
        assertThat(result).hasSize(1);
        final MenuItem item = result.iterator().next();
        assertBasic(item, "", longArg, "desc1");
        assertFunc(item, null, null);
    }

    @Test
    public void with_normal_case() {
        final String longArg = "longArg1";
        test.item(
                new MenuItemBuilder().longArgName(longArg)
                        .value(value1).inputChecker(inputChecker1).build()
        ).with(
                new MyMenuBuilder().item(
                        new MenuItemBuilder().argName("arg1").longArgName(longArg)
                                .description("desc1").value(value2).inputChecker(inputChecker2).build()
                )
        );

        final Collection<? extends MenuItem> result = test.getItems();

        assertThat(result).hasSize(1);
        final MenuItem item = result.iterator().next();
        assertBasic(item, "arg1", longArg, "desc1");
        assertFunc(item, value1, inputChecker1);
    }

    private void assertBasic(final MenuItem item, final String arg, final String longArg, final String desc) {
        assertThat(item).extracting("argName", "longArgName", "description")
                .containsExactly(arg, longArg, desc);
    }
    private void assertFunc(final MenuItem item, final Supplier<String> value, final Consumer<String> inputChecker) {
        assertThat(item).extracting("value", "inputChecker")
                .containsExactly(value, inputChecker);
    }

    private String getArg1() { return null; }
    private void setArg1(final String arg1) {}

    private String getArg2() { return null; }
    private void setArg2(final String arg2) {}

    private class MyMenuBuilder extends MenuBuilder {

    }
}