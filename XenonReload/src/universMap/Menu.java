package universMap;

import org.newdawn.slick.GameContainer;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

/** les classes GUI de slick2D*/

import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;

public class Menu extends BasicGameState implements ComponentListener{

	public static final int ID = 2;
	private Image menu;
	public StateBasedGame game;

	public GameContainer container;

	/** les deux boutons en bas du ChoixState*/
	private MouseOverArea quit;
	private MouseOverArea play;
	private MouseOverArea menuSel;

	/** Instanciation du gestionnaire Input */
	private Input input;

	@Override
	public int getID() {
		return ID;
	}

	//@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {

		input = container.getInput();

		menu = new Image("res/xenon.png");

		quit = new MouseOverArea(container,new Image("res/quit.png"), 350, 450,this);
		quit.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
		quit.setMouseOverColor(new Color(0.9f,0.9f,0.9f,1f));

		play = new MouseOverArea(container,new Image("res/play.png"), 350, 380, this);
		play.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
		play.setMouseOverColor(new Color(0.9f,0.9f,0.9f,1f));

		menuSel = new MouseOverArea(container,new Image("res/menu_sel.png"), 350, 295, this);
		menuSel.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
		menuSel.setMouseOverColor(new Color(0.9f,0.9f,0.9f,1f));
	}

	//@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {


		menu.draw(0,0);
		quit.render(container, g);
		play.render(container, g);
		menuSel.render(container, g);
		g.drawString("Dans le gestionnaire du Menu",0, 582);
		this.game = game;
	}

	//@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if (input.isKeyPressed(Input.KEY_ESCAPE))
		{
			System.exit(0); //on sort proprement du jeu
		}
	}

	//@Override
	public void componentActivated(AbstractComponent source) { //methode de l'interface ComponentListener

		if (source == quit) {
			game.getContainer().exit();
		}
		if (source == play) {
			game.enterState(GameState.ID);
		}
		if (source == menuSel) {
			game.enterState(MenuJoueur.ID);
		}
	}

}
