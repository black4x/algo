import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.max;

public class Main {

    public static void main(String[] args) {

        String xString = "3141592653589793238462643383279502884197169399375105820974944592";
        String yString = "2718281828459045235360287471352662497757247093699959574966967627";

        Main m = new Main();

        List<Integer> x = m.strToArray(xString);
        List<Integer> y = m.strToArray(yString);

        m.bigProduct(x, y).forEach(System.out::print);

    }

    List<Integer> strToArray(String s) {
        List<Integer> result = new ArrayList<>();
        int size = s.length();
        for (int i = 0; i < size; i++) {
            result.add(Integer.parseInt(s.charAt(i) + ""));
        }
        return result;
    }

    List<Integer> bigProduct(List<Integer> x, List<Integer> y) {

        if (x.size() == 1 && y.size() == 1) {
            return toArray(x.get(0) * y.get(0));
        }

        int size = x.size(); // todo if x.size != y.size
        List<Integer> ac = bigProduct(x.subList(0, size / 2), y.subList(0, size / 2));
        List<Integer> ad = bigProduct(x.subList(0, size / 2), y.subList(size / 2, size));
        List<Integer> bc = bigProduct(x.subList(size / 2, size), y.subList(0, size / 2));
        List<Integer> bd = bigProduct(x.subList(size / 2, size), y.subList(size / 2, size));

        List<Integer> sum_ac_bd = sum(addZeros(size, ac), bd);
        List<Integer> sum_ad_bc = addZeros(size / 2, sum(ad, bc));
        return sum(sum_ac_bd, sum_ad_bc);

    }


    List<Integer> sum(List<Integer> x, List<Integer> y) {
        int sizeX = x.size();
        int sizeY = y.size();

        int maxSize = max(sizeX, sizeY);
        List<Integer> xNormalized = prepandZeros(maxSize, x);
        List<Integer> yNormalized = prepandZeros(maxSize, y);

        List<Integer> result = new ArrayList<>();

        int carry = 0;
        for (int i = maxSize - 1; i >= 0; i--) {
            int s = xNormalized.get(i) + yNormalized.get(i) + carry;
            ArrayList<Integer> temp = toInverseArray(s);
            result.add(temp.get(0));
            if (temp.size() == 2) {
                carry = temp.get(1);
            } else {
                carry = 0;
            }
            if (temp.size() > 2) {
                throw new IllegalArgumentException("should not be more then 2 digits");
            }
        }
        if (carry > 0) {
            result.add(carry);
        }

        Collections.reverse(result);
        return result;
    }

    List<Integer> toArray(Integer i) {
        List<Integer> result = toInverseArray(i);
        Collections.reverse(result);
        return result;
    }

    List<Integer> toInverseArray(Integer i) {

        List<Integer> result = new ArrayList<>();
        if (i == 0) {
            result.add(0);
            return result;
        }

        while (i > 0) {
            result.add(i % 10);
            i = i / 10;
        }
        return result;
    }

    List<Integer> addZeros(int n, List<Integer> arr) {
        List<Integer> result = new ArrayList<>(arr);
        for (int i = 0; i < n; i++) {
            result.add(0);
        }
        return result;
    }

    List<Integer> prepandZeros(int n, List<Integer> arr) {
        if (n == arr.size()) {
            return arr;
        }
        List<Integer> result = new ArrayList<>();
        int numberOfZerosToPrepend = n - arr.size();
        for (int i = 0; i < numberOfZerosToPrepend; i++) {
            result.add(0);
        }
        result.addAll(arr);
        return result;
    }

}
