from collections import defaultdict
import operator
import os
import sys
import yaml

functions = ['SphereEvaluation', 'BentCigarFunction', 'KatsuuraEvaluation', 'SchaffersEvaluation']
file_number = 0


def write_config(config):
    global file_number
    with open('gridsearch/configurations/{}.yaml'.format(file_number), 'w') as file:
        file.write(yaml.dump(config))
    file_number += 1


def generate():
    global file_number
    for range_function in ['wrap']:
        for crossover in ['arithmetic', 'blend', 'random', 'uniform']:
            for mutation in ['random', 'adaptive']:
                for mutation_rate in [0.01, 0.3, 0.5]:
                    for parent_selection in ['fitnessproportional', 'rankbased', 'tournament']:
                        for elitism in ['true', 'false']:
                            for population_size in [30, 100, 250]:
                                for generation_gap in [0.5, 1]:
                                    for crossover_rate in [1]:

                                        config = {
                                            'rangefunction': range_function,
                                            'crossover': crossover,
                                            'mutation': mutation,
                                            'parentselection': parent_selection,
                                            'elitism': elitism,
                                            'populationsize': population_size,
                                            'generationgap': generation_gap,
                                            'crossoverrate': crossover_rate,
                                            'mutationrate': mutation_rate
                                        }

                                        if generation_gap < 1:
                                            for survivor_selection in ['fitnessproportional', 'rankbased', 'tournament']:
                                                config['survivorselection'] = survivor_selection

                                                if parent_selection == 'tournament' or survivor_selection == 'tournament':
                                                    for tournamentsize in [2, 5, 10]:
                                                        config['tournamentsize'] = tournamentsize
                                                        write_config(config)
                                                else:
                                                    write_config(config)
                                        else:
                                            write_config(config)

    print(file_number)


def analyse(function):

    results = defaultdict(lambda: [])

    for file_name in filter(lambda name: name.endswith(".txt"), os.listdir('gridsearch/{}/'.format(function))):

            with open('gridsearch/{}/{}'.format(function, file_name)) as file:
                (config, seed) = file_name[:-4].split('_')
                try:
                    score = float(file.readlines()[1].split(': ')[1][:-1])
                    results[config].append(score)
                except:
                    continue


    aggregated = sorted({key: sum(value) / len(value) for key, value in results.items()}.items(), key=operator.itemgetter(1), reverse=True)
    print(aggregated)


if __name__ == '__main__':

    if sys.argv[1] == 'generate':
        generate()

    if sys.argv[1] == 'analyse':
        analyse(sys.argv[2])
