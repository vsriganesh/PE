package com.iiitb.utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.iiitb.blocks.Block;
import com.iiitb.cfg.Accfg;

public class MergeAccfg {

	// Perform topological sort . For now this is hardcoded
	// FP is set here
	public static Accfg merge(ArrayList<Block> blockList) {

		Accfg merged = null;
		List<String> fpList = null;
		Iterator iter = blockList.iterator();
		merged = new Accfg();
		fpList = new ArrayList<String>();
		while (iter.hasNext()) {

			Block block = (Block) iter.next();
			if (block.getAccfg().getInput().isEmpty()) {
				fpList.addAll(block.getAccfg().getFp());

			} else
				continue;

		}

		iter = blockList.iterator();
		while (iter.hasNext()) {
			Block block = (Block) iter.next();
			if (block.getName().equalsIgnoreCase(Constants.SUM)) {
				
				fpList.addAll(block.getAccfg().getFp());

			}

		}

		merged.setFp(fpList);

		// Call to set input and output
		merged = findInputOutputVariable(merged, blockList);

		//System.out.println(merged);
		return merged;

	}

	// Set input and output for merged ACCFG
	public static Accfg findInputOutputVariable(Accfg accfg,
			ArrayList<Block> blockList) {

		List<String> input = new ArrayList<String>();
		List<String> output = new ArrayList<String>();

		Iterator blockListIter = blockList.iterator();
		while (blockListIter.hasNext()) {
			Block block = (Block) blockListIter.next();
			input.addAll(block.getAccfg().getInput());
			output.add(block.getAccfg().getOutput());

		}
		
		//System.out.println(input);
		//System.out.println(output);
		

		Iterator inputIter = input.iterator();
		String inputVar = "";
		String outputVar = "";
		while (inputIter.hasNext()) {
			inputVar = (String) inputIter.next();
			Iterator outputIter = output.iterator();

			while (outputIter.hasNext()) {

				outputVar = (String) outputIter.next();

				if (inputVar.equalsIgnoreCase(outputVar)) {
					inputIter.remove();
					outputIter.remove();
				}

				break;

			}

		}

		accfg.setInput(input);
		accfg.setOutput(output.get(0));

		return accfg;
	}

}