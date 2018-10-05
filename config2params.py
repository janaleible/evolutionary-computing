import sys
import yaml

configFileName = sys.argv[1]

with open(configFileName) as configFile:
    config = yaml.safe_load(configFile)

params = ''

for set_of_params in config:
    for key, value in set_of_params.items():
        params += ' -D{}={} '.format(key, value)

print(params)
