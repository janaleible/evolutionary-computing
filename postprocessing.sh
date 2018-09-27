# Parsing stdout collected in tmp/out.txt. Make new entry for each tag.

cat out/out.txt | grep ">>familyTree: " | sed -e 's|>>familyTree: ||' > visualisations/familyTree.dot
dot -Tsvg visualisations/familyTree.dot -o visualisations/familyTree.svg

cat out/out.txt | grep ">>populationStats: " | sed -e 's|>>populationStats: ||' > visualisations/populationStats.csv
python3 visual/plotFitness.py "visualisations/populationStats.csv" "visualisations/{}_fitness.pdf"
