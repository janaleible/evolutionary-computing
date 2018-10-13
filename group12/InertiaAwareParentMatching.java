package group12;

import java.util.List;
import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Collections;

public class InertiaAwareParentMatching extends ParentMatching {
    public InertiaAwareParentMatching() {
        super(null);
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

        double threshold = 83;

        for (int i = 0; i < numberOfMatches; i++) {
            Individual mother = mothers.get(i);
            Individual father = fathers.get(i);

            boolean added = false;

            if(DiversityMeasure.hammingDistance(mother.genome(),father.genome())>threshold){
                matches.add(new Individual[] {mother, father});
                added = true;
            }

            while(!added) {
                for (int j = 1; j < fathers.size(); j++) {
                    father = fathers.get((j + i) % fathers.size());

                    if (DiversityMeasure.hammingDistance(mother.genome(), father.genome()) > threshold) {
                        matches.add(new Individual[]{mother, father});
                        added = true;
                        break;
                    }
                }
                threshold = threshold - 1.0;
            }
        }

        return matches;
    }
}