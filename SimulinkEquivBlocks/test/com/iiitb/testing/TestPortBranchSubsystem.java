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
import com.iiitb.cfg.Accfg;
import com.iiitb.constant.Constants;
import com.iiitb.factory.BlockFactory;
import com.iiitb.factory.BlockFactoryTest;
import com.iiitb.utility.FetchInputFromLine;
import com.iiitb.utility.FetchInputFromLineSubsystem;
import com.iiitb.utility.FetchInputFromLineTest1;
import com.iiitb.utility.MergeAccfgTest;
import com.iiitb.utility.MergeAccfgTestSubsystem;


public class TestPortBranchSubsystem {

	
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
		 *  
		 *  
		 *  
		 *  In case of child subsystem , currSubSystemNode = Block and else part is executed
		 *  
		 *  
		 *  <Block BlockType="SubSystem" Name="Subsystem" SID="1">
		 *  
		 *  <P Name="Ports">[2, 1]</P>
		 *  <P Name="Position">[245, 109, 345, 151]</P>
		 *  <P Name="ZOrder">1</P>
		 *  <P Name="RequestExecContextInheritance">off</P>
		 *  <System>
		 *  
		 *  		<child nodes are not mentioned>
		 *  </System>
		 *  
		 *  </Block>
		 *  
		 * The final node under <Block> is <System>
		 * 
		 *  getLength()-2 is done instead of getLength()-1 because getLength()-1 returns #text (blank)
		 * */
		
		
		
		
		
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
		 * As per our test model , <System> should have 4 <Block> and 3 <Line> in the parent subsystem
		 * The Blocks include 2 - Constant , 1 - Sum , 1 - Subsystem
		 * testCounter should be 7
		 * tempSubsystemSystemChildren should contain all child of <System> [Parent Subsystem]
		 * at the end of iteration
		 * 
		 * 
		 * 
		 * 
		 * In case of child subsystem (Recursive call) as per our model  , <System> should have 4 <Block> and 3 <Line>
		 * 
		 * <Block BlockType="Inport" Name="In1" SID="2">
		 * <P Name="Position">[110, 103, 140, 117]</P>
		 * <P Name="ZOrder">-1</P>
		 * <P Name="IconDisplay">Port number</P>
		 * </Block>
		 * 
		 * <Block BlockType="Inport" Name="In2" SID="6">
		 * <P Name="Position">[140, 173, 170, 187]</P>
		 * <P Name="ZOrder">1</P>
		 * <P Name="Port">2</P>
		 * <P Name="IconDisplay">Port number</P>
		 * </Block>
		 * 
		 * <Block BlockType="Sum" Name="Sum" SID="7">
		 * <P Name="Ports">[2, 1]</P>
		 * <P Name="Position">[230, 85, 250, 105]</P>
		 * <P Name="ZOrder">2</P>
		 * <P Name="ShowName">off</P>
		 * <P Name="IconShape">round</P>
		 * <P Name="Inputs">|++</P>
		 * <P Name="InputSameDT">off</P>
		 * <P Name="OutDataTypeStr">Inherit: Inherit via internal rule</P>
		 * <P Name="SaturateOnIntegerOverflow">off</P>
		 * </Block>
		 * 
		 * <Block BlockType="Outport" Name="Out1" SID="3">
		 * <P Name="Position">[360, 103, 390, 117]</P>
		 * <P Name="ZOrder">-2</P>
		 * <P Name="IconDisplay">Port number</P>
		 * </Block>
		 * 
		 * 
		 * <Line> 
		 * <P Name="ZOrder">2</P>
		 * <P Name="SrcBlock">In1</P>
		 * <P Name="SrcPort">1</P>
		 * <P Name="Points">[70, 0]</P>
		 * <P Name="DstBlock">Sum</P>
		 * <P Name="DstPort">1</P>
		 * </Line>
		 * 
		 * 
		 * <Line><P Name="ZOrder">3</P>
		 * <P Name="SrcBlock">In2</P>
		 * <P Name="SrcPort">1</P>
		 * <P Name="Points">[65, 0]</P>
		 * <P Name="DstBlock">Sum</P>
		 * <P Name="DstPort">2</P>
		 * </Line>
		 * 
		 * <Line>
		 * <P Name="ZOrder">4</P>
		 * <P Name="SrcBlock">Sum</P>
		 * <P Name="SrcPort">1</P>
		 * <P Name="Points">[50, 0; 0, 15]</P>
		 * <P Name="DstBlock">Out1</P>
		 * <P Name="DstPort">1</P>
		 * </Line>
		 * 
		 * NOTE : Currently we do not process 'Outport'
		 * 
		 * testCounter should be 7
		 * tempSubsystemSystemChildren should contain all child of <System> [Child Subsystem]
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
		
