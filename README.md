Commandline Calculator
==
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
--
