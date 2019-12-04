package findingmaxflow;
import java.util.*;


public class FlowNetworkGenerator {
	
	// field
	protected int[][] graph;
	private int source;
	private int sink;
	private int V;
	private int E;
	
	// constructor
	public FlowNetworkGenerator() {
		Random rand = new Random();
		// source is 0
		this.source =0;
		// number of vertices is randomly generated from 5 to 15
		this.V = rand.nextInt(11)+5;
		// sink is the number of vertices since we start from 0
		this.sink =V-1;
		// the maximum of number of edges is when every two vertices is connected
		int high = V*(V-1)/2;
		// the minimum of number of edges is all vertices are connected in a line
		int low = V-1;
		// edge is randomly generated between max and min of number of edges
		this.E = rand.nextInt(high-low) + low;
		int e = E;
		// assign all the capacity to 0
		this.graph = new int[V][V];
		for (int i = 0; i < V; i++) {
			for (int j = 0; j < V; j++) {
				graph[i][j] = 0;
			}
		}
		// connect a line so that there is a flow from source to sink
		// and every vertex is involved in a flow from source to sink
		for (int i = 0; i < V-1; i++) {
			// the capacity is randomly generated from 0 to 100
			graph[i][i+1] = rand.nextInt(100);
			e--;
		}
		
		// randomly generated the remaining edges in the flow graph
		while(e!= 0) {
			int i = rand.nextInt(V-1);
			int j = rand.nextInt(V-1)+1;
			// if the edge is from a vertex and to itself
			// the edge and the reversed is not assigned
			if (i != j && graph[j][i] == 0 && graph[i][j] == 0) {
				graph[i][j] = rand.nextInt(100);
				e--;
			}
		}
	}
	
	// get the source of the flow network
	public int getSource() {
		return this.source;
	}
	
	// get the sink of the flow network
	public int getSink() {
		return this.sink;
	}
		
	// print the network in format of a 2d array
	// the row index is the from, column is to, the number is capacity
		public static void print (int[][] graph) {
			int m = graph.length;
			int n = graph[0].length;
			for(int i =0 ; i<m; i++) {
				for (int j = 0; j<n; j++)
					System.out.print (graph[i][j] + " ");
				System.out.println();
			}
		}
		
		public static void main (String[] args) {
			FlowNetworkGenerator flow = new FlowNetworkGenerator();
			print(flow.graph);
		}
}