import collections
import csv
import matplotlib.pyplot as plt
import sys

generations = []
islands = collections.defaultdict(lambda: {
    'averageFitness': [],
    'maximumFitness': []
})

csvPath = sys.argv[1]
outputPath = sys.argv[2]

with open(csvPath, 'r') as csvFile:

    reader = csv.reader(csvFile)
    for row in reader:
        generations.append(int(row[0]))
        islands[int(row[1])]['averageFitness'].append(float(row[3]))
        islands[int(row[1])]['maximumFitness'].append(float(row[2]))


for island, values in islands.items():
    figure, averageAxis = plt.subplots()

    averageAxis.set_xlabel('Generations')

    averageColor = 'tab:red'
    averageAxis.set_ylabel('Average fitness', color=averageColor)
    averageAxis.plot(generations, values['averageFitness'], color=averageColor)
    averageAxis.tick_params(axis='y', labelcolor=averageColor)

    maxColor = 'tab:blue'
    maxAxis = averageAxis.twinx()
    maxAxis.set_ylabel('Maximum Fitness', color=maxColor)
    maxAxis.plot(generations, values['maximumFitness'], color=maxColor)
    maxAxis.tick_params(axis='y', labelcolor=maxColor)

    figure.tight_layout()
    plt.savefig(outputPath.format(island))

