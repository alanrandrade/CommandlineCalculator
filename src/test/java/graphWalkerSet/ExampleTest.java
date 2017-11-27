package graphWalkerSet;
import org.graphwalker.core.condition.EdgeCoverage;
import org.graphwalker.core.condition.RequirementCoverage;
import org.graphwalker.core.condition.TimeDuration;
import org.graphwalker.core.condition.VertexCoverage;
import org.graphwalker.core.generator.RandomPath;
import org.graphwalker.core.machine.*;
import org.graphwalker.core.model.*;
import org.junit.Assert;
import org.junit.Test;



import eu.gillissen.commandline.calculator.Calc;
import eu.gillissen.commandline.calculator.exception.EvaluationException;
import eu.gillissen.commandline.calculator.exception.MemoryException;
import eu.gillissen.commandline.calculator.exception.ParseException;

import static org.hamcrest.core.Is.is;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ExampleTest extends ExecutionContext {
	
	Calc c;
	ArrayList<Double> array;
		
	public ExampleTest(){
		c = new Calc();
		array = new ArrayList<Double>();
		array.add(10.0);
	}

    public void v_Start() {
        System.out.println("v_Start");
    }
    
    
    public void e_Floor() throws EvaluationException, ParseException, MemoryException {
    	System.out.println("Executing edge Floor");
        Assert.assertEquals(c.parse(String.format("floor(%f)", array.get(0))).evaluate().doubleValue(), Math.floor(array.get(0)), 0);
    }
    
    public void e_Ceil() throws EvaluationException, ParseException, MemoryException {
    	System.out.println("Executing edge Ceil");
        Assert.assertEquals(c.parse(String.format("ceil(%f)", array.get(0))).evaluate().doubleValue(), Math.ceil(array.get(0)),0);
    }
    
    public void e_Round() throws EvaluationException, ParseException, MemoryException {
    	System.out.println("Executing edge Round");
        Assert.assertEquals(c.parse(String.format("round(%f)", array.get(0))).evaluate().doubleValue(), Math.round(array.get(0)),0);
    }
    
    public void e_Log() throws EvaluationException, ParseException, MemoryException {
    	System.out.println("Executing edge Log");
        Assert.assertEquals(c.parse(String.format("log(%f)", array.get(0))).evaluate().doubleValue(), Math.log10(array.get(0)),0);
    }
    
    public void e_Ln() throws EvaluationException, ParseException, MemoryException {
    	System.out.println("Executing edge Ln");
        Assert.assertEquals(c.parse(String.format("ln(%f)", array.get(0))).evaluate().doubleValue(), Math.log(array.get(0)),0.001);
    }
    
    public void e_Sqrt() throws EvaluationException, ParseException, MemoryException {
    	System.out.println("Executing edge Sqrt");
        Assert.assertEquals(c.parse(String.format("sqrt(%f)", array.get(0))).evaluate().doubleValue(), Math.sqrt(array.get(0)),0.001);
    }

    public void vertex2() {
        System.out.println("Printingvertex2");
    }

    public void vertex3() {
        throw new RuntimeException();
    }

    public boolean isFalse() {
        return false;
    }

    public boolean isTrue() {
        return true;
    }

    public void myAction() {
        System.out.println("Action called");
    }

    @Test
    public void success() {
        
        Vertex v_Start = new Vertex().setName("v_Start");
        Vertex v_ResultFloor = new Vertex().setName("v_ResultFloor");
        Vertex v_ResultCeil = new Vertex().setName("v_ResultCeil");
//    	Vertex start = new Vertex().setName("v_Start");
        Model model = new Model();
        
//        Edge e_root = new Edge()
//                .setName("e_Root")
//                .setSourceVertex(v_Start)
//                .setTargetVertex(v_Start);
        Edge e_floor = new Edge()
                .setName("e_Floor")
                .setSourceVertex(v_Start)
                .setTargetVertex(v_Start);
        Edge e_ceil = new Edge()
                .setName("e_Ceil")
                .setSourceVertex(v_Start)
                .setTargetVertex(v_Start);
        
        Edge e_round = new Edge()
                .setName("e_Round")
                .setSourceVertex(v_Start)
                .setTargetVertex(v_Start);
        Edge e_log = new Edge()
                .setName("e_Log")
                .setSourceVertex(v_Start)
                .setTargetVertex(v_Start);
        Edge e_ln = new Edge()
                .setName("e_Ln")
                .setSourceVertex(v_Start)
                .setTargetVertex(v_Start);
        Edge e_sqrt = new Edge()
                .setName("e_Sqrt")
                .setSourceVertex(v_Start)
                .setTargetVertex(v_Start);
          model.addVertex(v_Start);
//          model.addVertex(v_ResultCeil);
//          model.addVertex(v_ResultFloor);
          model.addEdge(e_floor);
          model.addEdge(e_ceil);
          model.addEdge(e_round);
          model.addEdge(e_log);
          model.addEdge(e_ln);
          model.addEdge(e_sqrt);

        this.setModel(model.build());
        //this.setPathGenerator(new RandomPath(new TimeDuration(10, TimeUnit.SECONDS )));
        this.setPathGenerator(new RandomPath(new EdgeCoverage(100) ));
        setNextElement(v_Start);
        Machine machine = new SimpleMachine(this);
        while (machine.hasNextStep()) {
            machine.getNextStep();
        }
    }

//    @Test(expected = MachineException.class)
//    public void failure() {
//        Vertex start = new Vertex();
//        Model model = new Model().addEdge(new Edge()
//                .setName("edge1")
//                .setGuard(new Guard("isFalse()"))
//                .setSourceVertex(start
//                        .setName("vertex1"))
//                .setTargetVertex(new Vertex()
//                        .setName("vertex2")));
//        this.setModel(model.build());
//        this.setPathGenerator(new RandomPath(new VertexCoverage(100)));
//        setNextElement(start);
//        Machine machine = new SimpleMachine(this);
//        while (machine.hasNextStep()) {
//            machine.getNextStep();
//        }
//    }

//    @Test
//    public void exception() {
//        Vertex start = new Vertex();
//        Model model = new Model().addEdge(new Edge()
//                .setName("edge1")
//                .setGuard(new Guard("isTrue()"))
//                .setSourceVertex(start
//                        .setName("vertex3"))
//                .setTargetVertex(new Vertex()
//                        .setName("vertex2")));
//        this.setModel(model.build());
//        this.setPathGenerator(new RandomPath(new VertexCoverage(100)));
//        setNextElement(start);
//        Machine machine = new SimpleMachine(this);
//        Assert.assertThat(getExecutionStatus(), is(ExecutionStatus.NOT_EXECUTED));
//        try {
//            while (machine.hasNextStep()) {
//                machine.getNextStep();
//                Assert.assertThat(getExecutionStatus(), is(ExecutionStatus.EXECUTING));
//            }
//        } catch (Throwable t) {
//            Assert.assertTrue(MachineException.class.isAssignableFrom(t.getClass()));
//            Assert.assertThat(getExecutionStatus(), is(ExecutionStatus.FAILED));
//        }
//    }
}