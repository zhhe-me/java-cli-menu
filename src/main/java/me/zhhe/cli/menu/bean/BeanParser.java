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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import me.zhhe.cli.menu.util.ExceptionUtil;

/**
 * A parser to distill bean's pairs of field + setter.
 * A field having its public setter with one String type parameter will be selected.
 * <p>Beans with inheritence have not yet been supported.</p>
 * @author zhhe.me@gmail.com.
 * @since 10/8/2018
 */
public class BeanParser {

    private static final BeanParser DEFAULT = new BeanParser();

    /** just for extension and unit test. */
    BeanParser() {}

    public static BeanParser getInstance() {
        return DEFAULT;
    }

    public Collection<? extends BeanItem> parse(final Object bean) {
        final Field[] fields = bean.getClass().getDeclaredFields();
        final Map<String, Method> validSetters = extractValidSetters(bean.getClass());
        final List<BeanItem> items = new ArrayList<>();
        Arrays.stream(fields).forEach(field -> {
            final String name = field.getName();
            final Method m = validSetters.get(formatSetterName(name));
            if (m!=null)
                field.setAccessible(true);
                items.add(new BeanItem(field.getName(),
                        () -> {
                            try {
                                final Object o = field.get(bean);
                                return o == null ? null : o.toString();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                                return null;
                            }
                        },
                        s -> {
                            try {
                                m.invoke(bean, s);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                final Throwable rootException = ExceptionUtil.getInstance().getRootException(e);
                                if (rootException.getClass() == IllegalArgumentException.class)
                                    throw IllegalArgumentException.class.cast(rootException);
                            }
                        }
                ));
        });

        return items;
    }

    private Map<String, Method> extractValidSetters(final Class<?> clazz) {
        final Map<String, Method> validSetters = new HashMap<>();
        final Method[] methods = clazz.getMethods();
        for (final Method m : methods) {
            if (!m.getName().startsWith("set"))
                continue;

            if (void.class != m.getReturnType())
                continue;

            final Parameter[] params = m.getParameters();
            if (params.length != 1)
                continue;

            // FIXME: other type with related converter should be supported in future.
            if (String.class != params[0].getType())
                continue;

            validSetters.put(m.getName(), m);
        }

        return validSetters;
    }

    private String formatSetterName(final String fieldName) {
        return String.format("set%s%s",
                fieldName.substring(0, 1).toUpperCase(Locale.ENGLISH), fieldName.substring(1));
    }
}
