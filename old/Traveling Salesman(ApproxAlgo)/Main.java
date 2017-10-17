import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	ArrayList<Pair> vertexs = new ArrayList<Main.Pair>();
	ArrayList <Integer> ans= new ArrayList<Integer>();
	ArrayList <Edge>edges;
	int prev[] ;
	List <ArrayList <Pair> > gr;
	public class Pair {
	    int x, y;
	    Pair(){};
	    Pair(int x , int y ) {
	        this.x = x;
	        this.y = y;
	    }
	} 

	class Edge implements Comparable<Edge> {
	    int u,v,length;
	    Edge(){};
	    @Override
		public String toString() {
			return "Edge [u=" + u + ", v=" + v + "]";
		}
		Edge(int u , int v ) {
	        this.u = u;
	        this.v = v;
	        this.length = cityBlockDistance(vertexs.get(u), vertexs.get(v));
	    } 
		@Override
		public int compareTo(Edge a) {
			if (this.length==a.length){
				return 0;
			}else if(this.length<a.length){
				return -1;
			}else {
				return 1;
			}	
		}
	} 
	 int cityBlockDistance(Pair a, Pair b) {
		    return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
		}
	int fPrev(int x) {
	    while (prev[x] != x)
	        x = prev[x];
	    return x;
	}
	void connect(int a, int b) {
	    prev[fPrev(a)] = fPrev(b);
	}
	boolean[] used ;
	void dfs(int vertex) {
	    used[vertex] = true;
	    ans.add(vertex);
	    for (int i = 0; i < gr.get(vertex).size(); ++i) {
	        int to = gr.get(vertex).get(i).x;
	        if (!used[to])
	            dfs(to);
	    }
	}

	 public static void main(String[] args) {
		
		int n=0;
		int res = 0;
		Main m  = new Main();
		m.gr  = new ArrayList <ArrayList <Pair>>();
		File f = new File("input.txt");
		try {
			FileReader r = new FileReader(f);
			Scanner sc = new Scanner(r);
			m.vertexs.add(m.new Pair(0, 0) );
			n=sc.nextInt();
			m.edges = new ArrayList<Main.Edge>();
			for (int i=0 ; i< n*n ; i++){
				
			}
			m.prev= new int[n*n];
			m.used= new boolean [n*n];
			 for (int i = 0; i < n+1; i++) {
					m.gr.add(new ArrayList<Main.Pair>());
				}
			 for (int i = 1; i <= n; ++i){
				 m.vertexs.add(m.new Pair(sc.nextInt(), sc.nextInt()) );
			 }
			 sc.close();
			}catch( IOException e ){
				
			}
	    for (int i = 1; i <= n; ++i){m.prev[i] = i;}			
	    for (int i = 1; i <= n; ++i)
	        for (int j = 1; j <= n; ++j){
	            m.edges.add(m.new Edge(i, j));
	        }
	    java.util.Collections.sort(m.edges);
	    for (int i = 0; i < m.edges.size(); ++i) {
	        int l = m.edges.get(i).u;
	        int r = m.edges.get(i).v;
	        if (m.fPrev(l) == m.fPrev(r))
	            continue;
	        m.connect(l, r);
	        m.gr.get(l).add(m.new Pair(r, m.edges.get(i).length));
	        m.gr.get(r).add(m.new Pair(l, m.edges.get(i).length));
	    }
	    m.dfs(1);
	    m.ans.add(m.ans.get(0));
	    for (int i = 1; i < m.ans.size(); ++i)
	        res += m.cityBlockDistance(m.vertexs.get(m.ans.get(i-1)),m.vertexs.get(m.ans.get(i)));
		try(FileWriter writer = new FileWriter("output.txt", false))
        {
			 for (int i = 0; i < m.ans.size(); ++i)
				 writer.write(m.ans.get(i)+" ");
			 writer.write(" "+res);	
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        } 
	}	
}
