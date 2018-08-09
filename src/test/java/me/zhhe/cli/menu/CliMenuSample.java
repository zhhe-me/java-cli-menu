/* &copy; 2018 zhhe.me@gmail.com. */
package me.zhhe.cli.menu;

/**
 * @author zhhe.me@gmail.com.
 * @since 7/8/2018
 */
class CliMenuSample {

    private String grammar;

    private String startRule;

    public static void main(final String... args) {
        final CliMenuSample sample = new CliMenuSample();

        final MenuBuilder builder = MenuBuilder.defaultBuilder();
        builder
                .menuTitle("Set Antlr parameters")
                .item()
                    .argument("--grammar", "it's an invalid value").alias("-g").format("Grammar")
                    .value(() -> sample.grammar)
                    .header("specify grammar name")
                    .description(()->"It should be same with file name of your .g4")
                    .inputChecker(sample::setGrammar)
                    .done()
                .item()
                    .argument("--startRule").alias("-r")
                    .value(() -> sample.startRule)
                    .header("start rule name. \"token\" is a special value.")
                    .inputChecker(sample::setStartRule)
                    .done()
                .build()
                .render();

        System.out.println("\nBack to main, let's continue.");
    }

    void setGrammar(final String value) {
        if (!"Csv".equals(value))
            throw new IllegalArgumentException("Only 'Csv' is acceptable!");

        grammar = value;
    }

    void setStartRule(final String value) {
        startRule = value;
    }
}
