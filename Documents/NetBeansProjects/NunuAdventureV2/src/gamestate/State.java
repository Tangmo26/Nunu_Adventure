package gamestate;

import audio.AudioPlayer;
import java.awt.event.MouseEvent;

import main.Game;
import ui.MenuButton;

public class State {
    
    protected Game game ;
    
    public State(Game game){
        this.game = game ;
    }
    //return boolean mouse in rectangle
    public boolean isIn(MouseEvent e, MenuButton mb) {
        return mb.getBounds().contains(e.getX(),e.getY()) ;
    }
    
    public Game getGame(){
        return game ;
    }
    
    public void setGameState(Gamestate state){
        switch (state) {
            case MENU -> {game.getAudioPlayer().playSong(AudioPlayer.MENU_1);
                System.out.println("do");}
            case PLAYING -> game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getLevelIndet());
        }
        Gamestate.state = state ;
    }
}