package findingmaxflow;
import java.util.*;

public class FlowNetworkGenerator {
	protected int[][] graph;
	private int source;
	private int sink;
	private int V;
	private int E;


	public FlowNetworkGenerator() {
		Random rand = new Random();
		this.source =0;
		this.V = rand.nextInt(11)+5;
		this.sink =V-1;
		int high = V*(V-1)/2;
		int low = V-1;
		this.E = rand.nextInt(high-low) + low;
		int e = E;
		this.graph = new int[V][V];
		for (int i = 0; i < V; i++) {
			for (int j = 0; j < V; j++) {
				graph[i][j] = 0;
			}
		}
		for (int i = 0; i < V-1; i++) {
			graph[i][i+1] = rand.nextInt(100);
			e--;
		}
		while(e!= 0) {
			int i = rand.nextInt(V-1);
			int j = rand.nextInt(V-1)+1;
			if (i != j && graph[j][i] == 0) {
				graph[i][j] = rand.nextInt(100);
				e--;
			}
		}
	}

	public int getSource() {
		return this.source;
	}

	public int getSink() {
		return this.sink;
	}

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

