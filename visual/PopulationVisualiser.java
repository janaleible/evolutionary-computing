package visual;

import group12.Individual;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Stack;

public class PopulationVisualiser {

	public static void visualise(String outputFile, Map<Integer, Individual> population, Individual start) {

		StringBuilder dot = new StringBuilder();
		dot.append("DiGraph {\n");

		Stack<Individual> stack = new Stack<>();
		stack.push(start);

		ArrayList<Integer> done = new ArrayList<>();

		while (!stack.empty()) {
			Individual current = stack.pop();
			dot.append(current.id).append("[label=\"").append(current.id).append("\\ngeneration: ").append(current.generation()).append("\\nfitness: ").append(current.getFitness()).append("\"];\n");

			if (current.parents() != null ) {
				if(!done.contains(current.parents()[0].id)) {
					stack.push(current.parents()[0]);
					done.add(current.parents()[0].id);
				}
				if(!done.contains(current.parents()[1].id)) {
					stack.push(current.parents()[1]);
					done.add(current.parents()[1].id);
				}
				dot.append(current.parents()[0].id).append("->").append(current.id).append("\n");
				dot.append(current.parents()[1].id).append("->").append(current.id).append("\n");
			}
		}

		dot.append("}");

		/*for (String line : dot.toString().split("\n")) {
			System.out.print(">>familyTree: ");
			System.out.println(line);
		}*/
	}
}
