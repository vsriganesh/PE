package com.iiitb.utility;

import static org.junit.Assert.assertEquals;

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
import org.junit.Test;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import com.iiitb.blocks.Block;
import com.iiitb.blocks.Delay;
import com.iiitb.constant.Constants;
import com.iiitb.testing.TestPortBranchSubsystem;

import expression.Expression;
import expression.Variable;

public class FetchInputFromLineLogicalOperator {
	public static Map<String, LinkedList<DestNode>> adjacencyList = new HashMap<String, LinkedList<DestNode>>();
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
	public void parseLineTest(ArrayList<Block> blockList,
			NodeList attributes) {
		
		String sourceNode = "";
		String destNode = "";
		List<DestNode> destNodeTemp = new LinkedList<DestNode>();
		String destPort = "";
		// Map to generate adjacency list representation of subsystem
		
		
		/* TEST : 1
		 * 
		 * blockList contains all the blocks in model as java objects with necessary attributes set
		 * 
		 * As per our model this basically contains 'Constant,Constant1,LogicalOperator'
		 * 
		 * attributes contain child nodes of <Line>
		 * 
		 * 
		 * */
		DestNode nodeIni = new DestNode();
		for (int iter = 0; iter < attributes.getLength(); iter++) {

			
			// Branch is not applicable as per our model
			
			if (attributes.item(iter).getNodeName().equalsIgnoreCase("P")
					|| attributes.item(iter).getNodeName()
							.equalsIgnoreCase("Branch"))

			{

				// Branch
				// Branch is not applicable for the current model
				if (attributes.item(iter).getNodeName()
						.equalsIgnoreCase("Branch")) {

					DestNode node = new DestNode();
					NodeList branchChildren = attributes.item(iter)
							.getChildNodes();
					for (int brTemp = 0; brTemp < branchChildren.getLength(); brTemp++) {
						if (branchChildren.item(brTemp).getNodeName()
								.equalsIgnoreCase("P")) {

							NamedNodeMap temp = branchChildren.item(brTemp)
									.getAttributes();

							for (int tempIter = 0; tempIter < temp.getLength(); tempIter++) {

								// System.out.println("boolean value "+temp.item(tempIter).getNodeValue().equalsIgnoreCase("DstBlock"));
								if (temp.item(tempIter).getNodeValue()
										.equalsIgnoreCase("DstBlock")) {
									// System.out.println("Dst block value "+branchChildren.item(brTemp).getTextContent());
									// destNode =
									// branchChildren.item(brTemp).getTextContent();
									node.setName(branchChildren.item(brTemp)
											.getTextContent());
									destNodeTemp.add(node);
								}

								if (temp.item(tempIter).getNodeValue()
										.equalsIgnoreCase("DstPort")) {
									// System.out.println("Dst block value "+branchChildren.item(brTemp).getTextContent());
									// destNode =
									// branchChildren.item(brTemp).getTextContent();

									destNodeTemp
											.get(destNodeTemp.indexOf(node))
											.setPort(
													branchChildren.item(brTemp)
															.getTextContent());

								}

							}

						}
					}

				}

				else {

					// for a single <p>
					NamedNodeMap temp = attributes.item(iter).getAttributes();

					for (int tempIter = 0; tempIter < temp.getLength(); tempIter++) {

						
						if (temp.item(tempIter).getNodeValue()
								.equalsIgnoreCase("SrcBlock")) {

							sourceNode = attributes.item(iter).getTextContent();

						}

						if (temp.item(tempIter).getNodeValue()
								.equalsIgnoreCase("DstBlock")) {

							nodeIni.setName(attributes.item(iter)
									.getTextContent());
							destNodeTemp.add(nodeIni);
							

						}

						// Used for switch to identify port of input and
						// condition
						if (temp.item(tempIter).getNodeValue()
								.equalsIgnoreCase("DstPort")) {
							
							// Name and port is set
							destNodeTemp.get(destNodeTemp.indexOf(nodeIni))
									.setPort(
											attributes.item(iter)
													.getTextContent());

						}

					}

				}

			}

		}

		// In a single <line> all attributes are captured.

		// SourceNode and list of destination nodes will be captured

		/* There are 2 <Line> for our model 
		 * 
		 * Test sourceNode and destNode
		 * 
		 * <Line>
		 * <P Name="ZOrder">1</P>
		 * <P Name="SrcBlock">Constant</P>
		 * <P Name="SrcPort">1</P>
		 * <P Name="Points">[68, 0; 0, 60]</P>
		 * <P Name="DstBlock">Sum</P>
		 * <P Name="DstPort">1</P>
		 * </Line>
		 * 
		 * <Line>
		 * <P Name="ZOrder">2</P>
		 * <P Name="SrcBlock">Constant1</P>
		 * <P Name="SrcPort">1</P>
		 * <P Name="Points">[150, 0]</P>
		 * <P Name="DstBlock">Sum</P>
		 * <P Name="DstPort">2</P>
		 * </Line>
		 * 
		 * 
		 * We are interested only in SrcBlock,DstBlock,DstPort in each <Line>
		 * 
		 * Note : DstPort is not used for this model though it is captured
		 * 
		 * 
		 * 
		 * TEST:2 
		 * 
		 * If the source node is 'Constant' or 'Constant1' , as per the model destination node should be LogicalOperator
		 * 
		 * 
		 * */

		if(sourceNode.equalsIgnoreCase("Constant") || sourceNode.equalsIgnoreCase("Constant1"))
		{
			assertEquals("LogicalOperator",nodeIni.getName().replaceAll("\n", ""));
		}
		
		
		
		
		/* Create an adjacency list for each Node 
		 * 
		 * 
		 * As per our model , adjacency list should be
		 * 
		 * Constant -> LogicalOperator
		 * Constant1 -> LogicalOperator
		 * 
		 * 
		 * 
		 * TEST:3
		 *  
		 *  From the adjacency list --  Map<String, LinkedList<DestNode>>
		 *  Check for any key the corresponding value list has 'LogicalOperator'
		 * 
		 * 
		 * */
		
		
		if (sourceNode != "") {

			if (adjacencyList.get(sourceNode) != null) {

				adjacencyList.get(sourceNode).addAll(destNodeTemp);

			} else {
				LinkedList<DestNode> addList = new LinkedList<DestNode>();

				addList.addAll(destNodeTemp);

				adjacencyList.put(sourceNode, addList);

			}
			
			// Test adjacency list
			
			assertEquals(adjacencyList.get(sourceNode).get(0).getName().replaceAll("\n", ""),"LogicalOperator");
			
			
			

			Iterator<Block> blockListIter = blockList.iterator();

			while (blockListIter.hasNext()) {

				Block blockObj = blockListIter.next();

				// CHECK THIS

				Iterator<DestNode> destNodeTempIter = destNodeTemp.iterator();
				while (destNodeTempIter.hasNext()) {

					DestNode tempDest = destNodeTempIter.next();
					destNode = tempDest.getName();

					destPort = tempDest.getPort();
					if (destNode.equalsIgnoreCase(((Variable) blockObj
							.getOutput()).getName())) {

						// input is not set for delay block as for delay block
						// input is based on delay length
						if (!destNode.startsWith(Constants.DELAY))
							blockObj.setInput(sourceNode, destPort);

						// System.out.println("Super ");
					}
				}
			
				//TEST:4
				/* Considering 
				 * <Line>
				 * <P Name="ZOrder">1</P>
				 * <P Name="SrcBlock">Constant</P>
				 * <P Name="SrcPort">1</P>
				 * <P Name="Points">[68, 0; 0, 60]</P>
				 * <P Name="DstBlock">Sum</P>
				 * <P Name="DstPort">1</P>
				 * </Line>
				 * 
				 * is parsed , input for 'LogicalOperator' block should be set as 'Constant' */
				
				if(((Variable)blockObj.getOutput()).getName().equalsIgnoreCase("LogicalOperator"))
				{
					assertEquals(((Variable)blockObj.getInput().get(0)).getName(), "Constant");
					
				}
			
			}

		
			
			
			
		}

		// Necessary inputs are set

		// Clear the list
		destNodeTemp.clear();

		Iterator<Block> blockListIter = blockList.iterator();

		while (blockListIter.hasNext()) {

			Block blockObj = blockListIter.next();

			if (blockObj.isInputSetFlag()
					|| destNode.startsWith(Constants.DELAY)) {

				
				// Applicable only for delay block. For other blocks input is
				// set.
				// Flag to set input based on delay length.
				/*
				 * If delay length is 1 then input is directly the source node
				 */
				
				
				// Delay is not part of model under test
				
				
				boolean flag = false;
				if (((Variable) blockObj.getOutput()).getName().startsWith(
						Constants.DELAY)) {
					if (((Delay) blockObj).getDelayLength() > 1) {
						blockObj.setInput(
								((Variable) blockObj.getOutput()).getName()
										+ "_"
										+ "delay_"
										+ (((Delay) blockObj).getDelayLength() - 2),
								destPort);
					} else {
						blockObj.setInput(sourceNode, destPort);
						flag = true;
					}
				}

				
				
				
				/*   TEST
				 * 
				 * 
				 * 	Check if the input for 'LogicalOperator' block is 'Constant' and 'Constant1'
				 * 
				 *  Check if expression created for 'LogicalOperator' block is -> LogicalOperator = Constant OR Constant1
				
				 * 
				 * */
				
				List<Expression> input = new ArrayList<Expression>();
				input = blockObj.getInput();
				blockObj.getAccfg().setInput(input);
				List<Expression> expr = new ArrayList<Expression>();

				expr.add(blockObj.expression());
				
				if(((Variable)blockObj.getOutput()).getName().equalsIgnoreCase("Logical"+"\n"+"Operator"))
				{
					String[] testInput = {"Constant","Constant1"};
					assertEquals(blockObj.getInput().toArray()[0].toString(),testInput[0]);
					assertEquals(blockObj.getInput().toArray()[1].toString(),testInput[1]);
					System.out.println("Test "+blockObj.expression().toString());
					assertEquals(blockObj.expression().toString(),"Logical"+"\n"+"Operator=(Constant OR Constant1)");
					
					
				}

				/*
				 * For Delay block there shouldn't be FP. Also check for delay
				 * length and add additional delay blocks to delayLengthList
				 */
				
				
				// Delay is not part of model under test
				
				if (((Variable) blockObj.getOutput()).getName().startsWith(
						Constants.DELAY)) {
					List<Delay> delayLengthSet = new ArrayList<Delay>();
					Block delayObj;
					for (int i = 0; i < ((Delay) blockObj).getDelayLength() - 1; i++) {
						String name = ((Variable) blockObj.getOutput())
								.getName() + "_" + "delay_" + i;
						String tempInput;
						if (i == 0)
							tempInput = sourceNode;
						else
							tempInput = ((Variable) blockObj.getOutput())
									.getName() + "_" + "delay_" + (i - 1);

						delayObj = new Delay(name);
						delayObj.setInput(tempInput, destPort);

						List<Expression> inputTemp = new ArrayList<Expression>();
						inputTemp = delayObj.getInput();
						delayObj.getAccfg().setInput(inputTemp);
						List<Expression> exprTemp = new ArrayList<Expression>();
						exprTemp.add(delayObj.expression());
						delayObj.getAccfg().setDelay(exprTemp);

						delayLengthSet.add(0, (Delay) delayObj);

					}

					((Delay) blockObj).setDelayLengthList(delayLengthSet);
					blockObj.getAccfg().setDelay(expr);

					// Finally only sourceNode is input to delay block
					if (!flag) {
						blockObj.setInput(sourceNode, destPort);
						input = blockObj.getInput();

						blockObj.getAccfg().setInput(input);
					}
				}

				else {

					blockObj.getAccfg().setFp(expr);
				}

			}

		}
		
		
		
		
	}

