package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Parent;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Role;
import seedu.address.model.person.Student;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code LinkCommand}.
 */
public class LinkCommandTest {

    private Model model;

    private Student studentA;
    private Student studentB;
    private Parent parentA;
    private Parent parentB;
    private Person person; // non-student, non-parent

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // Create sample Student and Parent contacts using PersonBuilder
        studentA = new PersonBuilder().withName("John Tan").withPhone("91234567")
                .withEmail("john@example.com").withAddress("123 Street")
                .withTags("Math").buildStudent();

        studentB = new PersonBuilder().withName("Alice Lim").withPhone("98765432")
                .withEmail("alice@example.com").withAddress("321 Avenue")
                .withTags("Science").buildStudent();

        parentA = new PersonBuilder().withName("Mrs Tan").withPhone("92345678")
                .withEmail("mrstan@example.com").withAddress("456 Road")
                .buildParent();

        parentB = new PersonBuilder().withName("Mr Lim").withPhone("99887766")
                .withEmail("mrlim@example.com").withAddress("654 Lane")
                .buildParent();

        person = new Person(
                Role.STUDENT, // role
                new Name("Chris Goh"),
                new Phone("90001111"),
                new Email("chris@email.com"),
                new Address("888 Random Street"),
                new Remark("")
        );

        // Add to model
        model.addPerson(studentA);
        model.addPerson(studentB);
        model.addPerson(parentA);
        model.addPerson(parentB);
    }

    @Test
    public void execute_validStudentParent_success() throws Exception {
        LinkCommand linkCommand = new LinkCommand(studentA, parentA);

        String expectedMessage = String.format(LinkCommand.MESSAGE_LINK_SUCCESS,
                Messages.format(studentA), Messages.format(parentA));

        assertCommandSuccess(linkCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_duplicateLink_throwsCommandException() throws Exception {
        // First link should succeed
        model.link(studentA, parentA);

        // Second attempt should fail
        LinkCommand duplicateLinkCommand = new LinkCommand(studentA, parentA);
        assertCommandFailure(duplicateLinkCommand, model, LinkCommand.MESSAGE_DUPLICATE_LINK);
    }

    @Test
    public void execute_samePerson_throwsCommandException() {
        LinkCommand linkCommand = new LinkCommand(studentA, studentA);
        assertCommandFailure(linkCommand, model, LinkCommand.MESSAGE_SAME_PERSON);
    }

    @Test
    public void execute_twoStudents_throwsCommandException() {
        LinkCommand linkCommand = new LinkCommand(studentA, studentB);
        assertCommandFailure(linkCommand, model, LinkCommand.MESSAGE_STUDENTS_INVALID_LINK);
    }

    @Test
    public void execute_twoParents_throwsCommandException() {
        LinkCommand linkCommand = new LinkCommand(parentA, parentB);
        assertCommandFailure(linkCommand, model, LinkCommand.MESSAGE_PARENTS_INVALID_LINK);
    }

    @Test
    public void execute_invalidInstance_throwsCommandException() {
        // Use a plain Person (not Student/Parent)
        LinkCommand linkCommand = new LinkCommand(person, studentA);
        assertCommandFailure(linkCommand, model, person.getName() + LinkCommand.MESSAGE_WRONG_INSTANCE);
    }

    @Test
    public void equals() {
        LinkCommand command1 = new LinkCommand(studentA, parentA);
        LinkCommand command2 = new LinkCommand(studentA, parentA);
        LinkCommand command3 = new LinkCommand(studentB, parentA);

        assertEquals(command1, command2);
        // Different pairing should not be equal
        assertEquals(false, command1.equals(command3));
    }
}
