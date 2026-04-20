package in.parser.exceptions;

public class QueryExceptionHandler {

    public static void handle(Exception e) {
        if (e instanceof QueryParsingException) {
            System.err.println("[PARSER ERROR] " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Root Cause: " + e.getCause().getMessage());
            }
        }
        else {
            System.err.println("[SYSTEM ERROR] An unexpected error occurred: " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }
}