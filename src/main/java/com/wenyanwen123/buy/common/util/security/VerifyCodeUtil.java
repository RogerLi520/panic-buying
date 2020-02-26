package com.wenyanwen123.buy.common.util.security;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @Description: 验证码
 * @Author liww
 * @Date 2020/2/23
 * @Version 1.0
 */
@Component
public class VerifyCodeUtil {

    /**
     * @Desc 生成公式验证码
     * @Author liww
     * @Date 2020/2/23
     * @Param []
     * @return java.awt.image.BufferedImage
     */
    public VerifyCodeResult createVerifyCode() {
        int width = 80;
        int height = 32;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);

        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);

        Random rdm = new Random();
        for (int i = 0; i < 50; i++) {
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }

        String verifyCode = generateVerifyCode(rdm);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        g.drawString(verifyCode, 8, 24);
        g.dispose();
        int answer = calc(verifyCode);
        // 返回结果
        VerifyCodeResult verifyCodeResult = new VerifyCodeResult();
        verifyCodeResult.setBufferedImage(image);
        verifyCodeResult.setAnswer(answer);
        return verifyCodeResult;
    }

    /**
     * @Desc 加减乘公式
     * @Author liww
     * @Date 2020/2/23
     * @Param
     * @return
     */
    private static char[] ops = new char[] {'+', '-', '*'};
    private String generateVerifyCode(Random rdm) {
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);
        char op1 = ops[rdm.nextInt(3)];
        char op2 = ops[rdm.nextInt(3)];
        String exp = ""+ num1 + op1 + num2 + op2 + num3;
        return exp;
    }

    /**
     * @Desc 公式答案
     * @Author liww
     * @Date 2020/2/23
     * @Param [exp]
     * @return int
     */
    private static int calc(String exp) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer)engine.eval(exp);
        }catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @Desc 验证码结果
     * @Author liww
     * @Date 2020/2/23
     * @Param
     * @return
     */
    @Data
    public class VerifyCodeResult {
        private BufferedImage bufferedImage;
        private int answer;
    }

}
