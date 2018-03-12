import java.util.*;

class Inversions {

	public static void main(String args[]){
			Tuple t = sortCount(Arrays.asList(2, 1, 4, 3, 6, 5));
			System.out.println(t.splitsNumber);
			t.array.forEach(System.out::print);
	}

	static Tuple sortCount(List<Integer> A){
			int n = A.size();
			if (n == 1) {
				return new Tuple(A, 0);
			}
			Tuple Bx = sortCount(A.subList(0, n/2));
			Tuple Cy = sortCount(A.subList(n/2, n));
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