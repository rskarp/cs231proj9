/*
* File: HuntTheWumpus.java
* Author: Riley Karp
* Date: 12/09/2016
*/

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.Point;

import javax.imageio.ImageIO;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.event.MouseInputAdapter;

import java.util.*;

/**
 * Creates a window with three buttons and a large
 * display area. The app then tracks keypresses to
 simulate a game of HuntTheWumpus.
**/
public class HuntTheWumpus extends JFrame {

	private LandscapeDisplay display; //holds the LandscapeDisplay
	private Landscape scape; //holds the Landscape
	private Control control;

	// controls whether the simulation is playing quitting, over, or new
	private enum PlayState { PLAY, QUIT, OVER, NEW }
	private PlayState state;

	/**
		 * Creates the main window
		 * @param rows the rows of the window in Vertices
		 * @param cols the cols of the window in Vertices
		 * @param randGraph controls whether the Graph is fully or randomly connected
	**/		 
	public HuntTheWumpus( int rows, int cols, boolean randGraph ) {
		super("HuntTheWumpus Display");
		this.scape = new Landscape( rows, cols, randGraph );
		this.display = new LandscapeDisplay( scape, 100 );
		state = PlayState.PLAY;

		JButton reset = new JButton("Reset");
		JButton newGame = new JButton("New Game");
		JButton quit = new JButton("Quit");
		JPanel panel = new JPanel( new FlowLayout(FlowLayout.RIGHT));
		panel.add( newGame );
		panel.add( reset );
		panel.add( quit );
		
		display.add( panel, BorderLayout.SOUTH );
		display.pack();

		control = new Control();
		display.addKeyListener(control);
		display.setFocusable(true);
		display.requestFocus();

		newGame.addActionListener( control );
		reset.addActionListener( control );
		quit.addActionListener( control );
	}

	//repaints the display
	public void iterate() throws InterruptedException {
		this.display.repaint();
		if( state == PlayState.OVER ) {
			display.setFocusable(false);
		}
		Thread.sleep(25);
	}
	
	//disposes of the display
	public void dispose() {
		display.dispose();
	}

	//sets what happens upon various actions
    private class Control extends KeyAdapter implements ActionListener {
		
		//used by the keyTyped method to determine what the Hunter should do in the given Direction
		private void hunterAction( Vertex.Direction dir, boolean shoot ) {
			Hunter h = scape.getHunter();
			Wumpus w = scape.getWumpus();
			boolean condition; //used for bounds checking
			if( dir == Vertex.Direction.north ) {
				condition = h.getLocation().getNeighbor(Vertex.Direction.north) != null;
			}
			else if( dir == Vertex.Direction.west ) {
				condition = h.getLocation().getNeighbor(Vertex.Direction.west) != null;
			}
			else if( dir == Vertex.Direction.east ) {
				condition = h.getLocation().getNeighbor(Vertex.Direction.east) != null;
			}
			else { //if dir == Vertex.Direction.south
				condition = h.getLocation().getNeighbor(Vertex.Direction.south) != null;
			}
			
			if( shoot == true ) { //Hunter wants to shoot
				if( condition ) {
					if( h.getLocation().getNeighbor(dir) == w.getHome() ) {
						w.setState( Wumpus.WumpusState.lost );
						System.out.println( "Congratulations, you shot the Wumpus!" );
					}
					else { // shot missed the Wumpus
						w.setState( Wumpus.WumpusState.won );
						System.out.println( "Darn, you missed the Wumpus and became " +
							"unarmed so the Wumpus ate you :(" );
						h.setAlive( false );
					}
					state = PlayState.OVER;
					System.out.println( "GAME OVER" );
				}
				else {
					System.out.println( "Oops, that's a wall, try again!" );
				}
			}
			else { //if shoot == false
				if( condition ) {
					h.setLocation( h.getLocation().getNeighbor( dir ) );
				}
			}
		}
		
