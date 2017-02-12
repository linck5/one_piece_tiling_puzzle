import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class PuzzlePiece {
	
	private BufferedImage image;
	private Graphics2D g2D;
	private int sourcePosX;
	private int sourcePosY;
	private int targetPosX;
	private int targetPosY;
	
	public PuzzlePiece(){}
	
	public PuzzlePiece(int sourcePosX, int sourcePosY, Graphics2D g2D)
	{
		this.sourcePosX = sourcePosX;
		this.sourcePosY = sourcePosY;
		this.targetPosX = sourcePosX;
		this.targetPosY = sourcePosY;
		this.g2D = g2D;
		
		try {
			image = ImageIO.read( getClass().getResource("fundo3.jpg") );
		}
		catch(IOException e) {
			System.out.println("Load Image error:");
		}
	}
	
	public void drawPiece()
	{
		int imgw = image.getWidth();
		int imgh = image.getHeight();
		int pw = GamePanel.PWIDTH;
		int ph = GamePanel.PHEIGHT;
		int xp = GamePanel.xPieces;
		int yp = GamePanel.yPieces;
		
		g2D.drawImage
		(image, 0 + (pw/xp * targetPosX), 0 + (ph/yp * targetPosY), pw/xp * (targetPosX + 1), ph/yp * (targetPosY + 1),
		0 + (imgw/xp * sourcePosX), 0 + (imgh/yp * sourcePosY), imgw/xp * (sourcePosX + 1), imgh/yp * (sourcePosY + 1), null);
	}

	public boolean isOnTheRightPlace(int i, int j){
		return i == sourcePosX && j == sourcePosY;
	}
	
	public void setPosition(int targetPosX, int targetPosY) {
		this.targetPosX = targetPosX;
		this.targetPosY = targetPosY;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	

}
