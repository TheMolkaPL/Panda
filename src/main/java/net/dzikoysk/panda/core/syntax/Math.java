package net.dzikoysk.panda.core.syntax;

import net.dzikoysk.panda.lang.PNumber;
import net.dzikoysk.panda.lang.PObject;
import net.dzikoysk.panda.util.MathBuilder;

import java.util.Stack;

public class Math implements IExecutable {

	private final MathBuilder mathBuilder;

	public Math(MathBuilder mathBuilder){
		this.mathBuilder = mathBuilder;
	}

	@Override
	public PObject run(Parameter instance, Parameter... parameters) {
		Stack<Double> values = new Stack<>();

		for(int i = 0; i < mathBuilder.size(); i++){
			MathBuilder.Type type = mathBuilder.next();
			if(type == MathBuilder.Type.OPERATOR){
				char operator = mathBuilder.getOperator();
				double t = values.pop();
				double d = values.pop();
				switch (operator){
					case '+':
						values.push(d + t);
						break;
					case '-':
						values.push(d - t);
						break;
					case '*':
						values.push(d * t);
						break;
					case '/':
						values.push(d / t);
						break;
					case '^':
						values.push(java.lang.Math.pow(d, t));
						break;
				}
			} else {
				Parameter parameter = mathBuilder.getParametr();
				Double value = Double.valueOf(parameter.getValue().toString());
				values.push(value);
			}
		}

		return new PNumber(values.pop());
	}

}
