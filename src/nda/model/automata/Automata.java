package nda.model.automata;

/**
 * Automata interface with basic behavior.
 * 
 * @author Douglas Silva
 * @author Marcelo Scheidt
 */
public interface Automata {

    /**
     * Add the given array as the states of the automata.
     * 
     * @param states The array of labels of the states.
     */
    void addStates(String[] states);

    /**
     * Add the given array as the alphabet of the automata.
     * 
     * @param alphabet The array with the alphabet.
     */
    void addAlphabet(String[] alphabet);

    /**
     * Add the given array as the transitions of the automata.
     * 
     * @param transition The array with the transitions.
     */
    void addTransition(String[] transition);

    /**
     * Mark the state with the given label as the initial one.
     * 
     * @param initial The initial state label
     */
    void addInitial(String initial);

    /**
     * Mark the states with the given labels as final ones.
     * 
     * @param finals The finals states labels.
     */
    void addFinal(String[] finals);

    /**
     * Test the given string against the automata. <br>
     * The automata will try to recognize the given string by starting on the initial state and character by character arrive on one final state with the permitted transitions.<br> 
     * The automata will return true iff at the end of the string the state is one final state and will return false otherwise.
     * 
     * @param test The string to test
     * 
     * @return true if the automata recognize the string, false otherwise.
     */
    boolean recognize(String test);
}
