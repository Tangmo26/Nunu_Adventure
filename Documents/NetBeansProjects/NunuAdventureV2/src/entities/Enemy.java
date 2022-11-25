package entities;

import main.Game;
import static utils.Constants.EnemyConstants.* ;
import static utils.HelpMethods.*;
import static utils.Constants.Directions.* ;

public abstract class Enemy extends Entity {
    
    private int aniIndex, enemyState, enemyType ;
    private int aniTick, aniSpeed = 15 ;
    private boolean firstUpdate = true ;
    private boolean inAir = false ;
    private float fallSpeed ;
    private float gravity = 0.04f * Game.SCALE ;
    private float walkSpeed = 1.0f * Game.SCALE ;
    private int walkDir = LEFT ;
    
    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType ;
        initHitbox(x, y, width, height) ;
        
    }
    
    private void updateAnimationTick(){
        aniTick ++ ;
        if(aniTick >= aniSpeed) {
            aniTick = 0 ;
            aniIndex ++ ;
            if(aniIndex >= GetSpriteAmount(enemyType, enemyState)){
                aniIndex = 0 ;
            }
        }
    }
    
    public void update(int[][] lvlData){
        updateMove(lvlData) ;
        updateAnimationTick() ;
    }
    
    private void updateMove(int[][] lvlData){
        if(firstUpdate) {
            if(!IsEntityOnFloor(hitbox, lvlData))
                inAir = true ;
            firstUpdate = false ;
        }
        
        if(inAir) {
            if(CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)){
                hitbox.y += fallSpeed ;
                fallSpeed += gravity ;
            }else {
                  inAir = false ;
                  hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed) ;
            }
        }else {
              switch (enemyState) {
                case IDEL :
                    enemyState = RUNNING_RIGHT ;
                    break ;
                case RUNNING_RIGHT :
                    float xSpeed = 0 ;
                    
                    if(walkDir == LEFT){
                        enemyState = RUNNING_LEFT ;
                        xSpeed = -walkSpeed ;
                    }
                    else{
                        enemyState = RUNNING_RIGHT ;
                        xSpeed = walkSpeed ;
                        
                    }
                    
                    if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
                        if(IsFloor(hitbox,xSpeed, lvlData)){
                            hitbox.x += xSpeed ;
                            return ;
                        }
                    changWalkDir() ;
                    
                    break ;
                    
                case RUNNING_LEFT :
                    xSpeed = 0 ;
                    
                    if(walkDir == LEFT){
                        enemyState = RUNNING_LEFT ;
                        xSpeed = -walkSpeed ;
                    }
                    else{
                        enemyState = RUNNING_RIGHT ;
                        xSpeed = walkSpeed ;
                        
                    }
                    
                    if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
                        if(IsFloor(hitbox,xSpeed, lvlData)){
                            hitbox.x += xSpeed ;
                            return ;
                        }
                    changWalkDir() ;
                    
                    break ;
            }
        }
    }
    
    public void resetEnemy() {
	hitbox.x = x;
	hitbox.y = y;
	firstUpdate = true;
	fallSpeed = 0;
}
    
    private void changWalkDir() {
        if(walkDir == LEFT)
            walkDir = RIGHT ;
        else
            walkDir = LEFT ;
    }

    public int getAniIndex() {
        return aniIndex ;
    }
    
    public int getEnimyState() {
        return enemyState ;
    }
}

