/* &copy; 2018 zhhe.me@gmail.com. */
package me.zhhe.cli.menu;

/**
 * @author zhhe.me@gmail.com.
 * @since 7/8/2018
 */
class CliMenuApp {

    public static void main(final String... args) {
        final MenuBuilder builder = MenuBuilder.defaultBuilder();
        builder
                .menuTitle("Set Antlr parameters")
                .item()
                    .argument("--grammar").alias("-g").format("Grammar").value(()->"Csv")
                    .header("specify grammar name")
                    .description(()->"It should be same with file name of your .g4")
                    .inputChecker(input -> { if (!input.trim().equals("1")) throw new IllegalArgumentException("Only 1 is acceptable."); })
                    .done()
                .item()
                    .argument("--startRule").alias("-r").value("token")
                    .header("start rule name. \"token\" is a special value.")
                    .done()
                .build()
                .render();

        System.out.println("\nBack to main, let's continue.");
    }
}
