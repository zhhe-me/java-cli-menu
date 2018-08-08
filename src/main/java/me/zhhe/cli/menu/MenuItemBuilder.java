/* &copy; 2018 zhhe.me@gmail.com. */
package me.zhhe.cli.menu;

import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

/**
 * @author zhhe.me@gmail.com.
 * @since 8/8/2018
 */
public class MenuItemBuilder {

    private final MenuBuilder menuBuilder;

    final MenuContext context;
    String argument;
    String alias;
    String format;
    String header;
    Supplier<?> value;
    Supplier<String> description;
    Function<String, InputResult> inputChecker;

    MenuItemBuilder(@Nonnull final MenuBuilder menuBuilder, @Nonnull final MenuContext context) {
        this.menuBuilder = menuBuilder;
        this.context = context;
    }

    public MenuItemBuilder argument(final String argument) {
        this.argument = argument;
        return this;
    }

    public MenuItemBuilder alias(final String alias) {
        this.alias = alias;
        return this;
    }

    public MenuItemBuilder format(final String format) {
        this.format = format;
        return this;
    }

    public MenuItemBuilder value(String value) {
        this.value = () -> value;
        return this;
    }

    public MenuItemBuilder value(Supplier<?> value) {
        this.value = value;
        return this;
    }

    public MenuItemBuilder header(final String header) {
        this.header = header;
        return this;
    }

    public MenuItemBuilder description(final Supplier<String> description) {
        this.description = description;
        return this;
    }

    public MenuItemBuilder inputChecker(final Function<String, InputResult> inputChecker) {
        this.inputChecker = inputChecker;
        return this;
    }

    public MenuBuilder done() {
        menuBuilder.item(new MenuItem(this));
        return menuBuilder;
    }
}
