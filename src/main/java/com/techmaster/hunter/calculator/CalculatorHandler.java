package com.techmaster.hunter.calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.techmaster.hunter.exception.HunterRunTimeException;
import com.techmaster.hunter.util.HunterUtility;


public class CalculatorHandler {
	
	private static final char PRE_BRACKET_CHAR = '(';
	private static final char POST_BRACKET_CHAR = ')';
	private static final Logger log = Logger.getLogger(CalculatorHandler.class);
	private static final List<String> SYMBOL_STR = Arrays.asList(new String[]{"/","x","*","+","-"});
	
	public static CalculatorHandler getIntance(){
		return  new CalculatorHandler();
	}
	
	public static String harmonizeMultiplication(String inputs){
		inputs = inputs.replaceAll("x", "*");
		inputs = inputs.replaceAll("X", "*");
		return inputs;
	}
	
	public static String removeSpaces(String input){
		return input = input==null? null : input.replaceAll("\\s", "").trim();
	}
	
	public static boolean isNumericOrPoint(char charLoc){
		if(charLoc == '.' || Character.isDigit(charLoc))
			return true;
		return false;
	}
	
	public static String cleanBracProcessMess(String component){
		String minus = "*-";
		String plus = "*+";
		if(component.contains(plus)){
			component = component.replaceAll(plus, "*"); 
			log.info("Clean clean up waster >>>> " + component);
		}else if(component.contains(minus)){
			log.info("Clean clean up waster >>>> " + component);
			int index = component.indexOf(minus);
			int stopIndx1 = index;
			char currCh = '1';
			double part1 = 0d;
			boolean isPreNeg = false;
			// collect before
			while(stopIndx1 >=0 && Character.isDigit(currCh) ){
				stopIndx1--;
				currCh = component.charAt(stopIndx1);
			}
			isPreNeg = stopIndx1 == 0 ? false : component.charAt(stopIndx1) == '-'; 
			String part1Str = component.substring(stopIndx1+1, index);
			if(HunterUtility.isNumeric(part1Str))
				part1 = Double.valueOf(part1Str);
			else
				throw new NumberFormatException("Cleaning up bracket process for " + component + " part1 is non numeric >> " + part1Str);
			
			// collect after
			int stopIndx2 = index + 1;
			currCh = '1';
			double part2 = 0d;
			while(stopIndx2 <= component.length() && Character.isDigit(currCh) || stopIndx2 <= component.length() && (currCh == '.' || currCh == 'E') ){
				stopIndx2++;
				if(stopIndx2 >= component.length())
					break;
				currCh = component.charAt(stopIndx2);
			}
			
			String part2Str = component.substring(index+2, stopIndx2 > component.length() ? stopIndx2-1 : stopIndx2);
			if(HunterUtility.isNumeric(part2Str))
				part2 = Double.valueOf(part2Str);
			else
				throw new NumberFormatException("Cleaning up bracket process for " + component + " part1 is non numeric >> " + part2Str);
			
			String finalStr = String.valueOf(part1 * part2);
			if(isPreNeg)
				component = replaceCharAt(component, stopIndx1 , '+');
			String cPart1 = component.substring(0, stopIndx1+1); 
			cPart1 += finalStr;
			String cPart2 = component.substring(stopIndx2, stopIndx2 > component.length() ? stopIndx2-1 : component.length());
			if(stopIndx2 >= component.length())
				return cPart1;
			finalStr =  cPart1 + cPart2;
			return finalStr;
		}
		
		return component;
	}
	
	
	
