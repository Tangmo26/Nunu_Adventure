package utils;

import entities.Monkey;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import main.Game;
import objects.Spike;
import static utils.Constants.EnemyConstants.MONKEY;
import static utils.Constants.ObjectConstants.*;

public class HelpMethods {

    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
        if (!IsSolid(x , y, lvlData))
            if (!IsSolid(x + width, y + height, lvlData))
                if (!IsSolid(x + width, y, lvlData))
                    if (!IsSolid(x, y + height, lvlData))
                        return true;
        return false;
    }

    private static boolean IsSolid(float x, float y, int[][] lvlData) {
        int maxWidth = lvlData[0].length * Game.TILES_SIZE;
        
	if (x < 0 || x >= maxWidth)
            return true;
	if (y < 0 || y >= Game.GAME_HEIGHT)
            return true;
                
        float xIndex = x / Game.TILES_SIZE;
	float yIndex = y / Game.TILES_SIZE;

	int value = lvlData[(int) yIndex][(int) xIndex];

	if (value >= 48 || value < 0 || value != 11)
            return true;
       
	return false ;
        
    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
	int currentTile = (int) (hitbox.x / Game.TILES_SIZE);
	if (xSpeed > 0) {
            // Right
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int) (Game.TILES_SIZE - hitbox.width);
            return tileXPos + xOffset - 1;
	} else
            // Left
            return currentTile * Game.TILES_SIZE;
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
	int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
	if (airSpeed > 0) {
            // Falling - touching floor
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset = (int) (Game.TILES_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
	} else
            // Jumping
            return currentTile * Game.TILES_SIZE;
    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
	// Check the pixel below bottomleft and bottomright
	if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
            if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
		return false;
        return true;
	}

    
    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData){
        return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData) ;
        
    }

    public static int[][] GetLevelData(BufferedImage img) {
	int[][] lvlData = new int[img.getHeight()][img.getWidth()];
	for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
		int value = color.getRed();
		if (value >= 48)
                    value = 11 ;
		lvlData[j][i] = value;
            }
	return lvlData;
        
    }
    
    public static ArrayList<Monkey> getMon(BufferedImage img) {
        ArrayList<Monkey> list = new ArrayList<>() ;
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
		int value = color.getGreen();
		if (value == MONKEY)
                    list.add(new Monkey(i * Game.TILES_SIZE, j * Game.TILES_SIZE)) ;
            }
        return list ;
    }
    
    public static ArrayList<Spike> GetSpikesTop(BufferedImage img){
        ArrayList<Spike> list = new ArrayList<>();
        
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
		Color color = new Color(img.getRGB(i, j));
		int value = color.getBlue();
		if (value == SPIKE_TOP)
                    list.add(new Spike(i * Game.TILES_SIZE, j * Game.TILES_SIZE, SPIKE_TOP));
            }
        
        return list ;
    }
    
    public static ArrayList<Spike> GetSpikesLeft(BufferedImage img){
        ArrayList<Spike> list = new ArrayList<>();
        
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
		Color color = new Color(img.getRGB(i, j));
		int value = color.getBlue();
                if(value == SPIKE_LEFT)
                    list.add(new Spike(i * Game.TILES_SIZE, j * Game.TILES_SIZE, SPIKE_TOP)) ;
            }
        
        return list ;
    }
    
    public static ArrayList<Spike> GetSpikesRight(BufferedImage img){
        ArrayList<Spike> list = new ArrayList<>();
        
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
		Color color = new Color(img.getRGB(i, j));
		int value = color.getBlue();
                if(value == SPIKE_RIGHT)
                    list.add(new Spike(i * Game.TILES_SIZE, j * Game.TILES_SIZE, SPIKE_TOP)) ;
            }
        
        return list ;
    }
     
    public static ArrayList<Spike> GetSpikesDown(BufferedImage img){
        ArrayList<Spike> list = new ArrayList<>();
        
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
		Color color = new Color(img.getRGB(i, j));
		int value = color.getBlue();
                if(value == SPIKE_DOWN)
                    list.add(new Spike(i * Game.TILES_SIZE, j * Game.TILES_SIZE, SPIKE_TOP)) ;
            }
        
        return list ;
    }
    
     public static Point GetPlayerSpawn(BufferedImage img){
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
		Color color = new Color(img.getRGB(i, j));
		int value = color.getGreen();
		if (value == 100)
                    return new Point(i * Game.TILES_SIZE, j* Game.TILES_SIZE) ;
            }
        
        return new Point(1 * Game.TILES_SIZE, 1* Game.TILES_SIZE) ;
     }
}