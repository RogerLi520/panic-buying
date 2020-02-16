package com.wenyanwen123.buy.commons.util;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * 随机数
 */
public final class RandomUtil {

    private RandomUtil() {
    }

    /**
     * 字符字典
     */
    private static final char[] VCCHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
            'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y'};
    /**
     * 数字字典
     */
    private static final char[] NUMBERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    /**
     * 生成随机数
     */
    private static Random random = new Random();

    /**
     * 获取随机字符串（4位）
     *
     * @return
     */
    public static String getRandomString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            builder.append(VCCHARS[random.nextInt(VCCHARS.length)]);
        }
        return builder.toString();
    }

    /**
     * 获取随机字符串
     *
     * @return
     */
    public static String getRandomString(int count) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            builder.append(VCCHARS[random.nextInt(VCCHARS.length)]);
        }
        return builder.toString();
    }

    /**
     * 获取随机数字（4位）
     *
     * @return
     */
    public static String getRandom() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            builder.append(NUMBERS[random.nextInt(NUMBERS.length)]);
        }
        return builder.toString();
    }

    /**
     * 获取随机数字
     *
     * @return
     */
    public static String getRandom(int count) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            builder.append(NUMBERS[random.nextInt(NUMBERS.length)]);
        }
        return builder.toString();
    }

    //region 暂时没用的方法

    /**
     * 获取随机数颜色[深色系]
     *
     * @return
     */
    private static Color getRandomColor() {

        return new Color(random.nextInt(80) + 80, random.nextInt(80) + 80, random.nextInt(80) + 80);

    }

    /**
     * 获取干扰色
     *
     * @param c
     * @return
     */
    private static Color getInterferenceColor(Color c) {

        return new Color(c.getRed() + random.nextInt(30) + 20, c.getBlue() + random.nextInt(30) + 20, c.getGreen() + random.nextInt(30) + 20);

    }

    /**
     * 获取某颜色的反色
     *
     * @param c
     * @return
     */
    private static Color getReverseColor(Color c) {

        return new Color(255 - c.getRed(), 255 - c.getBlue(), 255 - c.getGreen());

    }

    /**
     * 生成随机数图片
     *
     * @param request
     * @param response
     */
    private static void outputCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String randomString = getRandomString();

        response.setContentType("image/jpeg");
        request.getSession(true).setAttribute("ValidateCode", randomString);

        int width = 68;
        int height = 33;

        Color color = getRandomColor();
        Color reverse = getReverseColor(color);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics2D = image.createGraphics();
        graphics2D.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        //背景色
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, width, height);


        //画干扰线
        graphics2D.setColor(getInterferenceColor(color));
        for (int i = 0, n = random.nextInt(8) + 5; i < n; i++) {

            int x1 = random.nextInt(width);
            int x2 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int y2 = random.nextInt(height);
            graphics2D.drawLine(x1, y1, x2, y2);
        }

        //画文字
        graphics2D.setColor(color);
        graphics2D.drawString(randomString, 7, 24);

        //画干扰点
        graphics2D.setColor(reverse);
        for (int i = 0, n = random.nextInt(50) + 35; i < n; i++) {

            graphics2D.drawRect(random.nextInt(width), random.nextInt(height), 1, 1);
        }

        ServletOutputStream outputStream = response.getOutputStream();

//        JPEGImageEncoder jpegImageEncoder = JPEGCodec.createJPEGEncoder(outputStream);
//
//        jpegImageEncoder.encode(image);

        ImageIO.write(image, "jpg", outputStream);

        outputStream.flush();

    }

    //endregion
}