	public static String getPreAndPostNumbersAndProcess(String input, String symbol){
		
		input = removeSpaces(input);
		log.info("Obtaining processable blocks for the input string : " + input + " and the input symbol = " + symbol); 
		
		char[] characters = input.toCharArray();
		char[] startChar = null;
		char[] endChar = null;
		char symbolChar = symbol.toCharArray()[0];
		
		for(int i=0; i<characters.length; i++){
			char thisChar = characters[i];
			if(thisChar == symbolChar){
				// get location before
				int preLoc = i-1;
				int startIndex = 0;
				int endIndex = 0;
				boolean symbolFound = false;
				boolean isPositiveNumeric = (preLoc >=0 && preLoc < characters.length && HunterUtility.isNumeric(characters[preLoc])) || (preLoc >=0 && preLoc < characters.length && characters[preLoc] == '.');
				
				while(isPositiveNumeric){
					startIndex = preLoc;
					preLoc -- ;
					isPositiveNumeric = (preLoc >=0 && HunterUtility.isNumeric(characters[preLoc]) && Integer.valueOf(characters[preLoc]).intValue() >= 0);
					if(!isPositiveNumeric && preLoc >= 0 && preLoc < characters.length && !HunterUtility.isNumeric(characters[preLoc]) && characters[preLoc] == '.')
						isPositiveNumeric = true;
					symbolFound = true;
					if(!isPositiveNumeric){
						int size = i-(preLoc + 1);
						int startPoint = preLoc + 1;
						int endPoint = i-1;
						startChar = new char[size];
						for(int c = startPoint; c <= endPoint ;c++){
							int inPosition = c - startPoint;
							startChar[inPosition] = input.charAt(c);
						}
					}
				}
				log.info("Obtained the following starting numbers : " + (new String(startChar)));
				// get location after.
				if(symbolFound){
					preLoc = i + 1;
					isPositiveNumeric = HunterUtility.isNumeric(characters[preLoc]) && Integer.valueOf(characters[preLoc]).intValue() >= 0 || characters[preLoc] == '.';
					while(isPositiveNumeric){
						endIndex = preLoc;
						preLoc ++ ;
						isPositiveNumeric = (preLoc >=0 && preLoc < characters.length && HunterUtility.isNumeric(characters[preLoc]) && Integer.valueOf(characters[preLoc]).intValue() >= 0);
						if(!isPositiveNumeric && preLoc >= 0 && preLoc < characters.length && !HunterUtility.isNumeric(characters[preLoc]) && characters[preLoc] == '.')
							isPositiveNumeric = true;
						if(!isPositiveNumeric){
							int size = (preLoc - 1) - i;
							int startPoint = i + 1;
							int endPoint = preLoc - 1;
							endChar = new char[size];
							for(int c = startPoint; c <= endPoint ;c++){
								int inPosition = c - startPoint;
								endChar[inPosition] = input.charAt(c);
							}
						}
					}
					
					log.info("Obtained the following ending numbers : " + (new String(endChar)));
					
					double beforeNum = Double.valueOf(new String(startChar)).doubleValue();
					double afterNum = Double.valueOf(new String(endChar)).doubleValue();
					String processedSection = process(input, beforeNum,afterNum, symbol) + "";
					String toReplace = input.substring(startIndex, endIndex+1);
					
					input = input.replace(toReplace, processedSection);
					log.info("processed section = " + processedSection);
					
					return input;
					
				}
			}
		}
		return null;
		
	}
	
	public static String replaceCharAt(String component, int index, char replacement ){
		log.info("index >> " + index + " replacement >> " + replacement + " component before replacement >> " + component); 
		String part1 = component.substring(0, index); 
		part1 += replacement;
		String part2 = component.substring(index+1, component.length());
		String replaced = part1 + part2;
		log.info("component after replacement " + replaced); 
		return replaced;
	}
	
	private static double process(String input, double beforeNum, double afterNum, String symbol) {
		Calculator calculator = new CalculatorImpl();
		symbol = symbol.trim();
		if(symbol.equals("*") || symbol.equals("x"))
			return calculator.multiply(beforeNum, afterNum);
		if(symbol.equals("+"))
			return calculator.add(beforeNum, afterNum);
		if(symbol.equals("-"))
			return calculator.subtract(beforeNum, afterNum);
		if(symbol.equals("/"))
			return calculator.divide(beforeNum, afterNum);
		return 0;
	}

	
	public static List<Bracket> getBracketUnits(String inputs){
		
		inputs = removeSpaces(inputs);
		List<Bracket> brackets = new ArrayList<>();
		
		for(int i=0; i<inputs.length();i++){
			if(inputs.charAt(i) == PRE_BRACKET_CHAR){
				Bracket bracket =  getIntance().new Bracket();
				bracket.startIndx = i;
				bracket.type = PRE_BRACKET_CHAR; 
				brackets.add(bracket);
			}
			if(inputs.charAt(i) == POST_BRACKET_CHAR){
				if(brackets.size() > 0){
					for(int s = brackets.size() - 1; s >= 0; s-- ){
						Bracket bracket = brackets.get(s);
						if(bracket.type == PRE_BRACKET_CHAR && bracket.endIndx == 0){
							bracket.endIndx = i;
							String component = inputs.substring(bracket.startIndx, bracket.endIndx +1);
							bracket.component = component;
							log.info(bracket.component); 
							break;
						}
					}
				}
			}
			
		}
		log.info("Setting parent and children");
		setUnitsParenstAndChildren(brackets);
		/*cleanChilren(brackets);*/
		return brackets;
	}
	
