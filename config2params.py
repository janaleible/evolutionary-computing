import sys
import yaml

configFileName = sys.argv[1]

with open(configFileName) as configFile:
    config = yaml.safe_load(configFile)

params = ''

for param, value in config.items():
    params += ' -D{}={} '.format(param, value)

print(params)
