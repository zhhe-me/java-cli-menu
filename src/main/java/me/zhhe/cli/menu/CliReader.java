/* &copy; 2018 zhhe.me@gmail.com. */
package me.zhhe.cli.menu;

import java.io.Closeable;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author zhhe.me@gmail.com.
 * @since 7/8/2018
 */
class CliReader implements InputReader {
    private final Scanner scanner = new Scanner(System.in);

    public String read() {
        return scanner.nextLine();
    }

    @Override
    public void close() throws IOException {
        scanner.close();
    }
}
