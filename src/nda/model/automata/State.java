package nda.model.automata;

import java.util.HashMap;
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
}
