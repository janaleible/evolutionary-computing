package group12;

import java.util.List;
import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Collections;

public class AncestryAwareParentMatching extends ParentMatching {
    private int generations;

    public AncestryAwareParentMatching() {
        super(null);
        this.generations = 1;
    }

    public AncestryAwareParentMatching(int generations) {
        super(null);
        this.generations = generations;
    }

    @Override
    public List<Individual[]> getMatches(List<Individual> parents) {
        int listSize = parents.size();
        return match(
                parents.subList(0,(listSize+1)/2),
                parents.subList((listSize+1)/2,listSize)
        );
    }

    @Override
    protected List<Individual[]> match(List<Individual> mothers, List<Individual> fathers) {

        int numberOfMatches = Math.min(mothers.size(), fathers.size());
        List<Individual[]> matches = new ArrayList<>(numberOfMatches);

        for (int i = 0; i < numberOfMatches; i++) {
            Individual mother = mothers.get(i);
            Individual father = fathers.get(i);

            if(mother.parents()!=null && father.parents()!=null) {

                Set<Individual> parentsMother = new HashSet<Individual>(Arrays.asList(mother.parents()));
                Set<Individual> parentsFather = new HashSet<Individual>(Arrays.asList(father.parents()));

                boolean added = false;
                for(int j = 1; j<fathers.size(); j++){
                    if(!parentsFather.retainAll(parentsMother)){
                        added = true;
                        matches.add(new Individual[] {mother, father});
                        break;
                    }

                    father = fathers.get((j + i) % fathers.size());
                    if(father.parents()!=null) {
                        parentsFather = new HashSet<Individual>(Arrays.asList(father.parents()));
                    }
                    else{
                        added = true;
                        matches.add(new Individual[] {mother, father});
                        break;
                    }
                }

                if(!added){
                    matches.add(new Individual[] {mother, fathers.get(i)});
                }
            }
            else{
                matches.add(new Individual[] {mother, father});
            }
        }
        return matches;
    }
}