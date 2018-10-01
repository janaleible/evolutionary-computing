package group12;

import java.util.Random;

public class RandomMutation extends Mutation {

    private Random random;
    private double mutationRate;

    public RandomMutation(Random random, double mutationRate) {
        this.random = random;
        this.mutationRate = mutationRate;
    }

    public void setMutationRate(double newMutationRate){
        this.mutationRate = newMutationRate;
    }

    public double getMutationRate(){
        return this.mutationRate;
    }

    @Override
    public String toString() {
        return (new StringBuilder()).append("Random Mutation (mutation rate: ").append(mutationRate).append(")").toString();
    }

    @Override
    public Individual mutate(Individual self){
        double[] genome = self.genome();
        for(int i = 0; i<genome.length; i++){
            if(this.random.nextDouble() < this.mutationRate){
                genome[i] += random.nextGaussian();
            }
        }
        self.mutateGenome(genome);
        return self;
    }
}