from collections import defaultdict
import operator
import os
import sys
import yaml
import copy

functions = ['SphereEvaluation', 'BentCigarFunction', 'KatsuuraEvaluation', 'SchaffersEvaluation']

def write_config(config, filename):
    with open(filename, 'w') as file:
        file.write(yaml.dump(config))


def generate_baseline(approach):

    configs = []
    for range_function in ['wrap']:
        for crossover in ['arithmetic']:
            for mutation in ['random']:
                for mutation_rate in [0.01, 0.1]:
                    for parent_selection in ['fitnessproportional', 'rankbased']:
                        for elitism in ['true']:
                            for population_size in [250]:
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
                                            for survivor_selection in ['tournament']:
                                                config['survivorselection'] = survivor_selection

                                                if parent_selection == 'tournament' or survivor_selection == 'tournament':
                                                    for tournamentsize in [2, 5]:
                                                        config['tournamentsize'] = tournamentsize
                                                        # write_config(config, 'gridsearch/configs-baseline/{}.yaml'.format(filenumber))
                                                        # filenumber += 1
                                                        configs.append(config)
                                                else:
                                                    # write_config(config, 'gridsearch/configs-baseline/{}.yaml'.format(filenumber))
                                                    # filenumber += 1
                                                    configs.append(config)
                                        else:
                                            # write_config(config, 'gridsearch/configs-baseline/{}.yaml'.format(filenumber))
                                            # filenumber += 1
                                            configs.append(config)

    if approach == 'island':

        island_configs = []
        for config in configs:
            for numberOfIslands in [2, 4, 8]:
                for numberOfMigrants in [1, 2, 4]:
                    for generationsPerEpoch in [32, 64, 128]:
                        newConf = copy.deepcopy(config)
                        newConf['numberofislands'] = numberOfIslands
                        newConf['numberofmigrants'] = numberOfMigrants
                        newConf['generationsperepoch'] = generationsPerEpoch
                        island_configs.append(newConf)

        for i, config in enumerate(island_configs):
            write_config(config, 'gridsearch/configs-island/{}.yaml'.format(i))


        print(i)

    if approach == 'genders':
        gender_configs = []
        for population_size in [256, 128, 64]:

            for config in configs:
                newConf = copy.deepcopy(config)
                newConf['populationsize'] = population_size
                newConf['genderaware'] = 'true'
                gender_configs.append(newConf)

        for i, config in enumerate(gender_configs):
            write_config(config, 'gridsearch/configs-gender/{}.yaml'.format(i))

        print(i)

    if approach == 'taboo':
        for i, config in enumerate(configs):
            config['inertiaaware'] = 'true'
            write_config(config, 'gridsearch/configs-taboo/{}.yaml'.format(i))
        print(i)

    if approach == 'rts':

        rts_configs = set()
        for config in configs:
            for tournamentsize in [4, 16, 32, 64]:

                newConf = copy.deepcopy(config)
                newConf['survivorSelection'] = 'restrictedtournament'
                newConf['generationgap'] = '0.5'
                newConf['tournamentsize'] = tournamentsize
                rts_configs.add(config)

        for i, config in enumerate(rts_configs):
            write_config(config, 'gridsearch/configs-rts/{}.yaml'.format(i))


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

    for tournamentratio in [0.5, 0.25, 0.1]:
        for populationsize in [30, 100, 250]:
            write_config({
                'survivorselection': 'restrictedtournament',
                'populationsize': populationsize,
                'tournamentsize': int(populationsize * tournamentratio),
                'generationgap': 0.5
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
        generate_baseline('')

    if sys.argv[1] == 'generate-island':
        generate_baseline('island')

    if sys.argv[1] == 'generate-taboo':
        generate_baseline('taboo')

    if sys.argv[1] == 'generate-genders':
        generate_baseline('genders')

    if sys.argv[1] == 'generate-rts':
        generate_baseline('rts')

    if sys.argv[1] == 'analyse':
        analyse(sys.argv[2])
