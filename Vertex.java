/*
* File: Vertex.java
* Author: Riley Karp
* Date: 12/05/2016
*/

import java.util.HashMap;
import java.util.Collection;
import java.awt.Graphics;
import java.awt.Color;

public class Vertex extends Agent {

	public enum Direction { north, east, south, west}; //enumerated type for Directions
	private HashMap<Direction,Vertex> edges;
	private boolean marked;
	private int cost;
	private int root;
	private boolean visible;
	
	//returns the cardnial Direction opposite of the given Direction
	public static Direction opposite( Direction d ) {
		if( d == Direction.north ) {
			return Direction.south;
		}
		else if( d == Direction.south ) {
			return Direction.north;
		}
		else if( d == Direction.east ) {
			return Direction.west;
		}
		return Direction.east;
	}
	
	//creates a Vertex at the given row,col location, with the given root
	public Vertex( int row, int col, int root ) {
		super( row, col );
		edges = new HashMap<Direction,Vertex>();
		marked = false;
		cost = 0;
		this.root = root;
		visible = false;
	}
	
	//returns a reference to the Vertex neighboring this Vertex in the given Direction
	public Vertex getNeighbor( Direction dir ) {
		return edges.get( dir );
	}
	
	//returns a Collection of the Vertices neighboring this Vertex
	public Collection<Vertex> getNeighbors() {
		return edges.values();
	}
	
	//returns a reference to the root
	public int getRoot() {
		return root;
	}
	
	//returns a reference to the Vertex's visible field
	public boolean getVisible() {
		return this.visible;
	}
	
	//sets the Vertex's visible field to the given boolean value
	public void setVisible( boolean newVisible ) {
		this.visible = newVisible;
	}
	
	//sets the Vertex's root field to the given int value
	public void setRoot( int newRoot ) {
		this.root = newRoot;
	}
	
	//returns a reference to the cost associated with this Vertex
	public int getCost() {
		return cost;
	}
	
	//sets the cost associated with this Vertex
	public void setCost( int newCost ) {
		this.cost = newCost;
	}
	
	//returns a reference to the Vertex's marked field
	public boolean getMarked() {
		return marked;
	}
	
	//sets the Vertex's marked field
	public void setMarked( boolean newMarked ) {
		this.marked = newMarked;
	}
	
	//connects this Vertex to the given one in the specified Direction
	public void connect( Vertex other, Direction dir ) {
		edges.put( dir,other );
	}
	
	//draws the Vertex as a square in the given scale and Graphics window
	public void draw( Graphics g, int scale ) {
		if( !this.visible ) {
			return;
		}
		int xpos = this.getCol()*scale;
		int ypos = this.getRow()*scale;
		int border = 2;
		int half = scale/2;
		int eighth = scale/8;
		int sixteenth = scale/16;
		
		//draw walls of cave as square
		if( this.cost <= 2 ) {
			//wumpus is close
			g.setColor( Color.red );
		}
		else {
			//wumpus isn't close
			g.setColor( Color.black );
		}
		g.drawRect( xpos + border, ypos + border, scale - 2*border, scale - 2*border );
		
		//draw doorways as boxes
		g.setColor( Color.black );
		if( this.edges.containsKey( Direction.north ) ) {
			g.fillRect( xpos + half - sixteenth, ypos, eighth, eighth + sixteenth );
		}
		if( this.edges.containsKey( Direction.south ) ) {
            g.fillRect(xpos + half - sixteenth, ypos + scale - (eighth + sixteenth),
            eighth, eighth + sixteenth);
       	}
        if( this.edges.containsKey( Direction.west ) ) {
            g.fillRect(xpos, ypos + half - sixteenth, eighth + sixteenth, eighth);
        }
        if( this.edges.containsKey( Direction.east ) ) {
            g.fillRect(xpos + scale - (eighth + sixteenth), ypos + half - sixteenth,
            eighth + sixteenth, eighth);
        }
	}
	
	//returns a String displaying the Vertex's number of neighbors, cost, and marked field
	public String toString() {
		return "numNeighbors: " + edges.size() + ", cost: " + this.cost + ", marked: " + this.marked;
	}
	
	//tests the methods of the Vertex
	public static void main( String[] args ) {
		//test Direction enum
		Direction d1 = Direction.north;
		Direction d2 = Direction.south;
		Direction d3 = Direction.east;
		Direction d4 = Direction.west;
		System.out.println( "Opposite of north: " + opposite( d1 ) );
		System.out.println( "Opposite of south: " + opposite( d2 ) );
		System.out.println( "Opposite of east: " + opposite( d3 ) );
		System.out.println( "Opposite of west: " + opposite( d4 ) );
		
		//test Vertex class (still need to compare vertex objects)
		Vertex v1 = new Vertex(0,0,0);
		Vertex v2 = new Vertex(1,1,1);
		v1.setCost( 2 );
		v2.setCost( 4 );
		v2.setMarked( true );
		v1.connect( v2, d1 );
		v2.connect( v1, opposite(d1) );
		Vertex v3 = new Vertex(2,2,1);
		v2.connect( v3, d3 );
		System.out.println( "v1: " + v1 + "\nv2: " + v2 );
		System.out.println( "neighbor of v1 in Direction.north: " + v1.getNeighbor(d1) );
		System.out.println( "neighbor of v1 in Direction.south: " + v1.getNeighbor(d2) );
		System.out.println( "neighbors of v2: " + v2.getNeighbors() );
	}
}