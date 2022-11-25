package levels;

import entities.Monkey;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import main.Game;
import objects.Spike;
import utils.HelpMethods;
import static utils.HelpMethods.GetLevelData;
import static utils.HelpMethods.getMon;
import static utils.HelpMethods.GetPlayerSpawn;

public class Level {

    private BufferedImage img ;
    private int[][] lvlData ;
    
    private ArrayList<Spike> spikesTop ;
    private ArrayList<Spike> spikesLeft ;
    private ArrayList<Spike> spikesRight ;
    private ArrayList<Spike> spikesDown ;
    
    private ArrayList<Monkey> monkey ;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxlvlOffsetX;
    private Point playSpawn ;
    
    public Level(BufferedImage img){
        this.img = img ;
        
        createLevelData() ;
        createEnemies() ;
        createSpikes() ;
        
        calcLvlOffsets() ;
        calcPlayerSpawn() ;
    }
    
    private void calcPlayerSpawn(){
        playSpawn = GetPlayerSpawn(img) ;
    }
    
    private void calcLvlOffsets() {
        lvlTilesWide = img.getWidth() ;
        maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH ;
        maxlvlOffsetX = Game.TILES_SIZE * maxTilesOffset ;
    }
    
    private void createSpikes() {
        spikesTop = HelpMethods.GetSpikesTop(img);
        spikesLeft = HelpMethods.GetSpikesLeft(img);
        spikesRight = HelpMethods.GetSpikesRight(img) ;
        spikesDown = HelpMethods.GetSpikesDown(img) ;
    }
    
    public void createLevelData(){
        lvlData = GetLevelData(img) ;
    }
    
    private void createEnemies() {
        monkey = getMon(img) ;
    }
    
    public int getSpritIndex(int x ,int y){
        return lvlData[y][x] ;
    }
    public int[][] getLevelData(){
        return lvlData ;
    }
    public int getLvlOffset(){
        return maxlvlOffsetX ;
    }
    public ArrayList<Monkey> getMonkey(){
        return monkey ;
    }
    
    public ArrayList<Spike> getSpikesLeft(){
        return spikesLeft ;
    }
    public ArrayList<Spike> getSpikesRight(){
        return spikesRight ;
    }
    
    public ArrayList<Spike> getSpikesDown(){
        return spikesDown ;
    }
    
    public ArrayList<Spike> getSpikesTop() {
	return spikesTop;
    }
    
    public Point getPlayerSpawn(){
        return playSpawn ;
    }

}
