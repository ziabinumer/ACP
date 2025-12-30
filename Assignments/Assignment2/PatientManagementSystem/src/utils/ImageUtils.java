package utils;

import javax.swing.ImageIcon;

public class ImageUtils {
    public static ImageIcon icon() {
        return new ImageIcon(ImageUtils.class.getResource("/resources/icons/logo.png"));
    }
}