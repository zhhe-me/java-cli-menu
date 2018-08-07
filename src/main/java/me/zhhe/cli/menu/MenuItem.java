/* &copy; 2018 zhhe.me@gmail.com. */
package me.zhhe.cli.menu;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author zhhe.me@gmail.com.
 * @since 7/8/2018
 */
class MenuItem {

    private final MenuContext context;
    private final Supplier<String> titleSupplier;
    private final Supplier<String> questionSupplier;
    private final Function<String, AnswerResult> answerChecker;

    MenuItem(MenuContext context, Supplier<String> titleSupplier,
             Supplier<String> questionSupplier, Function<String, AnswerResult> answerConsumer) {
        this.context = context;
        this.titleSupplier = titleSupplier;
        this.questionSupplier = questionSupplier;
        this.answerChecker = answerConsumer;
    }

    String getTitle() {
        return titleSupplier.get();
    }

    void execute() {
        final String question = String.format("%s.[%s]", questionSupplier.get(), "default");
        context.getOutputWriter().printQuestion(question);

        final String answer = context.getInputReader().read();
        final AnswerResult result = answerChecker.apply(answer);
        if (!result.isCorrect()) {
            context.getOutputWriter().printAnswererWrongly(result.getReason());
            execute();
        }
    }

    State getState() {
        return State.CONTINUE;
    }
}
