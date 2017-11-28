package jUnitQuickCheckSet;

import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

import eu.gillissen.commandline.calculator.Calc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;


import static org.hamcrest.Matchers.*;



@RunWith(JUnitQuickcheck.class)
public class CalculatorProperties {
	
	Calc calc;
	
	@Before
	public void newCalc(){
		calc = new Calc();
	}
	
    
	@Rule
    public Timeout globalTimeout = Timeout.seconds(20);
    @Property (trials = 1000)public void RoundHasFractionalValueZero(BigDecimal numArg)
            throws Exception {
        	
        	
            BigDecimal round = calc.parse(String.format("round(%s)", numArg.toString())).evaluate();
            System.out.println("round ("+numArg.toString()+") has remainder:  "+round.remainder(BigDecimal.ONE)+"\n" );
            assertTrue((round.remainder(BigDecimal.ONE)).compareTo(BigDecimal.ZERO) == 0); 
       		
        }
    
    @Property (trials = 1000)public void FloorHasFractionalValueZero(BigDecimal numArg)
            throws Exception {
        	
        	//assumeThat(numArg,  );
            
            System.out.println(numArg);
            
            BigDecimal floor = calc.parse(String.format("floor(%s)", numArg.toString())).evaluate();
            //naturalLog = naturalLog.pow(-1);
            System.out.println("floor ("+numArg.toString()+") has remainder: "+floor.remainder(BigDecimal.ONE) +"\n" );
            //BigDecimal expected = new BigDecimal(Math.log10(numArg));
            assertTrue((floor.remainder(BigDecimal.ONE)).compareTo(BigDecimal.ZERO) == 0); 
       		//assertEquals(re	sult, new BigDecimal(Math.log10(numArg)));
            
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
    
    
    @Property (trials = 1000)public void LogOfAEqualsToTenToThePowerOfA(BigDecimal a)
            throws Exception {
        	
        	//assumeThat(numArg,  );
            
            
            
            BigDecimal logATenA = calc.parse(String.format("log(10^%s)"	, a.toString())).evaluate();
            //BigDecimal SameBase = calc.parse(String.format("(%f^%f)", base, (firstExp+secondExp))).evaluate();
            //naturalLog = naturalLog.pow(-1);
            //System.out.println("logATenA ("+logATenA+")*("+base+"^"+secondExp+"): "+sumMultSameBase + "\nExpected: "+ Math.pow(base, (firstExp+secondExp)) );
            System.out.println("log(10^"+a.toString()+")= " +logATenA+ " should be equals to: "+a.toString() );
            //BigDecimal expected = new BigDecimal(Math.log10(numArg));
            assertTrue(logATenA.compareTo(a) == 0);
            
       		//assertEquals(re	sult, new BigDecimal(Math.log10(numArg)));
            	
        }
    
    @Property (trials = 1000)public void sumOfExponentsWithSameBase(BigDecimal base, BigDecimal firstExp, BigDecimal secondExp)
            throws Exception {
        	
        	//assumeThat(numArg,  );
            
            
            
            BigDecimal sumMultSameBase = calc.parse(String.format("(%s^%s)*(%s^%s)", base.toString(), firstExp.toString(), base.toString(), secondExp.toString())).evaluate();
            BigDecimal SameBase = calc.parse(String.format("(%s^%s)", base.toString(), (firstExp.add(secondExp)).toString() )).evaluate();
            //naturalLog = naturalLog.pow(-1);
            System.out.println("("+base.toString()+"^"+firstExp.toString()+")*("+base.toString()+"^"+secondExp.toString()+"): "+sumMultSameBase+ "should be equals to ("+base.toString()+"^"+ (firstExp.add(secondExp)).toString() );
            //BigDecimal expected = new BigDecimal(Math.log10(numArg));
            assertTrue((sumMultSameBase.subtract(SameBase).abs()).compareTo(BigDecimal.ZERO) == 0);
            
       		//assertEquals(re	sult, new BigDecimal(Math.log10(numArg)));
            	
        }
    
    @Property (trials = 1000)public void comutativePropertyOfAddition(BigDecimal a, BigDecimal b)
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
    
    @Property (trials = 1000)public void comutativePropertyOfMultiplication(BigDecimal a, BigDecimal b)
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
            //naturalLog = naturalLog.pow(-1);
            //BigDecimal expected = new BigDecimal(Math.log10(numArg));
            assertTrue(bPlusCPlusA.compareTo(aPlusBPlusC) == 0);
            
       		//assertEquals(re	sult, new BigDecimal(Math.log10(numArg)));
            	
        }
    
