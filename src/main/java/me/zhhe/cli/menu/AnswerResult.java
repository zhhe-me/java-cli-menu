/* &copy; 2018 zhhe.me@gmail.com. */
package me.zhhe.cli.menu;

/**
 * @author zhhe.me@gmail.com.
 * @since 7/8/2018
 */
public class AnswerResult {

    public static final AnswerResult CORRECT = new AnswerResult(true, null);

    private final boolean correct;
    private final String reason;

    private AnswerResult(boolean correct, String reason) {
        this.correct = correct;
        this.reason = reason;
    }

    public AnswerResult(final String reason) {
        this(false, reason);
    }

    public boolean isCorrect() {
        return correct;
    }

    public String getReason() {
        return reason;
    }
}
