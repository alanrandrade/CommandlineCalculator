package jUnitQuickCheckSet;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;
import static org.junit.Assume.*;


import java.math.BigDecimal;
import java.math.BigInteger;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeThat;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.rules.Timeout;
import org.junit.runner.Description;
import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

import eu.gillissen.commandline.calculator.Calc;



@RunWith(JUnitQuickcheck.class)
public class CalculatorProperties {
	
	static Calc calc;
	
	@BeforeClass
	public static void newCalc(){
		calc = new Calc();
	}
	

	public void separator(){
		System.out.println("===================================");
		
	}
	
	@Rule
    public Timeout globalTimeout = Timeout.seconds(20);
	
	@Rule
    public TestWatcher testWatcher = new TestWatcher() {
        @Override
        protected void starting(Description description) {
            String methodName = description.getMethodName();
            String className = description.getClassName();
            System.out.println("Property under test: "  + methodName + "\n");
        }
        public void succeeded(Description description) {
        	System.out.println("[PASS]");
        	separator();
        	}
        public void failed(Throwable e, Description description) {
        	if(e.getMessage() != null)
        	 System.out.println("[FAIL] reason: "+e.getMessage());
        	else
        		System.out.println("[FAIL]: Property was broke.");
        	 separator();
        }

    };
    @Property (trials = 1000)public void RoundHasFractionalValueZero(BigDecimal numArg)
            throws Exception {
            
            
            BigDecimal round = calc.parse(String.format("round(%s)", numArg.toString())).evaluate();
            System.out.println("round ("+numArg.toString()+") has remainder:  "+round.remainder(BigDecimal.ONE)+"\n" );
            assertTrue((round.remainder(BigDecimal.ONE)).compareTo(BigDecimal.ZERO) == 0); 
            
       }
    
    @Property (trials = 1000)public void CeilHasFractionalValueZero(BigDecimal numArg)
            throws Exception {
        	
        	//assumeThat(numArg,  );
            
            System.out.println(numArg);
            
            BigDecimal ceil = calc.parse(String.format("ceil(%s)", numArg.toString())).evaluate();
            //naturalLog = naturalLog.pow(-1);
            System.out.println("ceil ("+numArg.toString()+") has remainder "+ceil.remainder(BigDecimal.ONE) );
            //BigDecimal expected = new BigDecimal(Math.log10(numArg));
            assertTrue((ceil.remainder(BigDecimal.ONE)).compareTo(BigDecimal.ZERO) == 0); 
       		//assertEquals(re	sult, new BigDecimal(Math.log10(numArg)));

        }
    
    @Property (trials = 1000)public void floorHasFractionalValueZero(BigDecimal numArg)
            throws Exception {
            
            
            BigDecimal round = calc.parse(String.format("floor(%s)", numArg.toString())).evaluate();
            System.out.println("floor ("+numArg.toString()+") has remainder:  "+round.remainder(BigDecimal.ONE)+"\n" );
            assertTrue((round.remainder(BigDecimal.ONE)).compareTo(BigDecimal.ZERO) == 0); 
            
       }

    
    //Change BigDecimalToInteger
    @Property (trials = 1000)public void LogOfTenToThePowerOfAEqualsToA(@InRange (minInt = -10) Integer a)

            throws Exception {
        	
        	//assumeThat(numArg,  );
    		assumeThat(a, is(either(greaterThan(0)).or(lessThan(0)) ) );
    		System.out.println(a);
           
            BigDecimal logATenA = calc.parse(String.format("log(10^%d)"	, a)).evaluate();
            
            System.out.println("log(10^"+a+")= " +logATenA+ " 	should be equals to: "+a );
            //BigDecimal expected = new BigDecimal(Math.log10(numArg));
            assertTrue(logATenA.compareTo(new BigDecimal(a)) == 0);
            
       		//assertEquals(re	sult, new BigDecimal(Math.log10(numArg)));
            	
        }
    

