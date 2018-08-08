/* &copy; 2018 zhhe.me@gmail.com. */
package me.zhhe.cli.menu;

import java.util.function.Function;
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
    final Function<String, InputResult> answerChecker;

    MenuItem(final MenuItemBuilder builder) {
        this.context = builder.context;
        this.argument = builder.argument;
        this.alias = builder.alias;
        this.format = builder.format;
        this.value = builder.value;
        this.header = builder.header;
        this.description = builder.description;
        this.answerChecker = builder.inputChecker;
    }

    String getTitle() {
        return description.get();
    }

    InputResult execute(final String input) {
        return answerChecker.apply(input);
    }

    State getState() {
        return State.CONTINUE;
    }
}