	public static List<Bracket> getParents(List<Bracket> brackets){
		List<Bracket> parents = buildParentFromChildren(brackets);
		log.info(">>>>> Printing all bracket units obtained."); 
		for(Bracket b : brackets){
			b.isParent = isParent(b); 
			log.info(b);
		}
		return parents;
	}
	
	
	public static String processBracketUnits(List<Bracket> bracketUnits, String input){
		
		String result = null;
		int lenLoss = 0;
		int lenGain = 0;
		boolean isLost = false;
		boolean isGained = false;
		String inputHolder = input;
		int brcktStrtIndx = 0;
		int brcktEndIndx = 0;
		
		for(int i=bracketUnits.size() - 1; i >= 0; i--){
			Bracket brack = bracketUnits.get(i);
			brcktStrtIndx = brack.startIndx;
			brcktEndIndx = brack.endIndx; 
			Bracket prevBracket = null;
			if(i < bracketUnits.size() - 1)
				prevBracket = bracketUnits.get(i+1); 
			log.info(" Bracket before any processes = " + brack);
			String component = brack.component;
			
			if(!brack.processed){
				component = getBracketTrimmedUnit(component);
			}
			component = processCleanUnit(component);
			log.info("ProcessComponent for " + brack.component  + " = " + component); 
			brack.component = component;
			brack.processed = true;
			log.info(" Bracket after all processes = " + brack);
			
				if (!isParent(brack)) {
					int rlStrtIndx = 0;
					int rlEndIndx = 0;
					boolean isOffRange = (prevBracket != null && prevBracket.startIndx > brcktStrtIndx && prevBracket.endIndx > brcktEndIndx);
					if(isOffRange){
						rlStrtIndx = brack.startIndx; 
						rlEndIndx = brack.endIndx;
					}else if( !isOffRange && (isLost || isGained)){
						if (isGained) {
							rlStrtIndx = (brack.startIndx) + lenGain;
							rlEndIndx = (brack.endIndx) + lenGain;
						} else if (isLost) {
							rlStrtIndx = (brack.startIndx) - lenLoss;
							rlEndIndx = (brack.endIndx) - lenLoss;
						}
					}else{
						rlStrtIndx = brack.startIndx; 
						rlEndIndx = brack.endIndx;
					}
					String currentHolder = inputHolder;
					String part1 = inputHolder.substring(0, rlStrtIndx);
					String part2 = inputHolder.substring(rlEndIndx + 1,inputHolder.length());
					part1 += component;
					inputHolder = part1 + part2;
					result = inputHolder;
					isLost = inputHolder.length() < currentHolder.length();
					isGained = inputHolder.length() > currentHolder.length();
					if (isLost) {
						lenLoss = currentHolder.length() - inputHolder.length();
						isGained = false;
					}
					if (isGained) {
						lenGain = inputHolder.length() - currentHolder.length();
						isLost = false;
					}
					
				} else {
					log.warn("This child is parent and it has a parent.");
				}
			}
		log.info("Parents and original input results after manipulation >> " + result); 
		result = processCleanUnit(result);
		return result;
	}
	
	public static String processCleanUnit(String component) {
		component = replaceReplaceables(component);
		component = cleanBracProcessMess(component);
		boolean hasWildSymbols = hasWildSymbols(component);
		for(String symbol : extractOrderedSymbols(component)){
			while(component.contains(symbol) && hasWildSymbols){
				char firstChar = component.charAt(0);
				if(!Character.isDigit(firstChar)){
					if(firstChar != '-' && firstChar != '+'){
						throw new IllegalArgumentException("Invalid component >>> " + component);
					}else{
						if(firstChar == '+'){
							component.replaceFirst("\\+", "");
						}else if(firstChar == '-'){
							//count the number of negatives
							int count = 0;
							boolean isAllNeg = true;
							for(char ch:component.toCharArray()){
								if(!Character.isDigit(ch)){
									count++;
									if(ch != '-' && ch !='.')
										isAllNeg = false;
								}
							}
							if(count == 1){
								return component;
							}else{
								if(isAllNeg){
									component = component.replaceFirst("\\-", "");
									component = component.replaceAll("\\-", "+");
									while(component.contains("+")){
										component = getPreAndPostNumbersAndProcess(component, "+");
										hasWildSymbols = hasWildSymbols(component);
									}
									return "-" + component; // make it a negative number
								}else{
									/* 
									 * thinking about how to handle this.
									 * this is a situation like this -5.0236-6.36+6.369 as opposed to the above  -5.0236-6.36-6.369
									 * It looks like it's very rare to get into this situation given how we got into the main 'if' of this section. 
									*/
								}
							}
						}
					}
				}
				component = getPreAndPostNumbersAndProcess(component, symbol);
				hasWildSymbols = hasWildSymbols(component);
			}
			
			boolean isSignedNumeric = true;
			if(!HunterUtility.isNumeric(component)){
				char chCh = component.charAt(0);
				isSignedNumeric = chCh == '+' || chCh == '-' && HunterUtility.isNumeric(component.substring(1, component.length()));
			}
			
			if(!hasWildSymbols && !HunterUtility.isNumeric(component)){
				
				if(isSignedNumeric)
					log.info(component + " isSignedNumeric = " + isSignedNumeric + " so it's doing nothing");
				else{
					
					log.info("Component without wild symbols >>>> " + component);
					String commaComp = component;
					char comma = ',';
					
					char [] chArray = commaComp.toCharArray();
					int len = chArray.length;
					
					for(int i=0; i < len; i++){
						if((chArray[i]=='-' || chArray[i]=='+') && chArray[i-1] != ',' && i>0){
							commaComp = insertCharaterfterIndex(commaComp, i-1, comma);
							chArray = commaComp.toCharArray();
							len = chArray.length;
						}
					}
					
					String[] negatives = null;
					String[] positives = null;
					String[] split = commaComp.split(comma+""); 
					
					for(String comp : split){
						if(comp.startsWith("-"))
							negatives = HunterUtility.initArrayAndInsert(negatives, comp);
						else
							positives = HunterUtility.initArrayAndInsert(positives, comp);
					}
					
					double negative = 0;
					if (negatives != null && negatives.length > 0)
						for (String num : negatives)
							negative += Double.valueOf(num);
					
					double positive = 0;
					if (positives != null && positives.length > 0)
						for (String num : positives)
							positive += Double.valueOf(num);
					
					double finl = negative + positive;
					component = Double.toString(finl);
					log.info("Component After wild symbols processing >>>> " + component);
					
				}
			}
		}
		return component;
	}
	
	
	public static boolean hasWildSymbols(String component){
		char [] wildSymbols = new char[]{'/','*'};
		for(char c : wildSymbols)
		for(char ch : component.toCharArray()){
			ch = ch=='X' || ch=='x' ? '*':ch;
			if (ch == c)
				return true;
		}
		return false;
	}
	
