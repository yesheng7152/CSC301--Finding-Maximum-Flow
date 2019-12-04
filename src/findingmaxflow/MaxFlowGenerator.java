package findingmaxflow;

import java.util.*;

public class MaxFlowGenerator {
	int[][] rGraph;
	int maxFlow;
	int V;
	FlowNetworkGenerator flow;

	public MaxFlowGenerator(FlowNetworkGenerator flow) {
		this.flow = flow;
		this.rGraph=new int[flow.getSink()+1][flow.getSink()+1];
		this.maxFlow=0;
		this.V=flow.getSink()+1;
	}

	boolean bfs(int rGraph[][], int s, int t, int parent[])
    {
        // Create a visited array and mark all vertices as not
        // visited
        boolean visited[] = new boolean[V];
        for(int i=0; i<V; ++i)
            visited[i]=false;

        // Create a queue, enqueue source vertex and mark
        // source vertex as visited
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(s);
        visited[s] = true;
        parent[s]=-1;

        // Standard BFS Loop
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

        // If we reached sink in BFS starting from source, then
        // return true, else false
        return (visited[t] == true);
    }

    // Returns tne maximum flow from s to t in the given graph
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

        for (u = 0; u < V; u++)
            for (v = 0; v < V; v++)
                this.rGraph[u][v] = flow.graph[u][v];

        // This array is filled by BFS and to store path
        int parent[] = new int[V];

        // Augment the flow while tere is path from source
        // to sink
        int s =flow.getSource();
        int t = flow.getSink();
        while (bfs(this.rGraph, s, t, parent))
        {
            // Find minimum residual capacity of the edhes
            // along the path filled by BFS. Or we can say
            // find the maximum flow through the path found.
            int path_flow = Integer.MAX_VALUE;
            for (v=t; v!=s; v=parent[v])
            {
                u = parent[v];
                path_flow = Math.min(path_flow, this.rGraph[u][v]);
            }

            // update residual capacities of the edges and
            // reverse edges along the path
            for (v=t; v != s; v=parent[v])
            {
                u = parent[v];
                this.rGraph[u][v] -= path_flow;
                this.rGraph[v][u] += path_flow;
            }
            this.maxFlow += path_flow;
            for(int i = 0; i <V; i++) {
            	for (int j = 0; j<V; j++) {
            		if(flow.graph[i][j]==0) {
            			this.rGraph[i][j]=0;
            		}else if(flow.graph[i][j]-this.maxFlow == this.rGraph[i][j]) {
            			this.rGraph[i][j]=this.maxFlow;}
//            		else {
//            			System.out.println(i);
//            			this.rGraph[i][j] = flow.graph[i][j]-this.rGraph[i][j];}
            	}
            }
            // Add path flow to overall flow
        }
    }

    public static void main (String[] args) {
    	FlowNetworkGenerator flow = new FlowNetworkGenerator();
    	flow.print(flow.graph);
    	System.out.println();
    	MaxFlowGenerator max = new MaxFlowGenerator(flow);
    	max.maxflow();
    	flow.print(max.rGraph);
    	System.out.println();
    	System.out.println(max.maxFlow);
    }
}

