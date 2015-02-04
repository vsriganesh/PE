package com.iiitb.utility;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import com.iiitb.blocks.*;

public class FetchInputFromLine {

	public static Block parseLine(ArrayList<Block> blockList, NodeList attributes) {
		// TODO Auto-generated method stub
		String sourceNode = "";
		String destNode = "";
		for (int iter = 0; iter < attributes.getLength(); iter++) {
			

			if (attributes.item(iter).getNodeName() == "P")

			{

				NamedNodeMap temp = attributes.item(iter).getAttributes();

				for (int tempIter = 0; tempIter < temp.getLength(); tempIter++) {
					
					//System.out.println("Testing :" +temp.item(tempIter).getNodeValue());
					if (temp.item(tempIter).getNodeValue()
							.equalsIgnoreCase("SrcBlock")) {

						sourceNode = attributes.item(iter).getTextContent();
						//System.out.println("src "+sourceNode);
						
					}

					if (temp.item(tempIter).getNodeValue()
							.equalsIgnoreCase("DstBlock")) {

						destNode = attributes.item(iter).getTextContent();
						//System.out.println("dest "+destNode);
					}

				}

				if (sourceNode != "" && destNode != "") {
						//System.out.println("Entered ");
					Iterator blockListIter = blockList.iterator();
					
					while (blockListIter.hasNext()) {
						
						Block blockObj = (Block) blockListIter.next();
						
						// CHECK THIS 
						
						//System.out.println("1 "+destNode);
						//System.out.println("2 "+blockObj.getName());
						
						if (destNode.equalsIgnoreCase(blockObj.getName())) {
							blockObj.setInput(sourceNode);
							
							//System.out.println("Super ");
							
							if(blockObj.isInputSetFlag())
							{
								
								//System.out.println("Entered both input");
								List<String> input = new ArrayList<String>();
								input = blockObj.getInput();
								blockObj.getAccfg().setInput(input);
								List<String> expr = new ArrayList<String>();
								expr.add(blockObj.expression());
								blockObj.getAccfg().setFp(expr);
								return blockObj;
								
							}
							else
							{
								return blockObj;
							}
							
							
							
							
						}
						

					}

				}

			}

		}
		return null;
	}

}