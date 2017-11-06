/*
* File: Wumpus.java
* Author: Riley Karp
* Date: 12/09/2016
*/

import java.awt.Graphics;
import java.awt.Color;

public class Wumpus extends Agent {

	public enum WumpusState { won, lost, hiding };//enumerated type for state of Wumpus
	private Vertex home;
	private WumpusState state;
	
	//creates a Wumpus object at the given row,col location
	public Wumpus( int row, int col ) {
		super( row,col );
		home = null;
		state = WumpusState.hiding;
	}
	
	//returns a reference to the state field of the Wumpus
	public WumpusState getState() {
		return this.state;
	}
	
	//sets the state field of the Wumpus to the given WumpusState
	public void setState( WumpusState newState ) {
		this.state = newState;
	}
	
	//returns a reference to the Vertex location of the Wumpus
	public Vertex getHome() {
		return this.home;
	}
	
	//sets the Vertex location of the Wumpus
	public void setHome( Vertex newHome ) {
		this.home = newHome;
	}
	
	//draws the Wumpus as a smiling circle if it wins, and a frowning circle if it loses
	public void draw( Graphics g, int scale ) {
		if( state == WumpusState.hiding ) {
			return;
		}
		if( state == WumpusState.won ) {
			g.setColor( Color.cyan );
		}
		else { //if state == WumpusState.lost
			g.setColor( Color.darkGray );
		}
		g.fillOval( home.getCol()*scale + 2 + (3*scale/8), home.getRow()*scale +
		2 + (3*scale/8), scale/4, scale/4 );
		//face
		
		if( state == WumpusState.won ) {
			g.setColor( Color.magenta );
			//smile
			g.drawArc( home.getCol()*scale + 2 + (7*scale/16), home.getRow()*scale +
			2 + (scale/2), scale/10, scale/20,0,-180 );
		}
		else {
			g.setColor( Color.red );
			//frown
			g.drawArc( home.getCol()*scale + 2 + (7*scale/16), home.getRow()*scale +
			2 + (scale/2), scale/10, scale/20,0,180 );
		}
		//eyes
		g.fillOval(home.getCol()*scale + 2 + (scale/2), home.getRow()*scale +
		2 + (7*scale/16), scale/18, scale/18 );
		g.fillOval(home.getCol()*scale + 2 + (7*scale/16), home.getRow()*scale +
		2 + (7*scale/16), scale/18, scale/18 );
	}
	
	//tests the methods of the Wumpus
	public static void main( String[] args ) {
		Wumpus w = new Wumpus(0,0);
		Vertex v = new Vertex(1,1,1);
		WumpusState state = WumpusState.won;
		w.setHome(v);
		w.setState(state);
		System.out.println( "Home: " + w.getHome() );
		System.out.println( "State: " + w.getState() );
	}
}