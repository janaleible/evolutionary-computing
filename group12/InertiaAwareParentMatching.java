package group12;

import java.util.List;
import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Collections;

public class InertiaAwareParentMatching extends ParentMatching {

    private DiversityMeasure diversityMeasure;

    public InertiaAwareParentMatching(ExtendedRandom random, DiversityMeasure diversityMeasure) {
        super(random);
        this.diversityMeasure = diversityMeasure;
    }

    @Override
    public List<Individual[]> getMatches(List<Individual> parents) {
        int listSize = parents.size();
        return match(
                parents.subList(0,(listSize+1)/2),
                parents.subList((listSize+1)/2,listSize)
        );
    }

    private double getThreshold(List<Individual> fathers, List<Individual> mothers) {
        List<Individual> totalPopulation = new ArrayList<>(fathers);
        totalPopulation.addAll(mothers);
        return this.diversityMeasure.measure(totalPopulation);
    }

    @Override
    protected List<Individual[]> match(List<Individual> mothers, List<Individual> fathers) {

        int numberOfMatches = Math.min(mothers.size(), fathers.size());
        List<Individual[]> matches = new ArrayList<>(numberOfMatches);


        for (int i = 0; i < numberOfMatches; i++) {
            Individual mother = mothers.get(i);

            double originalThreshold = getThreshold(fathers, mothers);
            double threshold = originalThreshold;

            boolean added = false;

            do {
                for (int j = 1; j < fathers.size(); j++) {
                    Individual father = fathers.get((j + i) % fathers.size());

                    if (DiversityMeasure.hammingDistance(mother.genome(), father.genome()) > threshold) {
                        matches.add(new Individual[]{mother, father});
                        added = true;
                        break;
                    }
                }
                threshold = 0.65 * threshold;

                if (!added && threshold < 0.01 * originalThreshold) {
                    // if the threshold becomes too low, add a random individual
                    matches.add(new Individual[] {
                        mother,
                        fathers.get(this.random.nextInt(fathers.size()))
                    });
                    break;
                }
            } while(!added);
        }

        return matches;
    }
}