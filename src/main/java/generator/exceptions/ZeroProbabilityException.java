package generator.exceptions;

public class ZeroProbabilityException extends IllegalArgumentException {
    public ZeroProbabilityException(String msg) {
        super(msg);
    }
}
