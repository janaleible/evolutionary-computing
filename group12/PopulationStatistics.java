package group12;

import java.util.ArrayList;
import java.util.List;

public class PopulationStatistics {

	private List<String> csvRows;

	public PopulationStatistics() {
		this.csvRows = new ArrayList<>();
	}

	public void update(int generation, double maximumFitness, double averageFitness, double averageAge, double diversity) {
		this.csvRows.add(this.toCSV(generation, maximumFitness, averageFitness, averageAge, diversity));
	}

	public void write() {
		for (String row : this.csvRows) {
			System.out.print(">>populationStats: ");
			System.out.println(row);
		}
	}

	private String toCSV(Object... columns) {
		StringBuilder csv = new StringBuilder();
		for (Object column : columns) {
			csv.append(",").append(column);
		}
		csv.deleteCharAt(0);
		return csv.toString();
	}
}
