package ui;

import gamestate.Gamestate;
import gamestate.Playing;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import main.Game;
import utils.LoadSave;
import static utils.Constants.GameOvers.*;

public class GameOverOverley {
    
    private Playing playing ;
    private BufferedImage gameOver ;
    
    public GameOverOverley(Playing playing){
        this.playing = playing ;
        loadImg() ;
    }
    
    public void draw(Graphics g){
        g.setColor(new Color( 0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
        g.drawImage(gameOver, (int)((Game.GAME_WIDTH / 2) - (GAMEOVER_WIDTH /2)), 0, GAMEOVER_WIDTH, GAMEOVER_HEIGHT, null) ;
    }
    public void loadImg(){
        gameOver = LoadSave.GetSpriteAtlas(LoadSave.GAMEOVER) ;
    }
    
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            playing.resetAll() ;
            playing.setGameState(Gamestate.MENU);
            Gamestate.state = Gamestate.MENU ;
        }
    }
}
