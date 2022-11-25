package gamestate;

import audio.AudioPlayer;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import main.Game;
import ui.MenuButton;
import utils.LoadSave;

public class Menu extends State implements Statemethods{
    
    private MenuButton[] buttons = new MenuButton[3] ;
    private BufferedImage backgroundImgMain;

    public Menu(Game game) {
        super(game);
        loadButtons() ;
        backgroundImgMain = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG) ;
    }

    
    private void loadButtons() {
        buttons[0] = new MenuButton(Game.GAME_WIDTH / 2 , (int)(280 * Game.SCALE), 0, Gamestate.PLAYING) ;
        buttons[1] = new MenuButton(Game.GAME_WIDTH + 2000, (int)(305 * Game.SCALE), 1, Gamestate.OPTIONS) ;
        buttons[2] = new MenuButton(Game.GAME_WIDTH / 2, (int)(320 * Game.SCALE), 2, Gamestate.QUIT) ;
        System.out.println("GAME_HEIGHT / 2 = " + (Game.GAME_HEIGHT / 2));
    }
        
    @Override
    public void update() {
        for(MenuButton mb : buttons)
            mb.update();
    }
    

    @Override
    public void draw(Graphics g) {
        
        g.drawImage(backgroundImgMain, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null) ;
        
       for(MenuButton mb : buttons)
           mb.draw(g) ;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(MenuButton mb : buttons) {
            if(isIn(e,mb)) {
                game.getAudioPlayer().playEffect(AudioPlayer.SELECT);
                mb.setMousePressed(true);
                break ;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for(MenuButton mb : buttons) {
            if(isIn(e,mb)) { 
                if(mb.isMousePressed())
                   mb.applyGamestate();
                if(mb.getState() == Gamestate.PLAYING)
                    game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getLevelIndet());
                break ;
            }
        }
        resetButtons() ;
    }
    
    private void resetButtons() {
        for(MenuButton mb : buttons) 
            mb.resetBools(); 
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        for(MenuButton mb : buttons) 
            mb.setMouseOver(false);
        
        for(MenuButton mb : buttons)
            if (isIn(e, mb)) {
                mb.setMouseOver(true);
                break ;
            }
    }

    @Override
    public void keyPressed(KeyEvent e) {
       if(e.getKeyCode() == KeyEvent.VK_ENTER)
           Gamestate.state = Gamestate.PLAYING ;
       System.out.println("enter");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }


    
}
