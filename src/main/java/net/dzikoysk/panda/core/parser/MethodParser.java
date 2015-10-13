package net.dzikoysk.panda.core.parser;

import net.dzikoysk.panda.PandaScript;
import net.dzikoysk.panda.core.ElementsBucket;
import net.dzikoysk.panda.core.parser.util.MethodInfo;
import net.dzikoysk.panda.core.parser.util.MethodParserUtils;
import net.dzikoysk.panda.core.scheme.MethodScheme;
import net.dzikoysk.panda.core.scheme.ObjectScheme;
import net.dzikoysk.panda.core.syntax.Method;
import net.dzikoysk.panda.core.syntax.Parameter;
import net.dzikoysk.panda.core.syntax.Block;

public class MethodParser {

	private final PandaScript script;
	private final Block block;
	private final String source;

	public MethodParser(PandaScript script, Block block, String source){
		this.script = script;
		this.block = block;
		this.source = source;
	}

	public Method parse(){
		MethodInfo mi = MethodParserUtils.getMethodIndication(block, source);
		if(mi == null){
			System.out.println("[MethodParser] Indication failed");
			return null;
		}

		if(mi.isExternal()) {
			if(mi.isStatic()) {
				for (ObjectScheme os : ElementsBucket.getObjects()) {
					if(!os.getName().equals(mi.getPseudoclass())) continue;
					for (MethodScheme ms : os.getMethods()) {
						if (!ms.getName().equals(mi.getMethod())) continue;
						return new Method(null, block, mi.getMethod(), ms.getExecutable(), mi.getParameters());
					}
				}
			} else {
				Parameter instance = mi.getInstance();
				String type = instance.getDataType();
				if(type != null) {
					for(ObjectScheme os : ElementsBucket.getObjects()){
						if(!type.equals(os.getName())) continue;
						for(MethodScheme ms : os.getMethods()){
							if(!ms.getName().equals(mi.getMethod())) continue;
							return new Method(mi.getInstance(), block, mi.getMethod(), ms.getExecutable(), mi.getParameters());
						}
					}
				}
				return new Method(mi.getInstance(), block, mi.getMethod(), null, mi.getParameters());
			}
		} else {
			return new Method(script, block, mi.getMethod(), mi.getParameters());
		}

		return null;
	}

}
