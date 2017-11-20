package com.techmaster.hunter.calculator;

import junit.framework.TestCase;

public class CalculatorTest extends TestCase {

	public void testIsCorrect()
	{
		Calculator calculator = CalculatorImpl.getInstance();
		assertEquals(2d, calculator.add(1, 1));
		assertEquals(21d, calculator.multiply(3, 7));
	}
	
}
