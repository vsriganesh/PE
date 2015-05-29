package com.iiitb.utility;

import java.util.List;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import com.iiitb.blocks.Block;
import com.iiitb.blocks.Constant;
import com.iiitb.blocks.Delay;
import com.iiitb.blocks.Divide;
import com.iiitb.blocks.InPort;
import com.iiitb.blocks.LogicalOperator;
import com.iiitb.blocks.MinMax;
import com.iiitb.blocks.RelationalOperator;
import com.iiitb.blocks.Sum;
import com.iiitb.blocks.Switch;
import com.iiitb.constant.Constants;
import com.iiitb.helper.ConstHelper;
import com.iiitb.helper.DelayHelper;
import com.iiitb.helper.DivideHelper;
import com.iiitb.helper.InPortHelper;
import com.iiitb.helper.LogicalHelper;
import com.iiitb.helper.MinMaxHelper;
import com.iiitb.helper.RelationalHelper;
import com.iiitb.helper.SumHelper;
import com.iiitb.helper.SwitchHelper;
import com.iiitb.inter.IHelper;

public class BlockFactoryUtility {

	public static void setBlockAttributes(List<String> attrToFetchList,
			NodeList attributes, Block block) {
		IHelper helper = null;
		
		
		for (String attrToFetch : attrToFetchList) {

			for (int iter = 0; iter < attributes.getLength(); iter++) {
				// Prevent #text tag
				if (attributes.item(iter).getNodeName() == Constants.PARA_TAG)

				{

					NamedNodeMap temp = attributes.item(iter).getAttributes();

					for (int tempIter = 0; tempIter < temp.getLength(); tempIter++) {
					
						if (temp.item(tempIter).getNodeValue()
								.equalsIgnoreCase(attrToFetch)) {

							if (block.getClass() == Constant.class) {
								
								helper = new ConstHelper();
								helper.setAttr(attributes, iter, block,
										attrToFetch);

							}

							if (block.getClass() == Sum.class) {

								helper = new SumHelper();
								helper.setAttr(attributes, iter, block,
										attrToFetch);

							}

							if (block.getClass() == Delay.class) {

								helper = new DelayHelper();
								helper.setAttr(attributes, iter, block,
										attrToFetch);

							}

							if (block.getClass() == Switch.class) {

								helper = new SwitchHelper();
								helper.setAttr(attributes, iter, block,
										attrToFetch);

							}

							if (block.getClass() == Divide.class) {

								helper = new DivideHelper();
								helper.setAttr(attributes, iter, block,
										attrToFetch);

							}

							
							if (block.getClass() == RelationalOperator.class) {

								helper = new RelationalHelper();
								helper.setAttr(attributes, iter, block,
										attrToFetch);

							}

							if (block.getClass() == LogicalOperator.class) {

								helper = new LogicalHelper();
								helper.setAttr(attributes, iter, block,
										attrToFetch);

							}
							
						
							if (block.getClass() == MinMax.class) {

								helper = new MinMaxHelper();
								helper.setAttr(attributes, iter, block,
										attrToFetch);

							}
							
							if (block.getClass() == InPort.class) {

								
								helper = new InPortHelper();
								helper.setAttr(attributes, iter, block,
										attrToFetch);

							}
							
							

						}

					}

				}

			}

		}
	}
}
