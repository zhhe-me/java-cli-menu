/* &copy; 2018 zhhe.me@gmail.com. */
package me.zhhe.cli.menu;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author zhhe.me@gmail.com.
 * @since 7/8/2018
 */
class TerminatedMenuItem extends MenuItem {

    TerminatedMenuItem(MenuItemBuilder builder) {
        super(builder);
    }

    @Override
    State getState() {
        return State.TERMINATED;
    }
}
