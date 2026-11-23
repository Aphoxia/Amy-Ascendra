package SpriteLoader;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public final class SpriteLoader {
    private static final Map<String, Image> SHEETS = new HashMap<>();

    public static Image loadSprite(

            String sourcepath,
            int frameWidth,
            int frameHeight,
            int column,
            int row) {

        Image sheet = SHEETS.computeIfAbsent(sourcepath, SpriteLoader::loadSpriteSheet);

        int sourceX = column * frameWidth;
        int sourceY = row * frameHeight;

        return new WritableImage(
                sheet.getPixelReader(),
                sourceX,
                sourceY,
                frameWidth,
                frameHeight
        );
    }

    private static Image loadSpriteSheet(String sourcepath) {
        String imageURL = ClassLoader.getSystemResource(sourcepath).toString();
        return new Image(imageURL, false);
    }
}
