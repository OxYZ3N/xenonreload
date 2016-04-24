package universMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.OutlineWobbleEffect;

public class ScrollingTexte {

	private UnicodeFont unicodeFont;
	private Graphics offscreenPreload;
	private Image preloaded;
	private String text = "The quick fox brown jump over the lazy dog...";
	private boolean btest=true; 
	
	public void init(GameContainer container) throws SlickException {
		   container.setShowFPS(true);

		   unicodeFont = new UnicodeFont("D:/java/ScreenPub/img/font/lcdn.ttf", 48, false, false);
		   unicodeFont.getEffects().add(new ColorEffect(java.awt.Color.white));
		   unicodeFont.getEffects().add(new OutlineWobbleEffect(0, java.awt.Color.black) );

		   container.getGraphics().setBackground(Color.darkGray);
		      
		   preloaded = new Image(unicodeFont.getWidth(text), unicodeFont.getHeight(text) );
		   offscreenPreload = preloaded.getGraphics();
		   offscreenPreload.setBackground(new Color(0,0,0,0)); // Fond transparent!
		   offscreenPreload.setFont(unicodeFont);
		}

	public void render(GameContainer container, Graphics g) throws SlickException {
	      
		   if(btest) {
		     unicodeFont.loadGlyphs();
		     offscreenPreload.drawString(text, 0, 0);
		     g.setColor(Color.white);
		     btest = false;
		   }
		   preloaded.draw(10, 33);
		            
		}
	
}
