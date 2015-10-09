package nda.model.automata;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
    
    private Integer index;
    private Set<String> visited;
    
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

        this.clean();
        State actual = this.initial;

        return this.recognize(text, actual);
    }

    /**
     * Clean all visited states and transitions.
     */
    private void clean() {
        Iterator<String> iterator = this.states.keySet().iterator();
        
        while(iterator.hasNext()) {
            this.states.get(iterator.next()).clean();
        }
        
        this.visited = null;
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
            if(this.finals.containsKey(actual.getId())) {
                return true;
            
            } else if(!actual.hasEpsilonTransition()) {
                return false;
            
            } else {
                this.clean();
                return isFinalReachable(actual);
            }
        }

        if(this.index == null) {
            this.index = 0;
        } else {
            this.index++;
        }
        
        String symbol = String.valueOf(text.charAt(0));
        text = text.substring(1);

        String[] nexts = actual.getTransitionWith(symbol);

        boolean recognized = false;

        if (nexts == null && !actual.hasEpsilonTransition()) {
            return false;
            
        } else if (nexts != null && nexts.length == 1) {
            recognized = this.recognize(text, this.states.get(nexts[0]));
            
        } else if(nexts != null) {
            recognized = false;
            
            for(int i = 0; i < nexts.length && !recognized; i++) {
                recognized = this.recognize(text, this.states.get(nexts[i]));
            }
        } 
        
        if(!recognized && actual.hasEpsilonTransition()) {
            String[] epsilonTransitions = actual.getEpsilonTransitions(this.index);
            recognized = false;
            
            for(int i = 0; i < epsilonTransitions.length && !recognized && epsilonTransitions[i] != null; i++) {
                actual.visit(this.index,epsilonTransitions[i]);
                this.index--;
                
                recognized = this.recognize(symbol + text, this.states.get(epsilonTransitions[i]));
            }
        }
        
        return recognized;
    }
    
    /**
     * Check if at least one final state is reachable from the given state using only epsilon transitions.
     * 
     * @param actual The state test from.
     * 
     * @return true if one final state is reachable from the given state, false otherwise.
     */
    private boolean isFinalReachable(State actual) {
        if(this.finals.containsKey(actual.getId())) {
            return true;
        }
        
        if(this.visited == null) {
            this.visited = new HashSet<String>();
        }
        
        if(this.visited.contains(actual.getLabel())) {
            return false;
        
        } else {
            this.visited.add(actual.getLabel());
            String[] array = actual.getEpsilonTransitions(0);
            
            boolean recognized = false;

            if(array != null) {
                
                for(int i = 0; i < array.length && !recognized; i++) {
                    recognized = this.isFinalReachable(this.states.get(array[i]));
                }
            }
            return recognized;
        }
    }
}
