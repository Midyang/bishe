package com.shop.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.constant.AdminConstant;
import com.shop.utils.CodeValid;

@Controller
@RequestMapping("/validateCode")
public class ValidateCodeController {

	private int x;
	/*
	 * 验证码
	 * @Param Admin_codeValid 验证码
	 * */
	@RequestMapping("/codeValid")
	@ResponseBody
	public void codeValid(HttpServletRequest request, HttpServletResponse response) {

		//创建一副内存图片,BufferedImage 设置宽度和高度
		BufferedImage image=new BufferedImage(AdminConstant.WIDTH, AdminConstant.HEIGHT, BufferedImage.TYPE_INT_RGB);
		//得到属于该图片的画笔:
		Graphics graphics=image.getGraphics();
			//画 边框
			graphics.setColor(Color.GRAY);
			graphics.drawRect(0, 0, AdminConstant.WIDTH, AdminConstant.HEIGHT);
			//填充背景色
			graphics.setColor(Color.white);
			graphics.fillRect(1, 1, AdminConstant.WIDTH-2, AdminConstant.HEIGHT-2);
			//干扰线
			int lineNumber=AdminConstant.RANDOM.nextInt(8);//随机画干扰线的数量
			for(int i=0;i<lineNumber;i++){
				graphics.setColor(AdminConstant.FONT_COLOR[AdminConstant.RANDOM.nextInt(AdminConstant.FONT_COLOR.length)]);//随机颜色
				graphics.drawLine(AdminConstant.RANDOM.nextInt(AdminConstant.WIDTH), AdminConstant.RANDOM.nextInt(AdminConstant.HEIGHT), AdminConstant.RANDOM.nextInt(AdminConstant.WIDTH), AdminConstant.RANDOM.nextInt(AdminConstant.HEIGHT));
			}
			String codeValid=CodeValid.getCode(4);
			System.err.println("当前验证码 	: "+codeValid);
			request.getSession().setAttribute("admin_codeValid", codeValid);
			//验证码
			x=AdminConstant.X_LOCATION;
			//画验证码
			for(int i=0;i<codeValid.length();i++){
				//随机设置颜色
				graphics.setColor(AdminConstant.FONT_COLOR[AdminConstant.RANDOM.nextInt(AdminConstant.FONT_COLOR.length)]);
				//随机字体, 随机字号 , 设置字体大小和字体(String 字体，int 风格，int 字号)
				graphics.setFont(new Font(AdminConstant.FONT_TYPE[AdminConstant.RANDOM.nextInt(AdminConstant.FONT_TYPE.length)], Font.BOLD|Font.ITALIC|Font.PLAIN, AdminConstant.FONT_SIZE[AdminConstant.RANDOM.nextInt(AdminConstant.FONT_SIZE.length)]));
				//给一个字符   字符的X Y 坐标
				graphics.drawString(codeValid.charAt(i)+"", x, AdminConstant.Y_LOCATION);
				x+=22;
			}
			//用 字节流进行输出 ImageIO
			//设置三个响应消息头,通知浏览器不要缓存
				response.setHeader("Expires", "-1");
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Pragma", "no-cache");
			ServletOutputStream outputStream = null;
			try {
				outputStream = response.getOutputStream();
			} catch (IOException e) {
				System.out.println("admin/caodeValid:outputStream = response.getOutputStream();报错");
				e.printStackTrace();
			}
			try {
				ImageIO.write(image, "jpg", outputStream);
			} catch (IOException e) {
				System.err.println("admin/caodeValid:ImageIO.write(image, \"jpg\", outputStream);报错!");
				e.printStackTrace();
			}
	}
}
