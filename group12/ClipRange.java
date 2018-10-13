package group12;

public class ClipRange extends RangeFunction{
    private double min;
    private double max;

    public ClipRange(double min, double max){
        this.min = min;
        this.max = max;
    }

    @Override
    public String toString() {
        return "ClipRange";
    }

    @Override
    public double[] limitToRange(double[] genome){
        for(int i = 0; i < genome.length; i++) {
            if (genome[i] > this.max) genome[i] = this.max;
            if (genome[i] < this.min) genome[i] = this.min;
        }
        return genome;
    }
}