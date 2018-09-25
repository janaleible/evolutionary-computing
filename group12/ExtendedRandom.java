package group12;

import java.util.Random;

public class ExtendedRandom extends Random {

	public double sampleFromRange(double lowerBound, double upperBound) {

		double range = upperBound - lowerBound;

		return (this.nextDouble() * range) - lowerBound;
	}

	public double[] array(int size, double lowerBound, double upperBound) {
		double[] array = new double[size];
		for (int i = 0; i < size; i++) {
			array[i] = sampleFromRange(lowerBound, upperBound);
		}
		return array;
	}

    public boolean coinflip() {
        return this.nextInt(2) == 0;
    }
}
