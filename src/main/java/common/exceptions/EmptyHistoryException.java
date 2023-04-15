/**
 * EmptyHistoryException is an Exception class that is thrown when a history is empty.
 */

package common.exceptions;

public class EmptyHistoryException extends Exception {
    public EmptyHistoryException() {
        super("No commands in history");
    }
}
