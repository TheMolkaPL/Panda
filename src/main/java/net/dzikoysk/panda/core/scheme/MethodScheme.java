package net.dzikoysk.panda.core.scheme;

import net.dzikoysk.panda.core.syntax.IExecutable;

public class MethodScheme {

	private final String name;
	private final IExecutable executable;

	public MethodScheme(String name, IExecutable executable){
		this.name = name;
		this.executable = executable;
	}

	public IExecutable getExecutable() {
		return executable;
	}

	public String getName() {
		return name;
	}

}
