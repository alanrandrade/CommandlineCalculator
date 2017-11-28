package jUnitQuickCheckSet;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeThat;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.Timeout;
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
	
	@Rule
    public Timeout globalTimeout = Timeout.seconds(20);
	
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
    public void lnInverseExponent(BigDecimal a) throws Exception {
    	    	
    	BigDecimal lnex = calc.parse(String.format("ln(e^%s)", a.toString())).evaluate();
    	BigDecimal x = calc.parse(String.format("%s", a.toString())).evaluate();
    	
    	System.out.println("ln(e^"+a+"): "+lnex + "\nExpected: "+ x + "\n" );
    	
    	
    	assertTrue((lnex.subtract(x).abs()).compareTo(BigDecimal.ZERO) == 0);
    	
    }
    
    @Property (trials = 5)
    public void sqrtProduct(BigDecimal a, BigDecimal b) throws Exception {
	
    	assumeThat(a, greaterThan(BigDecimal.ZERO));
    	assumeThat(b, greaterThan(BigDecimal.ZERO));
    	
    	BigDecimal sqrtMultiTogether = calc.parse(String.format("sqrt(%s*%s)", a.toString(), b.toString())).evaluate();
    	BigDecimal sqrtMultiSeparate = calc.parse(String.format("sqrt(%s)*sqrt(%s)", a.toString(), b.toString())).evaluate();
    	
    	System.out.println("sqrt(" + a + " * " + b + "): " + sqrtMultiTogether + "\n");
    	System.out.println("sqrt(" + a + ")" + " * " + "sqrt(" + b + "): " + sqrtMultiSeparate + "\n");
    	
    	assertTrue((sqrtMultiTogether.subtract(sqrtMultiSeparate).abs()).compareTo(BigDecimal.ZERO) == 0);
    	
    }
    
    @Property (trials = 5)
    public void sqrtDivision(BigDecimal a, BigDecimal b) throws Exception {
    	
    	assumeThat(a, greaterThan(BigDecimal.ZERO));  
    	assumeThat(b, greaterThan(BigDecimal.ZERO));
    	
    	BigDecimal sqrtDivTogether = calc.parse(String.format("sqrt(%s/%s)", a, b)).evaluate();
    	BigDecimal sqrtDivSeparate = calc.parse(String.format("sqrt(%s)/sqrt(%s)", a, b)).evaluate();
    	
    	System.out.println("sqrt(" + a + " / " + b + "): " + sqrtDivTogether + "\n");
    	System.out.println("sqrt(" + a + ")" + " / " + "sqrt(" + b + "): " + sqrtDivSeparate + "\n");
    	
    	assertTrue((sqrtDivTogether.subtract(sqrtDivSeparate).abs()).compareTo(BigDecimal.ZERO) == 0);
    	
    }
    @Property (trials = 5)
    public void sqrtPowerSeparate(BigDecimal a) throws Exception {
    	
    	assumeThat(a, greaterThan(BigDecimal.ZERO));  
    	
    	BigDecimal sqrtSeparate = calc.parse(String.format("sqrt(%s)^2", a)).evaluate();
    	BigDecimal x = calc.parse(String.format("%s", a)).evaluate();
    	
    	System.out.println("sqrt(" + a + ")" + " * " + "sqrt(" + a + "): " + sqrtSeparate + "\n");
    	
    	assertTrue((sqrtSeparate.subtract(x).abs()).compareTo(BigDecimal.ZERO) == 0);
    	
    }
    
    @Property (trials = 5)
    public void nthPowerSeparate(BigDecimal a, @InRange(minInt = 1) BigInteger n) throws Exception {
    	
    	assumeThat(n, greaterThan(BigInteger.ZERO));
    	assumeThat(a, greaterThan(BigDecimal.ZERO));  
    	
    	BigDecimal nthRootSeparate = calc.parse(String.format("(sqrt(%s)^%s", a, n)).evaluate();
    	BigDecimal x = calc.parse(String.format("%s", a)).evaluate();
    	
    	System.out.println("sqrt(" + a + ")" + " * " + "sqrt(" + a + "): " + nthRootSeparate + "\n");
    	
    	assertTrue((nthRootSeparate.subtract(x).abs()).compareTo(BigDecimal.ZERO) == 0);
    	
    }
    
    @Property (trials = 5)
    public void nthRootDivision(BigDecimal a, BigDecimal b, @InRange(minInt = 1) BigInteger n) throws Exception {
    	
    	assumeThat(n, greaterThan(BigInteger.ZERO));
    	
    	if( n / 2 == 0) 
    	{
    		//n is even
    		assumeThat(a, greaterThan(BigDecimal.ZERO));
    		assumeThat(b, greaterThan(BigDecimal.ZERO));
    	}
    	
    	BigDecimal nthRootDivTogether = calc.parse(String.format("root(%s/%s, %s)", a, b, n)).evaluate();
    	BigDecimal nthRootDivSeparate = calc.parse(String.format("root(%s,%s)/root(%s,%s)", a, n, b, n)).evaluate();
    	
    	System.out.println("root(" + a + " / " + b + "," + n + "): " + nthRootDivTogether + "\n");
    	System.out.println("root(" + a + "," + n + ")" + " / " + "root(" + b + "," + a + "): " + nthRootDivSeparate + "\n");
    	
    	assertTrue((nthRootDivTogether.subtract(nthRootDivSeparate).abs()).compareTo(BigDecimal.ZERO) == 0);
    	
    }
    
    
    @Property (trials = 5)
    public void nthRootProduct(BigDecimal a, BigDecimal b, @InRange(minInt = 1) BigInteger n) throws Exception {
    	
    	assumeThat(n, greaterThan(BigInteger.ZERO));
    	
    	if( n / 2 == 0) 
    	{
    		//n is even
    		assumeThat(a, greaterThan(BigDecimal.ZERO));
    		assumeThat(b, greaterThan(BigDecimal.ZERO));
    	}
    	
    	BigDecimal nthRootMultiTogether = calc.parse(String.format("root(%s*%s, %s)", a, b, n)).evaluate();
    	BigDecimal nthRootMultiSeparate = calc.parse(String.format("root(%s,%s)*root(%s,%s)", a, n, b, n)).evaluate();
    	
    	System.out.println("root(" + a + " * " + b + "," + n + "): " + nthRootMultiTogether + "\n");
    	System.out.println("root(" + a + "," + n + ")" + " * " + "root(" + b + "," + a + "): " + nthRootMultiSeparate + "\n");
    	
    	assertTrue((nthRootMultiTogether.subtract(nthRootMultiSeparate).abs()).compareTo(BigDecimal.ZERO) == 0);
    	
    }
    
}