    @Property (trials = 1000)public void commutativePropertyOfAddition(BigDecimal a, BigDecimal b)
            throws Exception {

        	
        	//assumeThat(numArg,  );
            
            BigDecimal aPlusB = calc.parse(String.format("%s + %s", a.toString(), b.toString())).evaluate();
            BigDecimal bPlusA = calc.parse(String.format("%s + %s", b.toString(), a.toString())).evaluate();
            System.out.println("("+a.toString()+"+"+b.toString()+"): "+aPlusB + "should be equals to: ("+b.toString()+"+"+a.toString()+")"+ bPlusA );
            //naturalLog = naturalLog.pow(-1);
            //BigDecimal expected = new BigDecimal(Math.log10(numArg));
            assertTrue(aPlusB.compareTo(bPlusA) == 0);

            
       		//assertEquals(re	sult, new BigDecimal(Math.log10(numArg)));
            	
        }
    

    @Property (trials = 1000)public void commutativePropertyOfMultiplication(BigDecimal a, BigDecimal b)

            throws Exception {
        	
        	//assumeThat(numArg,  );
            BigDecimal aTimesB = calc.parse(String.format("%s * %s", a.toString(), b.toString())).evaluate();
            BigDecimal bTimesA = calc.parse(String.format("%s * %s", b.toString(), a.toString())).evaluate();
            System.out.println("("+a+"*"+b+"): "+aTimesB + "should be equal to ("+b+"*"+a+"): "+ bTimesA );
            //naturalLog = naturalLog.pow(-1);
            //BigDecimal expected = new BigDecimal(Math.log10(numArg));
            assertTrue(aTimesB.compareTo(bTimesA) == 0);
            
       		//assertEquals(re	sult, new BigDecimal(Math.log10(numArg)));
            	
        }
    
    @Property (trials = 1000)public void associativePropertyOfAddition(BigDecimal a, BigDecimal b, BigDecimal c)

            throws Exception {
        	
        	//assumeThat(numArg,  );

            BigDecimal bPlusCPlusA = calc.parse(String.format("%s + (%s + %s)", a.toString(), b.toString(), c.toString())).evaluate();
            BigDecimal aPlusBPlusC = calc.parse(String.format("(%s + %s) + %s", a.toString(), b.toString(), c.toString())).evaluate();
            System.out.println("("+a.toString()+"+ ("+b.toString()+" + "+ c.toString()+") ): "+ bPlusCPlusA + " should be equals to ( ("+a.toString()+"+ "+b.toString()+") + "+ c.toString()+") ): "+ aPlusBPlusC);
            assertTrue(bPlusCPlusA.compareTo(aPlusBPlusC) == 0);

            
       		//assertEquals(re	sult, new BigDecimal(Math.log10(numArg)));
            	
        }

    @Property (trials = 1000)public void associativePropertyOfMultiplication(BigDecimal a, BigDecimal b, BigDecimal c)
            throws Exception {
        	
        	//assumeThat(numArg,  );

            BigDecimal bTimesCTimesA = calc.parse(String.format("%s * (%s * %s)", a.toString(), b.toString(), c.toString())).evaluate();
            BigDecimal aTimesBTimesC = calc.parse(String.format("(%s * %s) * %s", a.toString(), b.toString(), c.toString())).evaluate();
            System.out.println("("+a.toString()+" * ("+b.toString()+" * "+c.toString() +"): "+bTimesCTimesA + "( ("+a.toString()+" * "+b.toString()+") * "+c.toString() +"): "+aTimesBTimesC);
            
            assertTrue(bTimesCTimesA.compareTo(aTimesBTimesC) == 0);

       		//assertEquals(re	sult, new BigDecimal(Math.log10(numArg)));
            	
        }
    
    @Property (trials = 1000)public void multiplicativeIdentityProperty(BigDecimal a)
	  throws Exception {
		
		//assumeThat(numArg,  );
	  
	  
	  
	  BigDecimal aTimesOne = calc.parse(String.format("%s * 1.0", a.toString())).evaluate();
	  //System.out.println(a);
	  //System.out.println("      "+calc.parse(String.format("%d + 0.0", a)).evaluate());
	  BigDecimal oneTimesA = calc.parse(String.format("1.0 * %s", a.toString())).evaluate();
	  System.out.println("("+a.toString()+" * 1): "+aTimesOne +" should be equals to " + a.toString());
	  
	  assertTrue( ((aTimesOne.compareTo(oneTimesA)) == 0) && (oneTimesA.compareTo(a) == 0) ); //&& (oneTimesA.compareTo(new BigDecimal (a)) == 0) );
	  
			//assertEquals(re	sult, new BigDecimal(Math.log10(numArg)));
	  	
	}
    
    
    
