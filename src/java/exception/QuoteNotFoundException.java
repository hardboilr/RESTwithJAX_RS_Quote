package exception;

/**
 * @author Tobias Jacobsen
 */
public class QuoteNotFoundException extends Exception {

    public QuoteNotFoundException(String no_quote_with_that_id) {
        super(no_quote_with_that_id);
    }
}
