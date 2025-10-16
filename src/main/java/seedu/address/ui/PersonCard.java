package seedu.address.ui;

import java.util.Comparator;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.logic.Logic;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    public final Person person;
    private final Logic logic;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label linkedContacts;
    @FXML
    private Label remark;

    /**
     * Creates a {@code PersonCard} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex, Logic logic) {
        super(FXML);
        this.person = person;
        this.logic = logic;

        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        // Set remark if present
        String remarkText;
        if (person.getRemark().remarks.isEmpty() || person.getRemark() == null) {
            remarkText = "Nil";
        } else {
            remarkText = person.getRemark().remarks;
        }
        remark.setText("Remarks: " + remarkText);

        // show linked contacts if any exist
        String linkedText = getLinkedText(person);
        linkedContacts.setText(linkedText.isEmpty() ? "" : "Linked: " + linkedText);

        // show tags if person is a student
        if (person instanceof Student) {
            Student student = ((Student) person);
            student.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        } else {
            tags.getChildren().clear();
        }
    }

    /**
     * Returns a comma-separated list of linked contact names, or an empty string if none.
     */
    private String getLinkedText(Person person) {
        if (logic == null) {
            return "";
        }

        var linkedList = logic.getLinkedPersons(person);
        if (linkedList.isEmpty()) {
            return "";
        }

        String joined = linkedList.stream()
                .map(p -> p.getName().fullName)
                .limit(3)
                .collect(Collectors.joining(", "));
        return linkedList.size() > 3 ? joined + "…" : joined;
    }
}
