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

import com.google.common.base.Preconditions;

import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

/**
 * @author zhhe.me@gmail.com.
 * @since 7/8/2018
 */
public class Menu {

    private final MenuContext context;
    private final List<MenuItem> items;
    private final Map<MenuItem, String[]> failedChekcs;

    Menu(@Nonnull final MenuContext context,
         @Nonnull final List<MenuItem> items, @Nonnull final Map<MenuItem, String[]> failedChekcs) {
        Preconditions.checkNotNull(context, "context");
        Preconditions.checkArgument(items!=null && !items.isEmpty(), "at lease 1 items");

        this.context = context;
        this.items = Collections.unmodifiableList(items);
        this.failedChekcs = failedChekcs;
    }


    public void render() {
        context.getOutputWriter().printFailedChecks(failedChekcs);
        failedChekcs.clear();

        renderMenu();

        execute();
    }

    private void renderMenu() {
        context.getOutputWriter().printMainMenu(items);

        context.getOutputWriter().printAttachedMenuItem("R): refresh menu;  X): exit");
    }

    private void execute() {
        final String msg = String.format("Invalid option. It must be 1~%d.", items.size());

        while (true) {
            context.getOutputWriter().printEnterOption();
            final String input = context.getInputReader().read().trim();
            if (StringUtils.isBlank(input)) {
                context.getOutputWriter().printEnteredOptionWrongly(msg);
                continue;
            }
            int i = 0;

            final int idx = input.indexOf(' ');
            final String num = idx==-1 ? input : input.substring(0, idx);
            if ("R".equalsIgnoreCase(num)) {
                renderMenu();
                continue;
            }
            if ("X".equalsIgnoreCase(num)) {
                break;
            }

            try {
                i = Integer.parseInt(num);
            } catch (Exception e) {
                context.getOutputWriter().printEnteredOptionWrongly(msg);
                continue;
            }
            if (i < 1 || i > items.size()) {
                context.getOutputWriter().printEnteredOptionWrongly(msg);
                continue;
            }

            final MenuItem menuItem = items.get(i - 1);

            try {
                menuItem.execute(input.substring(idx + 1).trim());
            } catch (IllegalArgumentException e) {
                context.getOutputWriter().printInputWrongly(e.getMessage());
                continue;
            }
        }
    }

}
