#!/usr/bin/bash

cat input.txt |
    java -jar CommandlineCalculator-1.0-jar-with-dependencies.jar\
         > output.txt\
         2> errors.txt

cat output.txt | grep '^[^?].*=' > calculations.txt
cat calculations.txt |
    sed -e 's/\^/**/g' |
    sed -e 's/^\(.*\)=\(.*\)$/from math import e,pi,sqrt;print("[ OK ]" if (\1==\2) else "[ ERROR ] \1≠\2")/' |
    python
