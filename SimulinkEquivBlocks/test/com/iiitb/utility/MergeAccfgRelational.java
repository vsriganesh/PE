package com.iiitb.utility;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.iiitb.blocks.Block;
import com.iiitb.blocks.Constant;
import com.iiitb.blocks.Delay;
import com.iiitb.blocks.LogicalOperator;
import com.iiitb.blocks.Sum;
import com.iiitb.cfg.Accfg;
import com.iiitb.constant.Constants;
import com.iiitb.sort.TopologicalSort;

import expression.Expression;
import expression.Variable;

public class MergeAccfgRelational {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	public static Accfg mergeTest(ArrayList<Block> blockList) {

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
		 Iterator<String> iterKey =  FetchInputFromLineRelationalOperator.adjacencyList.keySet().iterator();
		
		 Map<String,LinkedList<String>> tempMap = new HashMap<String,LinkedList<String>>();
		
		 /* Since we require a map with <String,LinkedList<String>> we iterate through Map<String, LinkedList<DestNode>>
		  	 and create a necessary map*/
		 while(iterKey.hasNext())
		 {
			 String key = iterKey.next();
			 Iterator<DestNode> tempIter = FetchInputFromLineRelationalOperator.adjacencyList.get(key).iterator();
			 LinkedList<String> tempList = new LinkedList<String>();
			 while(tempIter.hasNext())
			 {
				 tempList.add(tempIter.next().getName());
			 }
			 
			 tempMap.put(key, tempList);
		 }
		  ArrayList<String> sortedList = ts.sortGraph(tempMap);
		
		 
		
		  /*
		   * TEST 1 :
		   * 
		   * 
		   * blockList should contain all blocks pertaining to our model
		   * As per our model it should contain - Constant , Constant1,RelationalOperator
		   * 
		   * sortedList should contain same blocks as blockList but topographically sorted
		   * 
		   * The list size should be 3 in both the cases
		   * 
		   * 
		   * */
		
		  assertEquals(3, blockList.size());
		  Block[] test = {new Constant("Constant"),new Constant("Constant1"),new LogicalOperator("Relational"+"\n"+"Operator")};
		  assertArrayEquals(test, blockList.toArray());

		  
		  assertEquals(3, sortedList.size());
		  String[] testSort = {"Constant1","Constant","Relational"+"\n"+
		  		"Operator"};
		  assertArrayEquals(testSort, sortedList.toArray());
		  
		  
		  
		  
		  
		  /*
		   * Merging individual ACCFG to a single ACCFG object
		   * 
		   * Order of merging is based on sortedList
		   * 
		   * Merged 
		   *  fpList , initList ,delayList are created from individual accfg objects corresponding to blocks
		   *  
		   *  As per our model , initList and delayList will be empty as there are no delay blocks.
		   *  
		   *  fpList should consist of FP in the following order as per the sortedList
		   *  
		   *  Constant1=1
		   *  Constant=1
		   *  Relational"+"\n"+"Operator=(Relational"+"\n"+"Operator=(Constant < Constant1) OR (Constant = Constant1)
		   * 
		   * Note : order of FP depends on topographical sort of blocks in model 
		   * sortedList holds the order
		   * 
		  
		   * */
		  
		  
		  
		  
		Iterator sortedIter = sortedList.iterator();
		
		String sortFp = "";

		while (sortedIter.hasNext()) {
			sortFp = (String) sortedIter.next();
			
			Iterator iter = blockList.iterator();
			while (iter.hasNext()) {
				Block block = (Block) iter.next();
				
				
				if (((Variable)block.getOutput()).getName().equalsIgnoreCase(sortFp)) {
					
					fpList.addAll(block.getAccfg().getFp());
					initList.addAll(block.getAccfg().getInit());
					if(!sortFp.startsWith(Constants.DELAY))
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
		
		System.out.println(fpList);
		
		//Testing
		
		Iterator<Expression> test7Iter = fpList.iterator();
		while(test7Iter.hasNext())
		{
			String tempFp = test7Iter.next().toString();
			
			
			assertTrue(tempFp, tempFp.equalsIgnoreCase("Relational"+"\n"+"Operator=((Constant < Constant1) OR (Constant = Constant1))") || tempFp.equalsIgnoreCase("Constant1=1") || tempFp.equalsIgnoreCase("Constant=1"));
			
		}
		
		
		
		merged.setInit(initList);
		assertEquals(0, initList.size());
		
		
		
		merged.setDelay(delayList);
		assertEquals(0, delayList.size());
		
		
		
	
		
		

		// Call to set input and output
		merged = findInputOutputVariableTest(merged, (ArrayList<Block>)blockListToPass);

		// System.out.println(merged);
		return merged;

	}
	// Set input and output for merged ACCFG
	public static Accfg findInputOutputVariableTest(Accfg accfg,
			ArrayList<Block> blockList) {

		
		/*
		 *  As per the algorithm the input and output variables cancel out each other
		 *  
		 *  
		 *  List 'input' consist of consolidated input variables from individual accfg objects corresponding to each block
		 *  List 'output' consist of consolidated output variables from individual accfg objects corresponding to each block
		 * 
		 *  Final cancelled out input,output is set to the merged accfg object
		 *  
		 *  
		 *  As per our model -
		 *  
		 *  Input , ouput for each block is -
		 *  
		 *  Constant  ==> input - ****				   , output - Constant
		 *  Constant1 ==> input - ****  			   , output - Constant1
		 *  RelationalOperator 	  ==> input - Constant & Constant1 , output - RelationalOperator 
		 *  
		 *  After cancelling out -
		 *  
		 *  input  - ****
		 *  output - RelationalOperator
		 * 
	 
		 * */
		
		
		
		
		
		
		
		
		
		List<Expression> input = new ArrayList<Expression>();
		List<Expression> output = new ArrayList<Expression>();

		Iterator blockListIter = blockList.iterator();
		while (blockListIter.hasNext()) {
			
		
			Block block = (Block) blockListIter.next();
			
			if(block.getAccfg().getInput()!=null)
			input.addAll(block.getAccfg().getInput());
			
			
			output.addAll(block.getAccfg().getOutput());
			
			

		}

		// Print consolidated input variables 
		System.out.println("Input Final "+input);
		
		// Print consolidated output variables
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
		accfg.setOutput(output);
		
		
		// Test
		assertEquals(0, input.size());
		assertEquals(1,output.size());
		assertEquals("Relational"+"\n"+"Operator", ((Variable)output.get(0)).getName());
		

		return accfg;
	}

}
