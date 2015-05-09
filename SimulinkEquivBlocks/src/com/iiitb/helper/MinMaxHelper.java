package com.iiitb.helper;

import org.w3c.dom.NodeList;

import com.iiitb.blocks.*;
import com.iiitb.constant.Constants;
import com.iiitb.inter.IHelper;

public class MinMaxHelper implements IHelper {

	@Override
	public void setAttr(NodeList attributes, int iter, Block block,String attrToFetch) {
		// TODO Auto-generated method stub
		
		
		System.out.println("attrToFetch ******************* MINMAX helper ********************** "+attrToFetch);
		
		if(attrToFetch.equalsIgnoreCase(Constants.FUNCTION))
		{
		if (attributes.item(iter).getTextContent()
				.contains("max")) {
			block.setSign(2);
		}

	

		else {

			block.setSign(1);
		}
		}
		
		
		
		if(attrToFetch.equalsIgnoreCase(Constants.INPUT))
		{
		
			((MinMax)block).setNoOfInputs(Integer.parseInt(attributes.item(iter).getTextContent()));
		

	

	
		}
	}
}
