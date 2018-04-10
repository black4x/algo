import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Implementation of quick sort.
 * use first element as a pivot in order to count all comparisons from the tast of week 3
 */

public class AQuickSort {

    static long runningTotal = 0;

    public static void main(String argsp[]) throws IOException {
        AQuickSort aQuickSort = new AQuickSort();
        final ArrayTuple result = new ArrayTuple(aQuickSort.initFromFile("quicksort.txt"), 0);
        aQuickSort.sort(result, 0, result.a.length);
        for (int i = 0; i < result.a.length; i++) {
            System.out.print(result.a[i] + " ");
        }
        System.out.println("---");
        for (int i = 0; i < result.a.length - 1; i++) {
            if (result.a[i] > result.a[i + 1]) {
                System.out.println("FUCK!");
            }
        }
        System.out.println("\npivot index = " + result.pivotIndex);
        System.out.println("total comparisons = " + runningTotal);
        runningTotal = 0;

        final ArrayTuple resultRight = new ArrayTuple(aQuickSort.initFromFile("quicksort.txt"), 0);
        aQuickSort.sortRight(resultRight, 0, resultRight.a.length);
        for (int i = 0; i < resultRight.a.length; i++) {
            System.out.print(resultRight.a[i] + " ");
        }
        System.out.println("\n--- identity check --- ");
        for (int i = 0; i < resultRight.a.length; i++) {
            if (resultRight.a[i] != result.a[i]) {
                System.out.println(resultRight.a[i] + " != " + result.a[i]);
            }
        }
        System.out.println("total comparisons = " + runningTotal);
        runningTotal = 0;

        final ArrayTuple resultMedian = new ArrayTuple(aQuickSort.initFromFile("quicksort.txt"), 0);
        aQuickSort.sortMedianOfThree(resultMedian, 0, resultMedian.a.length);
        for (int i = 0; i < resultMedian.a.length; i++) {
            System.out.print(resultMedian.a[i] + " ");
        }
        System.out.println("\n--- identity check --- ");
        for (int i = 0; i < resultMedian.a.length; i++) {
            if (resultMedian.a[i] != result.a[i]) {
                System.out.println(resultMedian.a[i] + " != " + result.a[i]);
            }
        }
        System.out.println("total comparisons = " + runningTotal);
    }

    private void sort(ArrayTuple at, int left, int right) {
        if (left >= right) return;
        runningTotal += (right - left - 1);
        ArrayTuple newPartitionedArray = partition(at.a, left, right);
        sort(newPartitionedArray, left, newPartitionedArray.pivotIndex);
        sort(newPartitionedArray, newPartitionedArray.pivotIndex + 1, right);
    }

    private void sortRight(ArrayTuple at, int left, int right) {
        if (left >= right) return;
        runningTotal += (right - left - 1);
        swap(at.a, left, right - 1);
        ArrayTuple newPartitionedArray = partition(at.a, left, right);
        sortRight(newPartitionedArray, left, newPartitionedArray.pivotIndex);
        sortRight(newPartitionedArray, newPartitionedArray.pivotIndex + 1, right);
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
        int size = right - left + 1;
        return size / 2 - 1;
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

    private int[] initFromFile(String fileName) throws IOException {
        int[] result = new int[10000];
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
        int pivotIndex;

        public ArrayTuple(int[] a, int pivotIndex) {
            this.a = a;
            this.pivotIndex = pivotIndex;
        }
    }

}
