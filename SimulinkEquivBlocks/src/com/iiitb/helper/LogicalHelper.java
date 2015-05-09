package com.iiitb.helper;

import org.w3c.dom.NodeList;

import com.iiitb.blocks.Block;
import com.iiitb.inter.IHelper;

public class LogicalHelper implements IHelper {

	@Override
	public void setAttr(NodeList attributes, int iter, Block block,String attrToFetch) {
		// TODO Auto-generated method stub
		
		System.out.println("In Logical Block ***************************** "+attributes.item(iter).getTextContent());
		if (attributes.item(iter).getTextContent()
				.equalsIgnoreCase("AND")) {
			block.setSign(1);
		}

		else if (attributes.item(iter).getTextContent()
				.equalsIgnoreCase("OR")) {
			block.setSign(2);
		}
		else if (attributes.item(iter).getTextContent()
				.equalsIgnoreCase("NOT")) {
			block.setSign(3);
		}
		else if (attributes.item(iter).getTextContent()
				.equalsIgnoreCase("NXOR")) {
			block.setSign(4);
		}
		else if (attributes.item(iter).getTextContent()
				.equalsIgnoreCase("NOR")) {
			block.setSign(5);
		}
		else if (attributes.item(iter).getTextContent()
				.equalsIgnoreCase("NAND")) {
			
			block.setSign(6);
		}
		else if (attributes.item(iter).getTextContent()
				.equalsIgnoreCase("XOR")) {
			
			block.setSign(7);
		}
		else {

			block.setSign(1);
		}
	}
}
