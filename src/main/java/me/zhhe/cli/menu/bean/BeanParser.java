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

import org.apache.commons.lang3.StringUtils;

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
import java.util.function.Function;
import java.util.function.Predicate;

import me.zhhe.cli.menu.Setter;
import me.zhhe.cli.menu.Option;
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
        final Class<?> clazz = bean.getClass();
        final Field[] fields = clazz.getDeclaredFields();
        final Method[] methods = clazz.getMethods();

        final Map<String, Method> settersWithAnnotation = extractMethods(methods,
                m -> m.isAnnotationPresent(Setter.class),
                m -> m.getAnnotation(Setter.class).value().trim());
        final Map<String, Method> settersViaNaming = extractMethods(methods,
                m -> m.getName().startsWith("set"), m -> m.getName());

        final List<BeanItem> items = new ArrayList<>();
        Arrays.stream(fields).forEach(field -> {
            final String fieldName = field.getName();
            final Option option = field.getAnnotation(Option.class);
            final String argName = option!=null ? option.name() : "";

            // in case of:
            //    a) option is null
            //    b) or both argName & longArgName is empty
            // fieldName will be used as longArgName.
            final String longArgName =
                    (option == null ||
                            (StringUtils.isBlank(option.longArgName())
                                    && StringUtils.isBlank(option.name()))
                    ) ? fieldName : option.longArgName();

            final String description = option!=null ? option.description() : "";
            // annotation has high priority than naming convention.
            final Method m = settersWithAnnotation.containsKey(fieldName) ?
                    settersWithAnnotation.get(fieldName) : settersViaNaming.get(formatSetterName(fieldName));

            if (m != null) {
                field.setAccessible(true);
                items.add(new BeanItem(argName, longArgName, description,
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
            }
        });

        return items;
    }

    private Map<String, Method> extractMethods(final Method[] methods,
        Predicate<Method> predict, Function<Method, String> key) {
        final Map<String, Method> ret = new HashMap<>();
        for (final Method m : methods) {
            if (!predict.test(m))
                continue;

            if (void.class != m.getReturnType())
                continue;

            final Parameter[] params = m.getParameters();
            if (params.length != 1)
                continue;

            // FIXME: other type with related converter should be supported in future.
            if (String.class != params[0].getType())
                continue;

            ret.put(key.apply(m), m);
        }

        return ret;
    }

    private String formatSetterName(final String fieldName) {
        return String.format("set%s%s",
                fieldName.substring(0, 1).toUpperCase(Locale.ENGLISH), fieldName.substring(1));
    }
}
