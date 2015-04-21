package com.iiitb.helper;

import org.w3c.dom.NodeList;

import com.iiitb.blocks.Block;
import com.iiitb.blocks.Switch;
import com.iiitb.constant.Constants;
import com.iiitb.inter.IHelper;

public class SwitchHelper implements IHelper {

	@Override
	public void setAttr(NodeList attributes, int iter, Block block,String attrToFetch) {
		

		// 0 is default threshold and this threshold is set initially when creating switch block.
		// Note: For default threshold , Threshold tag will not appear in XML
		
		if (attrToFetch
				.equalsIgnoreCase(Constants.THRESHOLD)) {

			((Switch) block).setThreshold(attributes.item(iter)
					.getTextContent());
		}

		if (attrToFetch
				.equalsIgnoreCase(Constants.CRITERIA)) {

			// Set >= expression or > expression
			// >= is default criteria and this criteria is set initially when creating switch block.
			// Note: For default criteria , Criteria tag will not appear in XML

			
			((Switch) block).setCriteria("Greater");
		}

		
	}

}
