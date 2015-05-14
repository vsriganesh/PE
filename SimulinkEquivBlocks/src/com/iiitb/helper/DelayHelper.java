package com.iiitb.helper;




import org.w3c.dom.NodeList;

import com.iiitb.blocks.Block;
import com.iiitb.blocks.Delay;
import com.iiitb.constant.Constants;
import com.iiitb.inter.IHelper;




public class DelayHelper implements IHelper {

	@Override
	public void setAttr(NodeList attributes, int iter, Block block,String attrToFetch) {
		// TODO Auto-generated method stub
		if(attrToFetch.equalsIgnoreCase(Constants.IC))
		((Delay)block).setInitialValue(Integer.parseInt(attributes.item(iter).getTextContent()));
		
		if(attrToFetch.equalsIgnoreCase(Constants.DELAY_LENGTH))
			((Delay)block).setDelayLength(Integer.parseInt(attributes.item(iter).getTextContent()));	
		
		
	}

}
