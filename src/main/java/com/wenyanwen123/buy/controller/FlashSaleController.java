package com.wenyanwen123.buy.controller;

import com.wenyanwen123.buy.common.core.annotation.AccessLimit;
import com.wenyanwen123.buy.common.domain.learningdb.User;
import com.wenyanwen123.buy.common.response.ResultResponse;
import com.wenyanwen123.buy.common.util.LogUtil;
import com.wenyanwen123.buy.service.FlashSaleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

/**
 * @Description: 抢购
 * @Author liww
 * @Date 2020/2/23
 * @Version 1.0
 */
@Api(value = "抢购")
@Controller
@RequestMapping("/api/flash-sale")
public class FlashSaleController {

    private static final Logger log = LoggerFactory.getLogger(FlashSaleController.class);

    @Autowired
    private FlashSaleService flashSaleService;

    @ApiOperation(value = "获取秒杀验证码")
    @ApiResponse(code = 200, message = "ok", response = ResultResponse.class)
    @ResponseBody
    @GetMapping(value = "verify-code")
    public ResultResponse getSeckillVerifyCod(HttpServletResponse response, User user, @RequestParam("goodsId") long goodsId) {
        LogUtil.callStart(log, "获取秒杀验证码");
        try {
            BufferedImage image  = flashSaleService.getSeckillVerifyCod(response, user, goodsId);
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "JPEG", out);
            out.flush();
            out.close();
            return null;
        } catch(Exception e) {
            e.printStackTrace();
            return ResultResponse.fail();
        }
    }

    @ApiOperation(value = "获取秒杀动态路径")
    @ApiResponse(code = 200, message = "ok", response = ResultResponse.class)
    @AccessLimit(seconds = 5, maxAccessCount = 10)
    @ResponseBody
    @GetMapping(value = "/path")
    public ResultResponse getSeckillPaht(HttpServletRequest request, User user, @RequestParam("goodsId")long goodsId, @RequestParam(value="verifyCode", defaultValue="0") Integer verifyCode) {
        LogUtil.callStart(log, "获取秒杀动态路径");
        ResultResponse resultResponse = flashSaleService.getSeckillPaht(request, user, goodsId, verifyCode);
        LogUtil.outputResult(log, resultResponse);
        return resultResponse;
    }

    @ApiOperation(value = "秒杀")
    @ApiResponse(code = 200, message = "ok", response = ResultResponse.class)
    @ResponseBody
    @PostMapping(value = "/{path}/seckill")
    public ResultResponse seckill(Model model, User user, @RequestParam("goodsId") Long goodsId, @PathVariable("path") String path) {
        LogUtil.callStart(log, "秒杀");
        ResultResponse resultResponse = flashSaleService.seckill(model, user, goodsId, path);
        LogUtil.outputResult(log, resultResponse);
        return resultResponse;
    }

    @ApiOperation(value = "秒杀结果")
    @ApiResponse(code = 200, message = "ok", response = ResultResponse.class)
    @ResponseBody
    @GetMapping(value = "/result")
    public ResultResponse seckillResult(Model model, User user, @RequestParam("goodsId") long goodsId) {
        LogUtil.callStart(log, "秒杀结果");
        ResultResponse resultResponse = flashSaleService.seckillResult(model, user, goodsId);
        LogUtil.outputResult(log, resultResponse);
        return resultResponse;
    }

}