package utils;

import main.Game;

public class Constants {
    
    public static class ObjectConstants {
        public static final int SPIKE_TOP = 0 ;
        public static final int SPIKE_LEFT = 1 ;
        public static final int SPIKE_RIGHT = 2 ;
        public static final int SPIKE_DOWN = 3 ;
        
        public static final int SPIKE_WIDTH_DEFAULT = 32 ;
        public static final int SPIKE_HEIGHT_DEFAULT = 32 ;
        public static final int SPIKE_WIDTH = (int)(SPIKE_WIDTH_DEFAULT * Game.SCALE);
        public static final int SPIKE_HEIGHT = (int)(SPIKE_HEIGHT_DEFAULT * Game.SCALE);
    }
    
    public static class GameOvers {
        public static final int GAMEOVER_WIDTH_DEFALSE = 234 ;
        public static final int GAMEOVER_HEIGHT_DEFALSE = 175 ;
        public static final int GAMEOVER_WIDTH = (int)(GAMEOVER_WIDTH_DEFALSE * Game.SCALE * 2) ;
        public static final int GAMEOVER_HEIGHT = (int)(GAMEOVER_HEIGHT_DEFALSE * Game.SCALE * 2) ;
    }
    
    public static class EnemyConstants {
        public static final int MONKEY = 0 ;
        public static final int IDEL = 0 ;
        public static final int RUNNING_RIGHT = 1 ;
        public static final int RUNNING_LEFT = 2 ;
        
        public static final int MONKEY_WIDTH_DEFAULT = 53 ;
        public static final int MONKEY_HEIGHT_DEFAULT = 31 ;
        
        public static final int MONKEY_WIDTH = (int) (MONKEY_WIDTH_DEFAULT * Game.SCALE) ;
        public static final int MONKEY_HEIGHT = (int) (MONKEY_HEIGHT_DEFAULT * Game.SCALE) ;
        
        public static final int MONKEY_DRAWOFFSET_X = (int)(21 * Game.SCALE) ;
        public static final int MONKEY_DRAWOFFSET_Y = (int)(6 * Game.SCALE) ;
        
        public static final int GetSpriteAmount(int enemy_Type, int enemy_state){
            switch(enemy_Type) {
                case MONKEY :
                    switch(enemy_state){
                        case IDEL :
                            return 4 ;
                        case RUNNING_RIGHT :
                            return 8 ;
                        case RUNNING_LEFT :
                            return 8 ;
                    }
                }
            return 0 ;
            }
    }
    
    public static class PlayerHeart{
        public static final int HEART_WIDTH_DEFAULT = 16 ;
        public static final int HEART_HEIGHT_DEFAULT = 15 ;
        public static final int HEART_WIDTH = (int)(HEART_WIDTH_DEFAULT * Game.SCALE * 2.2 ) ;
        public static final int HEART_HEIGHT = (int)(HEART_HEIGHT_DEFAULT * Game.SCALE * 2.2 ) ;
    }
    
    public static class Environment{
        public static final int GRAY_TREE_WIDTH_DEFAULT = 238 ;
        public static final int GRAY_TREE_HEIGHT_DEFAULT = 93 ;
        
        public static final int BLACK_TREE_WIDTH_DEFAULT = 238 ;
        public static final int BLACK_TREE_HEIGHT_DEFAULT = 128 ;
        
        public static final int GRAY_TREE_WIDTH = (int) (GRAY_TREE_WIDTH_DEFAULT * Game.SCALE * 2.9);
        public static final int GRAY_TREE_HEIGHT = (int) (GRAY_TREE_HEIGHT_DEFAULT * Game.SCALE * 2.9);
        
        public static final int BLACK_TREE_WIDTH = (int) (BLACK_TREE_WIDTH_DEFAULT * Game.SCALE * 2.6);
        public static final int BLACK_TREE_HEIGHT = (int) (BLACK_TREE_HEIGHT_DEFAULT * Game.SCALE * 2.6);
    }
    
    public static class UI{
        public static class Buttons{
            public static final int B_WIDTH_DEFAULT = 61;
            public static final int B_HEIGHT_DEFAULT = 9;
            public static final int B_WIDTH = (int) ((B_WIDTH_DEFAULT * Game.SCALE) * 3.0);
            public static final int B_HEIGHT = (int) ((B_HEIGHT_DEFAULT * Game.SCALE) * 3.0);
        }
        
        public static class PauseButtons{
            public static final int SOUND_SIZE_DEFAULT = 42 ;
            public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE) ;
        }
        
        public static class URMButtons{
            public static final int URM_DEFAULT_SIZE = 56 ;
            public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.SCALE) ;
        }
        
        public static class VolumButtons{
            public static final int VOLUME_DEFAULT_WEIGTH = 28 ;
            public static final int VOLUME_DEFAULT_HEIGHT = 44 ;
            public static final int SLIDER_DEFAULT_WEIGTH = 215 ;
            
            public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WEIGTH * Game.SCALE) ;
            public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * Game.SCALE) ;
            public static final int SLIDER_WEIGTH = (int) (SLIDER_DEFAULT_WEIGTH  * Game.SCALE) ;
            
        }
    }
    
    public static class Directions{
        public static final int LEFT = 0 ;
        public static final int UP = 1 ;
        public static final int RIGHT = 2 ;
        public static final int DOWN = 3 ;
    }
    
    //row in array
    public static class PlayerConstants{
        public static final int IDLE = 0 ;
        public static final int RUNNING = 1 ;
        public static final int JUMP  = 2;
        public static final int ATTACK_1 = 3 ;
        public static final int FALLING = 4 ;
        public static final int GROUND = 5 ;
        public static final int HIT = 6 ;

    
        public static int GetSpriteAmount(int player_action){
            switch(player_action) {
                case RUNNING  :
                    return 5 ;
                case IDLE :
                    return 4 ;
                case JUMP :
                    return 5 ;
                default :
                    return 1 ;
                
            }
        }
    }
}