    @Property (trials = 1000)public void AdditiveIdentityProperty(BigDecimal a)
            throws Exception {
        	
        	//assumeThat(numArg,  );
            BigDecimal aPlusZero = calc.parse(String.format("%s + 0", a.toString())).evaluate();
            //System.out.println(aPlusZero);
            //System.out.println("      "+calc.parse(String.format("%d + 0.0", a)).evaluate());
            BigDecimal zeroPlusA = calc.parse(String.format("0 + %s", a.toString())).evaluate();
            System.out.println("("+a.toString()+" + 0): "+aPlusZero + " should be equals to "+ "(0 +"+a.toString()+"):" +zeroPlusA );
            
            assertTrue( (aPlusZero.compareTo(zeroPlusA) == 0)  && (zeroPlusA.compareTo(a) == 0) );
            
       		//assertEquals(re	sult, new BigDecimal(Math.log10(numArg)));
            	
        }
    

    @Property (trials = 1000)public void additiveInverse(BigDecimal a)
            throws Exception {
        	
        	//assumeThat(numArg,  );
    		BigDecimal aPlusMinusA;
            if(a.compareTo(BigDecimal.ZERO) == -1)
            	aPlusMinusA = calc.parse(String.format("%s + (%s)", a.toString(), a.toString().replace('-', ' ') )).evaluate();
            else{
            	if(a.compareTo(BigDecimal.ZERO) == +1)
            		aPlusMinusA = calc.parse(String.format("%s + (%s)", a.toString(), "-"+a.toString() )).evaluate();
            	else
            		aPlusMinusA = calc.parse(String.format("%s + (%s)", a.toString(), a.toString() )).evaluate();
            }
            if(a.compareTo(BigDecimal.ZERO) == -1)
            	System.out.println(" ("+a.toString()+" -"+a.toString()+"):" +aPlusMinusA + "\nExpected: 0" );
            else
            	System.out.println(" ("+a.toString()+" "+a.toString()+"):" +aPlusMinusA + "\nExpected: 0" );
            //naturalLog = naturalLog.pow(-1);
            //BigDecimal expected = new BigDecimal(Math.log10(numArg));
            assertTrue(aPlusMinusA.compareTo(BigDecimal.ZERO) == 0);
            
       		//assertEquals(re	sult, new BigDecimal(Math.log10(numArg)));
            	
        }
    
	  @Property (trials = 1000)public void multiplicativeInverseProperty(BigDecimal a)
	  throws Exception {
		
		//assumeThat(numArg,  );
		  assumeThat(a, is(either(greaterThan(BigDecimal.ZERO)).or(lessThan(BigDecimal.ZERO)) ) );

	  
	  
	  
	  BigDecimal aTimesOneDividedA = calc.parse(String.format("%s * (%s/%s)", a.toString(), BigDecimal.ONE.toString(), a.toString())).evaluate();
	  System.out.println("("+a.toString()+" * (" +BigDecimal.ONE.toString()+" / "+a.toString()+"):" +aTimesOneDividedA + " should be equals to "+ BigDecimal.ONE.toString() );

	  //naturalLog = naturalLog.pow(-1);
	  //BigDecimal expected = new BigDecimal(Math.log10(numArg));
	  //assertEquals(aTimesOneA, expected); 
	  assertTrue(aTimesOneDividedA.compareTo(BigDecimal.ONE) == 0);
	  
			//assertEquals(re	sult, new BigDecimal(Math.log10(numArg)));
	  	
	}
	  
