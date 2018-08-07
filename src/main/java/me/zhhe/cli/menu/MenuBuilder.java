/* &copy; 2018 zhhe.me@gmail.com. */
package me.zhhe.cli.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A builder for {@link Menu}.
 * @author zhhe.me@gmail.com.
 * @since 7/8/2018
 */
public class MenuBuilder {
    private final List<MenuItem> items = new ArrayList<>();
    private String title;

    private final MenuContext context = new MenuContext(new CliReader(), new CliWriter());

    /** set menu's title. */
    public MenuBuilder menuTitle(final String title) {
        this.title = title;
        return this;
    }

    /** add a {@link MenuItem}. */
    public MenuBuilder menuItem(Supplier<String> titleSupplier,
                                Supplier<String> questionSupplier,
                                Function<String, AnswerResult> answerChecker) {

        items.add(new MenuItem(context, titleSupplier, questionSupplier, answerChecker));
        return this;
    }

    /** build {@link Menu} instance. */
    public Menu build() {
        items.add(new TerminatedMenuItem(context, () -> "Done", null, null));
        return new Menu(context, title, items);
    }

}
