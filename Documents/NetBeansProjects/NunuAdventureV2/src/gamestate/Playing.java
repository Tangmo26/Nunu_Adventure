package gamestate;

import entities.EnemyManager;
import entities.Entity;
import entities.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import levels.LevelManager;
import main.Game;
import objects.ObjectManager;
import ui.GameOverOverley;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;
import utils.LoadSave;
import static utils.Constants.Environment.* ;

public class Playing extends State implements Statemethods{
    private Player player; 
    private LevelManager levelManager ;
    private EnemyManager enemyManager ;
    private ObjectManager objectManager ;
    private PauseOverlay pauseOverlay ;
    private GameOverOverley gameOverOverlay ;
    private LevelCompletedOverlay levelCompletedOverlay ;
    private boolean paused = false ;
    
    private int xLvlOffset ;
    private int leftBorder = (int) (0.4 * Game.GAME_WIDTH) ;
    private int rightBorder = (int) (0.6 * Game.GAME_WIDTH) ;

    private int maxlvlOffsetX;
    
    private BufferedImage GreenTree, grayTree, blackTree ;
    
    private boolean gameOver ;
    private boolean lvlCompleted;

    public Playing(Game game) {
        super(game);
        initClasses() ;
        
        GreenTree = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_3) ;
        grayTree = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_2) ;
        blackTree = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_1) ;

        calcLvlOffset() ;
        loadStartLevel() ;
        
    }
    
    public void loadNextLevel(){
        resetAll() ;
        levelManager.loadNextLevel() ;
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        loadStartLevel() ;
    }
    
    private void loadStartLevel() {
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        objectManager.loadObjects(levelManager.getCurrentLevel());
    }
    
    private void calcLvlOffset(){
        maxlvlOffsetX = levelManager.getCurrentLevel().getLvlOffset() ;
    }
    
    private void initClasses() {
        levelManager = new LevelManager(game) ;
        enemyManager = new EnemyManager(this) ;
        objectManager = new ObjectManager(this) ;
        
        player = new Player( 200, 200, (int)(32 * Game.SCALE),(int)(30*Game.SCALE), this) ;
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData()) ;
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        
        pauseOverlay = new PauseOverlay(this) ;
        gameOverOverlay = new GameOverOverley(this) ;
        levelCompletedOverlay = new LevelCompletedOverlay(this) ;
        
    }

    @Override
    public void update() {
        if(paused){
            pauseOverlay.update();
        }
        else if(lvlCompleted) {
            levelCompletedOverlay.update();
        }
        else if(!gameOver){
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData());
            checkCloseToBorder() ;
        }
    }
    
    private void checkCloseToBorder() {
        int playerX =(int) player.getHitbox().x ;
        int diff = playerX - xLvlOffset ;
        
        if(diff > rightBorder)
            xLvlOffset += diff - rightBorder ;
        else if(diff < leftBorder)
            xLvlOffset += diff - leftBorder ;
        
        if(xLvlOffset > maxlvlOffsetX)
            xLvlOffset = maxlvlOffsetX ;
        else if(xLvlOffset < 0)
            xLvlOffset = 0 ;
    }

    @Override
    public void draw(Graphics g) {
        
        drawTree(g) ;
        
        levelManager.draw(g, xLvlOffset) ;
        player.render(g, xLvlOffset);
        enemyManager.draw(g, xLvlOffset);
        objectManager.draw(g, xLvlOffset);

        if(paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g) ;
        }
        else if(gameOver){
            gameOverOverlay.draw(g) ;
        }
        else if(lvlCompleted){
            levelCompletedOverlay.draw(g) ;
        }
    }
    
    private void drawTree(Graphics g) {
        for(int i = 0 ; i < 3 ; i++)
            g.drawImage(GreenTree, i * (GRAY_TREE_WIDTH - 2) - (int)(xLvlOffset * 0.1) , 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null) ;
        
        for(int i = 0 ; i < 3 ; i++)
            g.drawImage(grayTree, i * (GRAY_TREE_WIDTH - 2) - (int)(xLvlOffset * 0.3), (int)(65 * Game.SCALE), GRAY_TREE_WIDTH, GRAY_TREE_HEIGHT, null) ;
        
        for(int i = 0 ; i < 3 ; i++)
            g.drawImage(blackTree, i * (Game.GAME_WIDTH - 2) - (int)(xLvlOffset * 0.5), 0 + 50, Game.GAME_WIDTH + 50, Game.GAME_HEIGHT, null) ;
        
    }
    
    public void checkSpikesTouched(Player p) {
        objectManager.checkSpikesTouched(p);
    }
    
    public void resetAll() {
        // reset player, enemy, lvl etc .
        gameOver = false ;
        paused = false ;
        lvlCompleted = false ;
        
        player.resetAll() ;
        enemyManager.resetAllEnemies() ;
        objectManager.resetAllObject();
    }
    
    public void setGameOver(boolean gameOver){
        this.gameOver = gameOver ;
    }

    
    @Override
    public void mouseClicked(MouseEvent e) {

    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if(gameOver)
            gameOverOverlay.keyPressed(e) ;
        else
            switch(e.getKeyCode()){
                case KeyEvent.VK_A :
                    player.setLeft(true);
                    break ;
                case KeyEvent.VK_D :
                    player.setRight(true);
                    break ;
                case KeyEvent.VK_SPACE :
                    player.setJump(true);
                    break ;
                case KeyEvent.VK_BACK_SPACE :
                    Gamestate.state = Gamestate.MENU ;
                case KeyEvent.VK_ESCAPE :
                    paused = !paused ;
                    break ;
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!gameOver){
            switch(e.getKeyCode()){
            case KeyEvent.VK_A :
                player.setLeft(false);
                break ;
            case KeyEvent.VK_D :
                player.setRight(false);
                break ;
            case KeyEvent.VK_SPACE :
                player.setJump(false);
                break ;
            }
        }
    }
    
    public void mouseDragged(MouseEvent e){
        if(!gameOver)
            if(paused)
                pauseOverlay.mouseDragged(e);
    }
        
    @Override
    public void mousePressed(MouseEvent e) {
        if(!gameOver) {
            if(paused)
                pauseOverlay.mousePressed(e);
            else if(lvlCompleted)
                levelCompletedOverlay.mousePressed(e);
        }   
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!gameOver){
            if(paused)
                pauseOverlay.mouseReleased(e);
            else if(lvlCompleted)
                levelCompletedOverlay.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(!gameOver){
            if(paused)
                pauseOverlay.mouseMoved(e);
            else if(lvlCompleted)
                levelCompletedOverlay.mouseMoved(e);
        }
    }
    
    public void setLevelComplete(boolean levelComplete){
        this.lvlCompleted = levelComplete ;
    }
    
    //public void setLevelCom
    public int getLevelComplete(){
        return 1 ;
    }
    
    public void setMaxLvlOffset(int lvlOffset){
        this.maxlvlOffsetX = lvlOffset ;
    }

    public void unpauseGame(){
        paused = false ;
    }
    
    public void WindowFocusLost() {
        player.resetDirBoolean();
    }
    
    public Player getPlayer(){
        return player ;
    }
    
    public EnemyManager getEnemyManager(){
        return enemyManager ;
    }
    
    public LevelManager getLevelManager(){
        return levelManager ;
    }
    
    public ObjectManager getObjectManager(){
        return objectManager ;
    }
}
