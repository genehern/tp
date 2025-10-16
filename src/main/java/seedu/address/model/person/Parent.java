package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.person.Role.PARENT;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Student in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Parent extends Person {

    /**
     * Every field must be present and not null except remark.
     */
    public Parent(Name name, Phone phone, Email email, Address address, Remark remark) {
        super(PARENT, name, phone, email, address, remark);
        requireAllNonNull(name, phone, email, address);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameParent(Parent otherParent) {
        if (otherParent == this) {
            return true;
        }

        return otherParent != null
                && otherParent.getName().equals(getName());
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
        if (!(other instanceof Parent)) {
            return false;
        }

        Parent otherParent = (Parent) other;
        return this.getName().equals(otherParent.getName())
                && this.getPhone().equals(otherParent.getPhone())
                && this.getEmail().equals(otherParent.getEmail())
                && this.getAddress().equals(otherParent.getAddress())
                && this.getRemark().equals(otherParent.getRemark());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("role", "[Parent]")
                .add("name", getName())
                .add("phone", getPhone())
                .add("email", getEmail())
                .add("address", getAddress())
                .add("remark", getRemark())
                .toString();
    }

}
