import java.util.Comparator;
import edu.princeton.cs.algs4.*;
/**
 * BinarySearchDeluxe.java
 * 
 * A helper class that implements two different forms of binary search: 
 * finding the first or last index of a given, generic `Key`.
 * 
 * Compilation: javac-algs4 BinarySearchDeluxe.java
 * Execution:   java-algs4 BinarySearchDeluxe
 */

public class BinarySearchDeluxe {
    
    // Returns the index of the first key in a[] that equals the search key
    // or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key,
                                         Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null) {
            throw new NullPointerException();
        }
        int hi = a.length-1;
        int lo = 0;
        int compares = 0;
        while (lo <= hi) {
            int mid = lo + ((hi-lo)/2);
            if (comparator.compare(key, a[mid]) < 0) {
                hi = mid -1;
                compares++;
            }
            else if (comparator.compare(key, a[mid]) > 0) {
                lo = mid + 1;
                compares++;
            }
            else {
                while (lo < hi) {
                    mid = lo + ((hi-lo)/2);
                    if (comparator.compare(key, a[mid]) == 0) {
                        hi = mid - 1;
                    }
                    else if (comparator.compare(key, a[mid]) > 0){
                        lo = mid + 1;
                    }
                }
                return hi;
            }
        }
        return -1;
    }
    
    // Returns the index of the last key in a[] that equals the search key,
    // or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key,
                                         Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null) {
            throw new NullPointerException();
        }
        int hi = a.length-1;
        int lo = 0;
        while (lo <= hi) {
            int mid = lo + ((hi-lo)/2);
            if (comparator.compare(key, a[mid]) < 0) hi = mid -1;
            else if (comparator.compare(key, a[mid]) > 0) lo = mid + 1;
            else {
                int compares = 0;
                while (lo < hi) {
                    mid = lo + ((hi-lo)/2);
                    if (comparator.compare(key, a[mid]) == 0) {
                        lo = mid + 1;
                    }
                    else if (comparator.compare(key, a[mid]) < 0) {
                        hi = mid - 1;
                    }
                }
                return lo;
            }
        }
        return -1;
    }
    /**************************************************************/
    // unit testing
    public static void main(String[] args) {
        Term[] terms = new Term[11];
        terms[0] = new Term("ab", 3);
        terms[1] = new Term("acdc", 7);
        terms[2] = new Term("cde", 12);
        terms[3] = new Term("zeddu", 89374);
        terms[4] = new Term("aaaaaah", 0);
        terms[5] = new Term("progress", 1010101);
        
        for (int i = 6; i < 9; i++) {
            terms[i] = new Term("cde", 12);
        }
        terms[9] = new Term("fghi", 30);
        terms[10] = new Term("cdad", 23);
        
        Term term2 = new Term("cde", 12);
        
        Comparator<Term> comT = Term.byPrefixOrder(4);
        MergeX.sort(terms, comT);
        
        StdOut.println("First index: " + firstIndexOf(terms, term2, comT));
        StdOut.println("Last index: " + lastIndexOf(terms, term2, comT));
        
        StdOut.println();
        for (Term t : terms) {
            StdOut.println(t);
        }
        StdOut.println();
        
        Term[] terms2 = new Term[100];
        terms2[0] = new Term("sa", 0);
        
        for (int i = 1; i < 99; i++) {
            terms2[i] = new Term("ss", i);
        }
        
        terms2[99] = new Term("zs", 50000);
        Term term3 = new Term("ss", 37);
        
        MergeX.sort(terms2, comT);
        StdOut.println("First index: " + firstIndexOf(terms2, term3, comT));
        StdOut.println("Last index: " + lastIndexOf(terms2, term3, comT));
        
    }
}