	public static String replaceReplaceables(String component){
		component = component.replaceAll("\\+\\-", "\\-");
		component = component.replaceAll("\\-\\+", "\\-");
		component = component.replaceAll("\\-\\-", "\\+");
		component = component.replaceAll("\\+\\+", "\\+");
		return component;
	}
	
	public static String cleanUpBrackets(String component){
		
		char[] chArry = component.toCharArray();
		int arryLen = chArry.length;
		String holder = component;
		int preCount = 0;
		int postCount = 0;
		
		for(int i=0; i<arryLen; i++){
			if(i < arryLen - 1){
				char currCh = chArry[i];
				postCount = currCh == POST_BRACKET_CHAR ? postCount + 1: postCount;
				preCount = currCh == PRE_BRACKET_CHAR ? preCount + 1 : preCount;
				char aftrCh = chArry[i+1];
				if(aftrCh == PRE_BRACKET_CHAR){
					if(HunterUtility.isNumeric(currCh) || (currCh == POST_BRACKET_CHAR && aftrCh == PRE_BRACKET_CHAR)){
						holder = insertCharaterfterIndex(holder, i, '*');
						chArry = holder.toCharArray();
						arryLen = chArry.length; 
					}
				}
			}
		}
		
		if(preCount != postCount)
			throw new IllegalArgumentException("Pre and post brackets are not equal. Pre >> " + preCount + " Post >> " + postCount);
		
		return holder;
	}
	
	public static String insertCharaterfterIndex(String input, int afterIndex, char ch){
		int len = input.length();
		String part1 = input.substring(0, afterIndex+1);
		String part2 = input.substring(afterIndex+1, len);
		log.info("inserting character at " + (afterIndex + 1) + " of input >> " + input + " part1 = " + part1 + " part2 = " + part2); 
		part1 += ch;
		String finlStr = part1 + part2;
		log.info("Final >>  " + finlStr);
		return finlStr;
	}
	
	public static String[] extractOrderedSymbols(String unit){
		unit = removeSpaces(unit);
		char inputChar[] = unit.toCharArray();
		String toReturn[] = null;
		for (char ch : inputChar) {
			if (SYMBOL_STR.contains(ch + "")) {
				if (ch == 'x' || ch == 'X')
					ch = '*';
				boolean isAlreadyLoaded = false;
				if (toReturn != null) {
					for (String loadedSymbol : toReturn) {
						if (loadedSymbol.equals(ch + "")) {
							isAlreadyLoaded = true;
						}
					}
				}
				if (isAlreadyLoaded) continue;
				toReturn = HunterUtility.initArrayAndInsert(toReturn, ch + "");
			}
		}
		
		String[] ordered = new String[toReturn.length];
		int count = 0;
		
		for(String symbol : SYMBOL_STR){
			for(String to : toReturn ){
				if(symbol.equals(to)){
					ordered[count] = to;
					count++;  
				}
			}
		}
		
		log.debug("symbols found in ordered series = " + Arrays.toString(ordered)); 
		
		return ordered;
		
	}
	
