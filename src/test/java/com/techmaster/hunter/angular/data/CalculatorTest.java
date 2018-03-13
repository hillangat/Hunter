package com.techmaster.hunter.angular.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.techmaster.hunter.calculator.CalculatorImpl;

public class CalculatorTest {
	CalculatorImpl calculator = Mockito.mock(CalculatorImpl.class);
	
	@Before
	public void setup() { assertNotNull(calculator); }
	
	@Test
	public void testAdd() {
		assertEquals(10, calculator.add(5, 5), 0);
	}
}
