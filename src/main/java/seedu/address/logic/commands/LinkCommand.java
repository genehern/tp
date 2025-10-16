package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Parent;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;

/**
 * Links two contacts together (e.g. a student and a parent).
 */
public class LinkCommand extends Command {

    public static final String COMMAND_WORD = "link";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Links two contacts together by name and phone.\n"
            + "Parameters: sn/STUDENT_NAME [sp/STUDENT_PHONE] pn/PARENT_NAME [pp/PARENT_PHONE]\n"
            + "Example: " + COMMAND_WORD + " sn/John Tan sp/91234567 pn/Mrs Tan pp/92345678";

    public static final String MESSAGE_LINK_SUCCESS = "Linked %1$s ↔ %2$s";
    public static final String MESSAGE_DUPLICATE_LINK = "These contacts are already linked.";
    public static final String MESSAGE_SAME_PERSON = "Cannot link a contact to itself.";
    public static final String MESSAGE_NOT_FOUND = "One or both contacts could not be found.";
    public static final String MESSAGE_WRONG_INSTANCE = " must be a student or a parent contact.";
    public static final String MESSAGE_STUDENTS_INVALID_LINK = "Cannot link two students together.";
    public static final String MESSAGE_PARENTS_INVALID_LINK = "Cannot link two parents together.";
    public static final String MESSAGE_NOT_STUDENT = "Student contact not found or is not of type Student.";
    public static final String MESSAGE_NOT_PARENT = "Parent contact not found or is not of type Parent.";
    private final Person personA;
    private final Person personB;

    /**
     * Creates a LinkCommand with both contacts resolved.
     */
    public LinkCommand(Person personA, Person personB) {
        requireNonNull(personA);
        requireNonNull(personB);
        this.personA = personA;
        this.personB = personB;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        //check for duplicate contact
        if (personA.equals(personB)) {
            throw new CommandException(MESSAGE_SAME_PERSON);
        }
        //check if contact is parent student class
        if (!(personA instanceof Student) && !(personA instanceof Parent)) {
            throw new CommandException(personA.getName() + MESSAGE_WRONG_INSTANCE);
        }

        if (!(personB instanceof Student) && !(personB instanceof Parent)) {
            throw new CommandException(personB.getName() + MESSAGE_WRONG_INSTANCE);
        }

        //Prevent Student–Student and Parent–Parent links
        if (personA instanceof Student && personB instanceof Student) {
            throw new CommandException(MESSAGE_STUDENTS_INVALID_LINK);
        }

        if (personA instanceof Parent && personB instanceof Parent) {
            throw new CommandException(MESSAGE_PARENTS_INVALID_LINK);
        }
        //force Student↔Parent order (so link order doesn’t matter)
        Person student = personA instanceof Student ? personA : personB;
        Person parent = personA instanceof Parent ? personA : personB;

        boolean success = model.link(student, parent);
        if (!success) {
            throw new CommandException(MESSAGE_DUPLICATE_LINK);
        }

        return new CommandResult(String.format(MESSAGE_LINK_SUCCESS,
                Messages.format(student), Messages.format(parent)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof LinkCommand)) {
            return false;
        }
        LinkCommand otherCommand = (LinkCommand) other;
        return personA.equals(otherCommand.personA)
                && personB.equals(otherCommand.personB);
    }
}

