package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Adds a person to the address book.
 */
public class AddTagsCommand extends Command {

    public static final String COMMAND_WORD = "add tag type";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_REMARK + "REMARK] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_REMARK + "Hates Math "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New tags added: %1$s";
    public static final String MESSAGE_DUPLICATE_TAG = "Some tags already exist and were not added: %1$s";

    private final Set<Tag> toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddTagsCommand(Set<Tag> tags) {
        requireNonNull(tags);
        toAdd = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Set<Tag> alreadyPresent = model.addTagTypes(toAdd);
        Set<Tag> addedTags = new java.util.HashSet<>(toAdd);
        addedTags.removeAll(alreadyPresent);
        StringBuilder result = new StringBuilder();
        if (!addedTags.isEmpty()) {
            result.append(String.format(MESSAGE_SUCCESS, addedTags));
        }
        if (!alreadyPresent.isEmpty()) {
            if (result.length() > 0) result.append("\n");
            result.append(String.format(MESSAGE_DUPLICATE_TAG, alreadyPresent));
        }
        if (result.length() == 0) {
            throw new CommandException("No new tags were added.");
        }
        return new CommandResult(result.toString());
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
