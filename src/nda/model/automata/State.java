package nda.model.automata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Basic automata state implementation.
 * 
 * @author Douglas Silva
 * @author Marcelo Scheidt
 */
public class State {

    private final int id;
    private final String label;
    private final Map<String, String[]> transitions;

    private final Map<Integer, List<String>> visitedEpsilonTransitions;
    
    
    /**
     * Default constructor.
     * 
     * @param id The state identifier
     * @param label The state label.
     */
    public State(int id, String label) {
        this.id = id;
        this.label = label;
        this.transitions = new HashMap<String, String[]>();
        this.visitedEpsilonTransitions = new HashMap<Integer, List<String>>();
    }

    /**
     * Add the given transitions to the state.
     * 
     * @param transition The array to add as transitions.
     */
    public void addTransition(String transition) {
        transition = transition.replaceAll("[\\[\\]]", "");
        String[] parts = transition.split("-");
        
        String symbol = parts[0];
        String[] states = parts[1].split(",");
        
        this.transitions.put(symbol, states);
    }
    
    /**
     * Get the next states mapped with the given symbol.
     * 
     * @param symbol The symbol to search transitions.
     * 
     * @return The array of state labels.
     */
    public String[] getTransitionWith(String symbol) {
        return this.transitions.get(symbol);
    }
    
    /**
     * Return the next states mapped with epsilon transitions that already have been not used by the given character index.
     * 
     * @param index The index of the string
     * 
     * @return The array of state label.
     */
    public String[] getEpsilonTransitions(Integer index) {
        String[] all = this.getTransitionWith("");
        
        if(this.visitedEpsilonTransitions.containsKey(index)) {
            List<String> visited = this.visitedEpsilonTransitions.get(index);
            
            for(int i = 0; i < all.length; i++) {
                if(visited.contains(all[i])) {
                    all[i] = null;
                }
            }
        }
        
        return all;
    }

    /**
     * Check of the state has epsilon transitions.
     * 
     * @return true if the state has epsilon transitions, false otherwise.
     */
    public boolean hasEpsilonTransition() {
        return this.transitions.containsKey("");
    }

    /**
     * @return the state id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the state label
     */
    public String getLabel() {
        return label;
    }

    public void visit(Integer index, String label) {
        if(this.visitedEpsilonTransitions.containsKey(index)) {
            this.visitedEpsilonTransitions.get(index).add(label);
        
        } else {
            List<String> labels = new ArrayList<String>();
            labels.add(label);
            
            this.visitedEpsilonTransitions.put(index, labels);
        }
    }

    /**
     * Clean the epsilon transitions map.
     */
    public void clean() {
        this.visitedEpsilonTransitions.clear();
    }
}
