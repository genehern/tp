package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.tag.exceptions.PersonNotFoundInTagException;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * A hashmap that maps each unique Tag to an ObservableList of Person objects.
 * Enforces uniqueness of tags and non-null constraints.
 */
public class UniqueTagList {
    private final Map<Tag, ObservableList<Person>> tagMap = new HashMap<>();

    /**
     * Returns an unmodifiable view of the list of persons associated with the given tag.
     * The tag must exist in the map.
     * @param tag The tag whose associated persons are to be returned.
     * @return ObservableList of persons associated with the tag.
     */
    public ObservableList<Person> getPersonsWithTag(Tag tag) {
        requireNonNull(tag);
        if (!contains(tag)) {
            throw new TagNotFoundException();
        }
        return FXCollections.unmodifiableObservableList(tagMap.get(tag));
    }

    /**
     * Returns true if the list contains an equivalent tag as the given argument.
     */
    public boolean contains(Tag toCheck) {
        requireNonNull(toCheck);
        return tagMap.containsKey(toCheck);
    }

    /**
     * Adds a person to the list of persons for the given tag.
     * If the tag does not exist, it is created.
     */
    public void addPersonToTags(Person toAddPerson) {
        requireNonNull(toAddPerson);
        Set<Tag> tags = toAddPerson.getTags();
        for (Tag toAddTag : tags) {
            ObservableList<Person> personList;
            if (contains(toAddTag)) {
                personList = tagMap.get(toAddTag);
                if (!personList.contains(toAddPerson)) {
                    personList.add(toAddPerson);
                }
                continue;
            } else {
                personList = FXCollections.observableArrayList();
                personList.add(toAddPerson);
            }
            tagMap.put(toAddTag, personList);
        }
    }

    /**
     * Removes a person from one associated tag.
     */
    public void removePersonFromTag(Tag toRemoveTag, Person toRemovePerson) {
        requireAllNonNull(toRemovePerson);
        if (!contains(toRemoveTag)) {
            throw new TagNotFoundException();
        }
        ObservableList<Person> persons = tagMap.get(toRemoveTag);
        boolean found = false;
        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i).isSamePerson(toRemovePerson)) {
                persons.remove(i);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new PersonNotFoundInTagException();
        }
    }

    /**
     * Removes a person from its associated tags when it is deleted.
     */
    public void removePersonFromAllTags(Person toRemovePerson) {
        requireNonNull(toRemovePerson);
        Set<Tag> tags = toRemovePerson.getTags();
        for (Tag tag : tags) {
            ObservableList<Person> persons = tagMap.get(tag);
            for (int i = 0; i < persons.size(); i++) {
                if (persons.get(i).isSamePerson(toRemovePerson)) {
                    persons.remove(i);
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
