package seedu.address.storage;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Role;
import seedu.address.model.person.Student;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Student}.
 */
public class JsonAdaptedStudent extends JsonAdaptedPerson {

    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given student details.
     */
    @JsonCreator
    public JsonAdaptedStudent(
        @JsonProperty("role") Role role,
        @JsonProperty("name") String name,
        @JsonProperty("phone") String phone,
        @JsonProperty("email") String email,
        @JsonProperty("address") String address,
        @JsonProperty("remark") String remark,
        @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        super(role, name, phone, email, address, remark);
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedStudent(Student source) {
        super(Role.STUDENT, source);
        for (Tag tag : source.getTags()) {
            tags.add(new JsonAdaptedTag(tag));
        }
    }

    @Override
    public Person toModelType() throws IllegalValueException {

        Dictionary<String, Object> fieldSet = setCommonFields();

        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }
        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Student((Name) fieldSet.get("modelName"),
                (Phone) fieldSet.get("modelPhone"),
                (Email) fieldSet.get("modelEmail"),
                (Address) fieldSet.get("modelAddress"),
                (Remark) fieldSet.get("modelRemark"), modelTags);
    }
}
