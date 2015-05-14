package com.iiitb.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import com.iiitb.blocks.Block;
import com.iiitb.blocks.Delay;
import com.iiitb.constant.Constants;

import expression.Expression;
import expression.Variable;

public class FetchInputFromLine {
	public static Map<String, LinkedList<DestNode>> adjacencyList = new HashMap<String, LinkedList<DestNode>>();

	public static void parseLine(ArrayList<Block> blockList, NodeList attributes) {
		// TODO Auto-generated method stub

		String sourceNode = "";
		String destNode = "";
		List<DestNode> destNodeTemp = new LinkedList<DestNode>();
		String destPort = "";
		// Map to generate adjacency list representation of subsystem
		DestNode nodeIni = new DestNode();
		for (int iter = 0; iter < attributes.getLength(); iter++) {

			if (attributes.item(iter).getNodeName().equalsIgnoreCase("P")
					|| attributes.item(iter).getNodeName()
							.equalsIgnoreCase("Branch"))

			{

				// Branch

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

		System.out.println("sourceNode is " + sourceNode);
		System.out.println("destination is " + destNodeTemp);

		if (sourceNode != "") {

			if (adjacencyList.get(sourceNode) != null) {

				adjacencyList.get(sourceNode).addAll(destNodeTemp);

			} else {
				LinkedList<DestNode> addList = new LinkedList<DestNode>();

				addList.addAll(destNodeTemp);

				adjacencyList.put(sourceNode, addList);

			}

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

				List<Expression> input = new ArrayList<Expression>();
				input = blockObj.getInput();
				blockObj.getAccfg().setInput(input);
				List<Expression> expr = new ArrayList<Expression>();

				expr.add(blockObj.expression());

				/*
				 * For Delay block there shouldn't be FP. Also check for delay
				 * length and add additional delay blocks to delayLengthList
				 */
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

	/**
	 * 
	 * Fetch the values for port from current subsystem and propagate it into
	 * inner subsystem
	 * 
	 * 
	 * 
	 * */

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
