package main;

import Inputs.KeybordInputs;
import Inputs.MouseInput;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import static main.Game.GAME_HEIGHT ;
import static main.Game.GAME_WIDTH ;


public class GamePanel extends JPanel{
    private MouseInput mouseInput ;
    private Game game ;
    
    public GamePanel(Game game){      
        mouseInput = new MouseInput(this) ;
        this.game = game ;
        
        setPanelSize() ;
        addKeyListener(new KeybordInputs(this)) ;
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
        
    }
    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH,GAME_HEIGHT);
        setPreferredSize(size);
        
        System.out.println("size : "+GAME_WIDTH +" : "+GAME_HEIGHT);
    }
    
    public void updateGame(){

    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g) ;
        game.render(g) ;
        
    }
    public Game getGame(){
        return game ;
    }





}
