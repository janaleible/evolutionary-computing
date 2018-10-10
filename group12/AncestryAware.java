package group12;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AncestryAware extends Selection {
    Selection selection;

    public AncestryAware(Selection selection) {
        this.selection = selection;
    }

    @Override
    public String toString() {
        return (new StringBuilder()).append("Ancestry aware ").append(this.selection.toString()).toString();
    }

    @Override
    public List<Individual> select(int numberOfPicks, List<Individual> population) {
        List<Individual> parents = new ArrayList<>(numberOfPicks);
        parents.addAll(this.selection.select(numberOfPicks, population));

        return parents;
    }

    @Override
    public boolean isGenderAware() {
        return false;
    }

    @Override
    public boolean isAncestryAware() {
        return false;
    }
}