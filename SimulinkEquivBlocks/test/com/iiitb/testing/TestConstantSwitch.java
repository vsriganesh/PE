package com.iiitb.testing;


import static org.junit.Assert.*;

import java.util.ArrayList;




import java.util.Iterator;
import java.util.List;








import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.iiitb.blocks.Block;
import com.iiitb.cfg.Accfg;
import com.iiitb.constant.Constants;
import com.iiitb.factory.BlockFactory;
import com.iiitb.factory.BlockFactoryTest;
import com.iiitb.utility.FetchInputFromLine;
import com.iiitb.utility.FetchInputFromLineDivide;
import com.iiitb.utility.FetchInputFromLineRelationalOperator;
import com.iiitb.utility.FetchInputFromLineSwitch;
import com.iiitb.utility.FetchInputFromLineTest1;
import com.iiitb.utility.MergeAccfgDivide;
import com.iiitb.utility.MergeAccfgRelational;
import com.iiitb.utility.MergeAccfgSwitch;
import com.iiitb.utility.MergeAccfgTest;
import com.iiitb.utility.ParseXML;

public class TestConstantSwitch {

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
	public void test() {
		Document doc = ParseXML
				.initializeDocument("C:\\Users\\vsriganesh\\Documents\\MATLAB\\constantSwitch.xml");
		
		
		/* TEST : 1
		 *
		 * Initialise XML document 
		 * 
		 * Root Element of XML should be "<ModelInformation>"
		 * */
		assertEquals("ModelInformation", doc.getDocumentElement().getNodeName());
		
	
		// Test -- ParseXML.parseDocument()
		/* Expand parseDocument method in ParseXML class and test return types of intermediate 
		 * function calls*/
		
		

		NodeList tempForProcessing = null;
		Node currSubSystemNode = doc.getDocumentElement();
		
		if (currSubSystemNode.getNodeName().equalsIgnoreCase(
				Constants.MODEL_INFO)) {
			
			tempForProcessing = currSubSystemNode.getChildNodes();
			for (int tempForProcessingIter = 0; tempForProcessingIter < tempForProcessing
					.getLength(); tempForProcessingIter++) {
				if (tempForProcessing.item(tempForProcessingIter)
						.getNodeName().equals(Constants.MODEL)) {

					tempForProcessing = tempForProcessing.item(
							tempForProcessingIter).getChildNodes();
					break;

				}

			}

		}

		else
			tempForProcessing = currSubSystemNode.getChildNodes();
		
	
		
		/* TEST : 2
		 * In our case , currSubSystemNode = ModelInformation. If condition is executed 
		 * tempForProcessing has all child nodes of <Model>
		 *
		 * The final node under <Model> is <System>
		 * 
		 *  getLength()-2 is done instead of getLength()-1 because getLength()-1 returns #text (blank)
		 * */
		
		
		
		
		assertEquals("System",tempForProcessing.item(tempForProcessing.getLength()-2).getNodeName());
		
		
		
		
		/* TEST : 3
		 * 
		 * Access all child of <System>
		 * As per our test model , <System> should have 4 <Block> and 3 <Line>
		 * testCounter should be 6
		 * tempSubsystemSystemChildren should contain all child of <System>
		 * at the end of iteration
		 * 
		 * */
		
		NodeList tempSubsystemSystemChildren = null;
		
		for (int tempForProcessingIter = 0; tempForProcessingIter < tempForProcessing
				.getLength(); tempForProcessingIter++) {

			if (tempForProcessing.item(tempForProcessingIter).getNodeName()
					.equals(Constants.SYSTEM)) {
				tempSubsystemSystemChildren = tempForProcessing.item(
						tempForProcessingIter).getChildNodes();
				
			}

			else
				continue;
		}
		
		//Check for <Block> and <Line> under <System>
		int testCounter = 0;
		for (int test = 0; test < tempSubsystemSystemChildren
				.getLength(); test++) {
			
			if(tempSubsystemSystemChildren.item(test).getNodeName().equalsIgnoreCase("Block") || tempSubsystemSystemChildren.item(test).getNodeName().equalsIgnoreCase("Line"))
				testCounter++;
		}
		
		
		assertEquals(7, testCounter);
		
		
		
		
		/* TEST : 4
		 * 
		 * All <Block> under <System> are put in tempSubsystemSystemChildren list
		 * 
		 * All <Line> under <System> are put in lineChildNodesOfSystemNodeList list
		 * 
		 * As per our model ,
		 *
		 * When we iterate tempSubsystemSystemChildren and display node names, 
		 * there should be "Block,Block,Block,Block" corresponding to 
		 * "Constant,Constant1,Constant2,Switch"
		 * in any order
		 * 
		 *  When we iterate lineChildNodesOfSystemNodeList and display node names , 
		 *  there should be  "Line,Line,Line" corresponding to "Constant - Switch" , "Constant1-Switch" and "Constant2-Switch"
		 
		 * 
		 * */
		
		ArrayList<Node> blockChildNodesOfSystemNodeList = new ArrayList<Node>();
		ArrayList<Node> lineChildNodesOfSystemNodeList = new ArrayList<Node>();

		for (int tempForProcessingIter = 0; tempForProcessingIter < tempSubsystemSystemChildren
				.getLength(); tempForProcessingIter++) {

			if (tempSubsystemSystemChildren.item(tempForProcessingIter)
					.getNodeName().equalsIgnoreCase(Constants.BLOCK)) {

				blockChildNodesOfSystemNodeList
						.add(tempSubsystemSystemChildren
								.item(tempForProcessingIter));

			}

			if (tempSubsystemSystemChildren.item(tempForProcessingIter)
					.getNodeName().equalsIgnoreCase(Constants.LINE)) {

				lineChildNodesOfSystemNodeList
						.add(tempSubsystemSystemChildren
								.item(tempForProcessingIter));

			}

		}
		
		// 3 Nodes in blockChildNodesOfSystemNodeList
		String[] testArrayExpected = {"Block", "Block","Block","Block"};
		String[] testArrayActual = {blockChildNodesOfSystemNodeList.get(0).getNodeName(),blockChildNodesOfSystemNodeList.get(1).getNodeName(),blockChildNodesOfSystemNodeList.get(2).getNodeName(),blockChildNodesOfSystemNodeList.get(3).getNodeName()};
		assertArrayEquals(testArrayExpected,testArrayActual);
		
		// 2 Nodes in lineChildNodesOfSystemNodeList
		String[] testArrayExpectedLine = {"Line", "Line","Line"};
		String[] testArrayActualLine = {lineChildNodesOfSystemNodeList.get(0).getNodeName(),lineChildNodesOfSystemNodeList.get(1).getNodeName(),lineChildNodesOfSystemNodeList.get(2).getNodeName()};
		assertArrayEquals(testArrayExpectedLine,testArrayActualLine);
		
		
		
		
		/* TEST : 5
		 * 
		 * Create Java Objects corresponding to Nodes in blockChildNodesOfSystemNodeList
		 * 
		 * 
		 * 
		 * 
		 * */
		
		
		List<Block> blockList = new ArrayList<Block>();
		

		for (int nodeIter = 0; nodeIter < blockChildNodesOfSystemNodeList
				.size(); nodeIter++) {

			NamedNodeMap temp = blockChildNodesOfSystemNodeList.get(
					nodeIter).getAttributes();
			String blockName = "";
			Node blockType = null;
			for (int tempIter = 0; tempIter < temp.getLength(); tempIter++) {
				
				if (temp.item(tempIter).getNodeName()
						.equalsIgnoreCase(Constants.NAME)) {
					blockType = blockChildNodesOfSystemNodeList
							.get(nodeIter);
					// block type will be "Block"
					// block name will be BlockType_Name (for e.g) "Constant_Constant1"
					blockName = blockName+temp.item(tempIter).getNodeValue();
					
				}
				if (temp.item(tempIter).getNodeName()
						.equalsIgnoreCase(Constants.TYPE)) {
					
					// block name will be BlockType_Name (for e.g) "Constant_Constant1"
					if(blockName=="")
					blockName = temp.item(tempIter).getNodeValue()+"_";
					else
						blockName = temp.item(tempIter).getNodeValue()+"_"+blockName;
					
				}
			}
			
			/* Test blockName
			As per our model , blockName can only be one of the following - 
			
			* Constant_Constant1
			* Constant_Constant
			* Switch_Switch
			*
			*/
			
			
			if(blockName.contains("Constant1"))
			{
				assertEquals("Constant_Constant1", blockName);
			}
			else if(blockName.contains("Constant2"))
			{
				assertEquals("Constant_Constant2", blockName);
			}
			else if(blockName.contains("Constant"))
			{
				assertEquals("Constant_Constant", blockName);
			}
			else if(blockName.contains("Switch"))
			{
				assertEquals("Switch_Switch", blockName);
				
			}
			
			/* As per our model there is no subsystem and hence flow does not enter 
			 * 
			 * the if statement
			 * 
			 * */
			
			if (blockName != "" && blockType != null) {

				if (blockName.startsWith(Constants.SUB_SYS)) {

					
					ParseXML.countSubSystem++;
					
					ArrayList<String> tempInputList = new ArrayList<String>();
					
					// If there are input ports in the subsystem then we got
					// to find what is to be propagated into the subsystem
					// This can be done by analyzing the <line> of the
					// current subsystem

					for (int lineIter = 0; lineIter < lineChildNodesOfSystemNodeList
							.size(); lineIter++) {

						// tempInputList can be used for testing
						tempInputList.addAll(FetchInputFromLine
								.parseLineForPort(

								lineChildNodesOfSystemNodeList
										.get(lineIter).getChildNodes(),blockName.split("_", 2)[1]));

					}

					Accfg accfg = ParseXML.parseDocument(doc, blockType);
					
					
					Block block = BlockFactory.generateBlock(blockName.split("_", 2)[1],
							accfg);
					if (block != null)
						blockList.add(block);
					

				}

				else {
					NodeList attr = blockType.getChildNodes();

					
					/* Using BlockFactoryUtility and Helper class , necessary attributes
					 * for java objects corresponding to blocks are set
					 * 
					 * Based on the block, test if all necessary attributes are set
					 *    
					 * Based on our model there are only 2 block types : Constant,Switch 
					 * 
					 *  Constant: Value is 1
					 *  
					 *  Constant1: Value is 8
					 *  
					 *  Constant2: Value is 1
					 *  
					 *  Switch:  >= is default criteria and this criteria is set initially when creating switch block.
			         *   Note: For default criteria , Criteria tag will not appear in XML
					 */
					
					BlockFactoryTest test = new BlockFactoryTest();
					Block block = test.testGenerateBlockStringNodeList(blockName,
							attr);
					System.out.println("block name is "+blockName);
					if(blockName.contains("Constant1"))
					{
						assertEquals(block.getValue(), String.valueOf(8));
					}
					else if(blockName.contains("Constant"))
					{
						assertEquals(block.getValue(), String.valueOf(1));
					}
					else if(blockName.contains("Constant2"))
					{
						assertEquals(block.getValue(), String.valueOf(1));
					}
					else if(blockName.contains("Switch"))
					{
						assertEquals(block.getSign(), 0);
					}
					
					
					if (block != null)
					{
						blockList.add(block);
						
					}
						
					
				}
			}

		}
		
		/* TEST : 6
		 * 
		 * The blockList size should be 3 since as per our model there are 4 blocks
		 * 
		 * 
		 * */
		
		assertEquals(blockList.size(),4);
		
		
		
		/* TEST : 7
		 * 
		 * Check if proper FP is set for all blocks
		 * 
		 * FP for Switch block - Switch=(Switch=(Switch=(Constant2 > 0) OR (Constant2 = 0))?Constant:Constant1)
		 * FP for Constant block = Constant = value
		 * FP for Constant1 block = Constant1 = value
		 * 
		 * 
		 * 
		 * 
		 * */
		
		
		
		for (int nodeIter1 = 0; nodeIter1 < lineChildNodesOfSystemNodeList
				.size(); nodeIter1++) {


			 FetchInputFromLineSwitch testInput=new FetchInputFromLineSwitch();
			
			testInput.parseLineTest(
					(ArrayList<Block>) blockList,
					lineChildNodesOfSystemNodeList.get(nodeIter1)
							.getChildNodes());

		}
		
			Iterator<Block> test7Iter = blockList.iterator();
			while(test7Iter.hasNext())
			{
				String tempFp = test7Iter.next().toString();
				String testFp="";
				if(tempFp.equalsIgnoreCase("Constant"))
				{
					testFp = "Constant=1";
				}
				else if(tempFp.equalsIgnoreCase("Constant1"))
				{
					testFp = "Constant1=8";
				}
				else if(tempFp.equalsIgnoreCase("Constant2"))
				{
					testFp = "Constant2=1";
				}
				else
					testFp = "Switch=(Switch=(Switch=(Constant2 > 0) OR (Constant2 = 0))?Constant:Constant1)" ;
				
				assertTrue(testFp, testFp.equalsIgnoreCase("Switch=(Switch=(Switch=(Constant2 > 0) OR (Constant2 = 0))?Constant:Constant1)") || testFp.equalsIgnoreCase("Constant=1") || testFp.equalsIgnoreCase("Constant1=8")||testFp.equalsIgnoreCase("Constant2=1"));
				
			}
			
		
		/*
		 * TEST : 8
		 * 
		 * 
		 * Merge all individual ACCFG - 
		 * 
		 * 1) Merge FP list
		 * 2) Cancel input , output as per algorithm and generate final input , output list for merged ACCFG
		 * 
		 * 
		 * 
		 * 
		 * Test the size of init , input , output , delay list of the merged accfg object
		 * 
		 * As per our model , init and delay list size will be 0
		 * 
		 * input will be cancelled wit output and list size will be 0
		 * 
		 * output will have 'Sum' variable and hence the size will be 1
		 * 
		 
		 * 
		 * */
		
			Accfg retAccfg = MergeAccfgSwitch.mergeTest((ArrayList<Block>) blockList);
			
			assertEquals(0, retAccfg.getInit().size());

			assertEquals(1, retAccfg.getOutput().size());

			assertEquals(0, retAccfg.getInput().size());
			
			assertEquals(0, retAccfg.getDelay().size());
		
	}


	}


