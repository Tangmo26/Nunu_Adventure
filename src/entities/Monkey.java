package entities;
import java.awt.Graphics;
import main.Game;
import static utils.Constants.EnemyConstants.* ;
public class Monkey extends Enemy{
    
    public Monkey(float x, float y) {
        super(x, y, MONKEY_WIDTH, MONKEY_HEIGHT, MONKEY);
        initHitbox(x,y,(int)(31 * Game.SCALE),(int)(24 * Game.SCALE)) ;
    }

    @Override
    public void update() {
        
    }

    @Override
    public void render(Graphics g, int lvlOffset) {
        
    }
}