	public static String handleOperations(String input){
		input = removeSpaces(input);
		input = cleanUpBrackets(input);
		List<Bracket> bracketUnits = getBracketUnits(input);
		List<Bracket> blocks = markBlocks(bracketUnits);
		for(Bracket bracket : blocks){
			setBlockHierarchy(bracket);
			processBlock(bracket);
		}
		for(int i=blocks.size()-1;i>=0;i--){
			log.info("Input >>>> " + input);
			Bracket block = blocks.get(i); log.info(">>>>>>>> processing block with component >> " + block.component);
			String part1 = input.substring(0, block.startIndx); log.info(">>>>>>>> split the block with part1 >> " + part1);
			String part2 = input.substring(block.endIndx+1, input.length());log.info(">>>>>>>> split the block with part2 >> " + part2);
			cleanChild(block);
			String blockResult = block.component; log.info(">>>>>>>> Processed block component. Results  >>> " + blockResult);
			String fnlStr = part1 + blockResult + part2;
			input = fnlStr;
			log.info(">>>>>>>> Input after processing. Results  >>> " + input);
		}
		
		input = processCleanUnit(input);log.info(">>>>>>>> Final answer  >>> " + input);
		return input;
	}
	
	public static void main(String[] args) {
	
		String input = "6*(3+5 + 6(2*5*(5+6-9*8)))+4";
		//String input = "2*4*(3+5 + 6 * (2*5) * (100+5950) * 9) + 2*4*";
		long time = System.currentTimeMillis();
		String answer = handleOperations(input);
		time = System.currentTimeMillis() - time;
		System.out.println("time >>>> " + time);
		log.info("answer >>> " + answer); 
		
		/*markBlocks(bracketUnits);
		List<Bracket> children = new ArrayList<>();
		Bracket _2 = bracketUnits.get(2);
		Bracket _1 = bracketUnits.get(1);
		Bracket _0 = bracketUnits.get(0);
		log.info("id = " + _2.getId() + " startIndex = " + _2.startIndx);
		log.info("id = " + _1.getId() + " startIndex = " + _1.startIndx);
		log.info("id = " + _0.getId() + " startIndex = " + _0.startIndx);
		children.add(_2);
		children.add(_1);
		children.add(_0);
		Bracket parent = bracketUnits.get(0);
		parent.setChildren(children);
		replaceGeneration(parent); 
		
		List<Bracket> parents = getParents(bracketUnits);
		String bracketProcessOutput = processBracketUnits(parents, input);
		System.out.println(bracketProcessOutput);
		*///System.out.println(hasWildSymbols(input)); 
	}
	
	
	public static String getBracketTrimmedUnit(String component){
		char start = component.charAt(0);
		char end = component.charAt(component.length() -1 );
		if(start == PRE_BRACKET_CHAR && end == POST_BRACKET_CHAR){
			component = component.substring(1,component.length());  
			component = component.substring(0, component.length()-1);
		}
		return component;
	}
	
	public static boolean isParent(Bracket bracket){
		String component = bracket.component;
		component = getBracketTrimmedUnit(component);
		boolean isParent = component.contains("(") || component.contains(")");
		return isParent;
	}
	
	public static void setUnitsParenstAndChildren(List<Bracket> brackets){
		for(int i=0; i<brackets.size(); i++){
			Bracket bracket = brackets.get(i);
			int startIndx = bracket.startIndx;
			int endIndx = bracket.endIndx;
			for(Bracket brack : brackets){
				if(brack.startIndx > startIndx && brack.endIndx < endIndx && brack.parentId == 0){
					brack.setChild(true);
					brack.setParentId(bracket.getId());
					bracket.isParent = true;
					bracket.parents.add(brack);
					brack.children.add(bracket);
					if(!bracket.children.contains(brack))
						bracket.children.add(brack);
				}
			}
		}
		
	}

   private static void cleanChild(Bracket child){
	   if(child.processed)
		   return;
	   String component = child.component; 
		component = getBracketTrimmedUnit(component);
		String result = processCleanUnit(component);
		child.component = result;
		child.processed = true;
   }
   
  public static Bracket processBlock(Bracket block){
	  List<Bracket> blockChildren = block.children;
	  while(isParent(block)){
		  List<Bracket> rawAtomic = getRawAtomic(blockChildren); 
		  if(rawAtomic.isEmpty())
			  log.info("raw atomic is empty");
		  rawAtomic = sortBracketsByStartIndex(rawAtomic);
		  HunterUtility.logList(rawAtomic);
		  for(int i= rawAtomic.size()-1; i >= 0; i--){
			  Bracket atomic = rawAtomic.get(i);
			  if(isParent(atomic))
				  throw new HunterRunTimeException("Atomic presented is not atomic >>> " + atomic);
			  String atomicResult = processCleanUnit(getBracketTrimmedUnit(atomic.component)); 
			  log.info("Processed clean unit for atomic >>> " + atomic);
			  log.info("Results of processed atomic = " + atomicResult);
			  log.info("Replacing atomic in the parent component >> " + block.component);
			  replaceLowestChildInclosestParent(atomic, block); 
		  }
	  }
	  return block;
  }
   
