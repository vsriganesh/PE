package com.iiitb.test;

import org.w3c.dom.Document;

import com.iiitb.cfg.Accfg;
import com.iiitb.utility.ParseXML;

public class TestFeature {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Document doc = ParseXML
					.initializeDocument("C:/Users/vsriganesh/Documents/MATLAB/delay_timer.xml");

			/*
			 * 
			 * The currSubSystemNode represents the current subsystem node. For
			 * the first call since we are at the top most level we pass null to
			 * currSubSystemNode
			 */
			
			Accfg mergedAccfg = ParseXML.parseDocument(doc, doc.getDocumentElement());

			System.out.println("\n\nThe final merged ACCFG is : \n\n"+mergedAccfg);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
