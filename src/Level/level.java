package Level;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Main.Game;
import entities.Dark_knight;
import static utility.HelpMethod.getlvlData;
import static utility.HelpMethod.getEnemy1;


public class level {
    
    private BufferedImage img;
    private int[][] LevelData;
    private ArrayList<Dark_knight> dark_knights;
    private int levelTileWide;
    private int maxTilesOffset;
    private int maxLevelOffsetX;

    public level(BufferedImage img){
        this.img = img;
        createLevelDatas();
        createEnemies();
        CalculateOffsetLevel();
    }

    private void createLevelDatas() {
        LevelData = getlvlData(img);
    }

    
    private void CalculateOffsetLevel() {
        levelTileWide = img.getWidth();
        maxTilesOffset = levelTileWide - Game.Tiles_in_Width;
        maxLevelOffsetX = Game.Tiles_Size * maxTilesOffset;
    }

    private void createEnemies() {
        dark_knights = getEnemy1(img);
    }

    public int getTextureIndex(int x, int y) {
        return LevelData[y][x];
    }

    public int[][] getLevelData(){
        return LevelData;
    }

    public int getLevelOffSet() {
        return maxLevelOffsetX;
    }
    
    public ArrayList<Dark_knight> getEnemies1() {
        return dark_knights;
    }
}
