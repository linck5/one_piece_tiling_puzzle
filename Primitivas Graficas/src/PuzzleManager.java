import java.awt.Graphics2D;


public class PuzzleManager {
	
	private PuzzlePiece pieces[][] = new PuzzlePiece[GamePanel.xPieces][GamePanel.yPieces];
	
	public PuzzleManager(Graphics2D g)
	{
		for(int i = 0; i < GamePanel.xPieces; i++){
			for(int j = 0; j < GamePanel.yPieces; j++){
				pieces[i][j] = new PuzzlePiece(i, j, g);
			}
		}
	}
	
	public void shuffle(){
		for(int i=0; i < GamePanel.xPieces * GamePanel.yPieces; i++){
			swap(
				(int) Math.floor(Math.random()*GamePanel.xPieces),
				(int) Math.floor(Math.random()*GamePanel.yPieces),
				(int) Math.floor(Math.random()*GamePanel.xPieces),
				(int) Math.floor(Math.random()*GamePanel.yPieces)
			);
		}
	}
	
	public void swap(int pos1X, int pos1Y, int pos2X, int pos2Y){
		pieces[pos1X][pos1Y].setPosition( pos2X, pos2Y );
		pieces[pos2X][pos2Y].setPosition( pos1X, pos1Y );
		
		PuzzlePiece aux = pieces[ pos1X ] [ pos1Y ];
		pieces[pos1X][pos1Y] = pieces[pos2X][pos2Y];
		pieces[pos2X][pos2Y] = aux;
	}

	public boolean isSolved(){
		for(int i = 0; i < GamePanel.xPieces; i++)
			for(int j = 0; j < GamePanel.yPieces; j++)
				if(!pieces[i][j].isOnTheRightPlace(i, j)) return false;
		return true;
	}
	
	public PuzzlePiece[][] getPieces() {
		return pieces;
	}
}
