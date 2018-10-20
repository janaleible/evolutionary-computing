declare -a functions=("BentCigarFunction" "SchaffersEvaluation" "KatsuuraEvaluation")
declare -a approaches=("baseline" "island" "gender" "taboo" "rts")

## now loop through the above array
for function in "${functions[@]}"
do
    for approach in "${approaches[@]}"
    do
        python3 -W ignore analysis.py div-score $function $approach
        python3 -W ignore analysis.py results $function $approach
    done
done

