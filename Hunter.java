/*
* File: Hunter.java
* Author: Riley Karp
* Date: 12/09/2016
*/

import java.awt.Graphics;
import java.awt.Color;

public class Hunter extends Agent {

	private Vertex location;
	private boolean armed;
	private boolean alive;
	
	//creates a Hunter object at the given row,column location
	public Hunter( int r, int c ) {
		super( r, c );
		armed = false;
		alive = true;
		location = null;
	}
	
	//sets the Hunter's armed field to the given boolean value
	public void setArmed( boolean b ) {
		armed = b;
	}
	
	//returs a reference to the Hunter's armed filed
	public boolean getArmed() {
		return armed;
	}
	
	//sets the Hunter's alive field to the given boolean value
	public void setAlive( boolean b ) {
		alive = b;
	}
	
	//returs a reference to the Hunter's alive filed
	public boolean getAlive() {
		return alive;
	}
	
	//returns a reference to the Hunter's Vertex location
	public Vertex getLocation() {
		return this.location;
	}
	
	//sets the Hunter's Vertex location to the given Vertex
	public void setLocation( Vertex newVertex ) {
		location = newVertex;
		location.setVisible( true );
	}
	
	//draws the Hunter in the given scale and Graphics window, as a smiling square if it's
	//alive and a frowning square if it's not alive
	public void draw( Graphics g, int scale ) {
		if( alive == true ) {
			g.setColor( Color.green );
		}
		else {
			g.setColor( Color.black );
		}
		//body
		g.fillRect( location.getCol()*scale + 2 + (3*scale/8), location.getRow()*scale +
		2 + (3*scale/8), scale/4, scale/4 ); 
		
		//face
		if( alive == true ) {
			g.setColor( Color.blue );
			//smile
			g.drawArc( location.getCol()*scale + 2 + (7*scale/16), location.getRow()*scale +
			2 + (scale/2), scale/10, scale/20,0,-180 );
		}
		else {
			g.setColor( Color.white );
			//frown
			g.drawArc( location.getCol()*scale + 2 + (7*scale/16), location.getRow()*scale +
			2 + (scale/2), scale/10, scale/20,0,180 );
		}
		//eyes
		g.fillOval(location.getCol()*scale + 2 + (scale/2), location.getRow()*scale +
		2 + (7*scale/16), scale/18, scale/18 );
		g.fillOval(location.getCol()*scale + 2 + (7*scale/16), location.getRow()*scale +
		2 + (7*scale/16), scale/18, scale/18 );
	}
	
	//tests the methods of the Hunter
	public static void main( String[] args ) {
		Hunter h = new Hunter(0,0);
		Vertex v = new Vertex(1,1,1);
		h.setLocation(v);
		h.setArmed(true);
		h.setAlive(false);
		System.out.println( "Location: " + h.getLocation() );
		System.out.println( "Armed: " + h.getArmed() );
		System.out.println( "Alive " + h.getAlive() );
	}
}