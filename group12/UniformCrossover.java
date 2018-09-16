package group12;

import java.util.Random;

public class UniformCrossover extends Crossover {

    private Random random;

    public UniformCrossover(Random random) {
        this.random = random;
    }

    @Override
    public Individual cross(Individual one, Individual another, int generation) {

        Individual[] parents = new Individual[2];
        parents[0] = one;
        parents[1] = another;

        double[] childGenome = new double[10];

        for (int i = 0; i <10; i++) {
            if(coinflip()==1){
                childGenome[i] = one.genome()[i];
            }
            else {
                childGenome[i] = another.genome()[i];
            }
        }

        return new Individual(childGenome, generation, parents);
    }

    protected int coinflip() {
        return this.random.nextInt(2); // return random number from 0 to 1
    }
}