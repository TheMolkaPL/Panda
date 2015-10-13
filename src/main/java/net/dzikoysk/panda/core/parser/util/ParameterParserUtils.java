package net.dzikoysk.panda.core.parser.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

public class ParameterParserUtils {

	public static String[] split(String source){
		Collection<String> parametrs = new ArrayList<>();
		StringBuilder node = new StringBuilder();
		Stack<Character> characters = new Stack<>();
		char[] chars = source.toCharArray();
		boolean string = false;

		for(int i = 0; i < chars.length; i++){
			char c = chars[i];

			if(c == '"'){
				string = !string;
				node.append(c);
				continue;
			} else if (string){
				node.append(c);
				continue;
			} else if(node.length() == 0 && Character.isWhitespace(c)){
				continue;
			}

			switch (c){
				case '(':
					characters.push(c);
					node.append(c);
					break;
				case ')':
					if(characters.size() != 0) characters.pop();
					node.append(c);
					break;
				case ',':
					if(characters.size() == 0){
						String parametr = node.toString();
						node.setLength(0);
						parametrs.add(parametr);
						break;
					}
				default:
					node.append(c);
					break;
			}
		}
		if(node.length() != 0){
			String parametr = node.toString();
			node.setLength(0);
			parametrs.add(parametr);
		}

		return parametrs.toArray(new String[parametrs.size()]);
	}

	public static boolean isMethod(String param){
		SyntaxIndication indi = new Recognizer().recognize(param);
		return indi == SyntaxIndication.RUNTIME;
	}

	public static boolean isMath(String param){
		boolean string = false;
		for(char c : param.toCharArray()){
			if(c == '"'){
				string = !string;
				continue;
			} else if(string){
				continue;
			}
			if(c == '+' || c == '-' || c == '*' || c == '/' || c == '^') return true;
		} return false;
	}


}