		//Check for <Block> and <Line> under <System> [Parent/Child subsystem]
		int testCounter = 0;
		for (int test = 0; test < tempSubsystemSystemChildren
				.getLength(); test++) {
			
			if(tempSubsystemSystemChildren.item(test).getNodeName().equalsIgnoreCase("Block") || tempSubsystemSystemChildren.item(test).getNodeName().equalsIgnoreCase("Line"))
				testCounter++;
		}
		
		
		assertEquals(7, testCounter);
		
		
		
		
		/* TEST : 4
		 * 
		 * All <Block> under <System> [Parent/child Subsystem] are put in tempSubsystemSystemChildren list
		 * 
		 * All <Line> under <System> [Parent/child Subsystem] are put in lineChildNodesOfSystemNodeList list
		 * 
		 * As per our model ,
		 *
		 * In case of parent subsystem , 
		 * 
		 * When we iterate tempSubsystemSystemChildren and display node names, 
		 * there should be "Block,Block,Block,Block" corresponding to 
		 * "Constant,Constant1,Sum,Subsystem"
		 * in any order
		 * 
		 *  When we iterate lineChildNodesOfSystemNodeList and display node names , 
		 *  there should be  "Line,Line,Line,Line" corresponding to "Constant - Subsystem" ; "Constant1-SubSystem" ; "Constant1 - Sum"
		 *  and "Subsystem - Sum"
		 *  
		 *  But , there will be only "Line,Line,Line" since "Constant1-SubSystem" ; "Constant1 - Sum" are under the same
		 *  <Line> with <Branch>
		 *  
		 *  
		 *  Branch in XML ---
		 *  
		 *  <Line>
		 *  
		 *  <P Name="ZOrder">3</P>
		 *  <P Name="SrcBlock">Constant1</P>
		 *  <P Name="SrcPort">1</P>
		 *  <P Name="Points">[45, 0; 0, -50]</P>
		 *  
		 *  <Branch>
		 *  <P Name="ZOrder">6</P>
		 *  <P Name="Points">[100, 0; 0, 41; 160, 0]</P>
		 *  <P Name="DstBlock">Sum</P>
		 *  <P Name="DstPort">2</P>
		 *  </Branch>
		 *  
		 *  <Branch>
		 *  <P Name="ZOrder">5</P>
		 *  <P Name="Points">[0, -30]</P>
		 *  <P Name="DstBlock">Subsystem</P>
		 *  <P Name="DstPort">2</P>
		 *  </Branch>
		 *  
		 *  </Line>
		 
		 * 
		 * In case of child subsystem (Recursive call) ,
		 * 
		 * When we iterate tempSubsystemSystemChildren and display node names, 
		 * there should be "Block,Block,Block,Block" corresponding to 
		 * "In1,In2,Sum,Out1"
		 * in any order
		 * 
		 *  When we iterate lineChildNodesOfSystemNodeList and display node names , 
		 *  there should be  "Line,Line,Line" corresponding to "In1 - Sum" ; "In2-Sum" ; "Sum - Out1"

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
		
		// 4 Nodes in blockChildNodesOfSystemNodeList
		String[] testArrayExpected = {"Block", "Block","Block","Block"};
		String[] testArrayActual = {blockChildNodesOfSystemNodeList.get(0).getNodeName(),blockChildNodesOfSystemNodeList.get(1).getNodeName(),blockChildNodesOfSystemNodeList.get(2).getNodeName(),blockChildNodesOfSystemNodeList.get(3).getNodeName()};
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
			
			* Constant_Constant1
			* Constant_Constant
			* Sum_Sum
			* SubSystem_Subsystem
			* 
			* 
			* 
			* In child subsystem - recursive call, blockName can be -
			* 
			* Inport_In1
			* Inport_In2
			* Outport_Out1
			* Sum_Sum
			* 
			*/
			
			
			if(blockName.contains("Constant1"))
			{
				assertEquals("Constant_Constant1", blockName);
			}
			else if(blockName.contains("Constant"))
			{
				assertEquals("Constant_Constant", blockName);
			}
			else if(blockName.contains("Sum"))
			{
				assertEquals("Sum_Sum", blockName);
			}
			
