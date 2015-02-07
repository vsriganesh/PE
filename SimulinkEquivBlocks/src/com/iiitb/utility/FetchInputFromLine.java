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

public class FetchInputFromLine {
	public static Map<String, LinkedList<String>> adjacencyList = new HashMap<String, LinkedList<String>>();

	public static Block parseLine(ArrayList<Block> blockList,
			NodeList attributes) {
		// TODO Auto-generated method stub

		String sourceNode = "";
		String destNode = "";
		// Map to generate adjacency list representation of subsystem

		for (int iter = 0; iter < attributes.getLength(); iter++) {

			if (attributes.item(iter).getNodeName() == "P")

			{
				// for a single <p>
				NamedNodeMap temp = attributes.item(iter).getAttributes();

				for (int tempIter = 0; tempIter < temp.getLength(); tempIter++) {

					// System.out.println("Testing :"
					// +temp.item(tempIter).getNodeValue());
					if (temp.item(tempIter).getNodeValue()
							.equalsIgnoreCase("SrcBlock")) {

						sourceNode = attributes.item(iter).getTextContent();
						System.out.println("src inside" + sourceNode);

					}

					if (temp.item(tempIter).getNodeValue()
							.equalsIgnoreCase("DstBlock")) {

						destNode = attributes.item(iter).getTextContent();
						System.out.println("dest inside" + destNode);
					}

				}
			}
		}

		if (sourceNode != "" && destNode != "") {
			// System.out.println("Entered ");
			System.out.println("src " + sourceNode);
			System.out.println("dest " + destNode);
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

				// System.out.println("1 "+destNode);
				// System.out.println("2 "+blockObj.getName());

				if (destNode.equalsIgnoreCase(blockObj.getName())) {
					blockObj.setInput(sourceNode);

					// System.out.println("Super ");

					if (blockObj.isInputSetFlag()) {

						// System.out.println("Entered both input");
						List<String> input = new ArrayList<String>();
						input = blockObj.getInput();
						blockObj.getAccfg().setInput(input);
						List<String> expr = new ArrayList<String>();
						expr.add(blockObj.expression());
						blockObj.getAccfg().setFp(expr);
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
