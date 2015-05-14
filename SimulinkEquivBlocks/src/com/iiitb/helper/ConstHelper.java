package com.iiitb.helper;

import org.w3c.dom.NodeList;

import com.iiitb.blocks.Block;
import com.iiitb.inter.IHelper;

public class ConstHelper implements IHelper {
	@Override
	public void setAttr(NodeList attributes, int iter, Block block,String attrToFetch) {

		/* Value of constant block. If the value of constant block is 1 then the XML does not generate
		 value tag for constant block. So in else part by default 1 is set.*/
		
		
		if(attributes.item(iter).getTextContent()!= null && attributes.item(iter).getTextContent() !="" )
		block.setValue(attributes.item(iter).getTextContent());
		else
			block.setValue("1");
		// Set FP based on value
		/*List<Expression> expr = new ArrayList<Expression>();
		expr.add(block.expression());*/
		
		block.getAccfg().getFp().clear();
		block.getAccfg().getFp().add(block.expression());

	}

}