	  @Property (trials = 1000)public void multiplicativePropertyOfZero(BigDecimal a)
	            throws Exception {
	        	
	        	//assumeThat(numArg,  );
	            
	            
	            
	            BigDecimal aTimesZero = calc.parse(String.format("%s * %s)", a.toString(), BigDecimal.ZERO.toString())). evaluate();
	            BigDecimal zeroTimesA = calc.parse(String.format("%s * %s)", BigDecimal.ZERO.toString(), a.toString())). evaluate();
	            System.out.println(aTimesZero);
		  		//System.out.println("	aTimesZero ("+a+"*0):" +aTimesZero + "\nExpected: 0" );
	            //naturalLog = naturalLog.pow(-1);
	            //BigDecimal expected = new BigDecimal(Math.log10(numArg));
	            assertTrue( (aTimesZero.compareTo(zeroTimesA) == 0) && (zeroTimesA.compareTo(BigDecimal.ZERO) == 0));
	            
	       		//assertEquals(re	sult, new BigDecimal(Math.log10(numArg)));
	            	
	        }
	  
	  @Property (trials = 1000)public void quotientProperty(BigDecimal a, BigDecimal b)
	            throws Exception {
		        
		        assumeThat(a, is(either(greaterThan(BigDecimal.ZERO)).or(lessThan(BigDecimal.ZERO)) ) );
	        	assumeThat(b, is(either(greaterThan(BigDecimal.ZERO)).or(lessThan(BigDecimal.ZERO)) ) );

	            BigDecimal aTimesOneDividedByB = calc.parse(String.format("%s * (%s / %s) )", a.toString(), BigDecimal.ONE.toString(), b.toString() )). evaluate();
	            BigDecimal aDividedByB = calc.parse(String.format("%s / %s)", a.toString(), b.toString())). evaluate();
	            //System.out.println(aTimesZero);
		  		//System.out.println("	aTimesZero ("+a+"*0):" +aTimesZero + "\nExpected: 0" );
	            //naturalLog = naturalLog.pow(-1);
	            //BigDecimal expected = new BigDecimal(Math.log10(numArg));
	            assertTrue( (aTimesOneDividedByB.compareTo(aDividedByB) == 0));
	            
	       		//assertEquals(re	sult, new BigDecimal(Math.log10(numArg)));
	            	
	        }
	  
	  @Property (trials = 1000)public void distributivePropertyofMultiplicationOverAddition(BigDecimal a, BigDecimal b, BigDecimal c)
	            throws Exception {
	        	
	        	//assumeThat(numArg,  );
	            
	            
	            
	            BigDecimal bPlusCTimesA = calc.parse(String.format("%s * (%s + %s) )", a.toString(), b.toString(), c.toString() )). evaluate();
	            BigDecimal aTimesBPlusATimesC = calc.parse(String.format("%s * %s + %s * %s)", a.toString(), b.toString(), a.toString(), c.toString())). evaluate();
	            //System.out.println(aTimesZero);
		  		//System.out.println("	aTimesZero ("+a+"*0):" +aTimesZero + "\nExpected: 0" );
	            //naturalLog = naturalLog.pow(-1);
	            //BigDecimal expected = new BigDecimal(Math.log10(numArg));
	            assertTrue( (bPlusCTimesA.compareTo(aTimesBPlusATimesC) == 0));
	            
	       		//assertEquals(re	sult, new BigDecimal(Math.log10(numArg)));
	            	
	        }
	  //Brigel's Properties

    @Property (trials = 1000)
    public void lnInverseExponent(BigInteger a) throws Exception {
    	    	
    	BigDecimal lnex = calc.parse(String.format("ln(e^%s)", a.toString())).evaluate();
    	BigDecimal x = calc.parse(String.format("%s", a.toString())).evaluate();
    	
    	System.out.println("ln(e^"+a.toString()+"): "+lnex + "\nExpected: "+ x.toString() + "\n" );
    	
    	
    	assertTrue((lnex.subtract(x).abs()).compareTo(BigDecimal.ZERO) == 0);
    	
    }
    
