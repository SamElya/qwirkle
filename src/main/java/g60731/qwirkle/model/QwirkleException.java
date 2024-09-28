package g60731.qwirkle.model;

/**
 * Launch an exception if the user
 * don't follow the rule of the game quirkle
 */
public class QwirkleException extends RuntimeException {
    public QwirkleException(String s) {
        super(s);
    }
}
