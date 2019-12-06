package findingmaxflow;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

// sources: we use and modify edmonds-karp algorithm from
// https://www.geeksforgeeks.org/ford-fulkerson-algorithm-for-maximum-flow-problem/
public class MaxFlowGenerator {
	int[][] mGraph;
	int maxFlow;
	int V;
	FlowNetworkGenerator flow;
	
	// constructor
	// construct a maxflowgenerator given a flownetworkgenerator
	public MaxFlowGenerator(FlowNetworkGenerator flow) {
		this.flow = flow;
		this.mGraph=new int[flow.getSink()+1][flow.getSink()+1];
		this.maxFlow=0;
		this.V=flow.getSink()+1;
	}
	
	// use bfs to implement the edmonds-karp
	boolean bfs(int rGraph[][], int s, int t, int parent[]) 
    { 
        // Create a visited array to keep track of the vertices
        boolean visited[] = new boolean[V]; 
        for(int i=0; i<V; ++i) 
            visited[i]=false; 
  
        // Create a queue, enqueue source vertex and mark, for bfs
        LinkedList<Integer> queue = new LinkedList<Integer>(); 
        queue.add(s); 
        visited[s] = true; 
        parent[s]=-1; 
  
        // if the queue is not empty
        while (queue.size()!=0) 
        { 
            int u = queue.poll(); 
  
            for (int v=0; v<V; v++) 
            { 
                if (visited[v]==false && rGraph[u][v] > 0) 
                { 
                    queue.add(v); 
                    parent[v] = u; 
                    visited[v] = true; 
                } 
            } 
        } 
  
        // return if we reached sink in BFS starting from source
        return (visited[t] == true); 
    } 
  
    // calculate the maxflow of the maxflowgenerator
	// store the number of the maxflow and the graph of the maxflow as fields in the maxflowgenerator
    void maxflow() 
    { 
        int u, v; 
  
        // Create a residual graph and fill the residual graph 
        // with given capacities in the original graph as 
        // residual capacities in residual graph 
  
        // Residual graph where rGraph[i][j] indicates 
        // residual capacity of edge from i to j (if there 
        // is an edge. If rGraph[i][j] is 0, then there is 
        // not)  
        int rGraph[][] = new int[V][V];
        for (u = 0; u < V; u++) 
            for (v = 0; v < V; v++) 
                rGraph[u][v] = flow.graph[u][v]; 
  
        // This array is filled by BFS and to store path 
        int parent[] = new int[V]; 
  
        // Augment the flow while tere is path from source 
        // to sink 
        int s =flow.getSource();
        int t = flow.getSink();
        while (bfs(rGraph, s, t, parent)) 
        { 
            // Find minimum residual capacity of the edhes 
            // along the path filled by BFS. Or we can say 
            // find the maximum flow through the path found. 
            int path_flow = Integer.MAX_VALUE; 
            for (v=t; v!=s; v=parent[v]) 
            { 
                u = parent[v]; 
                path_flow = Math.min(path_flow, rGraph[u][v]); 
                this.mGraph[u][v] = path_flow;
            } 
  
            // update residual capacities of the edges and 
            // reverse edges along the path 
            for (v=t; v != s; v=parent[v]) 
            { 
                u = parent[v]; 
                rGraph[u][v] -= path_flow; 
                rGraph[v][u] += path_flow; 
                this.mGraph[u][v] = path_flow;
            } 
            this.maxFlow += path_flow; 
        } 
    } 
    
    public static String maxdot (int[][] mGraph, int[][] graph, int num, int maxflow) throws FileNotFoundException, UnsupportedEncodingException {
		String name = "maxgraph"+num+".dot";
		PrintWriter writer = new PrintWriter(name, "UTF-8");
        writer.println("digraph g{");
        writer.println();
        writer.println("rankdir = LR");
        int len = graph.length;
        for (int i = 0; i < len; i++) {
        	for (int j = 0; j < len; j++) {
        		if(mGraph[i][j]!=0) {
        			writer.println(i + " -> " + j + " [label = \" " + mGraph[i][j]+"/"+graph[i][j]+ " \"];");
        		}
        	}
        }
        writer.println();
        writer.println("label = " + "\" maxgraph "+num+":  maximum flow = "+maxflow+" \"");
        writer.println("}");
        writer.close();
        return name;
    }
    
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

