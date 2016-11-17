#!/usr/bin/bash

# Bash script om use cases te testen

BASEDIR="$HOME/CommandlineCalculator/auto-test/"

for testcase in exit\
                    function_assignment\
                    builtin_function1\
                    builtin_function2\
                    variable_assignment\
                    builtin_variables\
                    expression1\
                    expression2\
                    equality1\
                    equality2\
                    equality3
do
    echo -en ""$testcase": "

    # pipe input and catch outputs and errors

    cat $BASEDIR"usecases/"$testcase"_input.txt" |
        java -jar $BASEDIR"../target/CommandlineCalculator-1.0-jar-with-dependencies.jar"\
             > $BASEDIR"outputs/"$testcase"_output.txt"\
             2> $BASEDIR"outputs/"$testcase"_errors.txt"

    # compare stdout to expected stdout
    if diff $BASEDIR"usecases/"$testcase"_output.txt" $BASEDIR"outputs/"$testcase"_output.txt";
    then
        echo -n "[OK]"
    else
        echo "*FAIL* on ["$testcase"] Output"
        # show difference
        # diff $BASEDIR"usecases/"$testcase"_output.txt" $BASEDIR"outputs/"$testcase"_output.txt"
    fi

    # compare sterr to expected stderr
    if diff $BASEDIR"usecases/"$testcase"_errors.txt" $BASEDIR"outputs/"$testcase"_errors.txt";
    then
        echo "[OK]"
    else
        echo "*FAIL* ["$testcase"] Error"
        # show difference
        # diff $BASEDIR"usecases/"$testcase"_errors.txt" $BASEDIR"outputs/"$testcase"_errors.txt"
    fi

done

# shell script itself didn't fail
true