    @Property (trials = 1000)
    public void sqrtProduct(BigDecimal a, BigDecimal b) throws Exception {
	
    	assumeThat(a, is(either(equalTo(BigDecimal.ZERO)).or(greaterThan(BigDecimal.ZERO))));
    	assumeThat(b, is(either(equalTo(BigDecimal.ZERO)).or(greaterThan(BigDecimal.ZERO))));
    	
    	BigDecimal sqrtMultiTogether = calc.parse(String.format("sqrt(%s*%s)", a.toString(), b.toString())).evaluate();
    	BigDecimal sqrtMultiSeparate = calc.parse(String.format("sqrt(%s)*sqrt(%s)", a.toString(), b.toString())).evaluate();
    	
    	System.out.println("sqrt(" + a.toString() + " * " + b.toString() + "): " + sqrtMultiTogether + "\n");
    	System.out.println("sqrt(" + a.toString() + ")" + " * " + "sqrt(" + b.toString() + "): " + sqrtMultiSeparate + "\n");
    	
    	assertTrue((sqrtMultiTogether.subtract(sqrtMultiSeparate).abs()).compareTo(BigDecimal.ZERO) == 0);
    	
    }
    
    @Property (trials = 1000)
    public void sqrtDivision(BigDecimal a, BigDecimal b) throws Exception {
    	
    	assumeThat(a, is(either(equalTo(BigDecimal.ZERO)).or(greaterThan(BigDecimal.ZERO))));
    	assumeThat(b, greaterThan(BigDecimal.ZERO));
    	
    	BigDecimal sqrtDivTogether = calc.parse(String.format("sqrt(%s/%s)", a.toString(), b.toString())).evaluate();
    	BigDecimal sqrtDivSeparate = calc.parse(String.format("sqrt(%s)/sqrt(%s)", a.toString(), b.toString())).evaluate();
    	
    	System.out.println("sqrt(" + a.toString() + " / " + b.toString() + "): " + sqrtDivTogether + "\n");
    	System.out.println("sqrt(" + a.toString() + ")" + " / " + "sqrt(" + b.toString() + "): " + sqrtDivSeparate + "\n");
    	
    	assertTrue((sqrtDivTogether.subtract(sqrtDivSeparate).abs()).compareTo(BigDecimal.ZERO) == 0);
    	
    }
    @Property (trials = 1000)
    public void sqrtPowerSeparate(BigDecimal a) throws Exception {
    	
    	assumeThat(a, greaterThan(BigDecimal.ZERO));  
    	
    	BigDecimal sqrtSeparate = calc.parse(String.format("sqrt(%s)*sqrt(%s)", a.toString(), a.toString())).evaluate();
    	BigDecimal x = calc.parse(String.format("%s", a.toString())).evaluate();
    	
    	System.out.println("sqrt(" + a.toString() + ")" + " * " + "sqrt(" + a.toString() + "): " + sqrtSeparate + "\n");
    	
    	assertTrue((sqrtSeparate.subtract(x).abs()).compareTo(BigDecimal.ZERO) == 0);
    	
    }
    

    @Property (trials = 1000)
    public void nthPowerSeparate(BigDecimal a, @InRange(minInt = 1) BigInteger n) throws Exception {
    	
    	assumeThat(n, greaterThan(BigInteger.ZERO));
    	assumeThat(a, greaterThan(BigDecimal.ZERO));  

    	BigDecimal nthRootSeparate = calc.parse(String.format("root(%s, %s) ^ %s", a.toString(), n.toString(), n.toString())).evaluate();
    	//BigDecimal x = calc.parse(String.format("%s", a.toString())).evaluate();

    	
    	System.out.println("root(" + a.toString() + "," + n.toString() + ")" + "^" + n.toString() + " :" + nthRootSeparate + "\n");
    	System.out.println("expected: "+a.toString());
    	assertTrue((nthRootSeparate.subtract(a).abs()).compareTo(BigDecimal.ZERO) == 0);
    	
    }
    
