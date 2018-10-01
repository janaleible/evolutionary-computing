package group12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Comparator;

public class RankBasedSelection extends Selection{

    private Random random;
    private double sigma;

    public RankBasedSelection(Random random, double sigma) {
        this.random = random;
        this.sigma = sigma;
    }

    @Override
    public List<Individual> select(int numberOfPicks, List<Individual> population) {

        population.sort(Comparator.comparingDouble(Individual::getFitness));

        int mu = population.size();

        Double[] picks = new Double[numberOfPicks];
        for (int i = 0; i < numberOfPicks; i++) {
            picks[i] = this.random.nextDouble();
        }
        //Picks needs to be reversed (I think)
        Arrays.sort(picks);

        List<Individual> parents = new ArrayList<>(numberOfPicks);
        double runningTotal = 0;
        int pickIndex = 0;
        for (Individual individual : population) {
            runningTotal += (2-this.sigma)/mu + 2*pickIndex*(this.sigma-1)/(mu*(mu-1));
            while (picks[pickIndex] < runningTotal) {
                parents.add(individual);
                pickIndex++;
                if (pickIndex >= numberOfPicks) { break; }
            }
            if (pickIndex >= numberOfPicks) { break; }
        }
        return parents;
    }
}