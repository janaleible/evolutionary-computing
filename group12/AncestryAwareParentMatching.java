package group12;

import java.util.List;
import java.util.Set;
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
        return match(
                parents,
                parents
        );
    }

    @Override
    protected List<Individual[]> match(List<Individual> mothers, List<Individual> fathers) {

        int numberOfMatches = Math.min(mothers.size(), fathers.size());
        List<Individual[]> matches = new ArrayList<>(numberOfMatches);

        for (int i = 0; i < numberOfMatches; i++) {
            Individual mother = mothers.get(i);
            List<Individual> ancestorsMother = new ArrayList<Individual>();
            ancestorsMother.add(mother);
            for(int j = 0; j< generations; j++){
                for(int k = 0; k<ancestorsMother.size();k++){
                    if(ancestorsMother.get(k).parents()!=null && ancestorsMother.get(k).generation()>1) {
                        for (Individual parent : ancestorsMother.get(k).parents()) {
                            ancestorsMother.add(parent);
                        }
                    }
                }
            }
            ancestorsMother.remove(0);
            Set<Individual> motherAncestorSet = new HashSet<Individual>(ancestorsMother);

            Individual father = fathers.get(i);
            List<Individual> ancestorsFather = new ArrayList<Individual>();
            ancestorsFather.add(father);
            for(int j = 0; j< generations; j++){
                for(int k = 0; k<ancestorsFather.size();k++){
                    if(ancestorsFather.get(k).parents()!=null && ancestorsFather.get(k).generation()>1) {
                        for (Individual parent : ancestorsFather.get(k).parents()) {
                            ancestorsFather.add(parent);
                        }
                    }
                }
            }
            ancestorsFather.remove(0);
            Set<Individual> fatherAncestorSet = new HashSet<Individual>(ancestorsFather);

            int idx = 0;
            while(!Collections.disjoint(motherAncestorSet,fatherAncestorSet)){

                System.out.println(motherAncestorSet);
                System.out.println(fatherAncestorSet);
                System.out.println(Collections.disjoint(motherAncestorSet,fatherAncestorSet));
                fathers.get(idx);
                idx++;

                ancestorsFather = new ArrayList<Individual>();
                ancestorsFather.add(father);
                for(int j = 0; j< generations; j++){
                    for(int k = 0; k<ancestorsFather.size();k++){
                        if(ancestorsFather.get(k).parents()!=null && ancestorsFather.get(k).generation()>1) {
                            for (Individual parent : ancestorsFather.get(k).parents()) {
                                ancestorsFather.add(parent);
                            }
                        }
                    }
                }
                ancestorsFather.remove(0);
                fatherAncestorSet = new HashSet<Individual>(ancestorsFather);
            }

            matches.add(new Individual[] {mother, father});
        }

        return matches;
    }
}