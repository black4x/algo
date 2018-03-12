import java.util.*;

class Inversions {

	public static void main(String args[]){
			Tuple t = mergeAndCountSplit(Arrays.asList(1, 3, 5), Arrays.asList(2, 4, 6));
			System.out.println(t.splitsNumber);
			System.out.println(t.array.size());
			t.array.forEach(System.out::print);
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
			int splitsNumber = 0;

			while (D.size() < (B.size() + C.size())) {
				// all elements from C have been copied to D
				if (c >= C.size()){
						D.add(B.get(b));
						b++;
				} 
				// all elements from B have been copied to D
				else if (b >= B.size()){
					D.add(C.get(c));
					c++;
				} 
				// inversion element		
				else if (C.get(c) < B.get(b)){
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
		final int splitsNumber;

		public Tuple(List<Integer> array, int splitsNumber){
			this.array = array;
			this.splitsNumber = splitsNumber;
		}
	}

}