package Level;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Main.Game;
import entities.Dark_knight;
import entities.EnemyManager;
import entities.player;
import gameStates.GameState;
import gameStates.Playing;
import utility.SaveLoad;

public class LevelManager {
    
    private EnemyManager enemyManager;
    private Game game;
    private BufferedImage[] Level_texture;
    private ArrayList<level> levels;
    private int lvlIndex = 0;

    public LevelManager(Game game) {
        this.game = game;
        importTexture_Level();
        levels = new ArrayList<>();
        createAllLevel();
    }

    public void loadNextLevel() {
        lvlIndex++;
        if (lvlIndex >= levels.size()) {
            lvlIndex = 0;
            System.out.println("You completed the game!");
            GameState.state = GameState.Menu;
        }
        level newLevel = levels.get(lvlIndex);
        game.getPlaying().getEnemyManager().createEnemies(newLevel);
        game.getPlaying().getPlayer().LoadlevelData(newLevel.getLevelData());
        game.getPlaying().setMaxLevelOffSet(newLevel.getLevelOffSet());
    }

    private void createAllLevel() {
        BufferedImage[] allLevels = SaveLoad.GetAllLevels();
        for (BufferedImage img : allLevels)
            levels.add(new level(img));
    }

    private void importTexture_Level() {
        BufferedImage img = SaveLoad.getTextureAtlas(SaveLoad.LEVEL_ATLAS);
        Level_texture = new BufferedImage[48];
        for (int j = 0; j < 4; j++)
            for (int i = 0; i < 12; i++){
                int index = j * 12 + i;
                Level_texture[index] = img.getSubimage(i*32, j*32, 32, 32);
            }
    }

    public void draw(Graphics g, int OffsetLevel) {

        for (int j = 0; j < Game.Tiles_in_Height; j++)
            for (int i = 0; i < levels.get(lvlIndex).getLevelData()[0].length; i++){
                int index = levels.get(lvlIndex).getTextureIndex(i,j);
                g.drawImage(Level_texture[index], Game.Tiles_Size * i - OffsetLevel, Game.Tiles_Size * j, Game.Tiles_Size, Game.Tiles_Size,  null);
            }
    }

    public void update() {
        

    }

    public level getCurrentLevel() {
        return levels.get(lvlIndex);
    }

    public int getTotalLevel() {
        return levels.size();
    }

}
