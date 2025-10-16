package seedu.address.storage;

import java.util.Dictionary;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Parent;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Role;

/**
 * Jackson-friendly version of {@link Parent}.
 */
public class JsonAdaptedParent extends JsonAdaptedPerson {

    /**
     * Constructs a {@code JsonAdaptedParent} with the given parent details.
     */
    @JsonCreator
    public JsonAdaptedParent(@JsonProperty("role") Role role,
                             @JsonProperty("name") String name,
                             @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email,
                             @JsonProperty("address") String address,
                             @JsonProperty("remark") String remark) {
        super(role, name, phone, email, address, remark);
    }

    public JsonAdaptedParent(Person source) {
        super(Role.PARENT, source);
    }

    @Override
    public Person toModelType() throws IllegalValueException {

        Dictionary<String, Object> fieldSet = setCommonFields();

        return new Parent((Name) fieldSet.get("modelName"),
                (Phone) fieldSet.get("modelPhone"),
                (Email) fieldSet.get("modelEmail"),
                (Address) fieldSet.get("modelAddress"),
                (Remark) fieldSet.get("modelRemark"));
    }
}
