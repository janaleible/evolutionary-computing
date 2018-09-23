# Evolutionary Computing

Optimization Assignment. Group 12.

* [Course page on Canvas](https://canvas.vu.nl/courses/34739)
* [Report on Overleaf](https://www.overleaf.com/19178866swqfmddgvbhg#/71722249/)
* [Code on Github](https://github.com/janaleible/evolutionary-computing)

Build using:
```bash
./build.sh
```

Build and run tests using:
```bash
./run.sh
```
 
## Collecting Stats
Writing to file is forbidden for "security reasons".
Workaround: write everything to stdout (use `System.out.println()`), where each line starts with `>>yourTagHere: ` and pipe stdout into `tmp/out.txt`.
Add a line to `postprocessing.sh` that sorts the output into appropriate files and does any processing steps you might require.
Use the ignored visualisations directory for storing plots and the tracked visual directory for code that produces the plots.

## Intellij setup
1. In File -> Project Structure:
    * Choose Java 10 as SDK
    * Set the output directory to out. This folder isn't actually used, but required to make intellij happy
    * Make sure to mark the root directory as source root
    * Add the contest.jar as dependency
2. As Run Configuration:
    * Create new config for JAR Application
    * Choose testrun.jar in 'Path to JAR'
    * Set `-submission=player12 -evaluation=SphereEvaluation -seed=1` as program arguments (vary evaluation argument as in `run.sh` for different functions)
    * Set LD_LIBRARY_PATH=sourceroot as environment variable
    * Add external tool to 'Before launch': Program `/bin/sh`, arguments `build.sh`
    * In the logs tab, check save console output to file and select `tmp/out.txt`.

[Submit online](http://mac360.few.vu.nl:8080/EC_BB_ASSIGNMENT/index.html) using password `GG2o01g=`