			else if(blockName.contains("In1"))
			{
				assertEquals("Inport_In1", blockName);
			}
			else if(blockName.contains("In2"))
			{
				assertEquals("Inport_In2", blockName);
			}
			else if(blockName.contains("Out"))
			{
				assertEquals("Outport_Out1", blockName);
			}
			else
			{
				assertEquals("SubSystem_Subsystem", blockName);
			}
				
			
			
			/* As per our model there is subsystem and hence flow enters
			 * 
			 * the if statement
			 * 
			 * */
			
			if (blockName != "" && blockType != null) {

				if (blockName.startsWith(Constants.SUB_SYS)) {

					countSubSystem++;
					
					ArrayList<String> tempInputList = new ArrayList<String>();
					
					// If there are input ports in the subsystem then we got
					// to find what is to be propagated into the subsystem
					// This can be done by analysing the <line> of the
					// current subsystem

					
					/* 
					 * As per our subsystem there are input ports
					 * 
					 * There are 2 input ports --  Constant and Constant1 are to be 
					 * propagated to the inner subsystem
					 * 
					 * 
					 * tempInputList should contain Constant and Constant1
					 * 
					 * 
					 *  <Line>
					 *  
						<P Name="ZOrder">1</P>
						<P Name="SrcBlock">Constant</P>
						<P Name="SrcPort">1</P>
						<P Name="DstBlock">Subsystem</P>
						<P Name="DstPort">1</P>
						
      					</Line>
      					
      					
      					
      					 <Line>
						<P Name="ZOrder">3</P>
						<P Name="SrcBlock">Constant1</P>
						<P Name="SrcPort">1</P>
						<P Name="Points">[45, 0; 0, -50]</P>
						
						<Branch>
	  					<P Name="ZOrder">6</P>
	  					<P Name="Points">[100, 0; 0, 41; 160, 0]</P>
	  					<P Name="DstBlock">Sum</P>
	  					<P Name="DstPort">2</P>
						</Branch>
						
						<Branch>
	  					<P Name="ZOrder">5</P>
	  					<P Name="Points">[0, -30]</P>
	  					<P Name="DstBlock">Subsystem</P>
	  					<P Name="DstPort">2</P>
						</Branch>
						
      					</Line>
      					
					 * We are concerned about blocks whose destination is subsystem -- Constant and Constant1
					 * Thus  'tempInputList' should contain Constant and Constant1
					 * 
					 * 
					 * NOTE : If there are no input ports associated with subsystem , then the list will be empty
					 
					 * */
					for (int lineIter = 0; lineIter < lineChildNodesOfSystemNodeList
							.size(); lineIter++) {

						// tempInputList can be used for testing
						tempInputList.addAll(FetchInputFromLineSubsystem
								.parseLineForPort(

								lineChildNodesOfSystemNodeList
										.get(lineIter).getChildNodes()));
							
					}

					/*TEST : 6
					 * 
					 *  'tempInputList' should contain Constant and Constant1
					 * 
					 * */
					
					
					String testArray[] ={"Constant","Constant1"};
					assertArrayEquals(testArray, tempInputList.toArray());
					//System.out.println(blockType.getTextContent());
					
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
					 *  Constant: Value is 1
					 *  
					 *  Constant1: Value is 1
					 *  
					 *  Sum: Inputs is ++ and the corresponding key is 1 as per signList
					 *  map in Sum class (Sum Block implementation)
					 *  (Note:currently this is not used anywhere in code)
					 *  By default we consider only '++'
					 *  
					 *  
					 *  
					 *  
					 *  In case of child subsystem , 
					 *  
					 *  In1 : Value is Constant
					 *  In2 : Value is Constant1
					 *  
					 *  Out1 : Not processed
					 *  
					 *  Sum: Inputs is ++ and the corresponding key is 1 as per signList
					 *  map in Sum class (Sum Block implementation)
					 *  (Note:currently this is not used anywhere in code)
					 *  By default we consider only '++'
					 *  
					 *  
				 
					 *  
					 */
					
					BlockFactoryTest test = new BlockFactoryTest();
					Block block = test.testGenerateBlockStringNodeList(blockName,
							attr);
					
					if(blockName.contains("Constant1"))
					{
						assertEquals(block.getValue(), String.valueOf(1));
					}
					else if(blockName.contains("Constant"))
					{
						assertEquals(block.getValue(), String.valueOf(1));
					}
					else if(blockName.contains("In1"))
					{
						assertEquals(block.getValue(), "Constant");
					}
					else if(blockName.contains("In2"))
					{
						assertEquals(block.getValue(), "Constant1");
					}
					//We do not process OutPort
					else if(blockName.contains("Out1"))
					{
						assertNull("TESTING FOR NULL",block);
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
		 * 
		 * Note : In child subsystem Outport is not processed so we have 3 blocks in child subsystem also
		 * */
		
		assertEquals(blockList.size(),3);
		
		
		
		/* TEST : 7
		 * 
		 * Check if proper FP is set for all blocks - Parent Subsystem
		 * 
		 * FP for Sum block - Subsystem = Sum
		 * FP for Constant block - Constant = value
		 * FP for Constant1 block - Constant1 = value
		 * 
		 * 
		 * Check if proper FP is set for all blocks - Child Subsystem
		 * 
		 * FP for In1 block - In1 = Constant
		 * FP for In2 block - In2 = Constant1
		 * FP for Sum block - Sum = In1 + In2
		 * 
		 * 
		 * 
		 * 
		 * */
		
		
		
		for (int nodeIter = 0; nodeIter < lineChildNodesOfSystemNodeList
				.size(); nodeIter++) {


			 FetchInputFromLineSubsystem testInput = new FetchInputFromLineSubsystem();
			
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
					testFp = "Constant=1";
				}
				else if(tempFp.equalsIgnoreCase("Constant1"))
				{
					testFp = "Constant1=1";
				}
				else if(tempFp.equalsIgnoreCase("In1"))
				{
					testFp = "In1=Constant";
				}
				else if(tempFp.equalsIgnoreCase("In2"))
				{
					testFp = "In2=Constant1";
				}
				//For child subsystem (since Sum block is part of both parent and child subsystem)
				else if(tempFp.equalsIgnoreCase("Sum") && currSubSystemNode.getNodeName().equalsIgnoreCase("Block"))
				{
					testFp = "Sum=(In1 + In2)";
				}
				
				else
					testFp = "Sum=(Constant1 + Subsystem)";
				
				assertTrue(testFp, testFp.equalsIgnoreCase("In1=Constant")||testFp.equalsIgnoreCase("In2=Constant1")||testFp.equalsIgnoreCase("Sum=(In1 + In2)") || testFp.equalsIgnoreCase("Sum=(Constant1 + Subsystem)") || testFp.equalsIgnoreCase("Constant=1") || testFp.equalsIgnoreCase("Constant1=1"));
				
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
		
		// Testing is not performed for merging	
			
			Accfg retAccfg = MergeAccfgTestSubsystem.mergeTest((ArrayList<Block>) blockList);
			
	
			
			
			FetchInputFromLineSubsystem.adjacencyList.clear();

			subSystemMap.put("SubSystem_" + String.valueOf(countSubSystem),
					retAccfg);
			return retAccfg;
		
	}
	
	@Test
	public void test() {
	
		Document doc = initializeDocument("C:/Users/vsriganesh/Documents/MATLAB/test2.xml");
		Accfg finalAccfg = parseDocument(doc,doc.getDocumentElement());
		
	}
	

}
