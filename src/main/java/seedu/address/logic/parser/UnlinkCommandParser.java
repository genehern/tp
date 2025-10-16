package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.LinkCommand.MESSAGE_NOT_PARENT;
import static seedu.address.logic.commands.LinkCommand.MESSAGE_NOT_STUDENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_PHONE;

import java.util.List;

import seedu.address.logic.commands.UnlinkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Parent;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;

/**
 * Parses input arguments and creates a new {@code UnlinkCommand} object.
 * <p>
 * Ensures that sn/ refers to a {@code Student} contact
 * and pn/ refers to a {@code Parent} contact.
 */
public class UnlinkCommandParser implements Parser<UnlinkCommand> {

    private final Model model;

    /**
     * Constructs an {@code UnlinkCommandParser} with access to the current model.
     *
     * @param model The model used to resolve {@code Person} objects by name and phone.
     */
    public UnlinkCommandParser(Model model) {
        this.model = model;
    }

    @Override
    public UnlinkCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_STUDENT_NAME, PREFIX_STUDENT_PHONE,
                PREFIX_PARENT_NAME, PREFIX_PARENT_PHONE);

        if (!argMultimap.getValue(PREFIX_STUDENT_NAME).isPresent()
                || !argMultimap.getValue(PREFIX_PARENT_NAME).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnlinkCommand.MESSAGE_USAGE));
        }

        String studentName = argMultimap.getValue(PREFIX_STUDENT_NAME).get().trim();
        String studentPhone = argMultimap.getValue(PREFIX_STUDENT_PHONE).orElse("").trim();
        String parentName = argMultimap.getValue(PREFIX_PARENT_NAME).get().trim();
        String parentPhone = argMultimap.getValue(PREFIX_PARENT_PHONE).orElse("").trim();

        Person student = resolvePerson(studentName, studentPhone, Student.class);
        Person parent = resolvePerson(parentName, parentPhone, Parent.class);

        if (student == null) {
            throw new ParseException(MESSAGE_NOT_STUDENT);
        }
        if (parent == null) {
            throw new ParseException(MESSAGE_NOT_PARENT);
        }

        return new UnlinkCommand(student, parent);
    }

    /**
     * Finds a unique {@code Person} from the model by name and (optionally) phone number,
     * filtered to match the expected role type.
     *
     * @param name The contact name to search for (case-insensitive).
     * @param phone The optional phone number to disambiguate duplicate names.
     * @param expectedClass The expected contact subclass (e.g., {@code Student.class} or {@code Parent.class}).
     * @return The matching {@code Person}, or {@code null} if not found or ambiguous.
     */
    private Person resolvePerson(String name, String phone, Class<?> expectedClass) {
        List<Person> allPersons = model.getFilteredPersonList();
        List<Person> nameMatches = allPersons.stream()
                .filter(p -> p.getName().fullName.equalsIgnoreCase(name))
                .filter(expectedClass::isInstance)
                .toList();

        if (nameMatches.isEmpty()) {
            return null;
        }
        if (nameMatches.size() == 1) {
            return nameMatches.get(0);
        }
        if (!phone.isEmpty()) {
            return nameMatches.stream()
                    .filter(p -> p.getPhone().value.equals(phone))
                    .findFirst()
                    .orElse(null);
        }
        return null; // ambiguous case
    }
}
