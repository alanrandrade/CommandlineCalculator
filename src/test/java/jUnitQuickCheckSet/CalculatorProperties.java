package jUnitQuickCheckSet;

import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

import eu.gillissen.commandline.calculator.Calc;



@RunWith(JUnitQuickcheck.class)
public class CalculatorProperties {
	
	Calc calc;
	
	@Before
	public void newCalc(){
		calc = new Calc();
	}
	
    @Property(trials = 10) public void logFunctionShouldUseArgGreaterThan001(@InRange(minDouble = 0.00001) double numArg)
        throws Exception {
    	
    	assumeThat(numArg, greaterThan(0.00001));
        
        System.out.println(numArg);
        BigDecimal result = calc.parse(String.format("log(%f)", numArg)).evaluate();
        System.out.println("log ("+numArg+"): " + result +"\nExpected: "+ new BigDecimal(Math.log10(numArg)));
        BigDecimal expected = new BigDecimal(Math.log10(numArg));
        assertTrue(((result.subtract(expected).abs()).compareTo(new BigDecimal(0.0001))) == -1 ); 
   		//assertEquals(re	sult, new BigDecimal(Math.log10(numArg)));
        
    }
    
    @Property (trials = 10)public void RoundHasFractionalValueZero(double numArg)
            throws Exception {
        	
        	//assumeThat(numArg,  );
            
    		
            //System.out.println(Double.MIN_VALUE);
            
            BigDecimal round = calc.parse(String.format("round(%f)", numArg)).evaluate();
            //naturalLog = naturalLog.pow(-1);
            System.out.println("round ("+numArg+"): "+round.remainder(BigDecimal.ONE) + "\nExpected: "+ new BigDecimal((Math.round(numArg))).remainder(BigDecimal.ONE) );
            //BigDecimal expected = new BigDecimal(Math.log10(numArg));
            assertTrue((round.remainder(BigDecimal.ONE)).compareTo(BigDecimal.ZERO) == 0); 
       		//assertEquals(re	sult, new BigDecimal(Math.log10(numArg)));
            
        }
    
    @Property (trials = 10)public void FloorHasFractionalValueZero(double numArg)
            throws Exception {
        	
        	//assumeThat(numArg,  );
            
            System.out.println(numArg);
            
            BigDecimal floor = calc.parse(String.format("floor(%f)", numArg)).evaluate();
            //naturalLog = naturalLog.pow(-1);
            System.out.println("floor ("+numArg+"): "+floor.remainder(BigDecimal.ONE) + "\nExpected: "+ new BigDecimal((Math.round(numArg))).remainder(BigDecimal.ONE) );
            //BigDecimal expected = new BigDecimal(Math.log10(numArg));
            assertTrue((floor.remainder(BigDecimal.ONE)).compareTo(BigDecimal.ZERO) == 0); 
       		//assertEquals(re	sult, new BigDecimal(Math.log10(numArg)));
            
        }
    
    @Property (trials = 10)public void CeilHasFractionalValueZero(double numArg)
            throws Exception {
        	
        	//assumeThat(numArg,  );
            
            System.out.println(numArg);
            
            BigDecimal ceil = calc.parse(String.format("ceil(%f)", numArg)).evaluate();
            //naturalLog = naturalLog.pow(-1);
            System.out.println("ceil ("+numArg+"): "+ceil.remainder(BigDecimal.ONE) + "\nExpected: "+ new BigDecimal((Math.round(numArg))).remainder(BigDecimal.ONE) );
            //BigDecimal expected = new BigDecimal(Math.log10(numArg));
            assertTrue((ceil.remainder(BigDecimal.ONE)).compareTo(BigDecimal.ZERO) == 0); 
       		//assertEquals(re	sult, new BigDecimal(Math.log10(numArg)));
            	
        }
    
