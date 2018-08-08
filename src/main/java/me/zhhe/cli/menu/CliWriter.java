/* &copy; 2018 zhhe.me@gmail.com. */
package me.zhhe.cli.menu;

import com.google.common.base.MoreObjects;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
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
