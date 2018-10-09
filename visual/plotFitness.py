import csv
import matplotlib.pyplot as plt
import sys

generations = []
averageFitness = []
maximumFitness = []

csvPath = sys.argv[1]
outputPath = sys.argv[2]

with open(csvPath, 'r') as csvFile:

    reader = csv.reader(csvFile)
    for row in reader:
        generations.append(int(row[0]))
        averageFitness.append(float(row[2]))
        maximumFitness.append(float(row[1]))


figure, averageAxis = plt.subplots()

averageAxis.set_xlabel('Generations')

averageColor = 'tab:red'
averageAxis.set_ylabel('Average fitness', color=averageColor)
averageAxis.plot(generations, averageFitness, color=averageColor)
averageAxis.tick_params(axis='y', labelcolor=averageColor)

maxColor = 'tab:blue'
maxAxis = averageAxis.twinx()
maxAxis.set_ylabel('Maximum Fitness', color=maxColor)
maxAxis.plot(generations, maximumFitness, color=maxColor)
maxAxis.tick_params(axis='y', labelcolor=maxColor)

figure.tight_layout()
plt.savefig(outputPath)

