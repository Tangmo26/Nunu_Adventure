package entities;

import audio.AudioPlayer;
import gamestate.Playing;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Game;
import static utils.Constants.PlayerConstants.*;
import static utils.HelpMethods.*;
import utils.LoadSave;
import static utils.Constants.PlayerHeart.*;

public class Player extends Entity implements Runnable{
    
    private BufferedImage[][] animations;
    private BufferedImage heart ;
    private int aniTick , aniIndex = 0, aniSpeed = 10;
    private int playerAction = IDLE ;
    private boolean moving = false ;
    private boolean left, up, right, down, jump;
    private float playerSpeed = 2f * Game.SCALE;
    private int[][] lvlData ;
    private float xDrawOffset = 9 * Game.SCALE ;
    private float yDrawOffset = 2 * Game.SCALE ;
    private Thread threadHeart ;
    private boolean openHeart = true;
    
    //Jumping / Gravity
    private float airSpeed = 0f ;
    private float gravity = 0.15f * Game.SCALE ;
    private float jumpSpeed = -5f * Game.SCALE ;
    private float fallSpeedAfterCollision = 1.5f * Game.SCALE ;
    private boolean inAir = false ;
    
    private int MaxHealth = 3 ;
    private int HeartCount = MaxHealth ;
    private Playing playing;
    
    public Player(float x,float y,int width, int height, Playing playing) {
        super(x, y, width, height);
        loadAnimatios() ;
        loadHeart() ;
        threadHeart = new Thread(this) ;
        threadHeart.start();
       
        initHitbox(x, y, (int)(16 * Game.SCALE), (int)(27 * Game.SCALE));
        this.playing = playing ;
    }
    
    
    public void setSpawn(Point spawn){
        this.x = spawn.x ;
        this.y = spawn.y ;
        hitbox.x = x ;
        hitbox.y = y ;
    }
    
    public void setPlaying(Playing playing){
        this.playing = playing ;
    }

    @Override
    public void update(){
        if(HeartCount <= 0){
            playing.setGameOver(true) ;
            playing.getGame().getAudioPlayer().playEffect(AudioPlayer.GAMEOVER);
            return ;
        }
        
        updatePos() ;

        if(moving){
            checkSpikesTouched() ;
        }
        updateAnimationTick() ;
        setAnimation() ;
        
    }
    
    private void checkSpikesTouched() {
        playing.checkSpikesTouched(this);
    }
    
    @Override
    public void render(Graphics g, int lvlOffset){
        g.drawImage(animations[playerAction][aniIndex], (int)(hitbox.x - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset), width, height, null) ;
        
        if((hitbox.x - xDrawOffset) - lvlOffset > 1100){
            playing.setLevelComplete(true);
            playing.getGame().getAudioPlayer().playEffect(AudioPlayer.LVL_COMPLETED);
            playing.getGame().getAudioPlayer().setLevelSong(playing.getLevelManager().getLevelIndet()+1);
        }

        if(openHeart){
            g.drawImage(heart, 20, 15, HEART_WIDTH, HEART_HEIGHT, null) ;
            g.setFont(getFont((int)(26.66 * Game.SCALE))) ;
            g.setColor(Color.WHITE) ; 
            g.drawString(" X  " + HeartCount, (int)(50 * Game.SCALE), (int)(40 * Game.SCALE)) ;
        }
    }
    
    public Font getFont(int size){
        Font font = null;
	try {
            font = Font.createFont(Font.TRUETYPE_FONT,new File("font\\Pixel_Sans_Serif.ttf")) ;
            return font.deriveFont((float)size);
	} catch (Exception e) {
            e.printStackTrace();
	}
	return font;
    }
    
    public void loadHeart(){
        heart = LoadSave.GetSpriteAtlas(LoadSave.HEART) ;
    }
    
    private void updateAnimationTick() {
        aniTick++ ;
        if(aniTick >= aniSpeed){
            aniTick = 0 ;
            aniIndex += 1 ;
            if(aniIndex >= GetSpriteAmount(playerAction) ){
                aniIndex = 0 ;
            }
        }
    }
    