    @Property (trials = 1000)public void associationMult(BigDecimal a, BigDecimal b, BigDecimal c)
            throws Exception {
        	
        	//assumeThat(numArg,  );
            
            
            
            BigDecimal bTimesCTimesA = calc.parse(String.format("%s * (%s * %s)", a.toString(), b.toString(), c.toString())).evaluate();
            BigDecimal aTimesBTimesC = calc.parse(String.format("(%s * %s) * %s", a.toString(), b.toString(), c.toString())).evaluate();
            System.out.println("("+a.toString()+" * ("+b.toString()+" * "+c.toString() +"): "+bTimesCTimesA + "( ("+a.toString()+" * "+b.toString()+") * "+c.toString() +"): "+aTimesBTimesC);
            //naturalLog = naturalLog.pow(-1);
            //BigDecimal expected = new BigDecimal(Math.log10(numArg));
            assertTrue(bTimesCTimesA.compareTo(aTimesBTimesC) == 0);
            
       		//assertEquals(re	sult, new BigDecimal(Math.log10(numArg)));
            	
        }
    
    @Property (trials = 1000)public void MutiplicativeIdentityProperty(BigDecimal a)
	  throws Exception {
		
		//assumeThat(numArg,  );
	  
	  
	  
	  BigDecimal aTimesOne = calc.parse(String.format("%s * 1", a.toString())).evaluate();
	  //System.out.println(a);
	  //System.out.println("      "+calc.parse(String.format("%d + 0.0", a)).evaluate());
	  BigDecimal oneTimesA = calc.parse(String.format("1.0 * %s", a.toString())).evaluate();
	  System.out.println("("+a.toString()+" * 1): "+aTimesOne +" should be equals to " + a.toString());
	  //naturalLog = naturalLog.pow(-1);
	  //BigDecimal expected = new BigDecimal(Math.log10(numArg));
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
            //naturalLog = naturalLog.pow(-1);
            //BigDecimal expected = new BigDecimal(Math.log10(numArg));
            assertTrue( (aPlusZero.compareTo(zeroPlusA) == 0)  && (zeroPlusA.compareTo(a) == 0) );
            
       		//assertEquals(re	sult, new BigDecimal(Math.log10(numArg)));
            	
        }
    
    @Property (trials = 1000)public void additiveInverse(BigDecimal a)
            throws Exception {
        	
        	//assumeThat(numArg,  );
            
            
            
            BigDecimal aPlusMinusA = calc.parse(String.format("%s + (-%s)", a.toString(), a.toString())).evaluate();
            System.out.println("	additiveInverse ("+a.toString()+" -"+a.toString()+"):" +aPlusMinusA + "\nExpected: 0" );
            //naturalLog = naturalLog.pow(-1);
            //BigDecimal expected = new BigDecimal(Math.log10(numArg));
            assertTrue(aPlusMinusA.compareTo(BigDecimal.ZERO) == 0);
            
       		//assertEquals(re	sult, new BigDecimal(Math.log10(numArg)));
            	
        }
    
	  @Property (trials = 1000)public void multiplicativeInverseProperty(BigDecimal a)
	  throws Exception {
		
		//assumeThat(numArg,  );
	  
	  
	  
	  BigDecimal aTimesOneDividedA = calc.parse(String.format("%s * (%s/%s)", a.toString(), BigDecimal.ONE.toString(), a.toString())).evaluate();
	  System.out.println("("+a.toString()+" * (" +BigDecimal.ONE.toString()+" / "+a.toString()+"):" +aTimesOneDividedA + " should be equals to 1" );
	  //naturalLog = naturalLog.pow(-1);
	  //BigDecimal expected = new BigDecimal(Math.log10(numArg));
	  //assertEquals(aTimesOneA, expected); 
	  assertTrue(aTimesOneDividedA.compareTo(BigDecimal.ONE) == 0);
	  
			//assertEquals(re	sult, new BigDecimal(Math.log10(numArg)));
	  	
	}
	  
	  @Property (trials = 1000)public void multiplicationPropertyOfZero(BigDecimal a)
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
	        	
	        	//assumeThat(numArg,  );
	            
	            
	            
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
	  
}
