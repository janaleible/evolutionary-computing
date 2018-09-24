package group12;

import java.util.Random;

public class ExtendedRandom extends Random {

	public double sampleFromRange(double lowerBound, double upperBound) {

		double range = upperBound - lowerBound;

		return (this.nextDouble() * range) - lowerBound;
	}
}
