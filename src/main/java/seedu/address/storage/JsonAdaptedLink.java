package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.person.Person;

/**
 * Jackson-friendly version of a {@link seedu.address.model.util.RelationshipGraph} link
 * between two {@link seedu.address.model.person.Person} objects.
 * <p>
 * Each link stores the names of two contacts that are connected in the model.
 * This class is used to serialize and deserialize link data in {@code addressbook.json}.
 */
public class JsonAdaptedLink {

    private final String a;
    private final String b;

    /**
     * Constructs a {@code JsonAdaptedLink} with the specified contact names.
     * This constructor is used by Jackson during JSON deserialization.
     *
     * @param a The name of the first contact in the link.
     * @param b The name of the second contact in the link.
     */
    @JsonCreator
    public JsonAdaptedLink(@JsonProperty("a") String a,
                           @JsonProperty("b") String b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Constructs a {@code JsonAdaptedLink} from two {@code Person} objects.
     * The full names of the persons will be stored for serialization.
     *
     * @param a The first {@code Person} in the link.
     * @param b The second {@code Person} in the link.
     */
    public JsonAdaptedLink(Person a, Person b) {
        this.a = a.getName().fullName;
        this.b = b.getName().fullName;
    }

    /**
     * Returns the name of the first contact in this link.
     *
     * @return The name of the first contact.
     */
    public String getA() {
        return a;
    }

    /**
     * Returns the name of the second contact in this link.
     *
     * @return The name of the second contact.
     */
    public String getB() {
        return b;
    }
}

