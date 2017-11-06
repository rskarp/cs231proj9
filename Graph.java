/*
* File: Graph.java
* Author: Riley Karp
* Date: 11/28/2016
*/

import java.util.ArrayList;

public class Graph {

	private ArrayList<Vertex> vertices;
	
	//creates an empty Graph object
	public Graph() {
		vertices = new ArrayList<Vertex>();
	}
	
	//returns an ArrayList of the vertices in the Graph
	public ArrayList<Vertex> getVertices() {
		return vertices;
	}
	
	//returns the number of vertices in the Graph
	public int vertexCount() {
		return vertices.size();
	}
	
	//returns the root associated with the specified Vertex in the Graph 
	public int FIND( Vertex v ) {
		return v.getRoot();
	}
	
	//add an edge (connection) in the specified Direction between the two given vertices
	//and adds the two vertices to the Graph if they're not already there
	public void addEdge( Vertex v1, Vertex.Direction dir, Vertex v2 ) {
		if( vertices.contains(v1) == false ) {
			vertices.add( v1 );
		}
		if( vertices.contains(v2) == false ) {
			vertices.add( v2 );
		}
		v1.connect( v2, dir );
		v2.connect( v1, Vertex.opposite(dir) );
		
		int root_v1 = this.FIND(v1);
		int root_v2 = this.FIND(v2);
		if( root_v1 != root_v2 ) {
			v2.setRoot( root_v1 );
		}
	}
	
	//finds the shortest path from every Vertex in the Graph to the specified Vertex
	//using Dijkstra's Algorithm
	public void shortestPath( Vertex vO ) {
		//initialized all vertices to be unmarked and have infinite cost
		for( Vertex v: vertices ) {
			v.setMarked( false );
			v.setCost( Integer.MAX_VALUE );
		}
		PQHeap<Vertex> q = new PQHeap<Vertex>( new VertexComparator() );
		vO.setCost( 0 );
		q.add( vO );
		
		//find shortest distance
		while( q.size() > 0 ) {
			Vertex v = q.remove(); //vertex in q with lowest cost
			v.setMarked( true );
			for( Vertex w: v.getNeighbors() ) {
				if( w.getMarked() == false && (v.getCost() + 1) < w.getCost() ) {
					w.setCost( v.getCost() + 1 );
					q.removeItem( w ); //memory is lost since remove has an unused return value
					q.add( w );
				}
			}
		}
	}
	
	//tests methods of the Graph
	public static void main( String[] args ) {
		Graph g = new Graph();
		//create vertices
		Vertex v0 = new Vertex( 1,1,0 );
		Vertex v1 = new Vertex( 2,2,1 );
		Vertex v2 = new Vertex( 3,3,2 );
		Vertex v3 = new Vertex( 4,4,3 );
		Vertex v4 = new Vertex( 5,5,4 );
		Vertex v5 = new Vertex( 6,6,5 );
		
		//add edges
		g.addEdge( v0, Vertex.Direction.east, v1 );
		g.addEdge( v0, Vertex.Direction.south, v5 );
		g.addEdge( v1, Vertex.Direction.east, v2 );
		g.addEdge( v1, Vertex.Direction.south, v4 );
		g.addEdge( v2, Vertex.Direction.south, v3 );
		g.addEdge( v4, Vertex.Direction.east, v3 );
		g.addEdge( v5, Vertex.Direction.east, v4 );
		System.out.println( "vertexCount: " + g.vertexCount() );
		
		//test shortestPath( v0 )
		g.shortestPath( v0 );
		for( Vertex v: g.getVertices() ) {
			System.out.println( "Shortest distance from " + v.getRoot() + " to v0: " + v.getCost() );
		}
	}
}