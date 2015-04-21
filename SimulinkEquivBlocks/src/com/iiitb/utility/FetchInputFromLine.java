package com.iiitb.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import com.iiitb.blocks.Block;
import com.iiitb.blocks.Delay;
import com.iiitb.constant.Constants;

import expression.Expression;
import expression.Variable;

public class FetchInputFromLine {
	public static Map<String, LinkedList<String>> adjacencyList = new HashMap<String, LinkedList<String>>();

	public static Block parseLine(ArrayList<Block> blockList,
			NodeList attributes) {
		// TODO Auto-generated method stub

		String sourceNode = "";
		String destNode = "";
		String destPort="";
		// Map to generate adjacency list representation of subsystem

		for (int iter = 0; iter < attributes.getLength(); iter++) {

			if (attributes.item(iter).getNodeName() == "P")

			{
				// for a single <p>
				NamedNodeMap temp = attributes.item(iter).getAttributes();

				for (int tempIter = 0; tempIter < temp.getLength(); tempIter++) {

					 System.out.println("Testing :" +temp.item(tempIter).getNodeValue());
					if (temp.item(tempIter).getNodeValue()
							.equalsIgnoreCase("SrcBlock")) {

						sourceNode = attributes.item(iter).getTextContent();
						//System.out.println("src inside " + sourceNode);

					}

					if (temp.item(tempIter).getNodeValue()
							.equalsIgnoreCase("DstBlock")) {

						destNode = attributes.item(iter).getTextContent();
						//System.out.println("dest inside " + destNode);
					}
					
					// Used for switch to identify port of input and condition
					if (temp.item(tempIter).getNodeValue()
							.equalsIgnoreCase("DstPort")) {

						destPort = attributes.item(iter).getTextContent();
						//System.out.println("dest port " + destPort);
					}

				}
			}
		}

		if (sourceNode != "" && destNode != "") {
			// System.out.println("Entered ");
			System.out.println("src " + sourceNode);
			System.out.println("dest " + destNode);
			System.out.println("dest " + destPort);
			
			if (adjacencyList.get(sourceNode) != null) {

				adjacencyList.get(sourceNode).add(destNode);

			} else {
				LinkedList<String> addList = new LinkedList<String>();

				addList.add(destNode);

				adjacencyList.put(sourceNode, addList);

			}

			Iterator blockListIter = blockList.iterator();

			while (blockListIter.hasNext()) {

				Block blockObj = (Block) blockListIter.next();

				// CHECK THIS

				 System.out.println("1 "+destNode);
				 System.out.println("2 "+blockObj.getClass());

				if (destNode.equalsIgnoreCase(((Variable)blockObj.getOutput()).getName())) {
					
					// input is not set for delay block as for delay block input is based on delay length
					if(!destNode.startsWith(Constants.DELAY))
					blockObj.setInput(sourceNode,destPort);

					// System.out.println("Super ");

					if (blockObj.isInputSetFlag() || destNode.startsWith(Constants.DELAY)) {

						// System.out.println("Entered both input");
						
						//Flag to set input based on delay length.
						/* If delay length is 1 then input is directly the source node
						 */
						boolean flag = false;
						if(((Variable)blockObj.getOutput()).getName().startsWith(Constants.DELAY) && ((Delay)blockObj).getDelayLength()>1)
						{
							blockObj.setInput(((Variable)blockObj.getOutput()).getName()+"_"+"delay_"+(((Delay)blockObj).getDelayLength()-2),destPort);
						}
						else
						{
							blockObj.setInput(sourceNode,destPort);
							flag = true;
						}
						
						
						List<Expression> input = new ArrayList<Expression>();
						input = blockObj.getInput();
						blockObj.getAccfg().setInput(input);
						List<Expression> expr = new ArrayList<Expression>();
						expr.add(blockObj.expression());
						
						/* For Delay block there shouldn't be FP. Also check for delay length
						 and add additional delay blocks to delayLengthList */
						if(((Variable)blockObj.getOutput()).getName().startsWith(Constants.DELAY))
						{
							List<Delay> delayLengthSet = new ArrayList<Delay>();
							Block delayObj;
							for(int i=0;i<((Delay)blockObj).getDelayLength()-1;i++)
							{
								String name = ((Variable)blockObj.getOutput()).getName()+"_"+"delay_"+i;
								String tempInput;
								if(i==0)
									tempInput = sourceNode;
								else
									tempInput = ((Variable)blockObj.getOutput()).getName()+"_"+"delay_"+(i-1);
								
								
								delayObj = new Delay(name); 
								delayObj.setInput(tempInput, destPort);
								
								
								List<Expression> inputTemp = new ArrayList<Expression>();
								inputTemp = delayObj.getInput();
								delayObj.getAccfg().setInput(inputTemp);
								List<Expression> exprTemp = new ArrayList<Expression>();
								exprTemp.add(delayObj.expression());
								delayObj.getAccfg().setDelay(exprTemp);
								//System.out.println("Added "+delayObj);
								delayLengthSet.add(0,(Delay)delayObj);
								
							}
							//System.out.println("TESTINGGGGGGGGGGGG"+delayLengthSet);
							((Delay)blockObj).setDelayLengthList(delayLengthSet);
							blockObj.getAccfg().setDelay(expr);
							
							// Finally only sourceNode is input to delay block
							if(!flag)
							{
							blockObj.setInput(sourceNode,destPort);
							input = blockObj.getInput();
							//System.out.println("input "+input);
							blockObj.getAccfg().setInput(input);
							}
						}
						
						else
						{
						
						blockObj.getAccfg().setFp(expr);
						}
						return blockObj;

					} else {
						return blockObj;
					}

				}

			}

		}

		return null;
	}

}
