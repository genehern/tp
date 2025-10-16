package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.Dictionary;

import seedu.address.logic.commands.AddParentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Parent;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Role;

/**
 * Parses input arguments and creates a new AddParentCommand object
 */
public class AddParentCommandParser extends AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddParentCommand
     * and returns an AddParentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddParentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_REMARK);

        Dictionary<String, Object> fieldSet = setCommonFields(Role.PARENT, argMultimap);

        Parent parent = new Parent((Name) fieldSet.get("name"), (Phone) fieldSet.get("phone"),
                (Email) fieldSet.get("email"), (Address) fieldSet.get("address"),
                (Remark) fieldSet.get("remark"));

        return new AddParentCommand(parent);
    }

}
