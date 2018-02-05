package com.techmaster.hunter.hackerrank;

public class MaxDifference {
	
	static int[] max( int[] a ) {
		int max = 0, index = -1;
		for( int i = 0 ; i < a.length; i++ ) {
			if ( max < a[i] ) {
				max = a[i];
				index = i;
			}
		}
		return new int[] { max, index };
	}
	
	static int min( int[] a, int maxIndex ) {
		int min = 0, index = -1;
		for( int i = 0 ; i < maxIndex; i++ ) {
			if ( min > a[i] || i == 0 ) {
				min = a[i];
				index = i;
			}
		}
		return min = index == -1 ? -1 : min;
	}
	
	static int maxDifference(int[] a) {
		
		int  diff = -1, max = 0, index = -1, min = 0;
		
		if ( a == null || a.length == 0 )
			return diff;

		int[] maxResult = max( a );
		max   = maxResult[0];
		index = maxResult[1];
		
		if ( index == 0 ) {
			return -1;
		}
		
		min	  = min(a, index);
		
		return Math.abs(max - min);
    }
	
	public static void main(String[] args) {
		int[] a = {9, 5,-7,-16,-3,1,2};
		System.out.println(maxDifference(a));
	}

}
