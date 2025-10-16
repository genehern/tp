package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Role;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_REMARK = "Dislikes '/'";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_REMARK = BENSON.getRemark().remarks;
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validStudentDetails_returnsStudent() throws Exception {
        JsonAdaptedStudent student = new JsonAdaptedStudent(BENSON);
        assertEquals(BENSON, student.toModelType());
    }

    @Test
    public void toModelType_validParentDetails_returnsParent() throws Exception {
        JsonAdaptedParent parent = new JsonAdaptedParent(CARL);
        assertEquals(CARL, parent.toModelType());
    }

    @Test
    public void toModelType_validStudentWithRemark_returnsStudent() throws Exception {
        JsonAdaptedStudent student = new JsonAdaptedStudent(BENSON);
        assertEquals(VALID_REMARK, student.toModelType().getRemark().remarks);
    }

    @Test
    public void toModelType_validParentWithRemark_returnsParent() throws Exception {
        JsonAdaptedParent parent = new JsonAdaptedParent(CARL);
        assertEquals(VALID_REMARK, parent.toModelType().getRemark().remarks);
    }

    @Test
    public void toModelType_invalidStudentName_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(Role.STUDENT, INVALID_NAME, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, VALID_REMARK, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidParentName_throwsIllegalValueException() {
        JsonAdaptedParent parent = new JsonAdaptedParent(Role.PARENT, INVALID_NAME, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, VALID_REMARK);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, parent::toModelType);
    }

    @Test
    public void toModelType_nullStudentName_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(Role.STUDENT, null, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, VALID_REMARK, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullParentName_throwsIllegalValueException() {
        JsonAdaptedParent parent = new JsonAdaptedParent(Role.PARENT, null, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, VALID_REMARK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, parent::toModelType);
    }

    @Test
    public void toModelType_invalidStudentPhone_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(Role.STUDENT, VALID_NAME, INVALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, VALID_REMARK, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidParentPhone_throwsIllegalValueException() {
        JsonAdaptedParent parent = new JsonAdaptedParent(Role.PARENT, VALID_NAME, INVALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, VALID_REMARK);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, parent::toModelType);
    }

    @Test
    public void toModelType_nullStudentPhone_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(Role.STUDENT, VALID_NAME, null,
                VALID_EMAIL, VALID_ADDRESS, VALID_REMARK, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullParentPhone_throwsIllegalValueException() {
        JsonAdaptedParent parent = new JsonAdaptedParent(Role.PARENT, VALID_NAME, null,
                VALID_EMAIL, VALID_ADDRESS, VALID_REMARK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, parent::toModelType);
    }

    @Test
    public void toModelType_invalidStudentEmail_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(Role.STUDENT, VALID_NAME, VALID_PHONE,
                INVALID_EMAIL, VALID_ADDRESS, VALID_REMARK, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidParentEmail_throwsIllegalValueException() {
        JsonAdaptedParent parent = new JsonAdaptedParent(Role.PARENT, VALID_NAME, VALID_PHONE,
                INVALID_EMAIL, VALID_ADDRESS, VALID_REMARK);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, parent::toModelType);
    }

    @Test
    public void toModelType_nullStudentEmail_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(Role.STUDENT, VALID_NAME, VALID_PHONE,
                null, VALID_ADDRESS, VALID_REMARK, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullParentEmail_throwsIllegalValueException() {
        JsonAdaptedParent parent = new JsonAdaptedParent(Role.PARENT, VALID_NAME, VALID_PHONE, null,
                VALID_ADDRESS, VALID_REMARK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, parent::toModelType);
    }

    @Test
    public void toModelType_invalidStudentAddress_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(Role.STUDENT, VALID_NAME, VALID_PHONE,
                VALID_EMAIL, INVALID_ADDRESS, VALID_REMARK, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidParentAddress_throwsIllegalValueException() {
        JsonAdaptedParent parent = new JsonAdaptedParent(Role.PARENT, VALID_NAME, VALID_PHONE,
                VALID_EMAIL, INVALID_ADDRESS,
                VALID_REMARK);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, parent::toModelType);
    }

    @Test
    public void toModelType_nullStudentAddress_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(Role.STUDENT, VALID_NAME, VALID_PHONE,
                VALID_EMAIL, null,
                VALID_REMARK, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullParentAddress_throwsIllegalValueException() {
        JsonAdaptedParent parent = new JsonAdaptedParent(Role.PARENT, VALID_NAME, VALID_PHONE,
                VALID_EMAIL, null, VALID_REMARK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, parent::toModelType);
    }

    @Test
    public void toModelType_invalidStudentRemark_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(Role.STUDENT, VALID_NAME, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, INVALID_REMARK, VALID_TAGS);
        String expectedMessage = Remark.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidParentRemark_throwsIllegalValueException() {
        JsonAdaptedParent parent = new JsonAdaptedParent(Role.PARENT, VALID_NAME, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, INVALID_REMARK);
        String expectedMessage = Remark.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, parent::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedStudent student = new JsonAdaptedStudent(Role.STUDENT, VALID_NAME, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS,
                VALID_REMARK, invalidTags);
        assertThrows(IllegalValueException.class, student::toModelType);
    }

}
