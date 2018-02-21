import java.util.Comparator;
import edu.princeton.cs.algs4.*;

/**
 *Autocomplete.java
 * 
 * Fully implements "autocomplete" functionality, making use of `Term.java`
 * and `BinarySearchDeluxe.java`.
 * 
 * Given a source, .txt file and an integer, k, as input at invocation, 
 * in run-time, accepts a string as returns up k queries that match the string.
 * 
 * Compilation: javac-algs4 Autocomplete.java
 * Execution:   java-algs4 Autocomplete file.txt k
 * Example execution:
 * % java-algs4 Autocomplete wiktionary.txt 7
 * auto
 *         619695   automobile
 *         424997   automatic
 */

public class Autocomplete {
    private final Term[] termsArr;
    
    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null) throw new NullPointerException();
        
        termsArr = new Term[terms.length];
        
        for (int i = 0; i < terms.length; i++) {
            if (terms[i] == null) throw new NullPointerException();
            else termsArr[i] = terms[i];
        }

        MergeX.sort(termsArr);
    }
    /**************************************************************/
    /**************************************************************/
    
    // Returns all terms that start with the given prefix, in descending order
    // of weight.
    public Term[] allMatches(String prefix) {
        if (prefix == null) throw new NullPointerException();
        
        Term key = new Term(prefix, 9);
        int strLength = prefix.length();

        Comparator<Term> prefixCom = Term.byPrefixOrder(strLength);
        
        int until = BinarySearchDeluxe.lastIndexOf(termsArr, key, prefixCom);
        int from = BinarySearchDeluxe.firstIndexOf(termsArr, key, prefixCom);
        
        /* Covers any errors in either method by testing both results.
        * Even if there is only 1 result, until and from should equal the same
        * value.
        * So, if either equals -1, then assumes the other value is wrong.
        */
        if (until == -1 || from == -1) return new Term[0];
        
        Term[] matches = new Term[until-from+1];
        int j = 0;
        
        // copies the matching `Term`s into a new array
        for (int i = from; i <= until; i++) {
            matches[j] = termsArr[i];
            j++;
        }
        
        // sorts the array of matched `Term`s in descending weight order
        MergeX.sort(matches, Term.byReverseWeightOrder());
        return matches;
    }
    /**************************************************************/
    /**************************************************************/
    
    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        // asserts valid function parameters
        if (prefix == null) throw new NullPointerException();
        return allMatches(prefix).length;
    }
    
    /**************************************************************/
    // unit testing
    public static void main(String[] args) {

        // read in the terms from a file
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();          // read the next query
            terms[i] = new Term(query, weight);    // construct the term
        }

        // read in queries from standard input and print out the top k matching terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
           Term[] results = autocomplete.allMatches(prefix);
            for (int i = 0; i < Math.min(k, results.length); i++)
                StdOut.println(results[i]);
        }
    }
}