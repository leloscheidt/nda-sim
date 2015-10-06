package nda.model.automata;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A Non-Deterministic Automata implementation of the {@link Automata} interface.
 * 
 * @author Douglas Silva
 * @author Marcelo Scheidt
 */
public class NDA implements Automata {

    private Map<String, State> states;
    private Map<Integer, State> finals;
    private Set<String> alphabet;
    private State initial;

    @Override
    public void addStates(String[] states) {
        this.states = new HashMap<String, State>(states.length);

        for (int i = 0; i < states.length; i++) {
            State state = new State(i, states[i]);
            this.states.put(states[i], state);
        }
    }

    @Override
    public void addAlphabet(String[] alphabet) {
        this.alphabet = new HashSet<String>(alphabet.length);

        for (int i = 0; i < alphabet.length; i++) {
            this.alphabet.add(alphabet[i]);
        }
    }

    @Override
    public void addTransition(String[] transition) {

        for (String t : transition) {
            String s = t.substring(0, 1);
            this.states.get(s).addTransition(t.substring(1));
        }
    }

    @Override
    public void addInitial(String initial) {
        this.initial = this.states.get(initial);
    }

    @Override
    public void addFinal(String[] finals) {
        this.finals = new HashMap<Integer, State>(finals.length);

        for (int i = 0; i < finals.length; i++) {
            State state = this.states.get(finals[i]);
            this.finals.put(state.getId(), state);
        }
    }

    @Override
    public boolean recognize(String text) {
        char[] tokens = text.toCharArray();

        for(char token : tokens) {
            if(!this.alphabet.contains(String.valueOf(token))) {
                return false;
            }
        }
        
        State actual = this.initial;

        return this.recognize(text, actual);
    }

    /**
     * Try to recognize the given string with the actual state.
     * 
     * @param text The text to recognize.
     * @param actual The actual state to start the process.
     * 
     * @return true if the automata recognize the string, false otherwise.
     */
    private boolean recognize(String text, State actual) {
        if(text.isEmpty()) {
            return this.finals.containsKey(actual.getId());
        }

        String symbol = String.valueOf(text.charAt(0));
        text = text.substring(1);

        String[] nexts = actual.getTransitionWith(symbol);

        if (nexts == null && !actual.hasEpsilonTransition()) {
            return false;
            
        } else if (nexts != null && nexts.length == 1) {
            return this.recognize(text, this.states.get(nexts[0]));
            
        } else if(nexts != null) {
            boolean recognized = false;
            
            for(int i = 0; i < nexts.length && !recognized; i++) {
                recognized = this.recognize(text, this.states.get(nexts[i]));
            }
            return recognized;
            
        } else if(actual.hasEpsilonTransition()) {
            String[] epsilonTransitions = actual.getTransitionWith("");
            boolean recognized = false;
            
            for(int i = 0; i < epsilonTransitions.length && !recognized; i++) {
                recognized = this.recognize(symbol + text, this.states.get(epsilonTransitions[i]));
            }
            return recognized;
        }
        
        return false;
    }
}
