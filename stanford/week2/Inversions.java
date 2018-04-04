import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * File input.txt contains all of the 100,000 integers between 1 and 100,000 (inclusive) in some order, with no integer repeated.
 * <p>
 * Your task is to compute the number of inversions in the file given, where the ith row of the file indicates the ith entry of an array.
 **/
class Inversions {

    public static void main(String args[]) {
        Tuple t = sortCount(init("input.txt"));
        System.out.println(t.splitsNumber);
    }

    static List<Integer> init(String fileName) {
        List<Integer> numbers = new ArrayList<>();
        try {
            Path path = Paths.get(fileName);
            Scanner scanner = new Scanner(path);
            while (scanner.hasNext()) {
                if (scanner.hasNextInt()) {
                    numbers.add(scanner.nextInt());
                } else {
                    scanner.next();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return numbers;
    }

    static Tuple sortCount(List<Integer> A) {
        int n = A.size();
        if (n == 1) {
            return new Tuple(A, 0L);
        }
        Tuple Bx = sortCount(A.subList(0, n / 2));
        Tuple Cy = sortCount(A.subList(n / 2, n));
        Tuple Dx = mergeAndCountSplit(Bx.array, Cy.array);
        return new Tuple(Dx.array, Bx.splitsNumber + Cy.splitsNumber + Dx.splitsNumber);
    }

    static Tuple mergeAndCountSplit(List<Integer> B, List<Integer> C) {
        // resulting array
        List D = new ArrayList();
        // counter for array B
        int b = 0;
        // counter for array C
        int c = 0;
        // counter for array D
        int d = 0;
        // running sum of splits Numbers
        Long splitsNumber = 0L;

        while (D.size() < (B.size() + C.size())) {
            // all elements from C have been copied to D
            if (c >= C.size()) {
                D.add(B.get(b));
                b++;
            }
            // all elements from B have been copied to D
            else if (b >= B.size()) {
                D.add(C.get(c));
                c++;
            }
            // inversion element
            else if (C.get(c) < B.get(b)) {
                D.add(C.get(c));
                splitsNumber += B.size() - b;
                c++;
            }
            // normal order element
            else {
                D.add(B.get(b));
                b++;
            }
        }
        return new Tuple(D, splitsNumber);
    }

    static class Tuple {
        final List<Integer> array;
        final Long splitsNumber;

        public Tuple(List<Integer> array, Long splitsNumber) {
            this.array = array;
            this.splitsNumber = splitsNumber;
        }
    }

}