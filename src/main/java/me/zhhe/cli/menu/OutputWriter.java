/* &copy; 2018 Cisco Systems, Inc. */
package me.zhhe.cli.menu;

import java.io.Closeable;
import java.util.List;

/**
 * @author zhhe.me@gmail.com.
 * @since 7/8/2018
 */
public interface OutputWriter extends Closeable {

    void printMainMenu(String title, List<MenuItem> items);

    void printAttachedMenuItem(String msg);

    void printEnterOption();

    void printEnteredOptionWrongly(String msg);

    void printInputWrongly(String msg);

}
