package core;

import java.util.List;

/**
 * Class that contains commonly used numbers and defines valid values.
 */
@SuppressWarnings("magicnumber")
public final class Constants {
    public static final List<String> VALID_UNITS = List.of("ml", "cl", "dl", "gram");
    public static final List<String> VALID_TYPES = List.of("alcohol", "mixer", "extras");
    public static final int SCENE_WIDTH = 800;
    public static final int SCENE_HEIGHT = 600;
    public static final int CONTENT_HEIGHT = 540;
    public static final int FONT_SIZE_40 = 40;
}
