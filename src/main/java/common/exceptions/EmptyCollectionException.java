/**
 * EmptyCollectionException is an Exception class that is thrown when a collection is empty and an operation that requires elements to be present in the collection is attempted.
 */

package common.exceptions;

public class EmptyCollectionException extends Exception {
    public EmptyCollectionException() {
        super("Collection is empty, please add ticket");
    }
}