   public static List<Bracket> getRawAtomic(List<Bracket> blockChildren) {
	   if(blockChildren == null ||blockChildren.isEmpty()) return blockChildren;
	   List<Bracket> rawAtomic = new ArrayList<>();
	   for(Bracket brac : blockChildren){
		   if(!isParent(brac) && !brac.isReplacedInParent)
			   rawAtomic.add(brac); 
	   }
	return rawAtomic;
}

public static List<Bracket> sortBracketsByStartIndex(List<Bracket> brackets){
	   Object[] sortedBrackets = (Object[])brackets.toArray();
	   for(int i=0; i<sortedBrackets.length; i++){
		   for(int j=0; j<sortedBrackets.length; j++){
			   Bracket jBrack = (Bracket)sortedBrackets[j];
			   if(((Bracket)sortedBrackets[i]).startIndx < jBrack.startIndx){
				   Bracket holder = (Bracket)sortedBrackets[i];
				   sortedBrackets[i] = sortedBrackets[j];
				   sortedBrackets[j] = holder;
			   }
				   
		   }
	   }
	   brackets  = new ArrayList<>();
	   for(Object obj : sortedBrackets){
		  Bracket brac = (Bracket)obj;
		  brackets.add(brac);
		  log.info("id = " + brac.getId() + " startIndex = " + brac.startIndx); 
	   }
	   return brackets;
   }
   
  /* public static void replaceGeneration(Bracket parent){
	   //(3+5 + 6 * (2*5) * (100+5950) * 9)
	   List<Bracket> children = sortBracketsByStartIndex(parent.getChildren()); 
	   for(int i=children.size() - 1; i>=0; i--){
		   Bracket child = children.get(i);
		   replaceLowestChildInclosestParent(child, parent); 
	   }
   }*/
   
   public static List<Bracket> markBlocks(List<Bracket> brackets){
	   List<Bracket>  blocks = new ArrayList<>();
	   for(Bracket bracket : brackets){
		   brac:for(Bracket brac : brackets){
			   if(bracket.startIndx > brac.startIndx && bracket.endIndx < brac.endIndx){
				   bracket.isBlock = false; 
				   continue brac;
			   }
		   }
	   }
	   
	   for(Bracket brac : brackets){
		   if(brac.isBlock){
			   blocks.add(brac);
			   log.info("isBlock  >>>>> " + brac.component);
		   }
	   }
	   
	   return blocks;
	   
   }
   
   public static List<Bracket> setBlockHierarchy(Bracket block){
	   List<Bracket> blockChildren = block.children;
	   Object[] sortedBlockChldren = (Object[])blockChildren.toArray();
	   for(int i=0; i<sortedBlockChldren.length; i++){
		   for(int j=0; j<sortedBlockChldren.length; j++){
			   Bracket jBrack = (Bracket)sortedBlockChldren[j];
			   if(((Bracket)sortedBlockChldren[i]).parents.size() < jBrack.parents.size()){
				   Bracket holder = (Bracket)sortedBlockChldren[i];
				   sortedBlockChldren[i] = sortedBlockChldren[j];
				   sortedBlockChldren[j] = holder;
			   }
				   
		   }
	   }
	   blockChildren  = new ArrayList<>();
	   for(Object obj : sortedBlockChldren){
		  Bracket brac = (Bracket)obj;
		  blockChildren.add(brac);
		  log.info("id = " + brac.getId() + " start index = " + brac.startIndx + " parents # " + brac.parents.size() + " component = " + brac.component); 
	   }
	   return blockChildren;
   }
   
   
	public static void replaceLowestChildInclosestParent(Bracket child, Bracket parent){
		// (3+5 + 6(2*5)(20/5*3+10-5(100+5950-(100+5950))) * 9)
		String cComp = child.component;
		String oComp = cComp;
		String pComp = parent.component;
		log.info("replaceInParent replacing child comp = " + cComp + " in the parent comp = " + pComp);
		
		if(!isParent(child)){
			cleanChild(child); 
			cComp = child.component;
			
			int normStrtIndx = (child.startIndx - child.startNormalizer) - parent.startIndx; // 27 should be 19
			int normEndIndx = (child.endIndx - child.endNormalizer ) - parent.startIndx; // 53 should be 37
			
			StringBuilder builder = new StringBuilder();
			builder.append(pComp.substring(0, normStrtIndx));
			builder.append(cComp); log.info("normEndIndx + 1 = " + (normEndIndx+1) + " pComp length >> " + pComp.length() + " pComp >>>>> " + pComp);
			builder.append(pComp.substring(normEndIndx+1, pComp.length())); //
			
			String processed = builder.toString();
			log.info("replaceInParent after replacing child comp = " + cComp + " in the parent comp = " + pComp + " >>>>> results >>>> " + processed);
			parent.component = processed;
			child.setReplacedInParent(true);
			
			/* 
				find the brackets whose end indices come after the current child.
		 		find the brackets whose start index are greater than the end index of the current child.
		 	*/
			
			int lenChng = oComp.length() - cComp.length();
			parent.endNormalizer += lenChng;
			
			List<Bracket> blockChildren = parent.children;
			
			int diffHolder = 0;
			int index = 0;

			for(int i=0; i<blockChildren.size(); i++){
				Bracket cInput = blockChildren.get(i);
				if(isChildOf(child, cInput)){
					cInput.endNormalizer += lenChng;
					int difference = child.startIndx - cInput.startIndx;
					if(difference >=0 && diffHolder < difference){
						diffHolder = difference;
						if(i!=0)
							index = i;
					}
				}else if(cInput.startIndx > child.endIndx){
					cInput.startNormalizer += lenChng;
					cInput.endNormalizer += lenChng;
				}
				if(i == blockChildren.size()-1 && diffHolder != 0){
					Bracket closesParent = blockChildren.get(index);
					log.info("Child component >>> " + oComp + " and <<closest>> parent component >>> " + closesParent.component);
					String clstComp = closesParent.component;
					int cNormStrtIndx = (child.startIndx + child.startNormalizer) - closesParent.startIndx;
					int cNormEndIndx = (child.endIndx + child.endNormalizer ) - closesParent.startIndx;
					StringBuilder cBuilder = new StringBuilder();
					cBuilder.append(clstComp.substring(0, cNormStrtIndx));
					cBuilder.append(cComp);
					cBuilder.append(clstComp.substring(cNormEndIndx+1, clstComp.length()));
					closesParent.component= cBuilder.toString();
					log.info("Closest parent component after replacement >>> " + closesParent);
				}
			}
			
		}else{
			throw new HunterRunTimeException("Replace in parent but the child is parent >>> " + child); 
		}
		
	}
	
