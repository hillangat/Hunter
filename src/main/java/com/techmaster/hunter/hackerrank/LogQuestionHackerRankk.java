package com.techmaster.hunter.hackerrank;

public class LogQuestionHackerRankk {
	
	public static void main(String[] args) {
		System.out.println( new LogQuestionHackerRankk().replaceWord("WHERE TASK_ID=? AND TASK_NAME=?", "TASK_NAME=?", "'Kip'") );
	}
	
	
	public String replaceWord( String victim, String word, String value ) {
		int index = victim.indexOf( word );
		String part1 = victim.substring(0, index + word.length() - 1) ;
		String part2 = victim.substring(index + 1 + word.length() - 1, victim.length());
		return part1 + value + part2;
	}

}
