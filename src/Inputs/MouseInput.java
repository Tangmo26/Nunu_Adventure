package Inputs;

import gamestate.Gamestate;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import main.GamePanel;

public class MouseInput implements MouseListener,MouseMotionListener{
     
    private GamePanel gamePanal ;
    
    public MouseInput (GamePanel gamePanel){
        this.gamePanal = gamePanel ;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        switch(Gamestate.state){
            case PLAYING :
                gamePanal.getGame().getPlaying().mouseClicked(e);
                break ;
            default :
                break ;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch(Gamestate.state){
            case MENU :
                gamePanal.getGame().getMenu().mousePressed(e);
                break ;
            case PLAYING :
                gamePanal.getGame().getPlaying().mousePressed(e);
                break ;
            default :
                break ;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch(Gamestate.state){
            case MENU :
                gamePanal.getGame().getMenu().mouseReleased(e);
                break ;
            case PLAYING :
                gamePanal.getGame().getPlaying().mouseReleased(e);
                break ;
            default :
                break ;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
         switch(Gamestate.state){
            case PLAYING :
                gamePanal.getGame().getPlaying().mouseDragged(e);
                break ;
            default :
                break ;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch(Gamestate.state){
            case MENU :
                gamePanal.getGame().getMenu().mouseMoved(e);
                break ;
            case PLAYING :
                gamePanal.getGame().getPlaying().mouseMoved(e);
                break ;
            default :
                break ;
        }
    }
    
}
