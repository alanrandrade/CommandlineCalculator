Commandline Calculator
=================================================
Torxakis

In order to run the Command Line Calculator within the wrapper:
> Open a Command Line (cmd);
> Navigate to the project's folder;
> Execute the following command: "java -jar .\SockServImproved.jar 7890 java -jar .\target\Comman
dlineCalculator-1.0-jar-with-dependencies.jar";
> Open another Command Line, navigate to the folder and load "torxakis CalculatorModel.txs";
> "tester Model Sut";
> "test 100" (for example);
> Tests will start being executed.

===================================================
Requirements
--
* Java
* Maven (for easy installation, no external libraries required, manual compilation is possible)

Installation
--
Using Eclipse:

File > Import > Existing Maven Projects > Next > Browse > Select path/to/deriverable/CommandlineCalculator > Ok > Select /pom.xml ...CommandlineCalculator:1.0.jar > Finish

Project will be available in Package Explorer.

Right click on project > Run as.. > Maven install > Tests will run automatically.

**Important:** Make sure the test file located at src/test/java/jUnitQuickCheckSet is named CalculatorPropertiesTest, with **Test** on its name.

If maven install does not run the tests automatically, you can still run the file CalculatorProperties (CalculatorPropertiesTest) as a Junit test.

Right click on the test file > Run as > Junit Test



