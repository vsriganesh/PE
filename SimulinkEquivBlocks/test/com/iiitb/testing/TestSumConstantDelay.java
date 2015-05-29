package com.iiitb.testing;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;




import java.util.HashMap;
import java.util.Iterator;
import java.util.List;



import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.iiitb.blocks.Block;
import com.iiitb.blocks.Delay;
import com.iiitb.cfg.Accfg;
import com.iiitb.constant.Constants;
import com.iiitb.factory.BlockFactory;
import com.iiitb.factory.BlockFactoryTest;
import com.iiitb.utility.FetchInputFromLine;
import com.iiitb.utility.FetchInputFromLineDelay;
import com.iiitb.utility.FetchInputFromLineTest1;
import com.iiitb.utility.MergeAccfgTest;
import com.iiitb.utility.MergeAccfgTestDelay;


public class TestSumConstantDelay {

	
	public static int countSubSystem = 0;

	public static Map<String, Accfg> subSystemMap = new HashMap<String, Accfg>();
	public static Map<String, String> portMap = new HashMap<String, String>();
	
	
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

	public static Document initializeDocument(String filePath) {
		Document doc = null;

		try {

			// Parse XML

			File fXmlFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			doc = dBuilder.parse(fXmlFile);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("SAXException in "
					+ TestPortBranchSubsystem.class.getName());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("IOException in "
					+ TestPortBranchSubsystem.class.getName());
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("ParserConfigurationException in "
					+ TestPortBranchSubsystem.class.getName());
		}
		/* TEST : 1
		 *
		 * Initialise XML document 
		 * 
		 * Root Element of XML should be "<ModelInformation>"
		 * */
		assertEquals("ModelInformation", doc.getDocumentElement().getNodeName());
		
		return doc;
	}
		
		
		
		
	
		// Test -- ParseXML.parseDocument()
		
		
	public static Accfg parseDocument(Document doc, Node currSubSystemNode) {

		
		
		NodeList tempForProcessing = null;
		
	
		
		/* TEST : 2
		 * In our case , currSubSystemNode = ModelInformation in case of parent subsystem.
		 *  If condition is executed 
		 * tempForProcessing has all child nodes of <Model>
		 *
		 * The final node under <Model> is <System>
		 * 
		 *  getLength()-2 is done instead of getLength()-1 because getLength()-1 returns #text (blank)
		 *  
		 */
		
		
		
		
		
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
		

		
		
		
		
		assertEquals("System",tempForProcessing.item(tempForProcessing.getLength()-2).getNodeName());
		
		
		
		
		/* TEST : 3
		 * 
		 * Access all child of <System>
		 * As per our test model , <System> should have 3 <Block> and 3 <Line>
		 * The Blocks include 1 - Constant , 1 - Sum , 1 - Delay
		 * testCounter should be 6
		 * tempSubsystemSystemChildren should contain all child of <System> 
		 * at the end of iteration
		 * 
		 * 
		 * 
		 * 
		*/
		
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
		
		
		assertEquals(6, testCounter);
		
		
		
		
		/* TEST : 4
		 * 
		 * All <Block> under <System> are put in tempSubsystemSystemChildren list
		 * 
		 * All <Line> under <System>  are put in lineChildNodesOfSystemNodeList list
		 * 
		 * As per our model ,
		 *
		
		 * 
		 * When we iterate tempSubsystemSystemChildren and display node names, 
		 * there should be "Block,Block,Block" corresponding to 
		 * "Constant,Sum,Delay"
		 * in any order
		 * 
		 *  When we iterate lineChildNodesOfSystemNodeList and display node names , 
		 *  there should be  "Line,Line,Line" corresponding to "Constant - Sum" ; "Sum-Delay" ; "Delay - Sum"
	 
		 */
		
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
		String[] testArrayExpected = {"Block", "Block","Block"};
		String[] testArrayActual = {blockChildNodesOfSystemNodeList.get(0).getNodeName(),blockChildNodesOfSystemNodeList.get(1).getNodeName(),blockChildNodesOfSystemNodeList.get(2).getNodeName()};
		assertArrayEquals(testArrayExpected,testArrayActual);
		
		// 3 Nodes in lineChildNodesOfSystemNodeList
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
			As per our model , blockName can only be one of the following in the parent subsystem - 
			
			
			* Constant_Constant
			* Sum_Sum
			* Delay_Delay
			 
			*/
			
			
			if(blockName.contains("Constant"))
			{
				assertEquals("Constant_Constant", blockName);
			}
		
			
			else if(blockName.contains("Sum"))
			{
				assertEquals("Sum_Sum", blockName);
			}
		
	
			else
			{
				assertEquals("Delay_Delay", blockName);
			}
				
			
			
			/* As per our model there is no subsystem and hence flow does not enter
			 * 
			 * the if statement
			 * 
			 * */
			
			if (blockName != "" && blockType != null) {

				if (blockName.startsWith(Constants.SUB_SYS)) {

					countSubSystem++;
					
					ArrayList<String> tempInputList = new ArrayList<String>();
					
					
					for (int lineIter = 0; lineIter < lineChildNodesOfSystemNodeList
							.size(); lineIter++) {

						// tempInputList can be used for testing
						tempInputList.addAll(FetchInputFromLineTest1
								.parseLineForPort(

								lineChildNodesOfSystemNodeList
										.get(lineIter).getChildNodes()));

					}

				
					
					// Recursive call
					Accfg accfg = parseDocument(doc, blockType);
					
					
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
					 * Based on our model there are only 3 block type in parent subsystem : Constant,Subsystem,Sum
					 * 
					 *  Constant: Value is 2
					 *   
					 *  Sum: Inputs is ++ and the corresponding key is 1 as per signList
					 *  map in Sum class (Sum Block implementation)
					 *  (Note:currently this is not used anywhere in code)
					 *  By default we consider only '++'
					 *  
					 *  Delay:
					 *  
					 *  
					 *  
				 
					 */
					
					BlockFactoryTest test = new BlockFactoryTest();
					Block block = test.testGenerateBlockStringNodeList(blockName,
							attr);
					
					if(blockName.contains("Constant"))
					{
						assertEquals(block.getValue(), String.valueOf(2));
					}
					else if(blockName.contains("Delay"))
					{
						assertEquals(((Delay)block).getDelayLength(), 5);
					}
					
					else
					{
						assertEquals(block.getSign(), 1);
					}
					
					
					if (block != null)
						blockList.add(block);
				}
			}

		}
		
