package com.wz.common.util;

import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Random;

/**
 * @projectName: wz-component
 * @package: com.wz.common.util
 * @className: GeneratorCode
 * @description:
 * @author: Zhi
 * @date: 2020-03-03 10:51
 * @version: 1.0
 */
public class GeneratorCode {

    private static final String CODES = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final Random RANDOM = new Random();

    public static final GeneratorCode GENERATOR_CODE = new GeneratorCode();

    /**
     * 生成验证码图片的宽度
     */
    @Setter
    private int width = 100;
    /**
     * 生成验证码图片的高度
     */
    @Setter
    private int height = 50;

    @Setter
    private String[] fontNames = {"宋体", "楷体", "隶书", "微软雅黑"};

    /**
     * 定义验证码图片的背景颜色为白色
     */
    @Setter
    private Color bgColor = new Color(250, 255, 255);

    /**
     * 记录随机code
     */
    @Getter
    private String code;

    /**
     * 获取一个随意颜色
     */
    private Color randomColor() {
        int red = RANDOM.nextInt(150);
        int green = RANDOM.nextInt(150);
        int blue = RANDOM.nextInt(150);
        return new Color(red, green, blue);
    }

    /**
     * 获取一个随机字体
     */
    private Font randomFont() {
        String name = fontNames[RANDOM.nextInt(fontNames.length)];
        int style = RANDOM.nextInt(4);
        int size = RANDOM.nextInt(5) + 24;
        return new Font(name, style, size);
    }

    /**
     * 获取一个随机字符
     */
    private char randomChar() {
        return CODES.charAt(RANDOM.nextInt(CODES.length()));
    }

    /**
     * 创建一个空白的BufferedImage对象
     */
    private BufferedImage createImage() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        // 设置验证码图片的背景颜色
        g2.setColor(bgColor);
        g2.fillRect(0, 0, width, height);
        return image;
    }

    public BufferedImage getImage() {
        BufferedImage image = createImage();
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            String s = String.valueOf(randomChar());
            sb.append(s);
            g2.setColor(randomColor());
            g2.setFont(randomFont());
            float x = i * width * 1.0f / 4;
            g2.drawString(s, x, height - 15);
        }
        this.code = sb.toString();
        drawLine(image);
        return image;
    }

    /**
     * 绘制干扰线
     */
    private void drawLine(BufferedImage image) {
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        int num = 5;
        for (int i = 0; i < num; i++) {
            int x1 = RANDOM.nextInt(width);
            int y1 = RANDOM.nextInt(height);
            int x2 = RANDOM.nextInt(width);
            int y2 = RANDOM.nextInt(height);
            g2.setColor(randomColor());
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawLine(x1, y1, x2, y2);
        }
    }

    public static void outImage(BufferedImage image, OutputStream out) throws IOException {
        ImageIO.write(image, "PNG", out);
    }

    public static String outBase64(BufferedImage image, boolean isUrl) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", bos);
        byte[] bytes = bos.toByteArray();
        Base64.Encoder encoder = Base64.getEncoder();
        String encode = encoder.encodeToString(bytes);
        if (isUrl) {
            return "data:image/png;base64," + encode;
        }
        return encode;
    }

    public static void main(String[] args) throws IOException {
        GeneratorCode g = GENERATOR_CODE;
        BufferedImage image = g.getImage();
        System.out.println(g.getCode());
        OutputStream os = new FileOutputStream("/Users/wangzhi/work/wz-component/wz-common/src/main/java/com/wz/common/util/a.png");
        GeneratorCode.outImage(image, os);
        System.out.println(GeneratorCode.outBase64(image, true));
    }

}
