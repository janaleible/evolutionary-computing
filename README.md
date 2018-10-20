# Evolutionary Computing
Class project at Vrije Universiteit Amsterdam.


## Description
This project was built as an assignment in the course Evolutionary Computing at VU Amsterdam 2018.
It provides a flexible framework for testing evolutionary algorithms with different configurations on four 10-dimensional functions (Sphere, Bent Cigar, Schaffer's and Katsuura).

## Getting started

Build using ` ./build.sh `, build and run with ` ./run.sh`.

## Configuration
The algorithm can be configured using `.yaml` files. 
Below are some options, check out [the Configuration class](./group12/Configuration.java) for a complete list.

| Key               | Values                                                           |
|-------------------|------------------------------------------------------------------|
| populationsize    | any natural number                                               |
| generationgap     | between 0 and 1                                                  |
| crossover         | arithmetic, blend, random, simple, uniform                       |
| crossoverrate     | between 0 and 1                                                  |
| mutation          | no, random, adaptive                                             |
| mutationrate      | between 0 and 1                                                  |
| elitism           | true, false                                                      |
| sizeofelite       | any natural number, smaller than population size                 |
| parentselection   | fitnessproportional, rankbased, tournament                       |
| survivorselection | fitnessproportional, rankbased, tournament, restrictedtournament |
| numberofislands   | natural number                                                   |
| genderaware       | true, false                                                      |
| intertiaaware     | true, false                                                      |

Pass the configurations file like this: ` java $(python3 config2params.py config.yaml) -jar testrun.jar -submission=player12 -evaluation=SphereEvaluation -seed=1 `.
Only parameters that differ from the default configuration need to be specified in the config file.
 
## Collecting Stats
As writing to file is forbidden (for reasons), use following workaround:
write everything to stdout (use `System.out.println()`), where each line starts with `>>yourTagHere: ` and pipe stdout into a temp file.
Add a line to `postprocessing.sh` that sorts the output into appropriate files and does any processing steps you might require.
Use the ignored visualisations directory for storing plots and the tracked visual directory for code that produces the plots.

## Intellij setup
1. In File -> Project Structure:
    * Choose Java 8 as SDK
    * Set the output directory to out. This folder isn't actually used, but required to make Intellij happy
    * Make sure to mark the root directory as source root
    * Add the contest.jar as dependency
2. As Run Configuration:
    * Create new config for JAR Application
    * Choose testrun.jar in 'Path to JAR'
    * Set `-submission=player12 -evaluation=SphereEvaluation -seed=1` as program arguments (vary evaluation argument as in `run.sh` for different functions)
    * Set LD_LIBRARY_PATH=sourceroot as environment variable
    * Add external tool to 'Before launch': Program `/bin/sh`, arguments `build.sh`
    * In the logs tab, check save console output to file and select `tmp/out.txt`.
    * Optional: install python plugin and add run config for analysis/plotting scripts.

## Gridsearch
Make sure that [the gridsearch.py script](./gridsearch.py) is set up with all desired options.
Use the `gridsearch-diversity.sh` script to run gridsearch.
Evaluate results using `python gridsearch.py analyse /path/to/results/` for evaluation (results are stored in ./gridsearch/function-approach). 
This will print the best obtained score together with a config number (which corresponds to a file in gridseach/configs-approach).

## Experiments
Put all to-be-tested configs into experiments/configs and run `./run-all-experiments.sh`.
Evaluate results using visual/plotAll.sh