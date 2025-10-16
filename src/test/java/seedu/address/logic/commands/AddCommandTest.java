package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Parent;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullParent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddParentCommand(null));
    }

    @Test
    public void constructor_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddStudentCommand(null));
    }

    @Test
    public void execute_parentAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Parent validPerson = new PersonBuilder().buildParent();

        CommandResult commandResult = new AddParentCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_studentAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Student validPerson = new PersonBuilder().buildStudent();

        CommandResult commandResult = new AddStudentCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicateParent_throwsCommandException() {
        Parent validPerson = new PersonBuilder().buildParent();
        AddCommand addCommand = new AddParentCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_duplicateStudent_throwsCommandException() {
        Student validPerson = new PersonBuilder().buildStudent();
        AddCommand addCommand = new AddStudentCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Parent parentAlice = new PersonBuilder().withName("Alice").buildParent();
        Parent parentBob = new PersonBuilder().withName("Bob").buildParent();
        Student studentAlice = new PersonBuilder().withName("Alice").buildStudent();
        Student studentBob = new PersonBuilder().withName("Bob").buildStudent();

        AddParentCommand addParentAliceCommand = new AddParentCommand(parentAlice);
        AddParentCommand addParentBobCommand = new AddParentCommand(parentBob);
        AddStudentCommand addStudentAliceCommand = new AddStudentCommand(studentAlice);
        AddStudentCommand addStudentBobCommand = new AddStudentCommand(studentBob);

        // same object -> returns true
        assertTrue(addParentAliceCommand.equals(addParentAliceCommand));
        assertTrue(addStudentAliceCommand.equals(addStudentAliceCommand));
        assertTrue(addParentBobCommand.equals(addParentBobCommand));
        assertTrue(addStudentBobCommand.equals(addStudentBobCommand));

        // same values -> returns true
        AddParentCommand addParentAliceCommandCopy = new AddParentCommand(parentAlice);
        assertTrue(addParentAliceCommand.equals(addParentAliceCommandCopy));
        AddStudentCommand addStudentAliceCommandCopy = new AddStudentCommand(studentAlice);
        assertTrue(addStudentAliceCommand.equals(addStudentAliceCommandCopy));
        AddParentCommand addParentBobCommandCopy = new AddParentCommand(parentBob);
        assertTrue(addParentBobCommand.equals(addParentBobCommandCopy));
        AddStudentCommand addStudentBobCommandCopy = new AddStudentCommand(studentBob);
        assertTrue(addStudentBobCommand.equals(addStudentBobCommandCopy));

        // different types -> returns false
        assertFalse(addParentAliceCommand.equals(1));
        assertFalse(addStudentAliceCommand.equals(1));
        assertFalse(addParentBobCommand.equals(1));
        assertFalse(addStudentBobCommand.equals(1));

        // null -> returns false
        assertFalse(addParentAliceCommand.equals(null));
        assertFalse(addStudentAliceCommand.equals(null));
        assertFalse(addParentBobCommand.equals(null));
        assertFalse(addStudentBobCommand.equals(null));

        // different person -> returns false
        assertFalse(addParentAliceCommand.equals(addStudentAliceCommand));
        assertFalse(addParentAliceCommand.equals(addParentBobCommand));
        assertFalse(addStudentAliceCommand.equals(addStudentBobCommand));
        assertFalse(addStudentAliceCommand.equals(addParentBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddStudentCommand addStudentCommand = new AddStudentCommand(ALICE);
        String expected = AddStudentCommand.class.getCanonicalName() + "{toAddS=" + ALICE + "}";
        assertEquals(expected, addStudentCommand.toString());

        AddParentCommand addParentCommand = new AddParentCommand(CARL);
        String expectedParent = AddParentCommand.class.getCanonicalName() + "{toAddP=" + CARL + "}";
        assertEquals(expectedParent, addParentCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTag(Tag tag) {
           throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean personHasValidTags(Person p) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getLinkedPersons(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean link(Person a, Person b) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean unlink(Person a, Person b) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTagTypes(Set<Tag> tags) {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
