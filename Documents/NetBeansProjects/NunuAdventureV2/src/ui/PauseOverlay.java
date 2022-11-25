package ui;

import audio.AudioPlayer;
import gamestate.Gamestate;
import gamestate.Playing;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import main.Game;
import static utils.Constants.UI.URMButtons.*;
import utils.LoadSave;

public class PauseOverlay {
    
    private Playing playing ;
    private BufferedImage backgroundImg ;
    private int  bgX,bgY,bgW,bgH ;
    private AudioOptions audioOption ;
    
    private PauseButton menuB, replayB, unpauseB ;
    
    
    public PauseOverlay(Playing playing) {
        this.playing = playing ;
        loadBackground() ;
        audioOption = playing.getGame().getAudioOptions() ;
        
        createUemButton() ;
        
    }
    
    private void createUemButton() {
        int menuX = (int) (313 * Game.SCALE) ;
        int replayX = (int) (387 * Game.SCALE) ;
        int upauseX = (int) (462 * Game.SCALE) ;
        int bY = (int)(350 * Game.SCALE) ;
        
        menuB = new UrmButton(menuX, bY, URM_SIZE, URM_SIZE, 2) ;
        replayB = new UrmButton(replayX, bY, URM_SIZE, URM_SIZE, 1) ;
        unpauseB = new UrmButton(upauseX, bY, URM_SIZE, URM_SIZE, 0) ;
    }
   
    
    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND) ;
        bgW = (int) (backgroundImg.getWidth() * Game.SCALE) ;
        bgH = (int) (backgroundImg.getHeight() * Game.SCALE) ;
        bgX = (int) (Game.GAME_WIDTH / 2 - bgW / 2) ;
        bgY = (int) (Game.GAME_HEIGHT / 2 - bgH / 2) ;
    }
    
    public void update() {
        
        
        menuB.update();
        replayB.update();
        unpauseB.update();
        
        audioOption.update();
        
    }
    
    public void draw(Graphics g){
        //background
        g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null) ;
        
        //Urm buttons
        menuB.draw(g);
        replayB.draw(g);
        unpauseB.draw(g);
        
        audioOption.draw(g);

    }
    public void mouseDragged(MouseEvent e) {
        audioOption.mouseDragged(e);
    }
    
    public void mousePressed(MouseEvent e) {
        if (isIn(e, menuB)){
            menuB.setMousePressed(true);
            playing.getGame().getAudioPlayer().playEffect(AudioPlayer.SELECT);
        }
        else if (isIn(e, replayB)){
            playing.getGame().getAudioPlayer().playEffect(AudioPlayer.SELECT);
            replayB.setMousePressed(true);
        }
        else if (isIn(e, unpauseB)){
            playing.getGame().getAudioPlayer().playEffect(AudioPlayer.SELECT);
            unpauseB.setMousePressed(true);
        }
        else{
            playing.getGame().getAudioPlayer().playEffect(AudioPlayer.SELECT);
            audioOption.mousePressed(e);
        }
    }
    
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menuB)){
            if(menuB.getMousePressed()){
                
                Gamestate.state = Gamestate.MENU ;
                playing.resetAll();
                playing.unpauseGame();
                
            }
        }
        else if (isIn(e, replayB)){
            if(replayB.getMousePressed())
                playing.resetAll();
                //System.out.println("replay lvl!");
        }
        else if (isIn(e, unpauseB)){
            if(unpauseB.getMousePressed())
                playing.unpauseGame();
        }
        else
            audioOption.mouseReleased(e);
        
        menuB.resetBools();
        replayB.resetBools();
        unpauseB.resetBools();
    }

    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        unpauseB.setMouseOver(false);

        if (isIn(e, menuB))
            menuB.setMouseOver(true);
        else if (isIn(e, replayB))
            replayB.setMouseOver(true);
        else if (isIn(e, unpauseB))
            unpauseB.setMouseOver(true);
        else
            audioOption.mouseMoved(e);
    }
    
    public boolean isIn(MouseEvent e, PauseButton b){
        return b.getBounds().contains(e.getX(), e.getY()) ;
    }
    
    


    
}
