package group12;

import java.util.Random;

public class RandomMutation extends Mutation {

    private Random random;

    @Override
    public Individual mutate(Individual self){
        //Throw exception here?
        return self;
    }

    @Override
    public Individual mutate(Individual self, float mutationRate){
        double[] genome = self.genome();
        for(int i = 0; i<genome.length; i++){
            if(this.random.nextFloat() < mutationRate){
                genome[i] += random.nextGaussian();
            }
        }
        self.mutateGenome(genome);
        return self;
    }
}