    @Property (trials = 10)public void sumOfExponentsWithSameBase(double base, double firstExp, double secondExp)
            throws Exception {
        	
        	//assumeThat(numArg,  );
            
            
            
            BigDecimal sumMultSameBase = calc.parse(String.format("(%f^%f)*(%f^%f)", base, firstExp, base, secondExp)).evaluate();
            BigDecimal SameBase = calc.parse(String.format("(%f^%f)", base, (firstExp+secondExp))).evaluate();
            //naturalLog = naturalLog.pow(-1);
            System.out.println("SameBase ("+base+"^"+firstExp+")*("+base+"^"+secondExp+"): "+sumMultSameBase + "\nExpected: "+ Math.pow(base, (firstExp+secondExp)) );
            //BigDecimal expected = new BigDecimal(Math.log10(numArg));
            assertTrue((sumMultSameBase.subtract(SameBase).abs()).compareTo((new BigDecimal(0.0001))) == -1);
            
       		//assertEquals(re	sult, new BigDecimal(Math.log10(numArg)));
            	
        }
    
    @Property (trials = 100)public void comutation(double a, double b)
            throws Exception {
        	
        	//assumeThat(numArg,  );
            
            
            
            BigDecimal aPlusB = calc.parse(String.format("%f + %f", a, b)).evaluate();
            BigDecimal bPlusA = calc.parse(String.format("%f + %f", b, a)).evaluate();
            System.out.println("	Comutation ("+a+"+"+b+"): "+aPlusB + "\nExpected: "+ bPlusA );
            //naturalLog = naturalLog.pow(-1);
            //BigDecimal expected = new BigDecimal(Math.log10(numArg));
            assertTrue((aPlusB.subtract(bPlusA).abs()).compareTo(BigDecimal.ZERO) == 0);
            
       		//assertEquals(re	sult, new BigDecimal(Math.log10(numArg)));
            	
        }
    
    @Property (trials = 100)public void association(double a, double b, double c)
            throws Exception {
        	
        	//assumeThat(numArg,  );
            
            
            
            BigDecimal aPlusB = calc.parse(String.format("%f + (%f + %f)", a, b, c)).evaluate();
            BigDecimal bPlusA = calc.parse(String.format("(%f + %f) + %f", a, b, c)).evaluate();
            System.out.println("	Comutation ("+a+"+"+b+"): "+aPlusB + "\nExpected: "+ bPlusA );
            //naturalLog = naturalLog.pow(-1);
            //BigDecimal expected = new BigDecimal(Math.log10(numArg));
            assertTrue((aPlusB.subtract(bPlusA).abs()).compareTo(BigDecimal.ZERO) == 0);
            
       		//assertEquals(re	sult, new BigDecimal(Math.log10(numArg)));
            	
        }
    
    @Property (trials = 100)public void identity(double a)
            throws Exception {
        	
        	//assumeThat(numArg,  );
            
            
            
            BigDecimal aPlusB = calc.parse(String.format("%f + 0", a)).evaluate();
            BigDecimal bPlusA = calc.parse(String.format("0 + %f", a)).evaluate();
            System.out.println("	Identity ("+a+"): "+aPlusB + "\nExpected: "+ bPlusA );
            //naturalLog = naturalLog.pow(-1);
            //BigDecimal expected = new BigDecimal(Math.log10(numArg));
            assertTrue((aPlusB.subtract(bPlusA).abs()).compareTo(BigDecimal.ZERO) == 0);
            
       		//assertEquals(re	sult, new BigDecimal(Math.log10(numArg)));
            	
        }
    
    @Property (trials = 10)
    public void lnInverseExponent(double a) throws Exception {
    	    	
    	BigDecimal lnex = calc.parse(String.format("ln(e^%f)", a)).evaluate();
    	BigDecimal x = calc.parse(String.format("%f", a)).evaluate();
    	
    	System.out.println("ln(e^"+a+"): "+lnex + "\nExpected: "+ x + "\n" );
    	
    	
    	assertTrue((lnex.subtract(x).abs()).compareTo(BigDecimal.ZERO) == 0);
    	
    	
    	
    	
    }
}
