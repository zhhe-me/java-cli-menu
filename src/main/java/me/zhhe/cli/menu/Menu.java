/* &copy; 2018 zhhe.me@gmail.com. */
package me.zhhe.cli.menu;

import com.google.common.base.Preconditions;

import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

/**
 * @author zhhe.me@gmail.com.
 * @since 7/8/2018
 */
public class Menu {

    private final MenuContext context;
    private final String title;
    private final List<MenuItem> items;

    Menu(@Nonnull final MenuContext context, @Nonnull final String title,
         @Nonnull final List<MenuItem> items) {
        Preconditions.checkNotNull(context, "context");
        Preconditions.checkArgument(StringUtils.isNotBlank(title), "title must have valid value.");
        Preconditions.checkArgument(items!=null && !items.isEmpty(), "at lease 1 items");

        this.context = context;
        this.title = title.trim();
        this.items = Collections.unmodifiableList(items);
    }

    public void render() {
        final List<String> itemTitles = items.stream().map(item -> item.getTitle())
                .collect(Collectors.toList());
        context.getOutputWriter().printMainMenu(title, itemTitles);


        while (true) {
            final MenuItem menuItem = selectMenuItem();
            menuItem.execute();

            if (State.TERMINATED == menuItem.getState())
                break;
        }

        context.close();
    }

    private MenuItem selectMenuItem() {
        context.getOutputWriter().printEnterOption();
        final String line = context.getInputReader().read();
        final String msg = String.format("Invalid option. It must be 1~%d.", items.size());
        if (StringUtils.isBlank(line)) {
            context.getOutputWriter().printEnteredOptionWrongly(msg);
            return selectMenuItem();
        }
        int i = 0;
        try {
            i = Integer.parseInt(line.trim());
        } catch (Exception e) {
            context.getOutputWriter().printEnteredOptionWrongly(msg);
            return selectMenuItem();
        }
        if (i < 1 || i > items.size()) {
            context.getOutputWriter().printEnteredOptionWrongly(msg);
            return selectMenuItem();
        }

        final MenuItem menuItem = items.get(i - 1);
        context.getOutputWriter().printEnteredMenuItem(menuItem.getTitle());
        return menuItem;
    }

}
