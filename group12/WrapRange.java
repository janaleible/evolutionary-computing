package group12;

import java.lang.Math;

public class WrapRange extends RangeFunction{
    private double min;
    private double max;
    private double abs;

    public WrapRange(double min, double max){
        this.min = min;
        this.max = max;
        this.abs = Math.abs(this.max - this.min);
    }

    @Override
    public double[] limitToRange(double[] genome){
        for(int i = 0; i < genome.length; i++) {
            if (genome[i] > this.max){
                double remainder = genome[i] - this.max;
                double mod = remainder % this.abs;
                genome[i] = this.min + mod;
            }
            if (genome[i] < this.min){
                double remainder = genome[i] - this.min;
                double mod = remainder % this.abs;
                genome[i] = this.max + mod;
            }
        }
        return genome;
    }

    @Override
    public String toString() {
        return "WrapRange";
    }
}