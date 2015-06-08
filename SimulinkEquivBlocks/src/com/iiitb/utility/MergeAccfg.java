package com.iiitb.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.iiitb.blocks.Block;
import com.iiitb.blocks.Delay;
import com.iiitb.cfg.Accfg;
import com.iiitb.constant.Constants;
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
		List<Expression> delayList = new ArrayList<Expression>();
		/* Created a new list and copied all values from blockList. This is required since we remove blockList entries during iteration.
		 * We pass "blockListToPass" to  findInputOutputVariable method
		 */
		List<Block> blockListToPass = new ArrayList<Block>();
		blockListToPass.addAll(blockList);
		
		merged = new Accfg();
		
		
			//Topological sort 
		  
		
		  TopologicalSort ts = new TopologicalSort();
		 Iterator<String> iterKey =  FetchInputFromLine.adjacencyList.keySet().iterator();
		
		 Map<String,LinkedList<String>> tempMap = new HashMap<String,LinkedList<String>>();
		 while(iterKey.hasNext())
		 {
			 String key = iterKey.next();
			 Iterator<DestNode> tempIter = FetchInputFromLine.adjacencyList.get(key).iterator();
			 LinkedList<String> tempList = new LinkedList<String>();
			 while(tempIter.hasNext())
			 {
				 tempList.add(tempIter.next().getName());
			 }
			 
			 tempMap.put(key, tempList);
		 }
		  ArrayList<String> sortedList = ts.sortGraph(tempMap);
		
		  System.out.println("Before Sort List "+blockList);
		  System.out.println("Sorted List "+sortedList);
		
		


		Iterator<String> sortedIter = sortedList.iterator();
		
		String sortFp = "";

		while (sortedIter.hasNext()) {
			sortFp = (String) sortedIter.next();
			
			Iterator<Block> iter = blockList.iterator();
			while (iter.hasNext()) {
				Block block = (Block) iter.next();
				
				
				if (((Variable)block.getOutput()).getName().equalsIgnoreCase(sortFp)) {
					
					fpList.addAll(block.getAccfg().getFp());
					initList.addAll(block.getAccfg().getInit());
					delayList.addAll(block.getAccfg().getDelay());
					/*For Delay block with delay_length >1 , first individual delay components
					 * are added to delayList*/
					if(sortFp.startsWith(Constants.DELAY))
					{
						delayList.addAll(block.getAccfg().getDelay());
						Iterator<Delay> delayIter = ((Delay)block).getDelayLengthList().iterator();
						while(delayIter.hasNext())
						{
							delayList.addAll(delayIter.next().getAccfg().getDelay());
						
						}
						
					}
					
					iter.remove();
					break;
				}

			}

		}

		merged.setFp(fpList);
		merged.setInit(initList);
		merged.setDelay(delayList);

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

		Iterator<Block> blockListIter = blockList.iterator();
		while (blockListIter.hasNext()) {
			
		
			Block block = (Block) blockListIter.next();
			
			//System.out.println("Important Block Name ***************************************"+((Variable)(block.getOutput())).getName());
			//System.out.println("Important test ********************************************* "+block.getAccfg());
			
			if(block.getAccfg().getInput()!=null)
			input.addAll(block.getAccfg().getInput());
			
			
			output.addAll(block.getAccfg().getOutput());
			
			

		}

		 System.out.println("Input Final "+input);
		 System.out.println("Output Final "+output);

		Iterator<Expression> inputIter = input.iterator();
		String inputVar = "";
		String outputVar = "";
		while (inputIter.hasNext()) {
			inputVar = ((Variable) inputIter.next()).getName();
			Iterator<Expression> outputIter = output.iterator();

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
		accfg.setOutput(output);

		return accfg;
	}

}
