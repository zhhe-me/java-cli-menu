/* &copy; 2018 zhhe.me@gmail.com. */
package me.zhhe.cli.menu;

/**
 * @author zhhe.me@gmail.com.
 * @since 7/8/2018
 */
class CliMenuApp {

    public static void main(final String... args) {
        final MenuBuilder builder = new MenuBuilder();
        builder
                .menuTitle("Set Antlr parameters")
                .menuItem(()->"Menu 1",
                        ()->"Question 1",
                        ac -> ac.equals("1") ? AnswerResult.CORRECT : new AnswerResult("Bad"))
                .build()
                .render();

        System.out.println("\nBack to main, let's continue.");
    }
}
