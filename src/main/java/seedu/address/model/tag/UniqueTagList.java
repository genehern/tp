package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.tag.exceptions.StudentNotFoundInTagException;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * A hashmap that maps each unique Tag to an ObservableList of Student objects.
 * Enforces uniqueness of tags and non-null constraints.
 */
public class UniqueTagList {
    private final Map<Tag, ObservableList<Student>> tagMap = new HashMap<>();

    /**
     * Constructs a UniqueTagList from an AddressBook, populating tagMap with all tags and people.
     */
    public UniqueTagList(ReadOnlyAddressBook addressBook) {
        requireNonNull(addressBook);
        // Add all persons to their respective tags
        for (Person person : addressBook.getPersonList()) {
            addPersonToTags(person);
        }
    }

    /**
     * Returns true if the list contains an equivalent tag as the given argument.
     */
    public boolean containsTag(Tag toCheck) {
        requireNonNull(toCheck);
        return tagMap.containsKey(toCheck);
    }

    public boolean personHasValidTags(Person person) throws TagNotFoundException {
        requireNonNull(person);
        Set<Tag> tags = person.getTags();
        for (Tag tag : tags) {
            if (!containsTag(tag)) {
                throw new TagNotFoundException(tag);
            }
        }
        return true;
    }

    /**
     * Deletes a tag type from the map and removes it from all associated persons.
     */
    private void deleteTagType(Tag toDelete) {
        requireNonNull(toDelete);
        ObservableList<Person> persons = tagMap.get(toDelete);
        for (Person person : persons) {
            person.removeTag(toDelete);
        }
        tagMap.remove(toDelete);
    }

    /**
     * Deletes multiple tag types from the map.
     */
    public void deleteTagTypes(Set<Tag> tagsToDelete) {
        requireNonNull(tagsToDelete);
        for (Tag tag : tagsToDelete) {
                deleteTagType(tag);
            }
    }
    /**
     * Adds a student to the list of students for the given tag.
     * If the tag does not exist, it is created.
     */
    public void addStudentToTags(Student toAddStudent) {
        // Only students have tags, so we can just use Student objects here
        requireNonNull(toAddStudent);
        Set<Tag> tags = toAddStudent.getTags();
        for (Tag toAddTag : tags) {
            ObservableList<Student> studentList;
            if (contains(toAddTag)) {
                studentList = tagMap.get(toAddTag);
                if (!studentList.contains(toAddStudent)) {
                    studentList.add(toAddStudent);
                }
                tagMap.put(toAddTag, studentList);
            } else {
              tagMap.put(toAddTag, FXCollections.observableArrayList(toAddStudent));
            }
        }
    }

    /**
     * Removes a student from one associated tag.
     */
    public void removeStudentFromTag(Tag toRemoveTag, Student toRemoveStudent) {
        requireAllNonNull(toRemoveStudent);
        if (!contains(toRemoveTag)) {
            throw new TagNotFoundException();
        }
        ObservableList<Student> students = tagMap.get(toRemoveTag);
        boolean found = false;
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).isSamePerson(toRemoveStudent)) {
                students.remove(i);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new StudentNotFoundInTagException();
        }
    }

    /**
     * Removes a student from its associated tags when it is deleted.
     */
    public void removePersonFromAllTags(Student toRemoveStudent) {
        requireNonNull(toRemoveStudent);
        Set<Tag> tags = toRemoveStudent.getTags();
        for (Tag tag : tags) {
            ObservableList<Student> students = tagMap.get(tag);
            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).isSameStudent(toRemoveStudent)) {
                    students.remove(i);
                    break;
                }
            }
        }
    }


    /**
     * Replaces the current map with another UniqueTagList's contents.
     */
    public void setTags(UniqueTagList replacement) {
        requireNonNull(replacement);
        tagMap.clear();
        tagMap.putAll(replacement.tagMap);
    }

    /**
     * Returns all tags as an unmodifiable observable list.
     */
    public ObservableList<Tag> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(
                FXCollections.observableArrayList(tagMap.keySet()));
    }

    /**
     * Returns all tags currently in the map.
     */
    public Set<Tag> getTags() {
        return tagMap.keySet();
    }

    /**
     * Adds multiple tag types to the map.
     * Returns a set of tags that were not added because they already exist.
     */
    public Set<Tag> addTagTypes(Set<Tag> tagsToAdd) {
        requireNonNull(tagsToAdd);
        Set<Tag> alreadyPresent = new HashSet<>();
        for (Tag tag : tagsToAdd) {
            if (containsTag(tag)) {
                alreadyPresent.add(tag);
            } else {
                tagMap.put(tag, FXCollections.observableArrayList());
            }
        }
        return alreadyPresent;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UniqueTagList
                && tagMap.equals(((UniqueTagList) other).tagMap));
    }

    @Override
    public int hashCode() {
        return tagMap.hashCode();
    }

    @Override
    public String toString() {
        return tagMap.toString();
    }
}
