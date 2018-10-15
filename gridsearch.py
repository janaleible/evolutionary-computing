from collections import defaultdict
import operator
import os
import sys
import yaml

functions = ['SphereEvaluation', 'BentCigarFunction', 'KatsuuraEvaluation', 'SchaffersEvaluation']

def write_config(config, filename):
    with open(filename, 'w') as file:
        file.write(yaml.dump(config))


def generate_baseline():
    filenumber = 0
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
                                                        write_config(config, 'gridsearch/configs-baseline/{}.yaml'.format(filenumber))
                                                        filenumber += 1
                                                else:
                                                    write_config(config, 'gridsearch/configs-baseline/{}.yaml'.format(filenumber))
                                                    filenumber += 1
                                        else:
                                            write_config(config, 'gridsearch/configs-baseline/{}.yaml'.format(filenumber))
                                            filenumber += 1

    print(filenumber)


def generate_island():

    filenumber = 1

    for numberOfIslands in [2, 3, 5, 10]:
        for numberOfMigrants in [0, 1, 2]:
            for generationsPerEpoch in [25, 75, 150]:
                config = {
                    'numberofislands': numberOfIslands,
                    'numberofmigrants': numberOfMigrants,
                    'generationsperepoch': generationsPerEpoch
                }
                write_config(config, 'gridsearch/configs-island/{}.yaml'.format(filenumber))
                filenumber += 1


def generate_genders():
    write_config({
        'genderaware': 'true'
    }, 'gridsearch/configs-gender/0.yaml')


def generate_taboo():
    write_config({
        'inertiaaware': 'true'
    }, 'gridsearch/configs-taboo/0.yaml')


def generate_rts():
    filenumber = 0

    for tournamentsize in [2, 5, 10]:
        write_config({
            'survivorselection': 'restrictedtournament',
            'tournamentsize': tournamentsize
        }, 'gridsearch/configs-rts/{}.yaml'.format(filenumber))
        filenumber += 1

def analyse(path):

    results = defaultdict(lambda: [])

    for file_name in filter(lambda name: name.endswith(".txt"), os.listdir(path)):

            with open(os.path.join(path, file_name)) as file:
                (config, seed) = file_name[:-4].split('_')
                try:
                    score = float(file.readlines()[-2].split(': ')[1][:-1])
                    results[config].append(score)
                except:
                    continue


    aggregated = sorted({key: sum(value) / len(value) for key, value in results.items()}.items(), key=operator.itemgetter(1), reverse=True)
    print(aggregated[0])


if __name__ == '__main__':

    if sys.argv[1] == 'generate-baseline':
        generate_baseline()

    if sys.argv[1] == 'generate-island':
        generate_island()

    if sys.argv[1] == 'generate-taboo':
        generate_taboo()

    if sys.argv[1] == 'generate-genders':
        generate_genders()

    if sys.argv[1] == 'generate-rts':
        generate_rts()

    if sys.argv[1] == 'analyse':
        analyse(sys.argv[2])
