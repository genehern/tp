package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.person.Role.STUDENT;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Student in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student extends Person {

    // Tag field
    private final Set<Tag> tags = new HashSet<>();
    /**
     * Every field must be present and not null except remark.
     */
    public Student(Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags) {
        super(STUDENT, name, phone, email, address, remark);
        requireAllNonNull(name, phone, email, address, tags);
        this.tags.addAll(tags);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameStudent(Student otherStudent) {
        if (otherStudent == this) {
            return true;
        }

        return otherStudent != null
                && otherStudent.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Student)) {
            return false;
        }

        Student otherStudent = (Student) other;
        return this.getName().equals(otherStudent.getName())
                && this.getPhone().equals(otherStudent.getPhone())
                && this.getEmail().equals(otherStudent.getEmail())
                && this.getAddress().equals(otherStudent.getAddress())
                && this.getRemark().equals(otherStudent.getRemark())
                && this.getTags().equals(otherStudent.getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(getName(), getPhone(), getEmail(), getAddress(), getRemark(), getTags());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("role", "[Student]")
                .add("name", getName())
                .add("phone", getPhone())
                .add("email", getEmail())
                .add("address", getAddress())
                .add("remark", getRemark())
                .add("tags", getTags())
                .toString();
    }

}
