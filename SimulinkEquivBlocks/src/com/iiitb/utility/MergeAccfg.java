package com.iiitb.utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.iiitb.blocks.Block;
import com.iiitb.cfg.Accfg;
import com.iiitb.sort.TopologicalSort;

import expression.Expression;
import expression.Variable;

public class MergeAccfg {

	// Perform topological sort . For now this is hardcoded
	// FP is set here
	public static Accfg merge(ArrayList<Block> blockList) {

		Accfg merged = null;
		List<Expression> fpList = new ArrayList<Expression>();
		List<Expression> initList = new ArrayList<Expression>();
		/* Created a new list and copied all values from blockList. This is required since we remove blockList entries during iteration.
		 * We pass "blockListToPass" to  findInputOutputVariable method
		 */
		List<Block> blockListToPass = new ArrayList<Block>();
		blockListToPass.addAll(blockList);
		
		merged = new Accfg();
		
		
			//Topological sort 
		  
		
		  TopologicalSort ts = new TopologicalSort();
		  ArrayList<String> sortedList =
		  ts.sortGraph(FetchInputFromLine.adjacencyList);
		
		
		System.out.println("sorted List "+sortedList);
		System.out.println("Block List "+blockList);
		
		
		/*
		 * while (iter.hasNext()) {
		 * 
		 * Block block = (Block) iter.next(); if
		 * (block.getAccfg().getInput().isEmpty()) {
		 * fpList.addAll(block.getAccfg().getFp());
		 * 
		 * } else continue;
		 * 
		 * }
		 * 
		 * iter = blockList.iterator(); while (iter.hasNext()) { Block block =
		 * (Block) iter.next(); if
		 * (block.getName().equalsIgnoreCase(Constants.SUM)) {
		 * 
		 * fpList.addAll(block.getAccfg().getFp());
		 * 
		 * }
		 * 
		 * }
		 */

		Iterator sortedIter = sortedList.iterator();
		
		String sortFp = "";

		while (sortedIter.hasNext()) {
			sortFp = (String) sortedIter.next();
			System.out.println("Test in merge1 "+sortFp);
			Iterator iter = blockList.iterator();
			while (iter.hasNext()) {
				Block block = (Block) iter.next();
				System.out.println("Test in merge2 "+((Variable)block.getOutput()).getName());
				
				if (((Variable)block.getOutput()).getName().equalsIgnoreCase(sortFp)) {
					fpList.addAll(block.getAccfg().getFp());
					initList.addAll(block.getAccfg().getInit());
					iter.remove();
					break;
				}

			}

		}

		merged.setFp(fpList);
		merged.setInit(initList);

		// Call to set input and output
		merged = findInputOutputVariable(merged, (ArrayList<Block>)blockListToPass);

		// System.out.println(merged);
		return merged;

	}

	// Set input and output for merged ACCFG
	public static Accfg findInputOutputVariable(Accfg accfg,
			ArrayList<Block> blockList) {

		List<Expression> input = new ArrayList<Expression>();
		List<Expression> output = new ArrayList<Expression>();

		Iterator blockListIter = blockList.iterator();
		while (blockListIter.hasNext()) {
			
		
			Block block = (Block) blockListIter.next();
			System.out.println("Block Name "+((Variable)(block.getOutput())).getName());
			
			input.addAll(block.getAccfg().getInput());
			//System.out.println("Input "+block.getAccfg().getInput());
			
			output.add(block.getAccfg().getOutput());
			//System.out.println("Output "+block.getAccfg().getOutput());
			

		}

		 System.out.println("Input Final "+input);
		 System.out.println("Output Final "+output);

		Iterator inputIter = input.iterator();
		String inputVar = "";
		String outputVar = "";
		while (inputIter.hasNext()) {
			inputVar = ((Variable) inputIter.next()).getName();
			Iterator outputIter = output.iterator();

			while (outputIter.hasNext()) {

				outputVar = ((Variable) outputIter.next()).getName();

				if (inputVar.equalsIgnoreCase(outputVar)) {
					inputIter.remove();
					outputIter.remove();
					break;
				}

				

			}

		}

		accfg.setInput(input);
		if(output.size()!=0)
		accfg.setOutput(output.get(0));

		return accfg;
	}

}
