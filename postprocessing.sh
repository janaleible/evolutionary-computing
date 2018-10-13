in=$1
out=$2
# Parsing stdout collected in tmp/out.txt. Make new entry for each tag.

# cat $in | grep ">>familyTree: " | sed -e 's|>>familyTree: ||' > $out.dot
cat $in | grep ">>populationStats: " | sed -e 's|>>populationStats: ||' > $out.csv
