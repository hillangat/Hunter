package com.techmaster.hunter.calculator;

import org.apache.log4j.Logger;



public class CalculatorImpl extends AbstractCalculator {
	
	public static Calculator calculator = null;
	public Logger logger = Logger.getLogger(getClass() );
	
	static {
		if (calculator == null) {
			synchronized (CalculatorImpl.class) {
				calculator = new CalculatorImpl();	
			}
		}
	}
	
	public static Calculator getInstance(){
		return calculator;
	}

	
	@Override
	public double add(double firstNum, double secondNum) {
		return firstNum + secondNum;
	}
	@Override
	public double multiply(double firstNum, double secondNum) {
		return firstNum * secondNum;
	}
	@Override
	public double divide(double firstNum, double secondNum) {
		return firstNum/secondNum;
	}

	@Override
	public double subtract(double firstNum, double secondNum) {
		return firstNum - secondNum;
	}
	@Override
	public double square(double number) {
		return number * number;
	}
	@Override
	public double squareRoot(double number) {
		
		final double holder = number;
		
		if(number < 0 ) throw new IllegalArgumentException("Negative number ( " + number + " )");
		if(number == 0 || number == 1) return number;
		
		String numStr = (number + "");
		double decDivider = 1;
		int dotLoc = 0;
		String decSubStr = "";
		boolean hasDecimal = number > 1 && numStr.contains("."); 
		
		if(hasDecimal){
			// Need to handle something like this : 1.0E9
			dotLoc = numStr.indexOf("."); 
			decSubStr = numStr.substring(dotLoc+1, numStr.length());
			boolean hasExponent = numStr.contains("E");
			int eIndex = hasExponent ? numStr.indexOf("E") : 0;
			String withoutE = "";
			if (hasExponent) {
				withoutE = decSubStr.substring(0, eIndex - 2);
				log.info("String has an exponent  : " + holder + " >>> parts post dot and pre exponent = " + withoutE); 
			}
			if(decSubStr.equals("0") || (hasExponent & withoutE.equals("0")))
				hasDecimal = false;
			
			// brought this into the loop
			int decNO = decSubStr.length();
			decNO = decNO % 2 == 0 ? decNO : decNO + 1;
			decDivider = decNO/2;
			while(decNO >= 1){
				number *= 10;
				decNO -- ;
			}
			final double limitHolder = decDivider; decDivider = 1;
			for(double i=1;i<=limitHolder;i++)
				decDivider *= 10;
			
		}

		double square = 2;
		int power = 0;
		double tempNum = number;
		boolean isNormalize = false;
		
		while(tempNum >= 100){
			power++;
			isNormalize = true;
			double nextSquare =  Math.pow(2, power + 1);
			final double tempNumHolder = tempNum;
			if(tempNum/nextSquare <= 100 && power % 2 !=0){
				log.info(">>>>>>>> ODD power and will be exiting : power = " + power + " nextSquare = " + nextSquare);
				power++;
				square = Math.pow(2, power);  
				tempNum /= nextSquare;
				log.info(tempNumHolder + "/" + nextSquare + " = " + tempNum); 
				break;
			}
			square = Math.pow(2, power);  
			tempNum /= square;
			log.info("Divider = " + square + " tempNum = " + tempNum + " power = " + power); 
			log.info(tempNumHolder + "/" + square + " = " + tempNum);
		}
		if(isNormalize)
			tempNum = holder/square;
		
		double increment = 0.000001;
		double sqrt = 0;
		double lastBalancer = 1;
		
		boolean isIncreasedBalanced = false;
		boolean isReducedBalanced = false;
		
		if(tempNum < 1){
			log.info("Number is lesss than one. Needs a a balancer : " + tempNum);
			isIncreasedBalanced = true;
			while(tempNum <= 1){
				tempNum *= 100; 
				lastBalancer *= 10;
			}
		}
		
		if(tempNum > 50000){
			isReducedBalanced = true;
			while(tempNum > 50000){
				tempNum /= 100; 
				lastBalancer *= 10;
			}
		}
		
		log.info("Going into square root loop for the number " + tempNum); 
		
		for(double i=1; i<=tempNum;i+=increment){
			sqrt = i*i;
			if(sqrt >= tempNum){
				sqrt = i;
				break;
			}
		}
		
		sqrt = isIncreasedBalanced ? sqrt = sqrt / lastBalancer : sqrt;
		sqrt = isReducedBalanced ? sqrt = sqrt * lastBalancer : sqrt;
		square = Math.pow(2, power/2); 
		sqrt = isNormalize ? sqrt * square : sqrt; 								log.info(" isNormalized = "+ isNormalize +" Sqrt = "+ sqrt);
		sqrt = hasDecimal && !isNormalize ? sqrt / decDivider : sqrt; 			log.info(" hasDecimal = "+ hasDecimal +" Sqrt = "+ sqrt + " decDivider = " + decDivider);
		
		log.info("Square root of " + holder + " = " + sqrt);
		
		return sqrt;
		
	}
	
