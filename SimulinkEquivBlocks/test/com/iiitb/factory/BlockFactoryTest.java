package com.iiitb.factory;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.NodeList;

import com.iiitb.blocks.Block;
import com.iiitb.blocks.Constant;
import com.iiitb.blocks.Delay;
import com.iiitb.blocks.Divide;
import com.iiitb.blocks.InPort;
import com.iiitb.blocks.LogicalOperator;
import com.iiitb.blocks.MinMax;
import com.iiitb.blocks.RelationalOperator;
import com.iiitb.blocks.Sum;
import com.iiitb.blocks.Switch;
import com.iiitb.constant.Constants;
import com.iiitb.utility.BlockFactoryUtility;

public class BlockFactoryTest {

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

	@Test
	public Block testGenerateBlockStringNodeList(String blockName , NodeList attributes) {
		Block block = null;
		System.out.println("block name is "+blockName);
		if (blockName.startsWith(Constants.CONST)) {
			block = new Constant(blockName.split("_", 2)[1]);
			
			List<String> attrFetchList = new ArrayList<String>(); 
			attrFetchList.add(Constants.VALUE);
			
			/* Based on each block type , the attributes to fetch differs 
			 * 
			 * Incase of Constant block the only attribute to fetch is Value
			 * 
			 * 
			 * */
			List<String> testList = new ArrayList<String>();
			testList.add("Value");
			assertArrayEquals(testList.toArray(), attrFetchList.toArray());
			
			
			BlockFactoryUtility.setBlockAttributes(attrFetchList, attributes,
					block);

		}

		if (blockName.startsWith(Constants.SUM)) {
			block = new Sum(blockName.split("_", 2)[1]);
			
			List<String> attrFetchList = new ArrayList<String>(); 
			attrFetchList.add(Constants.INPUT);
			
			/* Based on each block type , the attributes to fetch differs 
			 * 
			 * Incase of Sum block the only attribute to fetch is Input
			 * 
			 * 
			 * */
			List<String> testList = new ArrayList<String>();
			testList.add("Inputs");
			assertArrayEquals(testList.toArray(), attrFetchList.toArray());
			
			
			BlockFactoryUtility.setBlockAttributes(attrFetchList, attributes,
					block);

		}
		
		if (blockName.startsWith(Constants.DIVIDE)) {
			block = new Divide(blockName.split("_", 2)[1]);
			
			List<String> attrFetchList = new ArrayList<String>(); 
			attrFetchList.add(Constants.INPUT);
			
			BlockFactoryUtility.setBlockAttributes(attrFetchList, attributes,
					block);

		}
		
		if (blockName.startsWith(Constants.LOGICAL)) {
			
			block = new LogicalOperator(blockName.split("_", 2)[1]);
			//System.out.println(block);
			List<String> attrFetchList = new ArrayList<String>(); 
			attrFetchList.add(Constants.OPERATOR);
			
			BlockFactoryUtility.setBlockAttributes(attrFetchList, attributes,
					block);

		}
		
        if (blockName.startsWith(Constants.RELATIONAL)) {
			
			block = new RelationalOperator(blockName.split("_", 2)[1]);
			//System.out.println(block);
			List<String> attrFetchList = new ArrayList<String>(); 
			attrFetchList.add(Constants.OPERATOR);
			
			BlockFactoryUtility.setBlockAttributes(attrFetchList, attributes,
					block);

		}
		
		if (blockName.startsWith(Constants.MINMAX)) {
			
			System.out.println("Inside MinMax block");
			block = new MinMax(blockName.split("_", 2)[1]);
			//System.out.println(block);
			List<String> attrFetchList = new ArrayList<String>(); 
			attrFetchList.add(Constants.FUNCTION);
			
			BlockFactoryUtility.setBlockAttributes(attrFetchList, attributes,
					block);

		}
		
		if (blockName.startsWith(Constants.DELAY)) {
			
			
			
			block = new Delay(blockName.split("_", 2)[1]);
			List<String> attrFetchList = new ArrayList<String>(); 
			attrFetchList.add(Constants.IC);
			attrFetchList.add(Constants.DELAY_LENGTH);
			
			/* Based on each block type , the attributes to fetch differs 
			 * 
			 * Incase of Delay block attributes to fetch are InitialCondition and DelayLength
			 * 
			 * 
			 * */
			List<String> testList = new ArrayList<String>();
			testList.add("InitialCondition");
			testList.add("DelayLength");
			assertArrayEquals(testList.toArray(), attrFetchList.toArray());
			
			
			
			BlockFactoryUtility.setBlockAttributes(attrFetchList, attributes,
					block);

		}
		
	if (blockName.startsWith(Constants.SWITCH)) {
			
		
			
			block = new Switch(blockName.split("_", 2)[1]);
			
			List<String> attrFetchList = new ArrayList<String>(); 
			attrFetchList.add(Constants.CRITERIA);
			attrFetchList.add(Constants.THRESHOLD);
			
			/* Based on each block type , the attributes to fetch differs 
			 * 
			 * Incase of Switch block attributes to fetch are Criteria and Threshold
			 * 
			 * 
			 * */
			List<String> testList = new ArrayList<String>();
			testList.add("Criteria");
			testList.add("Threshold");
			assertArrayEquals(testList.toArray(), attrFetchList.toArray());
			
			BlockFactoryUtility.setBlockAttributes(attrFetchList, attributes,
					block);

		}
	
	if (blockName.startsWith(Constants.INPORT)) {
		block = new InPort(blockName.split("_", 2)[1]);
		
		List<String> attrFetchList = new ArrayList<String>(); 
		attrFetchList.add(Constants.PORT);
		
		/* Based on each block type , the attributes to fetch differs 
		 * 
		 * Incase of Switch block attributes to fetch are Criteria and Threshold
		 * 
		 * 
		 * */
		List<String> testList = new ArrayList<String>();
		testList.add("Port");
		
		assertArrayEquals(testList.toArray(), attrFetchList.toArray());
		
		
		BlockFactoryUtility.setBlockAttributes(attrFetchList, attributes,
				block);
		
		
		

	}
		return block;

		
		
		
	}

	@Test
	public void testGenerateBlockStringAccfg() {
		fail("Not yet implemented");
	}

}
