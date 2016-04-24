package universMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
public class TableauEtoile {
	/**
	 * Tableau 1 : Fond étoilé du jeu
	 */
	private Image TableauEtoile;
	private Image rightTableauEtoile;
	//private float x;
	private float y = 0;
	private float newy = -768;
	public TableauEtoile() {

	}

	public void init() throws SlickException {
		TableauEtoile = new Image("res/bgblue.jpg");
		//rightTableauEtoile = new Image("res/bgblue2.jpg");
		//rightTableauEtoile = new Image(2048, 1536);
	}
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		//TableauEtoile.draw(0, 0);

		//g.translate(0, y);
		//g.drawImage(rightTableauEtoile,0,newy);
		g.drawImage(TableauEtoile,0,y);
		//g.resetTransform();
		//g.copyArea(rightTableauEtoile, 0, 1536);
		//g.drawImage(rightTableauEtoile, 0, y);
		//g.translate(0, y);
		y=y+3;
		//TableauEtoile.getFlippedCopy(false,true);
//TableauEtoile. setRotation(180);
		g.drawImage(TableauEtoile, 0, newy);
		newy=newy+3;
	}
	public void update(GameContainer container, int delta)
			throws SlickException {
		int h = TableauEtoile.getHeight()-1;
		System.out.println("Valeur de y : " + y);
		/*if(y < -h)
		{
			y =  + h; //y = 600 + h
			newy= +h;
		}
		else*/ if(y > h) //(y > 600+h)
		{
			y = 0;
			newy = -768;
		}
		//warp();
	}
	public void warp()
	{
		//int w = TableauEtoile.getWidth()-1;
		int h = TableauEtoile.getHeight()-1;
		//float newx = x;
		float newy = y;
		//controle si ne sort pas de l'écran...
		//if(x < -w) {newx = 800 + w;}
		//else if(x > 800+w){newx = -w;}
		if(y < -h) {newy = 2 + h;}
		else if(y > 2+h){newy = -h;}
		//enregistre les changements
		setPosition(newy);
	}
	public void setPosition(float y2) {
		//x = x2;
		y = y2;

	}
}