	public static void main(String[] args) {
		
		//System.out.println(getInstance().squareRoot(1000000));
		Long startTime = System.nanoTime();
		System.out.println(getInstance().squareRoot(1000000));
		Long endTime = System.nanoTime();
 
		System.out.println(endTime - startTime);
		
	}
	
	@Override
	public double cube(double number) {
		return number*number*number;
	}
	@Override
	public double cubeRoot(double number) {
		return 0;
	}
	@Override
	public double pi() {
		return Math.PI;
	}
	
	
	public double sinForm(double x){
		double answer=0,negOne=-1,numXpow=0,denNum=0, division=1,i=0;
		for(;division != 0;i++){
			negOne = basicRaiseTo(-1, i);
			numXpow = basicRaiseTo(x, 2*i+1);
			double top = negOne * numXpow;
			denNum = (2*i +1);
			denNum = factorial(denNum);
			division = top/denNum;
			answer += division;
		}
		return answer;
	}
	
	@Override
	public double sin(double x) {
		double answer=0,negOne=-1,numXpow=0,denNum=0, division=1,i=0;
		for(;division != 0;i++){
			negOne = basicRaiseTo(-1, i);
			numXpow = basicRaiseTo(x, 2*i+1);
			double top = negOne * numXpow;
			denNum = (2*i +1);
			denNum = factorial(denNum);
			division = top/denNum;
			answer += division;
		}
		return answer;
	}
	
	@Override
	public double sinInverse(double number) {
		double total=0, div = 1;
		for(double i=0; div != 0; i++){
			double 
			exponent = 2*i + 1, 
			topRaised = basicRaiseTo(number, exponent), 
			bottFact = factorial(exponent), 
			negOne = basicRaiseTo(-1, i);
			div = topRaised/bottFact;
			div *= negOne;
			total += div;
		}
		return total;
	}
	@Override
	public double tan(double number) {
		return 0;
	}
	@Override
	public double tanInvers(double number) {
		return 0;
	}
	@Override
	public double cos(double number) {
		return 0;
	}
	@Override
	public double cosInvers(double number) {
		return 0;
	}
	@Override
	public double sec(double number) {
		return 0;
	}
	@Override
	public double csc(double number) {
		return 0;
	}
	@Override
	public double cot(double number) {
		return 0;
	}
	@Override
	public double degreesToRadian(double number) {
		return 0;
	}
	@Override
	public double radiansToDegrees(double number) {
		return 0;
	}


	@Override
	public double factorial(double number) {
		final double counter = number;number = 1;
		for(double i=1; i<= counter; i++){
			number *= i ;
		}
 		return number;
	}


	@Override
	public double raisedTo(double base, double exponent) {
		
		if(base == 0 || exponent == 1) return base;
		if(base == 1 || exponent == 0) return 1;
		
		double answer = 0;
		
		boolean isNegExponent = exponent < 0;
		boolean isNegBase = base < 0;
		boolean isExponentEven = exponent % 2 == 0;
		boolean isFractBase = isFraction(base);
		boolean isFractExp = isFraction(exponent);
		
		// 10^10
		// 10^-10
		//-10^10
		//0.01^2
		//0.01^-1
		//0.01^0.01
		
		double[] expPair = null;
		double cleanedExp = 0;
		double expTens = 0;
		
		if(isFractExp && !isFractBase){
			exponent = inverse(exponent); // 2
			if(hasDecimal(exponent)){
				logger.info("The exponent passed is a fraction that does not yield a whole number >>> " + exponent);
				expPair = splitNumByDecimal(exponent);
				double part1 = expPair[0], part2 = expPair[1], answer2 =0;
				answer = basicMultiplyTill(base, part1); // for part1 only
				//answer2 = ; // 0.5
			}else{
				answer = basicMultiplyTill(base, exponent);
			}
			logger.info("Fraction Exponent : " + exponent + " base : " + base + " answer = " + answer );
			return answer;
		}
		
		if(!isNegBase && !isNegExponent){
			answer = basicRaiseTo(base, exponent);
		}else if(!isNegBase && isNegExponent) 
			answer =  inverse(basicRaiseTo(base, -exponent));
		else if(isNegBase && isExponentEven)
			answer =  basicRaiseTo(base, exponent);
		else if(isNegBase && !isExponentEven)
			answer =  negativeOf(basicRaiseTo(base, exponent)); 
		
		return 0;
	}


	@Override
	public double inverse(double number) {
		return 1/number;
	}


	@Override
	public double rootNOf(double base, double root) {
		if(root == 2)
			return square(base);
		return 0;
	}
	

}
