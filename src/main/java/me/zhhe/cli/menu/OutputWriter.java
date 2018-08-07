/* &copy; 2018 Cisco Systems, Inc. */
package me.zhhe.cli.menu;

import java.io.Closeable;
import java.util.List;

/**
 * @author zhhe.me@gmail.com.
 * @since 7/8/2018
 */
public interface OutputWriter extends Closeable {

    void printMainMenu(String title, List<String> items);

    void printEnterOption();

    void printEnteredOptionWrongly(String msg);

    void printEnteredMenuItem(String msg);

    void printQuestion(String msg);

    void printAnswererWrongly(String msg);

}
