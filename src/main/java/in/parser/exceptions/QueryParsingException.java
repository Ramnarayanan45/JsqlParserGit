package in.parser.exceptions;

public class QueryParsingException extends RuntimeException {
    
    public QueryParsingException(String message) {
        super(message);
    }

    public QueryParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}