		//controls what happens when w,a,s,d, , or q is typed
        public void keyTyped(KeyEvent e) {
            System.out.println( "Key Pressed: " + e.getKeyChar() );
            Hunter h = scape.getHunter();
            Wumpus w = scape.getWumpus();
            boolean shoot;
            
            //quits if q is pressed
            if( ("" + e.getKeyChar()).equalsIgnoreCase("q") ) {
                state = PlayState.QUIT;
                System.out.println( state );
            }

			//sets what the Hunter's action is depending on whether or not Hunter is armed
            if( !h.getArmed() ) {//if Hunter is unarmed
            	shoot = false;
            	if( ("" + e.getKeyChar()).equalsIgnoreCase(" ") ) {
					h.setArmed( true );
					System.out.println( "Armed" );
				}
            }
            else { // if hunter is armed
            	shoot = true;
            	if( ("" + e.getKeyChar()).equalsIgnoreCase(" ") ) {
					h.setArmed( false );
					System.out.println( "Disarmed" );
				}
            }
            
            //take action depending on which wasd key is pressed
			if( ("" + e.getKeyChar()).equalsIgnoreCase("w") ) {
				this.hunterAction( Vertex.Direction.north, shoot );
			}
			else if( ("" + e.getKeyChar()).equalsIgnoreCase("a") ) {
				this.hunterAction( Vertex.Direction.west, shoot );
			}
			else if( ("" + e.getKeyChar()).equalsIgnoreCase("d") ) {
				this.hunterAction( Vertex.Direction.east, shoot );
			}
			else if( ("" + e.getKeyChar()).equalsIgnoreCase("s") ) {
				this.hunterAction( Vertex.Direction.south, shoot );
			}
			
            //Hunter is eaten by Wumpus
            if( h.getLocation() == w.getHome() ) {
            	w.setState( Wumpus.WumpusState.won );
            	h.setAlive( false );
            	System.out.println( "Darn, you've stumbled upon the Wumpus and it has" +
            		" eaten you :(" );
            	state = PlayState.OVER;
            	System.out.println( "GAME OVER" );
            }
        }

		//controls what happens when the buttons are clicked
        public void actionPerformed(ActionEvent event) {
            if( event.getActionCommand().equalsIgnoreCase("Reset") ) {
            	//game is reset to it's original state
            	state = PlayState.PLAY;
            	System.out.println( state );
            	scape.getWumpus().setState( Wumpus.WumpusState.hiding );
                for( Vertex v: scape.getGraph().getVertices() ) {
                	v.setVisible(false);
                }
                scape.getHunter().setLocation( scape.getHunterStart() );
                scape.getHunter().setArmed( false );
                scape.getHunter().setAlive( true );
                System.out.println( "The game has been reset to its original state." );
                display.setFocusable(true);
				display.requestFocus();
            }
            else if( event.getActionCommand().equalsIgnoreCase("Quit") ) {
            	//game window closes and program is terminated
                state = PlayState.QUIT;
                System.out.println( state );
            }
            else if( event.getActionCommand().equalsIgnoreCase("New Game") ) {
            	//set state to NEW
                state = PlayState.NEW;
                System.out.println( "NEW GAME" );
            }
        }
    } // end class Control

	//runs the HuntTheWumpus game until user quits. cols and rows of vertices,
	//and fully-connected or random graph can be specified in command line
	public static void main(String[] argv) throws InterruptedException {
		int cols;
		int rows;
		boolean random;
		if( argv.length == 2 ) { //if cols and rows are specified
			cols = Integer.valueOf(argv[0]);
			rows = Integer.valueOf(argv[1]);
			random = false;
			System.out.println( "Using default value: random = false" );
		}
		else if( argv.length < 3 ) {
			cols = 6;
			rows = 6;
			random = false;
			System.out.println( "Using default values: cols = 6, rows = 6, random = false" );
		}
		else { //if all values are given
			cols = Integer.valueOf(argv[0]);
			rows = Integer.valueOf(argv[1]);
			random = Boolean.valueOf(argv[2]);
		}
		HuntTheWumpus w = new HuntTheWumpus( cols,rows, random );
		while(w.state != PlayState.QUIT) {
			w.iterate();
			if( w.state == PlayState.NEW ) {
				w.dispose();
				w = new HuntTheWumpus( cols,rows,random ); //memory is lost (reference to HuntTheWumpus object)
			}
		}
		w.dispose();
	}
} // end class HuntTheWumpus