package nda.control;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import nda.model.automata.Automata;
import nda.util.AutomataUtil;

/**
 * Main class.
 * 
 * @author Douglas Silva
 * @author Marcelo Scheidt
 */
public class Initializer {

    /**
     * Main method to run application
     * 
     * @param args The arguments.
     */
    @SuppressWarnings("resource")
    public static void main(String[] args) {

        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.println("                   Welcome to the Non-Deterministic Automata Simulator");
        System.out.println("-----------------------------------------------------------------------------------------------------");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Create a new NDA:\n");

        System.out.println("The definition of a non-deterministic automata T is:\n" + "T = {Q, E, S, q, F} where\n"
                + "  Q: is the set of states separeted by semi-colon (e. A;B;C;D),\n"
                + "  E: is the set with the alphabet separeted by semi-colon (e. y;i;o),\n"
                + "  S: is the set with the transition function separeted by semi-colon in the format (e. A[y-B,A]),\n"
                + "  q: is the initial state,\n" + "  F: is the set of final states separeted by semi-colon.\n");

        System.out.println("-----------------------------------------------------------------------------------------------------");

        System.out.print("Path of the automata file: ");

        String path = scanner.next();
        List<String> lines = null;

        try {
            lines = Files.readAllLines(Paths.get(path));

        } catch (Exception e) {
            System.out.println("Error loading file.");
        }

        Automata automata = AutomataUtil.fileLinesToAutomata(lines);
        String test = "";
        
        do {
            System.out.print("\nDo you want to test the empty string?(Y/N): ");
            test = scanner.next();
            
            if(test.toUpperCase().equals("Y")) {
                test = "";
            
            } else {
                System.out.print("\nString to test: ");
                test = scanner.next();
            }

            if (automata.recognize(test)) {
                System.out.println("The automata recognized the string");

            } else {
                System.out.println("The automata NOT recognized the string");
            }
            System.out.print("\nDo you want to test another string?(Y/N): ");
            test = scanner.next();

        } while (test.toUpperCase().equals("Y"));
        System.out.println("-- Bye!");
    }
}
