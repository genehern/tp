package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * A hashmap that maps each unique Tag to an ObservableList of Person objects.
 * Enforces uniqueness of tags and non-null constraints.
 */
public class UniqueTagList {
    private final Map<Tag, ObservableList<Person>> tagMap = new HashMap<>();

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

    public boolean personHasValidTags(Person person) {
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
    public void deleteTagType(Tag toDelete) {
        requireNonNull(toDelete);
        ObservableList<Person> persons = tagMap.get(toDelete);
        for (Person person : persons) {
            person.removeTag(toDelete);
        }
        tagMap.remove(toDelete);
    }
    /**
     * Adds a person to the list of persons for the given tag.
     */
    public void addPersonToTags(Person toAddPerson) {
        requireNonNull(toAddPerson);
        Set<Tag> tags = toAddPerson.getTags();
        for (Tag toAddTag : tags) {
            ObservableList<Person> personList;
            if (tagMap.containsKey(toAddTag)) {
                personList = tagMap.get(toAddTag);
                if (!personList.contains(toAddPerson)) {
                    personList.add(toAddPerson);
                }
                tagMap.put(toAddTag, personList);
            } else {
              tagMap.put(toAddTag, FXCollections.observableArrayList(toAddPerson));
            }
        }
    }

    /**
     * Removes a person from one associated tag. This assumes that the tag exists in the map.
     */
    public void removePersonFromTag(Tag toRemoveTag, Person toRemovePerson) {
        requireAllNonNull(toRemovePerson);
        ObservableList<Person> persons = tagMap.get(toRemoveTag);
        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i).isSamePerson(toRemovePerson)) {
                persons.remove(i);
                break;
            }
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
