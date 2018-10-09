from collections import defaultdict
import operator
import os

functions = ['SphereEvaluation', 'BentCigarFunction', 'KatsuuraEvaluation', 'SchaffersEvaluation']

for function in functions:

    configuration_scores = defaultdict(lambda: 0)

    for file_name in filter(lambda name: name.endswith(".txt"), os.listdir('gridsearch/{}/'.format(function))):

            with open('gridsearch/{}/{}'.format(function, file_name)) as file:

                properties = file.readline().strip('\n').split(', ')
                for property in properties:
                    (key, value) = property.split(': ')

                    if key == 'Score':
                        score = float(value)

                    if key == 'configuration':
                        id = value


                configuration_scores[id] += score

    topConfig = max(configuration_scores.items(), key=operator.itemgetter(1))[0]

    print('Function {}: config {}, score {}'.format(function, topConfig, configuration_scores[topConfig] / 5)) # normalise by number of tested seeds
