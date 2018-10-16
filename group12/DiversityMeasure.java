package group12;
import java.util.List;
import java.util.Arrays;

public abstract class DiversityMeasure {

	public abstract double measure(List<Individual> population);

	public static double hammingDistance(double[] from, double[] to) {

		double distance = 0;

		for (int i = 0; i < from.length; i++) {
			distance += Math.pow(from[i] - to[i], 2);
		}

		return distance;
	}

	public static double[] centroid(List<double[]> vectors) {

		double result[] = new double[vectors.get(0).length];

		for (double[] vector : vectors) {
			Arrays.setAll(result, i -> result[i] + (vector[i] / vectors.size()));
		}

		return result;
	}
}
