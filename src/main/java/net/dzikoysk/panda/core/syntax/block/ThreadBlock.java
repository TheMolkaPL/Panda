package net.dzikoysk.panda.core.syntax.block;

import net.dzikoysk.panda.core.parser.CustomParser;
import net.dzikoysk.panda.core.parser.ParameterParser;
import net.dzikoysk.panda.core.parser.util.BlockInfo;
import net.dzikoysk.panda.core.scheme.BlockScheme;
import net.dzikoysk.panda.core.syntax.Block;
import net.dzikoysk.panda.core.syntax.Executable;
import net.dzikoysk.panda.core.syntax.Parameter;
import net.dzikoysk.panda.lang.PObject;
import net.dzikoysk.panda.lang.PThread;

public class ThreadBlock extends Block {

	static {
		new BlockScheme(ThreadBlock.class, "thread").parser(new CustomParser<Block>() {
			@Override
			public Block parse(BlockInfo blockInfo, Block current, Block latest) {
				current = new ThreadBlock();
				current.setParameters(new ParameterParser().parse(current, blockInfo.getParameters()));
				return current;
			}
		});
	}

	private PThread pThread;

	public ThreadBlock(){
		super.setName("ThreadBlock");
	}

	public PObject start(final Parameter... vars){
		final Block block = super.getBlock();
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				for(Executable e : block.getExecutables()) {
					e.run(vars);
				}
			}
		});
		PObject value = parameters[0].getValue();
		thread.setName(pThread.getName());
		thread.start();
		return null;
	}

	@Override
	public PObject run(final Parameter... vars){
		PObject value = parameters[0].getValue();
		if(value instanceof PThread) {
			pThread = (PThread) value;
			pThread.setBlock(this);
		}
		return null;
	}

}
