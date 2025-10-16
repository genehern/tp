package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.util.RelationshipGraph;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueTagList tags;
    private final RelationshipGraph relationshipGraph = new RelationshipGraph();

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        this.tags.setTags(newData.getTagList());

        if (newData instanceof AddressBook) {
            this.relationshipGraph.copyFrom(((AddressBook) newData).getRelationshipGraph());
        }
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
        if (p instanceof Student) {
            tags.addStudentToTags((Student) p);
        }
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        // remove any existing links involving this person
        removeAllLinksFor(key);
        // remove any existing tags if the Person is a Student
        if (key instanceof Student) {
            tags.removePersonFromAllTags((Student) key);
        }
        persons.remove(key);
    }


    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }

    public RelationshipGraph getRelationshipGraph() {
        return relationshipGraph;
    }

    /**
     * Adds a bidirectional link between two persons.
     * Returns true if successful, false if link already exists.
     */
    public boolean linkPersons(Person a, Person b) {
        return relationshipGraph.addLink(a, b);
    }

    /**
     * Removes a bidirectional link between two persons.
     * Returns true if successful, false if no such link exists.
     */
    public boolean unlinkPersons(Person a, Person b) {
        return relationshipGraph.removeLink(a, b);
    }

    /**
     * Returns all persons linked to the given person.
     */
    public List<Person> getLinkedPersons(Person person) {
        return new ArrayList<>(relationshipGraph.getLinked(person));
    }

    /**
     * Removes all relationships involving this person (called during delete).
     */
    public void removeAllLinksFor(Person person) {
        relationshipGraph.removeAll(person);
    }

    /**
     * Returns the first {@code Person} in the address book whose name matches the given {@code name},
     * ignoring case sensitivity.
     * <p>
     * This method performs a linear search over the list of persons stored in the address book.
     * If multiple persons share the same name, the first match encountered will be returned.
     * If no match is found, {@code null} is returned.
     *
     * @param name The name of the person to find. Must not be {@code null}.
     * @return The matching {@code Person} object if found; otherwise {@code null}.
     */
    public Person findPersonByName(String name) {
        for (Person p : persons) {
            if (p.getName().fullName.equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

}
