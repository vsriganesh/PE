package com.iiitb.utility;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import com.iiitb.blocks.Block;
import com.iiitb.blocks.Constant;
import com.iiitb.blocks.Sum;
import com.iiitb.constant.Constants;
import com.iiitb.factory.ConstHelper;
import com.iiitb.factory.SumHelper;
import com.iiitb.inter.IHelper;

public class BlockFactoryUtility {

	public static void setBlockAttributes(String attrToFetch,
			NodeList attributes, Block block) {
		IHelper helper = null;
		for (int iter = 0; iter < attributes.getLength(); iter++) {
			// Prevent #text tag
			if (attributes.item(iter).getNodeName() == Constants.PARA_TAG)

			{

				NamedNodeMap temp = attributes.item(iter).getAttributes();

				for (int tempIter = 0; tempIter < temp.getLength(); tempIter++) {
					if (temp.item(tempIter).getNodeValue()
							.equalsIgnoreCase(attrToFetch)) {

						if (block.getClass().isInstance(Constant.class)) {
							helper = new ConstHelper();
							helper.setAttr(attributes, iter, block);
						}

						if (block.getClass().isInstance(Sum.class)) {
							helper = new SumHelper();
							helper.setAttr(attributes, iter, block);
						}

					}

				}

			}

		}

	}

}
