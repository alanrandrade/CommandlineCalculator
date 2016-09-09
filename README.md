Commandline Calculator
==
Requirements
--
* Java
* Maven (for easy installation, no external libraries required, manual compilation is possible)

Installation
--
`mvn install`

Starting
--
`java -jar target/CommandlineCalculator-1.0-jar-with-dependencies.jar`

or use `target/CommandlineCalculator-1.0.jar` in an existing project.

Usage
--
Evaluate expressions: `6^6+4/(5/2)`

Assign variables: `x=5+2`

Create functions: `f(x,y):=x^y`

Combine the results: `f(x,2)/(5+12)`
