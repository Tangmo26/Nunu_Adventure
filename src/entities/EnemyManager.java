package entities;

import gamestate.Playing;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import levels.Level;
import main.Game;
import utils.LoadSave;
import static utils.Constants.EnemyConstants.* ;

public class EnemyManager {
    
    private Playing playing ;
    private BufferedImage[][] monkeyArr ;
    private ArrayList<Monkey> monkeies = new ArrayList<>() ;
    
    public EnemyManager(Playing playing){
        this.playing = playing ;
        loadEnemyImgs() ;
    }
    
    public void loadEnemies(Level level) {
        monkeies = level.getMonkey() ;
        System.out.println("size of monkey : " + monkeies.size());
    }
    
    public void update(int[][] lvlData){
        for(Monkey m : monkeies)
            m.update(lvlData);
    }
    
    public void draw(Graphics g, int xLvlOffset) {
        drawMon(g, xLvlOffset) ;
    }
    
    private void drawMon(Graphics g,int xLvlOffset) {
        for(Monkey m : monkeies)
            g.drawImage(monkeyArr[m.getEnimyState()][m.getAniIndex()], (int) m.getHitbox().x - xLvlOffset, (int) (m.getHitbox().y - 4 * Game.SCALE), MONKEY_WIDTH, MONKEY_HEIGHT, null) ;
    }
        
    private void loadEnemyImgs(){
        monkeyArr = new BufferedImage[5][8] ;
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.MONKEY_SPRITE) ;
        for(int j = 0 ; j < monkeyArr.length ; j++)
            for(int i = 0 ; i < monkeyArr[j].length ; i++)
                monkeyArr[j][i] = temp.getSubimage(i * MONKEY_WIDTH_DEFAULT, j * MONKEY_HEIGHT_DEFAULT, MONKEY_WIDTH_DEFAULT, MONKEY_HEIGHT_DEFAULT) ;
    }
    public void resetAllEnemies() {
	for (Monkey m : monkeies)
            m.resetEnemy();
	}
    
}