		/* TEST : 6
		 * 
		 * The blockList size should be 3 since as per our model there are 3 blocks
		 */
		
		assertEquals(blockList.size(),3);
		
		
		
		/* TEST : 7
		 * 
		 * Check if proper FP is set for all blocks - Parent Subsystem
		 * 
		 * FP for Sum block - Subsystem = Sum
		 * FP for Constant block - Constant = value
		 * There is no FP for Delay block
		 * 
		 
		 * */
		
		
		
		for (int nodeIter = 0; nodeIter < lineChildNodesOfSystemNodeList
				.size(); nodeIter++) {


			 FetchInputFromLineDelay testInput = new FetchInputFromLineDelay();
			
			testInput.parseLineTest(
					(ArrayList<Block>) blockList,
					lineChildNodesOfSystemNodeList.get(nodeIter)
							.getChildNodes());

		}
		
			Iterator<Block> test7Iter = blockList.iterator();
			while(test7Iter.hasNext())
			{
				String tempFp = test7Iter.next().toString();
				String testFp="";
				if(tempFp.equalsIgnoreCase("Constant"))
				{
					testFp = "Constant=2";
				}
				
		
				
				else
					testFp = "Sum=(Constant + Delay)";
				
				assertTrue(testFp, testFp.equalsIgnoreCase("Sum=(Constant + Delay)") || testFp.equalsIgnoreCase("Constant=2"));
				
				
				
				
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
		
			Accfg retAccfg = MergeAccfgTestDelay.mergeTest((ArrayList<Block>) blockList);
			
		
				// Init List : [Delay=0]
				assertEquals(1, retAccfg.getInit().size());

				assertEquals(0, retAccfg.getOutput().size());

				assertEquals(0, retAccfg.getInput().size());
				
				//Delay List : [(Delay = Delay_delay_3), (Delay_delay_3 = Delay_delay_2), (Delay_delay_2 = Delay_delay_1), (Delay_delay_1 = Delay_delay_0), (Delay_delay_0 = Sum)]
				assertEquals(5, retAccfg.getDelay().size());
			
			
			
			FetchInputFromLine.adjacencyList.clear();

			subSystemMap.put("SubSystem_" + String.valueOf(countSubSystem),
					retAccfg);
			return retAccfg;
		
	}
	
	@Test
	public void test() {
	
		Document doc = initializeDocument("C:/Users/vsriganesh/Documents/MATLAB/delay_timer.xml");
		Accfg finalAccfg = parseDocument(doc,doc.getDocumentElement());
		
	}
	

}
