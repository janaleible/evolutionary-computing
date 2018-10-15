package group12;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;


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
    public List<Individual> select(int numberOfPicks, List<Individual> population) {
        int currentGeneration = population.stream().mapToInt(Individual::generation).max().orElse(0);
        List<Individual> offspring = population.stream().filter(individual -> individual.generation() == currentGeneration).collect(Collectors.toList());
        List<Individual> existing = population.stream().filter(individual -> individual.generation() < currentGeneration).collect(Collectors.toList());

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