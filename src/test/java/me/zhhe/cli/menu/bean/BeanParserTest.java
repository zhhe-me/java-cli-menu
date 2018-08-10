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

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.*;

/**
 * @author zhhe.me@gmail.com.
 * @since 10/8/2018
 */
public class BeanParserTest {

    private BeanParser test;

    @Before
    public void setUp() throws Exception {
        test = BeanParser.getInstance();
    }

    @Test
    public void parse() {
        final MyBean bean = new MyBean();
        final Collection<? extends BeanItem> items = test.parse(bean);
        assertThat(items).extracting("name").containsOnly("firstName", "title");

        final String firstName = "it's first name";
        items.stream().filter(item -> item.getName().equals("firstName"))
                .findFirst().get().getExecutor().accept(firstName);
        assertThat(bean.firstName).isEqualTo(firstName);

        final String title = "it's my title";
        items.stream().filter(item -> item.getName().equals("title"))
                .findFirst().get().getExecutor().accept(title);
        assertThat(bean.title).isEqualTo(title);
    }
}