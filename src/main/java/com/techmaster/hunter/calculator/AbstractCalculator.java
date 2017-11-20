package com.techmaster.hunter.calculator;

import java.util.Arrays;

import org.apache.log4j.Logger;


public abstract class AbstractCalculator implements Calculator {
	
	protected final static Logger log = Logger.getLogger(Calculator.class);
	
	public static double[] removeDecWthPower10(double number){
		Object[] stringAndLen = getPstDotLenAndWholStr(number);
		int decLen = (int)stringAndLen[1];	
		double tens = 0;
		while(decLen >= 1){
			number *= 10; 
			tens++; decLen--;
		}
		return new double[]{number, tens};
	}
	
	public static Object[] getPstDotLenAndWholStr(double number){
		String str = number + "";
		int dotLoc = str.indexOf(".");
		int len = str.length();
		String decimals = str.substring(dotLoc + 1, len);
		int decLen = decimals.length();
		if(decimals.equals("0"))
			decLen = 0;
		return new Object[]{str, decLen};
	}
	
	public static double[] splitNumByDecimal(double number){
		if(!hasDecimal(number))
			return new double[]{number, 0};
		String nStr = Double.toString(number);
		String part1 = nStr.substring(0, nStr.indexOf("."));
		String part2 = nStr.substring(nStr.indexOf(".")+1, nStr.length());
		double part1D = Double.parseDouble(part1);
		double part2D = Double.parseDouble(part2);
		double[] numbers = new double[]{part1D, part2D}; 
		log.info(number + " has been split into >> " + Arrays.toString(numbers));  
		return numbers;
	}
	
	public static double basicMultiplyTill(double base, double multiplier){
		return basicMultiplyTill(base, multiplier, 0);
	}
	
	public static double basicMultiplyTill(double base, double multiplier, double increment){
		increment = increment == 0d ? 1 : increment;
		if(increment < 0) throw new IllegalArgumentException("No passing a negative increment. Increment = " + increment);
		double result = 1;
		for(double i=1; i<=base; i+= increment){
			result = i;
			for(double j=1;j< multiplier;j++)
				result *= i;
			if(result >= base){
				result = i;
				break;
			}
		}
		return result;
	}
	
	public static double[] basicMultiplyTillWithChange(double base, double multiplier, double increment, double percentage){
		percentage = percentage/100;
		double[] nums = null;
		increment = increment == 0d ? 1 : increment;
		if(increment < 0) throw new IllegalArgumentException("No passing a negative increment. Increment = " + increment);
		double result = 1;
		for(double i=1; i<=base; i+= increment){
			result = i;
			for(double j=1;j< multiplier;j++)
				result *= i;
			boolean isGreat = result >= base;
			boolean isLess = result > base*percentage && result < base ;
			if(isGreat || isLess){
				nums = new double[]{i,result - base};  
				log.info("returning basicMultiply with difference >> " + Arrays.toString(nums));
				break;
			}
		}
		return nums;
	}
	
	public static double handleRaiseToDecimal(double base, double decimal){
		
		if(decimal == 0) return 1;
		if(decimal == 1 || base == 1) return base;
		if(decimal > 1) throw new IllegalArgumentException("handleRaiseToDecimal(double base, double decimal) accepts only decimal or  0 or 1 exponents! input = " + decimal);
		
		decimal = CalculatorImpl.getInstance().inverse(decimal); 
		double[] result = null; 
		double increment = 1, difference=0;
		boolean isGoodRange = true;
		
		while(isGoodRange){
			result = basicMultiplyTillWithChange(base, decimal, increment, 95);
			difference = result[1];
			boolean isLess = result[0] < base*.90, isMore = result[0] >= base*1.1;
			isGoodRange = !isLess && !isMore;
			if(!isGoodRange && isLess){
				increment += (increment*.90);
				isGoodRange = true;
			}else if(!isGoodRange && isMore){
				increment *=.90;
				isGoodRange = true;
			}else if(isGoodRange)
				break;
		}
		
		return result[0];
	}
	
	
	@Override
	public double tenRaiseTo(double exponent) {
		return basicRaiseTo(10, exponent); 
	}

	public static double basicRaiseTo(double base, double exponent){
		if(exponent == 0) return 1;
		if(base == 0) return base;
		double results = 1;
		while(exponent >= 1){
			results *= base;
			exponent -- ;
		}
		return results;
	}
	
	public static boolean hasDecimal(double number){
		String str = number + "";
		int dotLoc = str.indexOf(".");
		int len = str.length();
		String decimals = str.substring(dotLoc + 1, len);
		if(decimals.equals("0")) return false;
		return true;
	}
	
	public static double advancedRaisedTo(double number, double root){
		
		final double holder = number;
		
		if(number < 0 ) throw new IllegalArgumentException("Negative number ( " + number + " )");
		if(number == 0 || number == 1) return number;
		
		String numStr = (number + "");
		double decDivider = 1;
		int dotLoc = 0;
		boolean hasDecimal = number > 1 && numStr.contains("."); 
		
		if(hasDecimal){
			// Need to handle something like this : 1.0E9
			dotLoc = numStr.indexOf("."); 
			String decSubStr = numStr.substring(dotLoc+1, numStr.length());
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
	
	public static double negativeOf(double number){
		return -number;
	}
	
	public static boolean isFraction(double number){
		return number > 0 && number <1;
	}
	
	public static boolean isEven(double number){
		return number % 2 == 0;
	}
	
	public static boolean isOdd(double number){
		return number % 2 != 0;
	}
	
	public static void main(String[] args) {
		double [] result = basicMultiplyTillWithChange(1000, 2, 0.001, 98);
		double raisTo = basicRaiseTo(result[0], result[1]);
		System.out.println(basicMultiplyTillWithChange(1000, 2, 0.001, 98));
		System.out.println("RaisTo >> " + raisTo); 
	}

}
