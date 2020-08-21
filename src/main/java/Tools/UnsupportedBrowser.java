package Tools;

/**
 * 自定义异常，不支持的浏览器异常
 */
public class UnsupportedBrowser extends Exception {

    private static final long serialVersionUID = 509301975823976894L;

    public UnsupportedBrowser() {
        super();
    }

    public UnsupportedBrowser(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UnsupportedBrowser(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedBrowser(String message) {
        super(message);
    }

    public UnsupportedBrowser(Throwable cause) {
        super(cause);
    }
}
