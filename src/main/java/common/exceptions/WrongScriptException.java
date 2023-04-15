/**
 * WrongScriptException is an Exception class that is thrown when program has incorrect script.
 */

package common.exceptions;

public class WrongScriptException extends Exception {
    public WrongScriptException() {
        super("There is mistake in your script. Please fix it");
    }
}
