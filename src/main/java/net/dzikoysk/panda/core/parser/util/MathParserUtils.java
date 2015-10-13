package net.dzikoysk.panda.core.parser.util;

public class MathParserUtils {

	public static boolean compare(char prev, char current){
		return getOrder(prev) >= getOrder(current);
	}

	public static int getOrder(char c){
		int i = c == '*' || c == '/' || c == '^' ? 2 : 0;
		return i == 0 && (c == '+' || c == '-') ? 1 : i;
	}

}
