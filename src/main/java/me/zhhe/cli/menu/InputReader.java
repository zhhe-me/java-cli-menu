/* &copy; 2018 Cisco Systems, Inc. */
package me.zhhe.cli.menu;

import java.io.Closeable;

/**
 * @author zhhe.me@gmail.com.
 * @since 7/8/2018
 */
interface InputReader extends Closeable {

    String read();

}
