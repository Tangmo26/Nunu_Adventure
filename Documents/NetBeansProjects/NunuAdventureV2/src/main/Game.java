package main;

import audio.AudioPlayer;
import gamestate.Gamestate;
import gamestate.Menu;
import gamestate.Playing;
import java.awt.Graphics;
import ui.AudioOptions;

public class Game implements Runnable{
    
    private GameWindow gameWindow ;
    private GamePanel gamePanel ;
    private Thread gameThread ;
    private final int UPS_SET = 120 ;
    
    private Playing playing ;
    private Menu menu ;
    private AudioOptions audioOption ;
    private AudioPlayer audioPlayer ;
    
    public final static int TILES_DEFAULT_SIZE = 32 ;
    public final static float SCALE = 1.5f ;
    public final static int TILES_IN_WIDTH = 26 ;
    public final static int TILES_IN_HEIGHT = 14 ;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE) ;
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH ;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT ;
    
    public Game(){
        initClasses() ;
        
        gamePanel = new GamePanel(this) ;
        gameWindow = new GameWindow(gamePanel) ;
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();

        startGameLoop();
    }
    
    private void initClasses() {
        audioOption = new AudioOptions(this) ;
        audioPlayer = new AudioPlayer() ;
        menu = new Menu(this) ;
        playing = new Playing(this) ;
    }
    
    private void startGameLoop(){
        gameThread = new Thread(this) ;
        gameThread.start() ; 
    }
    
    public void update(){
        switch(Gamestate.state){
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            case OPTIONS :
                break ;
            case QUIT :
                System.exit(0);
            default:
                break;
        }
    }
    
    public void render(Graphics g){
        switch(Gamestate.state){
            case MENU :
                menu.draw(g);
                break;
            case PLAYING :
                playing.draw(g);
                break;
            default:
                break;
        }
    }
    
    @Override
    public void run() {
        
        double timePerUpdate = 1000000000.0 / UPS_SET ;
         
        long previousTime = System.nanoTime() ;
        
        int frame = 0 ;
        int updates = 0 ;
        long lastCheck = System.currentTimeMillis() ;
    
        double deltaU = 0 ;
        double deltaF = 0 ;
        
        while(true) {
            long currentTime = System.nanoTime() ;
            
            deltaU += (currentTime - previousTime) / timePerUpdate ;
            deltaF += (currentTime - previousTime) / timePerUpdate ;
            
            previousTime = currentTime ;

            if(deltaU >= 1){
                update() ;
                updates++ ;
                deltaU-- ;
            }
            if(deltaF >= 1){
                gamePanel.repaint() ;
                frame++ ;
                deltaF-- ;
            }

            if(System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck = System.currentTimeMillis() ;
                frame = 0 ;
                updates = 0 ;
            }
        }
    }
    public void WindowFocusLost() {
        if(Gamestate.state == Gamestate.PLAYING){
            playing.getPlayer().resetDirBoolean();
        }
    }
    
    public Menu getMenu(){
        return menu ;
    }
    
    public Playing getPlaying(){
        return playing ;
    }
    
    public AudioOptions getAudioOptions(){
        return audioOption ;
    }
    
    public AudioPlayer getAudioPlayer(){
        return audioPlayer ;
    }
}
