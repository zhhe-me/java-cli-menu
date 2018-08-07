/* &copy; 2018 zhhe.me@gmail.com. */
package me.zhhe.cli.menu;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;

/**
 * @author zhhe.me@gmail.com.
 * @since 7/8/2018
 */
class CliWriter implements OutputWriter {


    @Override
    public void close() throws IOException {
        ;
    }

    @Override
    public void printMainMenu(String title, List<String> items) {
        final String formattedTitle = String.format(" *    %s   * ", title);
        final String _s = StringUtils.repeat('=', formattedTitle.length());
        System.out.format("%n%s%n%s%n%s%n%n", _s, formattedTitle, _s);

        for (int i = 0; i < items.size(); i++) {
            System.out.format("%d) %s%n", i+1, items);
        }
    }

    @Override
    public void printEnterOption() {
        System.out.format("%n%nEnter option: ");
    }

    @Override
    public void printEnteredOptionWrongly(String msg) {
        System.out.format(">! %s%n", msg);
    }

    @Override
    public void printEnteredMenuItem(String title) {
        System.out.format("[Item: %s]%n", title);
    }

    @Override
    public void printQuestion(String msg) {
        System.out.format("  %s: ", msg);
    }

    @Override
    public void printAnswererWrongly(String msg) {
        System.out.format("  >! %s%n", msg);
    }
}
