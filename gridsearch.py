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
    for range_function in ['wrap', 'clip']:
        for crossover in ['arithmetic', 'blend', 'random', 'uniform']:
            for mutation in ['random', 'adaptive']:
                for mutation_rate in [0.01, 0.3, 0.5]:
                    for parent_selection in ['fitnessproportional', 'rankbased', 'tournament']:
                        for elitism in ['true', 'false']:
                            for population_size in [30, 100, 250]:
                                for generation_gap in [0.5, 1]:
                                    for crossover_rate in [0.8, 1]:

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


if __name__ == '__main__':

    if sys.argv[1] == 'generate':
        generate()

# for function in functions:
#
#     configuration_scores = defaultdict(lambda: 0)
#
#     for file_name in filter(lambda name: name.endswith(".txt"), os.listdir('gridsearch/{}/'.format(function))):
#
#             with open('gridsearch/{}/{}'.format(function, file_name)) as file:
#
#                 properties = file.readline().strip('\n').split(', ')
#                 for property in properties:
#                     (key, value) = property.split(': ')
#
#                     if key == 'Score':
#                         score = float(value)
#
#                     if key == 'configuration':
#                         id = value
#
#
#                 configuration_scores[id] += score
#
#     topConfig = max(configuration_scores.items(), key=operator.itemgetter(1))[0]
#
#     print('Function {}: config {}, score {}'.format(function, topConfig, configuration_scores[topConfig] / 5)) # normalise by number of tested seeds