	public static boolean isChildOf(Bracket child, Bracket parent){
		return (child.startIndx > parent.startIndx && child.endIndx < parent.endIndx);
	}
	
	public static List<Bracket> buildParentFromChildren(List<Bracket> brackets){
		List<Bracket> parents = new ArrayList<>();
		for(Bracket bracket : brackets){
			String parentComponent = bracket.component;
			int lenLoss = 0;
			int lenGain = 0;
			boolean isLost = false;
			boolean isGained = false;
			int startIndx = bracket.startIndx;
			if(bracket.getParentId() == 0){
				List<Bracket> children = bracket.getChildren();
				for(Bracket brack : children){ 
					if(brack.getParentId() == bracket.getId() && brack.isReplacedInParent == false && isParent(bracket)){ 
						String childComponent = brack.component;
						brack.isReplacedInParent = true;
						if(!isParent(brack)){
							// 5+5-6-(4.45-5)+4+4
							int rlStrtIndx = 0;
							int rlEndIndx = 0;
							
							int normalStrtIndx = brack.startIndx - startIndx;
							int normalEndIndx = brack.endIndx - startIndx;
							
							if(brack.isIndexAffected()){
								if(isGained){
									rlStrtIndx = normalStrtIndx + lenGain;
									rlEndIndx = normalEndIndx + lenGain;
								}else if(isLost){
									rlStrtIndx = normalStrtIndx - lenLoss;
									rlEndIndx = normalEndIndx - lenLoss;
								}
							}else{
								rlStrtIndx = normalStrtIndx;
								rlEndIndx = normalEndIndx;
							}
							
							String parentComponentHolder = parentComponent;//log.info("parentComponent = " + parentComponent + " Start index = " + rlStrtIndx);
							if(parentComponent.length() < rlStrtIndx){
								log.info(brack);
								log.info(bracket);
							}
								
							String part1 = parentComponent.substring(0, rlStrtIndx);  
							String part2 = parentComponent.substring(rlEndIndx + 1, parentComponent.length());
							part1 += childComponent;
							parentComponent = part1 + part2;
							
							bracket.component = parentComponent;
							
							isLost = parentComponentHolder.length() > parentComponent.length();
							isGained = parentComponentHolder.length() < parentComponent.length();
							
							if(isLost) lenLoss = parentComponentHolder.length() - parentComponent.length();
							if(isGained) lenGain = parentComponent.length() + parentComponentHolder.length();
							
							if(isLost || isGained){
								setIsAffacted(brack, children, false);
							}else{
								setIsAffacted(brack, children, true);
							}
							
						}else{
							log.warn("This child is parent and it has a parent."); 
						}
					}
				}

				parents.add(bracket);
				
				isLost = false;
				isGained = false;
				lenGain = 0;
				lenLoss = 0;
				
			}
		}
		return parents;
	}
	
