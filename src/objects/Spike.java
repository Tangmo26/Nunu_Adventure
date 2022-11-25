package objects;

import main.Game;

public class Spike extends GameObject{

    public Spike(int x, int y, int objType) {
        super(x, y, objType);
        
        initHitbox(27,27) ;
        xDrawOffset = 0 ;
        yDrawOffset = (int)(27*Game.SCALE*0.1) ;
        hitbox.y += yDrawOffset ;
    }
}
