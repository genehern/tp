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

    public boolean containsTag(Tag toCheck) {
        requireNonNull(toCheck);
        return tagMap.containsKey(toCheck);
    }

    /**
     * Returns true if the list contains an equivalent tag as the given argument.
     */
    public boolean contains(Tag toCheck) {
        requireNonNull(toCheck);
        return tagMap.containsKey(toCheck);
    }

    public void addTagType(Tag toAdd) {
        requireNonNull(toAdd);
        if (containsTag(toAdd)) {
            return;
        }
        tagMap.put(toAdd, FXCollections.observableArrayList());
    }

    /**
     * Deletes a tag type from the map and removes it from all associated persons.
     * @throws TagNotFoundException if the tag to delete does not exist.
     */
    public void deleteTagType(Tag toDelete) throws TagNotFoundException {
        requireNonNull(toDelete);
        if (!containsTag(toDelete)) {
            throw new TagNotFoundException();
        }
        ObservableList<Person> persons = tagMap.get(toDelete);
        for (Person person : persons) {
            person.removeTag(toDelete);
        }
        tagMap.remove(toDelete);
    }
    /**
     * Adds a person to the list of persons for the given tag.
     * @return Set of tags that were not found.
     */
    public Set<Tag> addPersonToTags(Person toAddPerson) {
        requireNonNull(toAddPerson);
        Set<Tag> tags = toAddPerson.getTags();
        Set<Tag> tagsNotFound = new java.util.HashSet<>();
        for (Tag toAddTag : tags) {
            ObservableList<Person> personList;

            if (containsTag(toAddTag)) {
                personList = tagMap.get(toAddTag);
                if (!personList.contains(toAddPerson)) {
                    personList.add(toAddPerson);
                }
                tagMap.put(toAddTag, personList);
            } else {
                tagsNotFound.add(toAddTag);
            }
        }
        return tagsNotFound;
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
