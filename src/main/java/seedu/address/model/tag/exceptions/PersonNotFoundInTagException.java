package seedu.address.model.tag.exceptions;

/**
 * Signals that the operation is unable to find the specified person in tag.
 */
public class PersonNotFoundInTagException extends Exception {
    public PersonNotFoundInTagException() {
        super("No such person in tag");
    }
}
