import csv
import matplotlib.pyplot as plt
import sys
from collections import defaultdict
import os


def average(values: []) -> float:
    return sum(values) / len(values)

avgFitness_by_generation = defaultdict(lambda: [])
maxFitness_by_generation = defaultdict(lambda: [])
diversity_by_generation = defaultdict(lambda: [])


function = sys.argv[1]
out_dir = sys.argv[2]

csvPath = 'experiments/{}_results'.format(function)
outputPath = 'experiments/{}/visuals'.format(function)

if not os.path.exists(outputPath):
    os.makedirs(outputPath)

if os.path.isdir(csvPath):
    result_files = os.listdir(csvPath)
else:
    result_files = [csvPath]

for result_file in result_files:

    with open(os.path.join(csvPath, result_file), 'r') as csvFile:

        reader = csv.reader(csvFile)
        for row in reader:
            generation = int(row[0])
            avgFitness_by_generation[generation].append(float(row[3]))
            maxFitness_by_generation[generation].append(float(row[2]))
            diversity_by_generation[generation].append(float(row[5]))


generations = range(1, len(avgFitness_by_generation) + 1)
averageFitness = []
maximumFitness = []
diversity = []

for generation in generations:
    averageFitness.append(average(avgFitness_by_generation[generation]))
    maximumFitness.append(average(maxFitness_by_generation[generation]))
    diversity.append(average(diversity_by_generation[generation]))

print(max(maximumFitness))

figure, maxAxis = plt.subplots(figsize=(8, 4))

maxAxis.set_xlabel('Generations')
maxColor = 'tab:blue'
averageColor = 'tab:green'
diversityColor = 'tab:red'

# averageAxis = maxAxis.twinx()
# averageAxis.set_ylabel('Average fitness', color=averageColor)
# averageAxis.plot(generations, averageFitness, color=averageColor)
# averageAxis.tick_params(axis='y', labelcolor=averageColor)

maxAxis.set_ylabel('Maximum Fitness', color=maxColor)
maxAxis.plot(generations, maximumFitness, color=maxColor)
maxAxis.tick_params(axis='y', labelcolor=maxColor)
maxAxis.set_ylim(0, 10.5)
maxAxis.set_yticks([0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0])

diversityAxis = maxAxis.twinx()
diversityAxis.set_yscale('log')
diversityAxis.set_ylabel('Diversity', color=diversityColor)
diversityAxis.plot(generations, diversity, color=diversityColor)
diversityAxis.tick_params(axis='y', labelcolor=diversityColor)
diversityAxis.set_ylim(0.0001, 100)

figure.tight_layout()
plt.savefig('{}/{}.eps'.format(out_dir, function))