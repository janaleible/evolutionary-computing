package group12;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class RestrictedTournamentSelection extends TournamentSelection {

    public RestrictedTournamentSelection(int tournamentSize, ExtendedRandom random) {
        super(tournamentSize, random);
    }

    @Override
    public String toString() {
        return (new StringBuilder()).append("Restricted ").append(super.toString()).toString();
    }

    @Override
    public boolean RTSflag() { return true; }

    @Override
    public List<Individual> select(int generation, List<Individual> population) {
        List<Individual> offspring = new ArrayList<Individual>();
        List<Individual> existing = new ArrayList<Individual>();

        for (Individual individual : population) {
            if (individual.generation() == generation) {
                offspring.add(individual);
            } else {
                existing.add(individual);
            }
        }

        for (Individual candidate : offspring) {
            Individual[] contestants = getContestants(existing);
            Individual mostSimilar = findMostSimilar(candidate, contestants);

            if (candidate.getFitness() > mostSimilar.getFitness()) {
                Collections.replaceAll(existing, mostSimilar, candidate);
            }
        }
        return existing;
    }

    private Individual findMostSimilar(Individual candidate, Individual[] contestants) {

        Individual mostSimilar = null;
        double lowestDistance = Double.POSITIVE_INFINITY;

        for (Individual contestant : contestants) {

            if (candidate != contestant) {
                double distance = getEuclidDistance(candidate.genome(), contestant.genome());

                if (distance < lowestDistance) {
                    mostSimilar = contestant;
                    lowestDistance = distance;
                }
            }
        }
        return mostSimilar;
    }

    private double getEuclidDistance(double[] from, double[] to) {

        double distance = 0;

        for (int i = 0; i < from.length; i++) {
            distance += Math.pow(from[i] - to[i], 2);
        }

        return Math.sqrt(distance);
    }
}