    @Property (trials = 1000)
    public void nthRootDivision(BigDecimal a, BigDecimal b, @InRange(minInt = 1) BigInteger n) throws Exception {
    	
    	assumeThat(n, greaterThan(BigInteger.ZERO));
    	

    	if( n.intValue() % 2 == 0) 
    	{
    		//n is even
    		assumeThat(a, greaterThan(BigDecimal.ZERO));
    		assumeThat(b, greaterThan(BigDecimal.ZERO));
    	}
    	System.out.println(a + "b "+b+ " n" +n);
    	BigDecimal nthRootDivTogether = calc.parse(String.format("root(%s/%s, %s)", a.toString(), b.toString(), n.toString())).evaluate();
    	BigDecimal nthRootDivSeparate = calc.parse(String.format("root(%s,%s)/root(%s,%s)", a.toString(), n.toString(), b.toString(), n.toString())).evaluate();
    	
    	System.out.println("root(" + a.toString() + " / " + b.toString() + "," + n.toString() + "): " + nthRootDivTogether + "\n");
    	System.out.println("root(" + a.toString() + "," + n.toString() + ")" + " / " + "root(" + b.toString() + "," + n.toString() + "): " + nthRootDivSeparate + "\n");
    	
    	assertTrue((nthRootDivTogether.subtract(nthRootDivSeparate).abs()).compareTo(BigDecimal.ZERO) == 0);
    	
    }
    
    
    @Property (trials = 1000)
    public void nthRootProduct(BigDecimal a, BigDecimal b, @InRange(minInt = 1) BigInteger n) throws Exception {
    	
    	assumeThat(n, greaterThan(BigInteger.ZERO));
    	
    	if( n.divide(new BigInteger("2")).compareTo(BigInteger.ZERO) == 0) 

    	{
    		//n is even
    		assumeThat(a, greaterThan(BigDecimal.ZERO));
    		assumeThat(b, greaterThan(BigDecimal.ZERO));
    	}
    
    	
    	BigDecimal nthRootMultiTogether = calc.parse(String.format("root(%s*%s, %s)", a.toString(), b.toString(), n.toString())).evaluate();
    	BigDecimal nthRootMultiSeparate = calc.parse(String.format("root(%s,%s)*root(%s,%s)", a.toString(), n.toString(), b.toString(), n.toString())).evaluate();
    	
    	System.out.println("root(" + a.toString() + " * " + b.toString()+ "," + n.toString() + "): " + nthRootMultiTogether + "\n");
    	System.out.println("root(" + a.toString() + "," + n.toString() + ")" + " * " + "root(" + b.toString() + "," + a.toString() + "): " + nthRootMultiSeparate + "\n");
    	
    	assertTrue((nthRootMultiTogether.subtract(nthRootMultiSeparate).abs()).compareTo(BigDecimal.ZERO) == 0);
    	
    }
    
    @Property (trials = 1000)
    public void lnPower(BigDecimal a, BigInteger n) throws Exception{
 	   
 	   assumeThat(a, greaterThan(BigDecimal.ZERO));
 	   
 	   BigDecimal lnAPowerN = calc.parse(String.format("ln(%s^%s)", a.toString(), n.toString())).evaluate();
 	   BigDecimal lnAMultiplyN = calc.parse(String.format("%s * ln(%s)", n.toString(), a.toString())).evaluate();
 	   
 	   System.out.println("ln(" + a.toString() + " ^ " + n.toString() + "): " + lnAPowerN + "\n");
       System.out.println(n.toString() + " * " + "ln(" + a.toString() + "): " + lnAMultiplyN + "\n");
    	   
    	   assertTrue((lnAPowerN.subtract(lnAMultiplyN).abs()).compareTo(BigDecimal.ZERO) == 0);
 	   
    }
    
   @Property (trials = 1000)
   public void lnProductAddition(BigDecimal a, BigDecimal b) throws Exception{
	   
	   assumeThat(a, greaterThan(BigDecimal.ZERO));
	   assumeThat(b, greaterThan(BigDecimal.ZERO));
	   
	   BigDecimal lnProduct = calc.parse(String.format("ln(%s*%s)", a.toString(), b.toString())).evaluate();
	   BigDecimal lnAddition = calc.parse(String.format("ln(%s)+ ln(%s)", a.toString(), b.toString())).evaluate();
	   
	   System.out.println("ln(" + a.toString() + " * " + b.toString() + "): " + lnProduct + "\n");
   	   System.out.println("ln(" + a.toString() + "+" + "ln(" + b.toString() + "): " + lnAddition + "\n");
   	   
   	   assertTrue((lnProduct.subtract(lnAddition).abs()).compareTo(BigDecimal.ZERO) == 0);
	   
   }
   