    private void setAnimation() {
        int startAni = playerAction ;
        
        if(moving)
            playerAction = RUNNING ;
            
        else
            playerAction = IDLE ;
        
        if(inAir){
            if(airSpeed < 0)
                playerAction  = JUMP ;
            else
                playerAction = RUNNING ;
        }
        if(startAni != playerAction)
            resetAniTick() ;
        
    }
    private void resetAniTick(){
        aniTick = 0 ;
        aniIndex = 0 ;
    }
    
    private void updatePos() {
        moving = false ;
        
        if(jump){
            jump() ;
        }
        
        if(!inAir)
            if(!left && !right || (right && left))
                return ;
        
        float xSpeed = 0;
        
        if(left) 
            xSpeed -= playerSpeed ;
        if(right)
            xSpeed += playerSpeed ;
        
        if(!inAir) {
            if(!IsEntityOnFloor(hitbox , lvlData)) {
                inAir = true ;
            }
        }
        
        if(inAir) {
            if(CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)){
                hitbox.y += airSpeed ;
                airSpeed += gravity ;
                updateXPos(xSpeed);
            }else {
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed) ;
                if(airSpeed > 0)
                    resetInAir() ;
                else
                    airSpeed = fallSpeedAfterCollision ;
                updateXPos(xSpeed);
            }
        }
        else
            updateXPos(xSpeed);
        moving = true ;
    }
    
    private void jump() {
        playing.getGame().getAudioPlayer().playEffect(AudioPlayer.JUMP);
        if(inAir)
            return;
        inAir = true ;
        airSpeed = jumpSpeed ;
    }
    
    public void kill(){
        HeartCount -= 1 ;
        AniHit() ;
    }
        
    private void resetInAir() {
        inAir = false ;
        airSpeed = 0 ;
    }
        
    private void updateXPos(float xSpeed) {
        if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)){
            hitbox.x += xSpeed ;
        }else{
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed) ;
        }
        
    }
    private void loadAnimatios() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
            
        animations = new BufferedImage[5][5] ;
        for(int j = 0 ; j < animations.length ; j++)
            for(int i = 0 ; i < animations[0].length ; i++)
                animations[j][i] = img.getSubimage(i * 34, j*32, 34, 32) ;
        
    }
    
    public void loadLvlData(int[][] lvlData){
        this.lvlData = lvlData ;
        if(!IsEntityOnFloor(hitbox, lvlData))
            inAir = true ;
    }
    
    public void resetDirBoolean() {
        left = false ;
        right = false ;
        up = false ;
        down = false ;
    }
    
    public void resetAll(){
        resetDirBoolean();
        inAir = false ;
        moving = false;
        
        playerAction = IDLE ;
        HeartCount = MaxHealth ;
        
        hitbox.x = x ;
        hitbox.y = y ;
        
        
        if(!IsEntityOnFloor(hitbox, lvlData))
            inAir = true ;
        
    }
    
    public void AniHit(){
        resetDirBoolean();
        inAir = false ;
        moving = false;
        
        playerAction = IDLE ;
        
        hitbox.x = x ;
        hitbox.y = y ;
        
        if(!IsEntityOnFloor(hitbox, lvlData))
            inAir = true ;
        
        
    }
    
    public boolean getLeft(){
        return left ;
    }
    public void setLeft(boolean left){
        this.left = left ;
    }
    public boolean getUp(){
        return up ;
    }
    public void setUp(boolean up){
        this.up = up ;
    }
    public boolean getRight(){
        return right ;
    }
    public void setRight(boolean right){
        this.right = right ;
    }
    public boolean getDown(){
        return down ;
    }
    public void setDown(boolean down){
        this.down = down ;
    }
    public void setJump(boolean jump){
        this.jump = jump ;
    }

    @Override
    public void run() {
        while(true){
            openHeart = !openHeart ;
            try {
                threadHeart.sleep(500) ;
            } catch (InterruptedException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
