/* &copy; 2018 zhhe.me@gmail.com. */
package me.zhhe.cli.menu;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author zhhe.me@gmail.com.
 * @since 7/8/2018
 */
class MenuItem {

    final MenuContext context;
    final String argument;
    final String alias;
    final String format;
    final String header;
    final Supplier<?> value;
    final Supplier<String> description;
    final Consumer<String> inputChecker;

    MenuItem(final MenuItemBuilder builder) {
        this.context = builder.context;
        this.argument = builder.argument;
        this.alias = builder.alias;
        this.format = builder.format;
        this.value = builder.value;
        this.header = builder.header;
        this.description = builder.description;
        this.inputChecker = builder.inputChecker;
    }

    String getTitle() {
        return description.get();
    }

    void execute(final String input) {
        inputChecker.accept(input);
    }

    State getState() {
        return State.CONTINUE;
    }
}
