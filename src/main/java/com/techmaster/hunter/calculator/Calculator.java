package com.techmaster.hunter.calculator;

public interface Calculator {
	
	public double add(double firstNum, double secondNum);
	public double multiply(double firstNum, double secondNum);
	public double divide(double firstNum, double secondNum);
	public double subtract(double firstNum, double secondNum);
	public double square(double number);
	public double squareRoot(double number);
	public double cube(double number);
	public double cubeRoot(double number);
	public double factorial(double number);
	public double raisedTo(double base, double exponentr);
	public double inverse(double number);
	public double rootNOf(double base, double root);
	public double tenRaiseTo(double exponent);
	
	public double pi();
	
	public double sin(double number);
	public double sinInverse(double number);
	public double tan(double number);
	public double tanInvers(double number);
	public double cos(double number);
	public double cosInvers(double number);
	
	public double sec(double number);
	public double csc(double number);
	public double cot(double number);
	
	public double degreesToRadian(double number);
	public double radiansToDegrees(double number);
	
	
	

}
