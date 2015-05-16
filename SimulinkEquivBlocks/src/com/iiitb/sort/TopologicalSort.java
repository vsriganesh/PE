package com.iiitb.sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Sorts graph - Subsystem representation
 * 
 */

public class TopologicalSort {

	/**
	 * 
	 * Identify Root Nodes of the Subsystem
	 * 
	 */
	public ArrayList<String> rootNodes(
			Map<String, LinkedList<String>> adjacencyList) {

		List<String> rootNodes = new ArrayList<String>();
		Iterator<String> keySetIter = adjacencyList.keySet().iterator();

		String key = "";
		String rootCheck = "";
		while (keySetIter.hasNext()) {
			rootCheck = (String) keySetIter.next();
			Iterator<String> iter = adjacencyList.keySet().iterator();
			boolean rootCheckFlag = true;
			while (iter.hasNext()) {
				key = (String) iter.next();

				if (adjacencyList.get(key).contains(rootCheck)) {
					rootCheckFlag = false;
					break;
				}

			}

			if (rootCheckFlag)
				rootNodes.add(rootCheck);

		}

		return (ArrayList<String>) rootNodes;

	}

	/**
	 * 
	 * @param adjacencyList
	 *            - Subsystem in adjacency list representation
	 * @return - Returns incoming edge count of each vertex (* Only for vertex
	 *         that has an outgoing edge)
	 */

	public Map<String, Integer> incomingCount(
			Map<String, LinkedList<String>> adjacencyList) {

		Map<String, Integer> countIncoming = new HashMap<String, Integer>();
		Iterator<String> keySetIter = adjacencyList.keySet().iterator();

		String key = "";
		String rootCheck = "";
		int incomingCount = 0;
		while (keySetIter.hasNext()) {
			rootCheck = (String) keySetIter.next();
			incomingCount = 0;
			Iterator<String> iter = adjacencyList.keySet().iterator();

			while (iter.hasNext()) {
				key = (String) iter.next();

				if (adjacencyList.get(key).contains(rootCheck)) {

					incomingCount++;
				}

			}

			countIncoming.put(rootCheck, incomingCount);

		}
		return countIncoming;
	}

	/**
	 * Sorts graph (Represented in Adjacency list representation) - Topological
	 * Sort of the subsystem . "rootNodes" hold the root nodes of the subsystem
	 * 
	 */
	public ArrayList<String> sortGraph(
			Map<String, LinkedList<String>> adjacencyList) {

		// System.out.println("Adjacency list "+adjacencyList);
		Set<String> delayList = new TreeSet<String>();
		Iterator<String> testIter = adjacencyList.keySet().iterator();

		// To remove delay block as delay block forms cycle

		while (testIter.hasNext()) {
			String key = (String) testIter.next();

			List<String> tempList = adjacencyList.get(key);
			Iterator<String> temp = tempList.iterator();
			while (temp.hasNext()) {
				String tempDelay = (String) temp.next();
				if (tempDelay.contains("Delay")) {

					temp.remove();

				}
			}
		}

		// Identify Root Nodes in the Subsystem
		List<String> rootNodes = rootNodes(adjacencyList);

		// Get incoming edge count for each vertex
		Map<String, Integer> incomingCount = incomingCount(adjacencyList);

		// Set is used to avoid duplicate entries
		Set<String> lastNodeList = new TreeSet<String>();

		List<String> sortedList = new ArrayList<String>();
		String rootNode = "";

		while (!rootNodes.isEmpty()) {
			rootNode = (String) rootNodes.get(0);

			sortedList.add(rootNode);
			String adjNode = "";
			int count;
			Iterator<String> adjNodeList = adjacencyList.get(rootNode)
					.iterator();
			while (adjNodeList.hasNext()) {
				adjNode = (String) adjNodeList.next();

				/*
				 * Ignore this node as this node has no outgoing edges and has
				 * only incoming edges. So anyway this node will be the last
				 * node in our sorted list
				 */
				if (incomingCount.get(adjNode) == null) {
					lastNodeList.add(adjNode);
					continue;
				}
				count = incomingCount.get(adjNode);
				count = count - 1;
				if (count == 0) {

					/*
					 * adjNode has become a root node when incoming edge count
					 * is 0. So remove it from adjNodeList and add to rootNodes
					 * list.
					 */

					rootNodes.add(adjNode);

					adjNodeList.remove();
				} else {
					// Decrement incoming edge count of adjNode and add to
					// incomingCount Map
					incomingCount.put(adjNode, count);
				}
			}

			// Remove the current root node from rootNodes list
			rootNodes.remove(0);
		}
		// Nodes with no out going edges are added here. These nodes were
		// initially ignored
		sortedList.addAll(lastNodeList);

		// All delay blocks should be added in the end of sortedList for a
		// subsystem

		Iterator<String> iter = sortedList.iterator();
		while (iter.hasNext()) {
			String block = (String) iter.next();
			if (block.startsWith("Delay")) {
				delayList.add(block);
				iter.remove();

			}
		}

		sortedList.addAll(delayList);

		return (ArrayList<String>) sortedList;
	}
}