   @Property (trials = 1000)
   public void lnDivSubtract(BigDecimal a, BigDecimal b) throws Exception{
	   
	   assumeThat(a, greaterThan(BigDecimal.ZERO));
	   assumeThat(b, greaterThan(BigDecimal.ZERO));
	   
	   BigDecimal lnDivision = calc.parse(String.format("ln(%s/%s)", a.toString(), b.toString())).evaluate();
	   BigDecimal lnSubstraction = calc.parse(String.format("ln(%s) - ln(%s)", a.toString(), b.toString())).evaluate();
	   
	   System.out.println("ln(" + a.toString() + " / " + b.toString() + "): " + lnDivision + "\n");
   	   System.out.println("ln(" + a.toString() + " - " + "ln(" + b.toString() + "): " + lnSubstraction + "\n");
   	   
   	   assertTrue((lnDivision.subtract(lnSubstraction).abs()).compareTo(BigDecimal.ZERO) == 0);
	   
   }
   
   @Property (trials = 1000)
   public void logPower(BigDecimal a, BigInteger n) throws Exception{
	   
	   assumeThat(a, greaterThan(BigDecimal.ZERO));
	   
	   BigDecimal logAPowerN = calc.parse(String.format("log(%s^%s)", a.toString(), n.toString())).evaluate();
	   BigDecimal logAMultiplyN = calc.parse(String.format("%s * log(%s)", n.toString(), a.toString())).evaluate();
	   
	   System.out.println("log(" + a.toString() + " ^ " + n.toString() + "): " + logAPowerN + "\n");
       System.out.println(n.toString() + " * " +"log(" + a.toString() + "): " + logAMultiplyN + "\n");
   	   
   	   assertTrue((logAPowerN.subtract(logAMultiplyN).abs()).compareTo(BigDecimal.ZERO) == 0);
	   
   }
   
   @Property (trials = 1000)
   public void logProductAddition(BigDecimal a, BigDecimal b) throws Exception{
	   
	   assumeThat(a, greaterThan(BigDecimal.ZERO));
	   assumeThat(b, greaterThan(BigDecimal.ZERO));
	   
	   BigDecimal logProduct = calc.parse(String.format("log(%s*%s)", a.toString(), b.toString())).evaluate();
	   BigDecimal logAddition = calc.parse(String.format("log(%s)+ log(%s)", a.toString(), b.toString())).evaluate();
	   
	   System.out.println("log(" + a.toString() + " * " + b.toString() + "): " + logProduct + "\n");
   	   System.out.println("log(" + a.toString() + "+" + "log(" + b.toString() + "): " + logAddition + "\n");
   	   
   	   assertTrue((logProduct.subtract(logAddition).abs()).compareTo(BigDecimal.ZERO) == 0);
	   
   }
   
   @Property (trials = 1000)
   public void logDivSubtract(BigDecimal a, BigDecimal b) throws Exception{
	   
	   assumeThat(a, greaterThan(BigDecimal.ZERO));
	   assumeThat(b, greaterThan(BigDecimal.ZERO));
	   
	   BigDecimal logDivision = calc.parse(String.format("log(%s/%s)", a.toString(), b.toString())).evaluate();
	   BigDecimal logSubstraction = calc.parse(String.format("log(%s) - log(%s)", a.toString(), b.toString())).evaluate();
	   
	   System.out.println("log(" + a.toString() + " / " + b.toString() + "): " + logDivision + "\n");
   	   System.out.println("log(" + a.toString() + " - " + "log(" + b.toString() + "): " + logSubstraction + "\n");
   	   
   	   assertTrue((logDivision.subtract(logSubstraction).abs()).compareTo(BigDecimal.ZERO) == 0);
	   
   }
}
