package group12;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class RestrictedTournamentSelection extends TournamentSelection {

    public RestrictedTournamentSelection(int tournamentSize, ExtendedRandom random) {
        super(tournamentSize, random);
        this.tournamentSize = tournamentSize;
        this.random = random;
    }

    @Override
    public String toString() {
        return (new StringBuilder()).append("Restricted tournament selection (size: ").append(this.tournamentSize).append(")").toString();
    }

    @Override
    public List<Individual> select(int numberOffspring, List<Individual> population) {
        List<Individual> offspring = new ArrayList<Individual>(numberOffspring);
        List<Individual> existing = new ArrayList<Individual>(population.size() - numberOffspring);

        offspring.addAll(population.subList(population.size() - numberOffspring, population.size()));
        existing.addAll(population.subList(0, population.size() - numberOffspring));


        for (Individual candidate : offspring) {
            Individual[] contestants = getContestants(existing);
            Individual mostSimilar = findMostSimilar(candidate, contestants);

            if (candidate.getFitness() > mostSimilar.getFitness()) {
                Collections.replaceAll(existing, mostSimilar, candidate);
            }
        }
        return existing;
    }

    public int RTSflag() {
        return 1;
    }

    private Individual findMostSimilar(Individual candidate, Individual[] contestants) {

        Individual mostSimilar = contestants[0];
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