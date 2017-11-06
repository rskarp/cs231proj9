/*
* File name: Landscape.java
* Author: Riley Karp
* Date: 12/09/2016
*/

import java.awt.Graphics;
import java.util.Random;

public class Landscape {

	private Hunter hunter;
	private Wumpus wumpus;
	private Graph graph;
	private int rows;
	private int cols;
	private Vertex[][] grid;
	private Random rand;
	private Vertex hunterStart;
	
	//creates a Landscape object that holds a grid of Vertices with a Hunter and Wumpus
	public Landscape( int cols, int rows, boolean randGraph ) {
		this.rows = rows;
		this.cols = cols;
		grid = new Vertex[rows][cols];
		hunter = new Hunter(0,0);//note: the given row,col location is irrelevant & not used
		wumpus = new Wumpus(1,1);//note: the given row,col location is irrelevant & not used
		graph = new Graph();
		rand = new Random();
		
		//create rows*cols vertices
		int counter = 0;
		for( int i = 0; i < rows; i++ ) {
			for( int j = 0; j < cols; j++ ) {
				grid[i][j] = new Vertex( i,j, counter );
				counter++;
			}
		}
		
		//connect vertices 
		if( randGraph == false ) { //connect all vertices to their surrounding vertices
			for( int i = 0; i < rows; i++ ) {
				for( int j = 0; j < cols; j++ ) {
					if( j+1 < cols ) {
						graph.addEdge( grid[i][j], Vertex.Direction.east, grid[i][j+1] );
					}
					if( i+1 < rows ) {
						graph.addEdge( grid[i][j], Vertex.Direction.south, grid[i+1][j] );
					}
				}
			}
		}
		else { //randomly connect vertices to a random number of other vertices
			for( int i = 0; i < rows; i++ ) {
				for( int j = 0; j < cols; j++ ) {
					int connections = rand.nextInt(3); //sets # of connections to 0,1,or 2
					//while the Vertex has less neighbors than the connections variable
					while( grid[i][j].getNeighbors().size() < connections ) {
						int side = rand.nextInt(4); //randomly choose which side to connect
						if( j+1 < cols && side == 0 ) {
							graph.addEdge( grid[i][j], Vertex.Direction.east, grid[i][j+1] );
						}
						else if( i+1 < rows && side == 1 ) {
							graph.addEdge( grid[i][j], Vertex.Direction.south, grid[i+1][j] );
						}
						else if( i-1 > 0 && side == 2 ) {
							graph.addEdge( grid[i][j], Vertex.Direction.north, grid[i-1][j] );
						}
						else if( j-1 > 0 && side == 3 ) {
							graph.addEdge( grid[i][j], Vertex.Direction.west, grid[i][j-1] );
						}
					}
				}
			}
		}
		
		//place Hunter and Wumpus in random unique places in the same connected
		//component of the graph
		int huntLoc = rand.nextInt( graph.getVertices().size() );
		while( graph.getVertices().get( huntLoc ).getNeighbors().size() < 2 ) {
			huntLoc = rand.nextInt( graph.getVertices().size() ); //memory loss of last random int
		}//makes it so the Wumpus isn't in the only connected room to the Hunter
		hunterStart = graph.getVertices().get( huntLoc );
		hunter.setLocation( hunterStart );
		int wumpLoc = rand.nextInt(  graph.getVertices().size() );
		while( wumpLoc == huntLoc || graph.FIND(hunter.getLocation()) !=
			graph.FIND(graph.getVertices().get(wumpLoc)) ) {
			wumpLoc = rand.nextInt(  graph.getVertices().size() );//memory loss of last random int
		}
		wumpus.setHome( graph.getVertices().get(wumpLoc) );
		
		//assigns cost to each vertex based on distance to Wumpus
		graph.shortestPath( wumpus.getHome() );
	}
	
	//returns number of rows in Landscape
	public int getRows() {
		return this.rows;
	}
	
	//returns number of cols in Landscape
	public int getCols() {
		return this.cols;
	}
	
	//returns a reference to the Vertex at which the Hunter started
	public Vertex getHunterStart() {
		return this.hunterStart;
	}
	
	//returns reference to the Graph of vertices
	public Graph getGraph() {
		return this.graph;
	}
	
	//returns reference to the Hunter
	public Hunter getHunter() {
		return this.hunter;
	}
	
	//returns reference to the Wumpus
	public Wumpus getWumpus() {
		return this.wumpus;
	}
	
	//returns string with dimensions of Landscape (rows,cols)
	public String toString() {
		return "(" + this.getRows() + "," + this.getCols() + ")";
	}
	
	//draws the agents
	public void draw( Graphics g, int scale ) {
		//draw background agents
		for( Agent a: graph.getVertices() ) {
			a.draw( g, scale );
		}
		hunter.draw( g, scale );
		wumpus.draw( g, scale );
	}
	
	//tests the methods of class
	public static void main( String[] args ) {
		Landscape l = new Landscape( 5,5,false );
		System.out.println( "Rows: " + l.getRows() + ", Cols: " + l.getCols() );
		System.out.println( "Dimensions: " + l.toString() );
		System.out.println( "Num vertices: " + l.getGraph().vertexCount() );
	}
}