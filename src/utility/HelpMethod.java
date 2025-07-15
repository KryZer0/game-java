package utility;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Main.Game;
import entities.Dark_knight;
import static utility.player_constant.EnemyConstant.Enemy1;
public class HelpMethod {
    
    public static boolean MoveAllowed(float x, float y, float width, float height, int[][] LevelData) {
		if (!IsSolid(x, y, LevelData))
			    if (!IsSolid(x + width, y, LevelData))
				    if (!IsSolid(x, y + height, LevelData))
                        if (!IsSolid(x + width, y + height, LevelData))
						return true;
		return false;
	}

    private static boolean IsSolid(float x, float y, int[][] LevelData) {
        int maxWidth = LevelData[0].length * Game.Tiles_Size;
        if (x < 0 || x >= maxWidth)
            return true;
        if (y < 0 || y >= Game.Game_Height)
            return true;

        float xIndex = x / Game.Tiles_Size;
        float yIndex = y / Game.Tiles_Size;

        return isTileSolid((int) xIndex, (int) yIndex, LevelData);
    }

    public static boolean isTileSolid(int xTile, int yTile, int[][] LevelData) {
        int value = LevelData[yTile][xTile];

        if (value >= 48 || value < 0 || value != 11)
            return true;
        return false;
    }

    public static float getPlayerXPos(Rectangle2D.Float HitBox, float xSpeed){
        int currentTile = (int) (HitBox.x / Game.Tiles_Size);
        if (xSpeed > 0) {
            int tileXPos = currentTile * Game.Tiles_Size;
            int xOffSet = (int) (Game.Tiles_Size - HitBox.width);
            return tileXPos + xOffSet;
        } else {
            return currentTile * Game.Tiles_Size;
        }
    }

    public static float getPlayerYPosUnderOrAboveTile(Rectangle2D.Float HitBox, float airSpeed){
        int currentTile = (int) (HitBox.y / Game.Tiles_Size);
        if (airSpeed > 0) {
            int tileYPos =  currentTile * Game.Tiles_Size;
            int yOffSet = (int) (Game.Tiles_Size - HitBox.height);
            return tileYPos + yOffSet + Game.Tiles_Size - 1;
        } else {
            return currentTile * Game.Tiles_Size;
        }
    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float HitBox, int[][] LevelData) {
		// Check the pixel below bottomleft and bottomright
		if (!IsSolid(HitBox.x, HitBox.y + HitBox.height + 1, LevelData))
			if (!IsSolid(HitBox.x + HitBox.width, HitBox.y + HitBox.height + 1, LevelData))
				return false;
		return true;

	}

    public static boolean IsFloor(Rectangle2D.Float HitBox, float xSpeed, int[][] LevelData) {
        if (xSpeed > 0) {
		    return IsSolid(HitBox.x + HitBox.width + xSpeed, HitBox.y + HitBox.height + 1, LevelData);
        } else {
            return IsSolid(HitBox.x + xSpeed, HitBox.y + HitBox.height + 1, LevelData);
        }
	}

    public static boolean isTileWalkable(int xStart, int xEnd, int y, int[][] LevelData) {
        for (int i = 0; i < xStart - xEnd; i++) {
            if(isTileSolid(xStart + i, y, LevelData)) 
                return false;
            if(!isTileSolid(xStart + i, y + 1, LevelData)) 
                return false;
        }
        return true;
    }

    public static boolean IsInSight(int [][] LevelData, Rectangle2D.Float firstHitBox, Rectangle2D.Float secondHitBox, int tileY) {
        int firstTileX = (int) (firstHitBox.x / Game.Tiles_Size);
        int secondTileX = (int) (secondHitBox.x / Game.Tiles_Size);

        if (secondTileX > firstTileX) 
            return isTileWalkable(firstTileX, secondTileX, tileY, LevelData);
        else
            return isTileWalkable(secondTileX, firstTileX, tileY, LevelData);
    }

    public static int[][] getlvlData(BufferedImage img) {
        int [][] LevelData = new int[img.getHeight()][img.getWidth()];
        
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++){
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value >= 48)
                        value = 0;                 
                    LevelData[j][i] = value;
                
            }
        return LevelData;
    }

    public static ArrayList<Dark_knight> getEnemy1(BufferedImage is){
        ArrayList<Dark_knight> list = new ArrayList<>();
        for (int j = 0; j < is.getHeight(); j++)
            for (int i = 0; i < is.getWidth(); i++){
                Color color = new Color(is.getRGB(i, j));
                int value = color.getGreen();
                if (value == Enemy1)
                    list.add(new Dark_knight(i * Game.Tiles_Size, j * Game.Tiles_Size));
            }
        return list;
    }
}
