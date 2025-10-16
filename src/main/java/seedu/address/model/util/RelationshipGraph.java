package seedu.address.model.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.util.Pair;
import seedu.address.model.person.Person;

/**
 * Represents undirected relationships (links) between Person objects.
 */
public class RelationshipGraph {

    private final Map<Person, Set<Person>> links = new HashMap<>();

    /** Adds a bidirectional link between two persons. Returns true if successful, false if it already exists. */
    public boolean addLink(Person a, Person b) {
        if (a.equals(b)) {
            return false; // no self link
        }
        links.putIfAbsent(a, new HashSet<>());
        links.putIfAbsent(b, new HashSet<>());

        boolean addedA = links.get(a).add(b);
        boolean addedB = links.get(b).add(a);
        return addedA || addedB;
    }

    /** Removes a bidirectional link between two persons. */
    public boolean removeLink(Person a, Person b) {
        boolean removed = false;
        if (links.containsKey(a)) {
            removed |= links.get(a).remove(b);
            if (links.get(a).isEmpty()) {
                links.remove(a);
            }
        }
        if (links.containsKey(b)) {
            removed |= links.get(b).remove(a);
            if (links.get(b).isEmpty()) {
                links.remove(b);
            }
        }
        return removed;
    }

    /** Returns linked persons of the given person. */
    public Set<Person> getLinked(Person person) {
        return links.getOrDefault(person, new HashSet<>());
    }

    /** Cleans up all relationships involving this person (called when person is deleted). */
    public void removeAll(Person person) {
        if (!links.containsKey(person)) {
            return;
        }
        for (Person p : new HashSet<>(links.get(person))) {
            links.get(p).remove(person);
        }
        links.remove(person);
    }

    /** copy the content of param graph into this graph */
    public void copyFrom(RelationshipGraph other) {
        links.clear();
        for (Map.Entry<Person, Set<Person>> entry : other.links.entrySet()) {
            links.put(entry.getKey(), new HashSet<>(entry.getValue()));
        }
    }

    public List<Pair<Person, Person>> getAllLinksAsPairs() {
        List<Pair<Person, Person>> pairs = new ArrayList<>();
        for (Map.Entry<Person, Set<Person>> entry : links.entrySet()) {
            for (Person linked : entry.getValue()) {
                // ensure each pair is only recorded once
                if (entry.getKey().hashCode() < linked.hashCode()) {
                    pairs.add(new Pair<>(entry.getKey(), linked));
                }
            }
        }
        return pairs;
    }


}
