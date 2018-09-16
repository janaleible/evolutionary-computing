package group12;

import java.util.Random;

public class UniformCrossover extends Crossover {

    private Random random;

    public UniformCrossover(Random random) {
        this.random = random;
    }

    @Override
    public Individual[] cross(Individual one, Individual another, int generation) {

        Individual[] parents = new Individual[2];
        parents[0] = one;
        parents[1] = another;

        double[][] childGenomes = new double[2][10];

        for (int i = 0; i <10; i++) {
            if(coinflip()==1){
                childGenomes[0][i] = one.genome()[i];
                childGenomes[1][i] = another.genome()[i];
            }
            else {
                childGenomes[0][i] = another.genome()[i];
                childGenomes[1][i] = one.genome()[i];
            }
        }

        Individual[] children = new Individual[2];
		children[0] = new Individual(childGenomes[0], generation, parents);
		children[1] = new Individual(childGenomes[1], generation, parents);

		return children;
    }

    private int coinflip() {
        return this.random.nextInt(2); // return random number from 0 to 1
    }
}