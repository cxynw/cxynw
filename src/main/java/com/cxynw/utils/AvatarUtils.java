package com.cxynw.utils;

import com.sun.istack.NotNull;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class AvatarUtils {

    /**
     * 根据用户的第一个字，生成一张dark gray底，白色的字的头像
     *
     * @param nickname
     * @return
     */
    public static BufferedImage generateFaceByUsername(@NotNull String nickname){
        String trim = nickname.trim();
        if(trim.isBlank() || trim.isEmpty() || !trim.matches("^\\w+.*")){
            trim = "M";
        }

        int faceSize = 100;
        int fontSize = faceSize - faceSize / 3;
        BufferedImage face = new BufferedImage(faceSize,faceSize,BufferedImage.TYPE_INT_RGB);

        Graphics2D g = (Graphics2D)face.getGraphics();
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0,0,faceSize,faceSize);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Microsoft YaHei UI",0,fontSize));
        String string = trim.toUpperCase().substring(0, 1);
        FontMetrics fm = g.getFontMetrics();
        Rectangle2D rectangle2D = fm.getStringBounds(string, g);
        g.drawString(string,(int)(face.getWidth()-rectangle2D.getWidth())/2,
                (int)(face.getHeight()-(rectangle2D.getHeight()))/2+fm.getAscent());
        return face;
    }


}
