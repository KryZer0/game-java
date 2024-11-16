package utility;

import Main.Game;


public class player_constant {

    public static class EnemyConstant {
        public static final int Enemy1 = 0;

        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int HITTING = 3;
        public static final int HURT = 4;
        public static final int DEAD = 5;

        public static final int ENEMY1_WIDTH_DEFAULT = 48;
        public static final int ENEMY1_HEIGHT_DEFAULT = 48;
        public static final int ENEMY1_WIDTH = (int) (ENEMY1_WIDTH_DEFAULT * Game.Scale);
        public static final int ENEMY1_HEIGHT = (int) (ENEMY1_HEIGHT_DEFAULT * Game.Scale);
        public static final int ENEMY1_OFFSET_X = (int) (9 * Game.Scale);
        public static final int ENEMY1_OFFSET_Y = (int) (-4 * Game.Scale);

        public static int GetspriteAmount (int enemyType, int enemyState) {
            switch (enemyType) {
                case Enemy1:
                switch (enemyState) {
                    case IDLE:
                        return 8;
                    case RUNNING:
                        return 8;
                    case HITTING:
                        return 8;
                    case HURT:
                        return 8;
                    case DEAD:
                        return 8;
                }
            }
            return 0;
        }

        public static int getMaxHealth(int enemyType) {
            switch (enemyType) {
                case Enemy1:
                    return 20;          
                default:
                    return 1;
            }
        }

        public static int getEnemyDamage(int enemyType){
            switch (enemyType) {
                case Enemy1:
                    return 10;          
                default:
                    return 1;
            }
        }
    }

    public static class UI {
        public static class Button {
            public static final int Button_Width_Default = 140;
            public static final int Button_Height_Default =56;
            public static final int Button_Width = (int) (Button_Width_Default * Game.Scale);
            public static final int Button_Height = (int) (Button_Height_Default * Game.Scale);
        }

        public static class PauseButtons {
            public static final int SoundSizeDefault = 42;
            public static final int SoundSize = (int) (SoundSizeDefault * Game.Scale);
        }

        public static class CreButton {
            public static final int CRESizeDefault = 56;
            public static final int CRESize = (int) (CRESizeDefault * Game.Scale);
        }

        public static class VolumeButton {
            public static final int VolumeXSizeDefault = 28;
            public static final int VolumeYSizeDefault = 44;
            public static final int VolumeSliderXDefault = 215;

            public static final int VolumeX = (int) (VolumeXSizeDefault * Game.Scale);
            public static final int VolumeY = (int) (VolumeYSizeDefault * Game.Scale);
            public static final int VolumeSliderX = (int) (VolumeSliderXDefault * Game.Scale);
        }
    }

    // this class is for direction of the entities in order to move
    public static class direction {
        public static final int left = 0;
        public static final int up = 1;
        public static final int right = 2;
        public static final int down = 3;
    }
    
    // this class is for animation load, used on Panel.java
    public static class constant {
        public static final int idle = 0;
        public static final int running = 1;
        public static final int jumping = 2;
        public static final int falling = 3;
        public static final int hitting = 4;
        public static final int hurt = 5;
        public static final int dying = 6;
        public static final int prepare_blocking =7;
        public static final int blocking = 8;
        public static final int release_blocking = 9;
        public static final int dead = 10;

        public static int GetspriteAmount(int player_action){

            // The return value is based on pixel art (Sprite), pixel art can be found in src/img
            switch (player_action) {
                case idle:
                    return 5;
                case running:
                    return 8;
                case hitting:
                    return 6;
                case hurt:
                    return 1;
                case dying:
                    return 7;
                case jumping:
                    return 3;
                case falling:
                    return 2;
                case prepare_blocking:
                    return 2;
                case blocking:
                    return 2;
                case release_blocking:
                    return 2;
                case dead:
                    return 2;
            
                default:
                return idle;
            }
        }
    }
}
