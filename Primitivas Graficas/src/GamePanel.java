import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.Random;
import java.awt.image.*;


public class GamePanel extends JPanel implements Runnable
{
public static final int PWIDTH = 640;
public static final int PHEIGHT = 480;
public static final int xPiecesInput = Integer.parseInt(JOptionPane.showInputDialog("Quantas linhas o Quebra-Cabeças deve ter?"));
public static final int yPiecesInput = Integer.parseInt(JOptionPane.showInputDialog("Quantas colunas o Quebra-Cabeças deve ter?"));
public static final int xPieces = xPiecesInput > 0 && xPiecesInput <= 100? xPiecesInput: 2;
public static final int yPieces = yPiecesInput > 0 && yPiecesInput <= 100? yPiecesInput: 2;
private Thread animator;
private boolean running = false;
private boolean gameOver = false; 

private BufferedImage dbImage;
private Graphics2D dbg;

public PuzzleManager puzzle;

public int FPS,SFPS;
public int fpscount;
private boolean firstClick = true;
private int posA[] = new int[2];
private int posB[] = new int[2];

public Random rnd = new Random();

public BufferedImage fundo;

public boolean LEFT, RIGHT,UP,DOWN;

public int MouseX,MouseY;

public GamePanel()
{

	

	setBackground(Color.white);
	setPreferredSize( new Dimension(PWIDTH, PHEIGHT));

	// create game components
	setFocusable(true);

	requestFocus(); // JPanel now receives key events
	
	if (dbImage == null){
		dbImage = new BufferedImage(PWIDTH, PHEIGHT,BufferedImage.TYPE_INT_ARGB);
		if (dbImage == null) {
			System.out.println("dbImage is null");
			return;
		}else{
			dbg = (Graphics2D)dbImage.getGraphics();
		}
	}
	
	puzzle = new PuzzleManager(dbg);
	puzzle.shuffle();
	
	// Adiciona um Key Listner
	addKeyListener( new KeyAdapter() {
		public void keyPressed(KeyEvent e)
			{ 
				int keyCode = e.getKeyCode();
				
				if(keyCode == KeyEvent.VK_LEFT){
					LEFT = true;
				}
				if(keyCode == KeyEvent.VK_RIGHT){
					RIGHT = true;
				}
				if(keyCode == KeyEvent.VK_UP){
					UP = true;
				}
				if(keyCode == KeyEvent.VK_DOWN){
					DOWN = true;
				}	
			}
		@Override
			public void keyReleased(KeyEvent e ) {
				int keyCode = e.getKeyCode();
				
				if(keyCode == KeyEvent.VK_LEFT){
					LEFT = false;
				}
				if(keyCode == KeyEvent.VK_RIGHT){
					RIGHT = false;
				}
				if(keyCode == KeyEvent.VK_UP){
					UP = false;
				}
				if(keyCode == KeyEvent.VK_DOWN){
					DOWN = false;
				}
			}
	});
	
	addMouseMotionListener(new MouseMotionListener() {
		
		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			MouseX = e.getX();
			MouseY = e.getY();
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			MouseX = e.getX();
			MouseY = e.getY();
		}
	});
	
	addMouseListener(new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			
			if(firstClick){
				if(puzzle.isSolved()){
					puzzle.shuffle();
					firstClick = !firstClick;
				}
				else{
					posA[0] = (int) Math.floor( ( (float) MouseX / PWIDTH ) * xPieces );
					posA[1] = (int) Math.floor( ( (float) MouseY / PHEIGHT ) * yPieces );
				}
			}
			else{
				posB[0] = (int) Math.floor( ( (float) MouseX / PWIDTH ) * xPieces );
				posB[1] = (int) Math.floor( ( (float) MouseY / PHEIGHT ) * yPieces );
				
				puzzle.swap(posA[0], posA[1], posB[0], posB[1]);
			}
			firstClick = !firstClick;
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	});
	
	
	
	MouseX = MouseY = 0;
	


} // end of GamePanel()

public void addNotify()
{
	super.addNotify(); // creates the peer
	startGame(); // start the thread
}

private void startGame()
// initialise and start the thread
{
	if (animator == null || !running) {
		animator = new Thread(this);
		animator.start();
	}
} // end of startGame()

public void stopGame()
// called by the user to stop execution
{ running = false; }


public void run()
/* Repeatedly update, render, sleep */
{
	running = true;
	
	long DifTime,TempoAnterior;
	
	int segundo = 0;
	
	DifTime = 0;
	
	TempoAnterior = System.currentTimeMillis();
	
	while(running) {
	
		gameUpdate(DifTime); // game state is updated
		gameRender(); // render to a buffer
		paintImmediately(0, 0, 640, 480); // paint with the buffer
	
		try {
			Thread.sleep(0); // sleep a bit
		}	
		catch(InterruptedException ex){}
		
		DifTime = System.currentTimeMillis() - TempoAnterior;
		TempoAnterior = System.currentTimeMillis();
		
		if(segundo!=((int)(TempoAnterior/1000))){
			FPS = SFPS;
			SFPS = 1;
			segundo = ((int)(TempoAnterior/1000));
		}else{
			SFPS++;
		}
	}
System.exit(0); // so enclosing JFrame/JApplet exits
} // end of run()

int timerfps = 0;

private void gameUpdate(long DiffTime)
{ 
	
}

private void gameRender()
// draw the current frame to an image buffer
{
	// clear the background
	dbg.setColor(Color.white);
	dbg.fillRect (0, 0, PWIDTH, PHEIGHT);
	
	for(PuzzlePiece[] i: puzzle.getPieces()){
		for(PuzzlePiece j: i){
			j.drawPiece();
		}
	}
	
	for(int i=0; i < PWIDTH; i+= PWIDTH / xPieces){
		dbg.setColor(Color.white);
		dbg.drawLine(i, 0, i, PHEIGHT);
	}
	for(int i=0; i < PHEIGHT; i+= PHEIGHT / yPieces){
		dbg.setColor(Color.white);
		dbg.drawLine(0, i, PWIDTH, i);
	}
	
	dbg.setColor(Color.BLUE);
	dbg.setFont(new Font("Helvetica", Font.PLAIN, 12));
	dbg.drawString("FPS: "+FPS+" "+MouseX+" "+MouseY, 10, 10);	
	
	if(puzzle.isSolved()){
		dbg.setFont(new Font("Helvetica", Font.PLAIN, 16));
		dbg.drawString("Clique na imagem para jogar novamente.",PWIDTH/2 - 145, PHEIGHT/2);	
	}
}




public void paintComponent(Graphics g)
{
	super.paintComponent(g);

	if (dbImage != null){
		
		g.drawImage(dbImage, 0, 0, null);
	}
}

public static void main(String args[])
{
	GamePanel ttPanel = new GamePanel();

	JFrame app = new JFrame("Swing Timer Test");
	  app.getContentPane().add(ttPanel, BorderLayout.CENTER);
	  app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	  app.pack();
	  app.setResizable(false);  
	  app.setVisible(true);
}


} // end of GamePanel class

