package nda.util;

import java.util.List;

import nda.model.automata.Automata;
import nda.model.automata.NDA;

/**
 * Automata util class to help build an automata from file.
 * 
 * @author Douglas Silva
 * @author Marcelo Scheidt
 */
public class AutomataUtil {
    
    /**
     * Try to build an automata from the given lines.
     * 
     * @param lines The lines with the formal description of an automata.
     * 
     * @return An Automata
     */
    public static Automata fileLinesToAutomata(List<String> lines) {
        
        Automata automata = new NDA();
        
        for(String line : lines) {
            System.out.println(line);
            int begin = line.indexOf("{") + 1;
            
            if(line.startsWith("Q")) {
                line = line.substring(begin, line.length() - 1);

                String[] states = line.split(";");
                automata.addStates(states);
            }
            
            if(line.startsWith("E")) {
                line = line.substring(begin, line.length() - 1);

                String[] alphabet = line.split(";");
                automata.addAlphabet(alphabet);
            }
            
            if(line.startsWith("S")) {
                line = line.substring(begin, line.length() - 1);

                String[] transition = line.split(";");
                automata.addTransition(transition);
            }
            
            if(line.startsWith("q")) {
                line = line.trim();
                
                String initial = line.substring(line.length() - 1, line.length());
                automata.addInitial(initial);
            }
            
            if(line.startsWith("F")) {
                line = line.substring(begin, line.length() - 1);

                String[] finals = line.split(";");
                automata.addFinal(finals);
            }
        }
        
        return automata;
    }
}
