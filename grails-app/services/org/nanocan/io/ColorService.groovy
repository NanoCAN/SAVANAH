package org.nanocan.io

import java.awt.Color

import static java.awt.Color.getHSBColor

class ColorService {
    def public static randomColor(){
        Random random = new Random();
        final float hue = random.nextFloat();
        // Saturation between 0.1 and 0.3
        final float saturation = (random.nextInt(2000) + 1000) / 10000f;
        final float luminance = 0.9f;
        final Color color = getHSBColor(hue, saturation, luminance);
        String rgb = Integer.toHexString(color.getRGB());
        return "#" + rgb.substring(2, rgb.length());
    }
}