	public static void setIsAffacted(Bracket brack, List<Bracket> brackets, boolean reset){
		long parent = brack.getParentId();
		int strtIndx = brack.startIndx;
		int endIndx = brack.endIndx;
		for(Bracket bracket : brackets){
			if(bracket.getParentId() != 0 && bracket.getParentId() == parent){
				if(reset){
					bracket.isIndexAffected = false;
				}else{
					if(strtIndx < bracket.startIndx && endIndx < bracket.endIndx){
						bracket.isIndexAffected = true;
					}else{
						bracket.isIndexAffected = false;
					}
				}
			}
		}
	}
	
	private static long counter = 0L;
	
	private class Bracket {
		
		private char type;
		private int startIndx;
		private int endIndx = 0;
		private String component;
		private boolean processed = false;
		private boolean isParent = false;
		private boolean isChild = false;
		private boolean isReplacedInParent = false;
		private long id;
		private long parentId;
		private boolean isIndexAffected = false;
		private boolean isBlock = true;
		
		private int endNormalizer = 0;
		private int startNormalizer = 0;
		
		private boolean isMultiParent = false;
		private List<Bracket> children = new ArrayList<>();
		private List<Bracket> parents = new ArrayList<>();
		
		public Bracket() {
			super();
			counter++;
			this.id = counter;
		}
		
		public void setReplacedInParent(boolean isReplacedInParent) {
			this.isReplacedInParent = isReplacedInParent;
		}
		public void setChild(boolean isChild) {
			this.isChild = isChild;
		}

		public long getId() {
			return id;
		}

		public long getParentId() {
			return parentId;
		}

		public void setParentId(long parentId) {
			this.parentId = parentId;
		}

		public boolean isIndexAffected() {
			return isIndexAffected;
		}

		public List<Bracket> getChildren() {
			return children;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((children == null) ? 0 : children.hashCode());
			result = prime * result
					+ ((component == null) ? 0 : component.hashCode());
			result = prime * result + endIndx;
			result = prime * result + (int) (id ^ (id >>> 32));
			result = prime * result + (isBlock ? 1231 : 1237);
			result = prime * result + (isChild ? 1231 : 1237);
			result = prime * result + (isIndexAffected ? 1231 : 1237);
			result = prime * result + (isMultiParent ? 1231 : 1237);
			result = prime * result + (isParent ? 1231 : 1237);
			result = prime * result + (isReplacedInParent ? 1231 : 1237);
			result = prime * result + (int) (parentId ^ (parentId >>> 32));
			result = prime * result
					+ ((parents == null) ? 0 : parents.hashCode());
			result = prime * result + (processed ? 1231 : 1237);
			result = prime * result + startIndx;
			result = prime * result + type;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Bracket other = (Bracket) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (children == null) {
				if (other.children != null)
					return false;
			} else if (!children.equals(other.children))
				return false;
			if (component == null) {
				if (other.component != null)
					return false;
			} else if (!component.equals(other.component))
				return false;
			if (endIndx != other.endIndx)
				return false;
			if (id != other.id)
				return false;
			if (isBlock != other.isBlock)
				return false;
			if (isChild != other.isChild)
				return false;
			if (isIndexAffected != other.isIndexAffected)
				return false;
			if (isMultiParent != other.isMultiParent)
				return false;
			if (isParent != other.isParent)
				return false;
			if (isReplacedInParent != other.isReplacedInParent)
				return false;
			if (parentId != other.parentId)
				return false;
			if (parents == null) {
				if (other.parents != null)
					return false;
			} else if (!parents.equals(other.parents))
				return false;
			if (processed != other.processed)
				return false;
			if (startIndx != other.startIndx)
				return false;
			if (type != other.type)
				return false;
			return true;
		}

		private CalculatorHandler getOuterType() {
			return CalculatorHandler.this;
		}

		@Override
		public String toString() {
			return "Bracket [type=" + type + ", startIndx=" + startIndx
					+ ", endIndx=" + endIndx + ", component=" + component
					+ ", processed=" + processed + ", isParent=" + isParent
					+ ", isChild=" + isChild + ", isReplacedInParent="
					+ isReplacedInParent + ", id=" + id + ", parentId="
					+ parentId + ", isIndexAffected=" + isIndexAffected
					+ ", isBlock=" + isBlock + ", endNormalizer="
					+ endNormalizer + ", startNormalizer=" + startNormalizer
					+ ", isMultiParent=" + isMultiParent +  "]";
		}
		
		 public void printInt( int[] integers ) {
			  if ( integers == null || integers.length == 0 )	
				return;
			  for( int i=0; i<integers.length; i++ ) {
			     int value = integers[i];
			     if ( value  % 2 != 0 && value % 3 != 0 )
			    	 System.out.println( "Odd number" );
			     if ( value % 3 == 0 && value % 2 != 0 )
			    	 System.out.println( "Multiple of 3" );
			     if ( ( value % 2 %  3 ) != 0  )
					System.out.println( "Odd number multiple of 3" );
			  }
			}

		
		
	}
	
	
	

}
