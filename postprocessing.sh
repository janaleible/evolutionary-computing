# Parsing stdout collected in tmp/out.txt. Make new entry for each tag.

cat tmp/out.txt | grep ">>familyTree: " | sed -e 's|>>familyTree: ||' > visualisations/familyTree.dot
dot -Tsvg visualisations/familyTree.dot -o visualisations/familyTree.svg

cat tmp/out.txt | grep ">>populationStats: " | sed -e 's|>>populationStats: ||' > visualisations/populationStats.csv
python3 visual/plotFitness.py "visualisations/populationStats.csv" "visualisations/fitness.pdf"
