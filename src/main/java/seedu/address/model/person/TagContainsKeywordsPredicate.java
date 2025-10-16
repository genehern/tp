package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Student}'s tags matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person student) {
        /* FilterTagCommand is general and only takes in Predicate<Person>
         * But Predicate do not accept generics and there is no way for the code to know
         * that Student is a subclass of Person.
         * Hence, we need to perform a cast here.
         */
        assert student instanceof Student;
        return ((Student) student).getTags().stream()
                .map(Tag::getTagName)
                .anyMatch(tagName -> keywords.stream()
                        .anyMatch(keyword -> tagName.equalsIgnoreCase(keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagContainsKeywordsPredicate)) {
            return false;
        }

        TagContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (TagContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
