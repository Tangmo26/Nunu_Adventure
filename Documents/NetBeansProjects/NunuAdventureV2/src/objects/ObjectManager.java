package objects ;

import entities.Player ;
import gamestate.Playing ;
import java.awt.Graphics ;
import java.awt.image.BufferedImage ;
import java.util.ArrayList ;
import levels.Level ; 
import static utils.Constants.ObjectConstants.* ;
import utils.LoadSave ;

public class ObjectManager {
    private Playing playing ;
    private BufferedImage[] spikeImg ;
    private ArrayList<Spike> spikesTop ;
    private ArrayList<Spike> spikesLeft ;
    private ArrayList<Spike> spikesRight ;
    private ArrayList<Spike> spikesDown ;
    
    public ObjectManager(Playing playing) {
	this.playing = playing;
        loadImgs() ;  
    }
   
    public void checkSpikesTouched(Player p) {
	for (Spike s : spikesTop)
            if (s.getHitbox().intersects(p.getHitbox())){
		p.kill();
            }
        for (Spike s : spikesLeft)
            if (s.getHitbox().intersects(p.getHitbox()))
		p.kill();
        for (Spike s : spikesRight)
            if (s.getHitbox().intersects(p.getHitbox()))
		p.kill();
        for (Spike s : spikesDown)
            if (s.getHitbox().intersects(p.getHitbox()))
		p.kill();
   }
   
    public void loadObjects(Level newlevel){
        spikesTop = newlevel.getSpikesTop() ;
        spikesLeft = newlevel.getSpikesLeft() ;
        spikesRight = newlevel.getSpikesRight() ;
        spikesDown = newlevel.getSpikesDown() ;
    }
   
    public void loadImgs(){
        
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.TRAP_TLRD_ATLAS);
        
        spikeImg = new BufferedImage[4] ;
        
        for(int i = 0 ; i < 4 ; i++){
            spikeImg[i] = img.getSubimage(i * SPIKE_WIDTH_DEFAULT, 0, SPIKE_WIDTH_DEFAULT, SPIKE_HEIGHT_DEFAULT) ;
        }
    }
   
    public void draw(Graphics g, int xLvlOffset){
        drawTrap(g, xLvlOffset) ;
    }

    private void drawTrap(Graphics g, int xLvlOffset) {
        for(Spike s : spikesTop){
            
            g.drawImage(spikeImg[0], (int)(s.getHitbox().x - xLvlOffset), (int)(s.getHitbox().y - s.getyDrawOffset()), SPIKE_WIDTH, SPIKE_HEIGHT, null);
        }
        for(Spike s : spikesLeft){
            g.drawImage(spikeImg[1], (int)(s.getHitbox().x - xLvlOffset), (int)(s.getHitbox().y - s.getyDrawOffset()), SPIKE_WIDTH, SPIKE_HEIGHT, null);
        }
        for(Spike s : spikesRight){
            g.drawImage(spikeImg[2], (int)(s.getHitbox().x - xLvlOffset), (int)(s.getHitbox().y - s.getyDrawOffset()), SPIKE_WIDTH, SPIKE_HEIGHT, null);
        }
        for(Spike s : spikesDown){
            g.drawImage(spikeImg[3], (int)(s.getHitbox().x - xLvlOffset), (int)(s.getHitbox().y - s.getyDrawOffset()), SPIKE_WIDTH, SPIKE_HEIGHT, null);
        }
    }
    
    public void resetAllObject(){
        loadObjects(playing.getLevelManager().getCurrentLevel());
    }
}
