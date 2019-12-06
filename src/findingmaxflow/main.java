package findingmaxflow;

import java.io.IOException;
/**
 * CSC301 Analysis of Algorithm
 * Assignment 06: Finding Max Flow
 * @author Yesheng Chen [chenyesh] and Kandice Wu [wuzhaoqi]
 */
public class main {
	 // for testing
    public static void main (String[] args) throws IOException, InterruptedException {
    	//generating flow network
    	for(int i=0; i<10; i++) {
    	FlowNetworkGenerator flow = new FlowNetworkGenerator();
    	
    	//generating flow network dot file
    	String file=flow.dotfile(flow.graph,i);
    	//preparing for png file
    	ProcessBuilder processBuilder = new ProcessBuilder();
    	String[] command = {"/bin/bash", "-c", "/usr/local/bin/dot -Tpng "+file+" -o "+file+".png"};
    	processBuilder.command(command);
    	//generating png from dot file 
    	Process process = processBuilder.start();
    	
    	//generating max flow network
    	MaxFlowGenerator max = new MaxFlowGenerator(flow);
    	max.maxflow();

    	//generating max flow dot file 
    	String maxfile = max.maxdot(max.mGraph,flow.graph,i,max.maxFlow);
    	
    	//preparing for png file
    	String[] command2 = {"/bin/bash", "-c", "/usr/local/bin/dot -Tpng "+maxfile+" -o "+maxfile+".png"};
    	processBuilder.command(command2);
    	
    	//generating png from dot file 
    	Process process2 = processBuilder.start();
    	}
    }
}
