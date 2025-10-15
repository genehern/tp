package seedu.address.model.tag.exceptions;

import seedu.address.model.tag.Tag;

/**
 * Signals that the operation is unable to find the specified tag.
 */
public class TagNotFoundException extends IllegalArgumentException {
    public TagNotFoundException(Tag tagName) {
        super(String.format("Tag: %s does not exist.", tagName));
    }
}
