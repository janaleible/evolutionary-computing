import csv
import matplotlib.pyplot as plt
import sys
from collections import defaultdict
import os
from scipy.stats import ttest_ind, wilcoxon

def average(values: []) -> float:
    return sum(values) / len(values)


def read(function):
    
    avgFitness_by_generation = defaultdict(lambda: [])
    maxFitness_by_generation = defaultdict(lambda: [])
    diversity_by_generation = defaultdict(lambda: [])

    maxFitness_by_run = defaultdict(lambda: 0)
    finalDiversity_by_run = {}

    csvPath = 'experiments/{}_results'.format(function)
    outputPath = 'experiments/{}/visuals'.format(function)

    if not os.path.exists(outputPath):
        os.makedirs(outputPath)

    if os.path.isdir(csvPath):
        result_files = os.listdir(csvPath)
    else:
        result_files = [csvPath]

    for runNumber, result_file in enumerate(result_files):

        with open(os.path.join(csvPath, result_file), 'r') as csvFile:

            reader = csv.reader(csvFile)
            for row in reader:
                generation = int(row[0])
                avgFitness_by_generation[generation].append(float(row[3]))
                maxFitness_by_generation[generation].append(float(row[2]))
                diversity_by_generation[generation].append(float(row[5]))

                if(float(row[2])) > maxFitness_by_run[runNumber]: maxFitness_by_run[runNumber] = float(row[2])
                finalDiversity_by_run[runNumber] = float(row[6])

    generations = range(1, len(avgFitness_by_generation) + 1)
    averageFitness = []
    maximumFitness = []
    diversity = []

    for generation in generations:
        averageFitness.append(average(avgFitness_by_generation[generation]))
        maximumFitness.append(average(maxFitness_by_generation[generation]))
        diversity.append(average(diversity_by_generation[generation]))
        
    return averageFitness, maximumFitness, diversity, avgFitness_by_generation, maxFitness_by_generation, diversity_by_generation, maxFitness_by_run, finalDiversity_by_run



if __name__ == '__main__':

    function = sys.argv[2]
    approach = sys.argv[3]

    (baselineAvgFitness, baselineMaxFitness, baselineDiv, baselineFullFitness, baselineFullMaxFitness, baselineFullDiv, baselineMaxFitness_by_run, baselineFinalDivByRun) = read('{}-{}'.format('baseline', function))
    (islandAvgFitness, islandMaxFitness, islandDiv, islandFullFitness, islandFullMaxFitness, islandFullDiv, islandMaxFitness_by_run, islandFinalDivByRun) = read('{}-{}'.format(approach, function))

    if sys.argv[1] == 'plot-ts':
        outFile = sys.argv[4]

        tvalues = []
        pvalues = []

        numberOfGenerations = min(len(baselineFullDiv), len(islandFullDiv))

        for generation in range(1, numberOfGenerations + 1):
            (tvalue, pvalue) = ttest_ind(baselineFullDiv[generation], islandFullDiv[generation])
            tvalues.append(tvalue)
            pvalues.append(pvalue)

        # plt.plot(range(min(len(baselineFullDiv), len(islandFullDiv))), tvalues)
        plt.plot(range(numberOfGenerations), pvalues)
        plt.plot(range(numberOfGenerations), [0.05] * numberOfGenerations)

        plt.saveFig(outFile)

    if sys.argv[1] == 'scatter':

        outFile = sys.argv[4]

        plt.scatter(baselineFinalDivByRun.values(), baselineMaxFitness_by_run.values(), color='tab:red')
        plt.scatter(islandFinalDivByRun.values(), islandMaxFitness_by_run.values(), color='tab:blue')

        plt.saveFig(outFile)

    if sys.argv[1] == 'div-score':

        print(function, approach)

        baselineDiversity_by_run = {}
        islandDiversity_by_run = {}
        for run in range(100):
            baselineDiversity_by_run[run] = average([value[run] for value in filter(lambda l: len(l) == 100, baselineFullDiv.values())])
            islandDiversity_by_run[run] = average([value[run] for value in filter(lambda l: len(l) == 100, islandFullDiv.values())])

        print(
            'average diversity: ', average(baselineDiv) if approach == 'baseline' else average(islandDiv),
            wilcoxon(list(islandDiversity_by_run.values()), list(baselineDiversity_by_run.values())).pvalue < 0.05
        )
        print(
            'final accumulated diversity: ', average(baselineFinalDivByRun.values()) if approach == 'baseline' else average(islandFinalDivByRun.values())
        )
