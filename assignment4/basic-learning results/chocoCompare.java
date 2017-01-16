package brpcompare;

import com.google.common.collect.ImmutableSet;
import net.automatalib.util.automata.Automata;
import net.automatalib.words.Word;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;

/**
 * Created by petra on 14-12-16.
 */
public class BRPCompare {

    public static void main(String[] args) throws IOException {
        String model1 = "/hypothesis0.dot",
                model2 = "/hypothesis1.dot";
        Collection<String> inputAlphabet = ImmutableSet.of("5ct","10ct","mars","twix","snickers");
        
        GraphvizParser p = new GraphvizParser(Paths.get(new File("").getAbsolutePath().concat(model1)));
        GraphvizParser p2 = new GraphvizParser(Paths.get(new File("").getAbsolutePath().concat(model2)));
        try {
	        Word<String> compareResult = Automata.findSeparatingWord(p.createMachine(), p2.createMachine(), inputAlphabet);
	        if(compareResult == null) {
	            System.out.println("The automata are equal!");
	        } else {
	            System.out.println("The automata are not equal! A counterexample is: " + compareResult);
	        }
        } catch (NullPointerException e) {
        	e.printStackTrace();
        	System.err.println("Did you compare with the right input alphabet?");
        }
    }
}
