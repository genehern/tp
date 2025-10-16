package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MATH;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Student person = new PersonBuilder().buildStudent();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSameStudent() {
        // Student
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_MATH).buildStudent();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).buildStudent();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).buildStudent();
        assertFalse(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).buildStudent();
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void isSameParent() {
        // Parent
        // same object -> returns true
        assertTrue(CARL.isSamePerson(CARL));

        // null -> returns false
        assertFalse(CARL.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedCarl = new PersonBuilder(CARL).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_MATH).buildParent();
        assertTrue(CARL.isSamePerson(editedCarl));

        // different name, all other attributes same -> returns false
        editedCarl = new PersonBuilder(CARL).withName(VALID_NAME_BOB).buildParent();
        assertFalse(ALICE.isSamePerson(editedCarl));

        // name differs in case, all other attributes same -> returns false
        Person editedBob = new PersonBuilder(DANIEL).withName(VALID_NAME_BOB.toLowerCase()).buildParent();
        assertFalse(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(DANIEL).withName(nameWithTrailingSpaces).buildParent();
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equalsStudent() {
        // Student
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).buildStudent();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).buildStudent();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).buildStudent();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).buildStudent();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).buildStudent();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_MATH).buildStudent();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void equalsParent() {
        // Parent
        // same values -> returns true
        Person carlCopy = new PersonBuilder(CARL).buildParent();
        assertTrue(CARL.equals(carlCopy));

        // same object -> returns true
        assertTrue(CARL.equals(CARL));

        // null -> returns false
        assertFalse(CARL.equals(null));

        // different type -> returns false
        assertFalse(CARL.equals(5));

        // different person -> returns false
        assertFalse(CARL.equals(BOB));

        // different name -> returns false
        Person editedCarl = new PersonBuilder(CARL).withName(VALID_NAME_BOB).buildParent();
        assertFalse(CARL.equals(editedCarl));

        // different phone -> returns false
        editedCarl = new PersonBuilder(CARL).withPhone(VALID_PHONE_BOB).buildParent();
        assertFalse(CARL.equals(editedCarl));

        // different email -> returns false
        editedCarl = new PersonBuilder(CARL).withEmail(VALID_EMAIL_BOB).buildParent();
        assertFalse(CARL.equals(editedCarl));

        // different address -> returns false
        editedCarl = new PersonBuilder(CARL).withAddress(VALID_ADDRESS_BOB).buildParent();
        assertFalse(CARL.equals(editedCarl));
    }

    @Test
    public void toStringMethod() {
        String expectedStudent = Student.class.getCanonicalName() + "{role=[Student], name=" + ALICE.getName()
                + ", phone=" + ALICE.getPhone() + ", email=" + ALICE.getEmail() + ", address="
                + ALICE.getAddress() + ", remark=" + ALICE.getRemark() + ", tags=" + ALICE.getTags() + "}";
        assertEquals(expectedStudent, ALICE.toString());

        String expectedParent = Parent.class.getCanonicalName() + "{role=[Parent], name=" + CARL.getName()
                + ", phone=" + CARL.getPhone() + ", email=" + CARL.getEmail() + ", address="
                + CARL.getAddress() + ", remark=" + CARL.getRemark() + "}";
        assertEquals(expectedParent, CARL.toString());
    }

    @Test
    public void remark_field() {
        Remark remark1 = new Remark("Hates Math");
        Remark remark2 = new Remark("Loves Math");
        Student studentWithRemark1 = new PersonBuilder().withRemark("Hates Math").buildStudent();
        Parent parentWithRemark2 = new PersonBuilder().withRemark("Loves Math").buildParent();
        Parent parentWithNoRemark = new PersonBuilder().withRemark("").buildParent();

        assertEquals(remark1.remarks, studentWithRemark1.getRemark().remarks);
        assertEquals(remark2.remarks, parentWithRemark2.getRemark().remarks);
        assertEquals("", parentWithNoRemark.getRemark().remarks);
        assertFalse(studentWithRemark1.equals(parentWithRemark2));
        assertTrue(studentWithRemark1.equals(new PersonBuilder().withRemark("Hates Math").buildStudent()));
    }
}
