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

import java.util.Collection;

import me.zhhe.cli.menu.bean.BeanItem;
import me.zhhe.cli.menu.bean.BeanParser;

/**
 * A builder which can extract {@link MenuItem} from bean instance
 * which has pairs of field + setter (public and one String paramenter with void return).
 * @author zhhe.me@gmail.com.
 * @since 10/8/2018
 */
public class BeanMenuBuilder extends MenuBuilder {

    public BeanMenuBuilder bean(Object bean) {
        final Collection<? extends BeanItem> beanItems = BeanParser.getInstance().parse(bean);
        for (final BeanItem beanItem : beanItems) {
            final MenuItemBuilder itemBuilder = new MenuItemBuilder(null)
                    .longArgName(beanItem.getName())
                    .value(beanItem.getValue())
                    .inputChecker(beanItem.getExecutor());
            item(new MenuItem(itemBuilder));
        }
        return this;
    }

}
