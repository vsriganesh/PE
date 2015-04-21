package com.iiitb.inter;

import org.w3c.dom.NodeList;

import com.iiitb.blocks.Block;

/**
 * Interface for helper class
 *
 */
public interface IHelper {

	public void setAttr(NodeList attributes, int iter, Block block,String attrToFetch);
}