	public static ArrayList<String> parseLineForPort(NodeList attributes) {
		// TODO Auto-generated method stub

		String sourceNode = "";

		boolean store = false;

		ArrayList<String> tempList = new ArrayList<String>();
		for (int iter = 0; iter < attributes.getLength(); iter++) {

			if (attributes.item(iter).getNodeName().equalsIgnoreCase("P")
					|| attributes.item(iter).getNodeName()
							.equalsIgnoreCase("Branch")) {

				if (attributes.item(iter).getNodeName()
						.equalsIgnoreCase("Branch")) {

					NodeList branchChildren = attributes.item(iter)
							.getChildNodes();
					for (int brTemp = 0; brTemp < branchChildren.getLength(); brTemp++) {
						if (branchChildren.item(brTemp).getNodeName()
								.equalsIgnoreCase("P")) {

							NamedNodeMap temp = branchChildren.item(brTemp)
									.getAttributes();

							for (int tempIter = 0; tempIter < temp.getLength(); tempIter++) {

								if (temp.item(tempIter).getNodeValue()
										.equalsIgnoreCase("DstBlock")) {

									if (branchChildren.item(brTemp)
											.getTextContent()
											.startsWith(Constants.SUB_SYS_CASE)) {

										store = true;

									}
								}

								if (temp.item(tempIter).getNodeValue()
										.equalsIgnoreCase("DstPort")) {

									if (store) {
										tempList.add(sourceNode);

										TestPortBranchSubsystem.portMap.put(branchChildren
												.item(brTemp).getTextContent(),
												sourceNode);
										/* We use portMap in ParseXML class because when InPort block is created ,
										   portMap in ParseXML is referred to setValue()
										*/
										ParseXML.portMap.put(branchChildren
												.item(brTemp).getTextContent(),
												sourceNode);
										store = false;
									}

								}

							}

						}
					}

				}

				else {

					NamedNodeMap temp = attributes.item(iter).getAttributes();

					for (int tempIter = 0; tempIter < temp.getLength(); tempIter++) {

						if (temp.item(tempIter).getNodeValue()
								.equalsIgnoreCase("SrcBlock")) {

							sourceNode = attributes.item(iter).getTextContent();

						}

						if (temp.item(tempIter).getNodeValue()
								.equalsIgnoreCase("DstBlock")) {

							if (attributes.item(iter).getTextContent()
									.startsWith(Constants.SUB_SYS_CASE)) {
								store = true;

							}
						}
						if (temp.item(tempIter).getNodeValue()
								.equalsIgnoreCase("DstPort")) {

							if (store) {
								tempList.add(sourceNode);
								TestPortBranchSubsystem.portMap.put(attributes.item(iter)
										.getTextContent(), sourceNode);
								
								/* We use portMap in ParseXML class because when InPort block is created ,
								   portMap in ParseXML is referred to setValue()
								*/
								ParseXML.portMap.put(attributes.item(iter)
										.getTextContent(), sourceNode);
								store = false;
							}

						}

					}
				}
			}

		}
		return tempList;
	}
}
