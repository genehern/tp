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
public class DeleteTagsCommand  extends Command {

    public static final String COMMAND_WORD = "delete_tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " Parameters: "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "All tags deleted: %1$s";

    private final Set<Tag> toDelete;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public DeleteTagsCommand(Set<Tag> tags) {
        requireNonNull(tags);
        toDelete = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        for (Tag tag : toDelete) {
            if (!model.hasTag(tag)) {
                throw new CommandException(String.format("Tag %s does not exist", tag));
            }
        }
        model.deleteTagTypes(toDelete);
        return new CommandResult("Tags deleted successfully!");
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toDelete", toDelete)
                .toString();
    }
}
