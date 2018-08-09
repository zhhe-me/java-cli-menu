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

import com.google.common.base.MoreObjects;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author zhhe.me@gmail.com.
 * @since 7/8/2018
 */
class CliWriter implements OutputWriter {

    private static final String START = " ";
    private static final String START_PARAM_TEXT = StringUtils.repeat(' ', 10);

    @Override
    public void close() throws IOException {
        ;
    }

    @Override
    public void printFailedChecks(Map<MenuItem, String[]> faileChecks) {
        if (faileChecks.isEmpty())
            return;

        System.out.format(" Wrong arguments:%n");
        faileChecks.forEach( (k, vs) -> System.out.format("   %s: [%s]%n       %s%n", k.argument, vs[0], vs[1]));

    }

    @Override
    public void printMainMenu(String title, List<MenuItem> items) {
//        final String formattedTitle = String.format(" *    %s   * ", title);
//        final String _s = StringUtils.repeat('=', formattedTitle.length());
//        System.out.format("%n%s%n%s%n%s%n%n", _s, formattedTitle, _s);

        int[] idx = {1};
        items.stream().forEach(e -> printMeneItem(idx[0]++, e));
    }

    private void printMeneItem(final int no, final MenuItem item) {
        System.out.format("%n%s%d) %s,%s %s [%s]%n",
                START, no, getSafeValue(item.argument), getSafeValue(item.alias),
                getSafeValue(item.format), getSafeValue(item.value));

        if (StringUtils.isNoneBlank(item.header))
            System.out.format("%s%s%n", START_PARAM_TEXT, item.header);

        final String desc = getSafeValue(item.description);
        if (StringUtils.isNotBlank(desc))
            System.out.format("%n%s%s%n", START_PARAM_TEXT, desc);
    }

    private String getSafeValue(final String value) {
        return MoreObjects.firstNonNull(value, "");
    }

    private String getSafeValue(Supplier<?> supplier) {
        return supplier==null ? "" : MoreObjects.firstNonNull(supplier.get(), "").toString();
    }

    @Override
    public void printAttachedMenuItem(String msg) {
        System.out.format("%n%s--%n%s%s%n", START, START, msg);
    }


    @Override
    public void printEnterOption() {
        System.out.format("%n%nYour input [# value]: ");
    }

    @Override
    public void printEnteredOptionWrongly(String msg) {
        System.out.format(">! %s%n", msg);
    }

    @Override
    public void printInputWrongly(String msg) {
        System.out.format("  >! %s%n", msg);
    }
}
