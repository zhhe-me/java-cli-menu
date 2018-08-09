/* &copy; 2018 zhhe.me@gmail.com. */
package me.zhhe.cli.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A builder for {@link Menu}.
 * @author zhhe.me@gmail.com.
 * @since 7/8/2018
 */
public class MenuBuilder {
    private final List<MenuItem> items = new ArrayList<>();
    private String title;
    private final Map<MenuItem, String[]> failedChecks = new HashMap<>();

    private final MenuContext context;

    /** default builder for CLI. */
    public static MenuBuilder defaultBuilder() {
        return new MenuBuilder(new MenuContext(new CliReader(), new CliWriter()));
    }

    public MenuBuilder(final MenuContext context) {
        this.context = context;
    }

    /** set menu's title. */
    public MenuBuilder menuTitle(final String title) {
        this.title = title;
        return this;
    }

    public MenuItemBuilder item() {
        return new MenuItemBuilder(this, context);
    }

    MenuBuilder item(final MenuItem item) {
        items.add(item);
        return this;
    }

    void logFailedCheck(final MenuItem item, final String[] msg) {
        failedChecks.put(item, msg);
    }

    /** build {@link Menu} instance. */
    public Menu build() {
        return new Menu(context, title, items, failedChecks);
    }

}
