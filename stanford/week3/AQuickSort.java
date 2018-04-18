import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Implementation of quick sort.
 * using the "median-of-three" pivot rule.
 * [The primary motivation behind this rule is to do a little bit of extra work
 * to get much better performance on input arrays that are nearly sorted or reverse sorted.]
 */

public class AQuickSort {

    // number of comparisons
    static long runningTotal = 0;

    public static void main(String args[]) throws IOException {
        AQuickSort aQuickSort = new AQuickSort();

        final ArrayTuple resultMedian = new ArrayTuple(aQuickSort.initFromFile("quicksort.txt", 10000));
        aQuickSort.sortMedianOfThree(resultMedian, 0, resultMedian.a.length);

        System.out.println("total comparisons = " + runningTotal);
    }


    private void sortMedianOfThree(ArrayTuple at, int left, int right) {
        if (left >= right) return;
        runningTotal += (right - left - 1);
        swap(at.a, left, medianBasedIndex(at.a, left, right));
        ArrayTuple newPartitionedArray = partition(at.a, left, right);
        sortMedianOfThree(newPartitionedArray, left, newPartitionedArray.pivotIndex);
        sortMedianOfThree(newPartitionedArray, newPartitionedArray.pivotIndex + 1, right);
    }

    /**
     * return index of median element between left, middle, right indexes.
     */
    private int medianBasedIndex(int a[], int left, int right) {
        int medianIndex = calcMedianIndex(left, right);
        int x1 = a[left];
        int x2 = a[medianIndex];
        int x3 = a[right - 1];

        if (x1 < x3) {
            if (x3 < x2) return right - 1;
            if (x1 < x2) return medianIndex;
            return left;
        }
        if (x2 > x1) return left;
        if (x2 > x3) return medianIndex;
        return right - 1;
    }

    private int calcMedianIndex(int left, int right) {
        int size = right - left;
        if (size % 2 == 0) return size / 2 - 1 + left;
        else return size / 2 + left;

    }

    // partitioning the array around the pivot with the invariant:
    // [<p, p, >p]
    // input: array, left index, right index
    // output: partitioned array within [left index, right index]
    private ArrayTuple partition(int a[], int left, int right) {
        int p = a[left];
        int i = left + 1;
        // j - what have be looked so far (invariant: i<=j)
        // i - split between <p, and >p
        for (int j = left + 1; j < right; j++) {
            if (a[j] < p) {
                // swap left most entry i, which is bigger that the pivot with
                // discovered less then the pivot entry.
                swap(a, i, j);
                // advance left seen index i = i + 1, because it has more more element.
                i++;
            }
        }
        // final swap to put pivot in correct place: right most element less then pivot: i-1
        swap(a, left, i - 1);
        return new ArrayTuple(a, i - 1);
    }

    private void swap(int a[], int index1, int index2) {
        int buffer = a[index1];
        a[index1] = a[index2];
        a[index2] = buffer;
    }

    private int[] initFromFile(String fileName, int N) throws IOException {
        int[] result = new int[N];
        Path path = Paths.get(fileName);
        Scanner scanner = new Scanner(path);
        int i = 0;
        while (scanner.hasNext()) {
            result[i++] = scanner.nextInt();
        }
        return result;
    }

    private static class ArrayTuple {
        int[] a;
        int pivotIndex = 0;

        ArrayTuple(int[] a, int pivotIndex) {
            this.a = a;
            this.pivotIndex = pivotIndex;
        }

        ArrayTuple(int[] a) {
            this.a = a;
        }
    }

}
