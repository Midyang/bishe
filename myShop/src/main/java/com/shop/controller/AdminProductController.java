package com.shop.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.pojo.Product;
import com.shop.pojo.Secondcategory;
import com.shop.service.AdminProductService;
import com.shop.service.AdminSecondcategoryService;
import com.shop.utils.JsonUtil;
import com.shop.utils.Message;
import com.shop.utils.Page;
import com.shop.utils.ValidateValue;

@Controller
@RequestMapping("/product")
public class AdminProductController {
	@Autowired
	private AdminProductService service;
	@Autowired
	private AdminSecondcategoryService secondSerice;
	
	@RequestMapping("/list")
	public String list(String mohu,String curPage,Model model){

		// 参数验证
		Integer currPage=ValidateValue.isInteger(curPage)?Integer.parseInt(curPage.trim()):1;
		mohu=ValidateValue.isEmpty(mohu)?"":mohu.trim();
		// 页面显示信息数量
		int sizePage=4; 
		PageHelper.startPage(currPage,sizePage);
		List<Product>list=service.list(mohu);
		PageInfo<Product> info = new PageInfo<Product>(list);
		Page page = new Page(currPage, (int)info.getTotal(), sizePage);
		
		model.addAttribute("page",page);
		model.addAttribute("list",list);
		model.addAttribute("mohu",mohu);
		model.addAttribute("fromLocation", "0");//1:来源于详细查询 0:来源于普通查询
		return "back_page/product/product_List";
	}
	//二级分类信息
	@RequestMapping("/addPreList")
	public String addPreList(Model model){
		Message<Secondcategory> message = new Message<Secondcategory>();//指定List中的数据类型
		List<Secondcategory> list = secondSerice.addPreList();
		message.setList(list);
		model.addAttribute("message",message);
		return "back_page/product/product_Add";
	}
	//根据ID删除商品信息
	@RequestMapping("/deleteById")
	public String deleteById(Integer id,String curPage,String mohuName){
		try {
			if(id != null) {
				if( service.deleteById(id)>0 ) {
					return "redirect:/product/list?mohuName="+mohuName+"&curPage="+curPage;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("假装是日志 { product-操作动作 : 删除 ; 位置 : product/del; 类型 : 非正常操作 ; 处理 : 强制下线!");
		return "redirect:/login/exitLogin";
	}
	//根据ID 查询商品信息
	@RequestMapping("/selectById")
	public String update(Integer id,Model model){
		try{
			if(id != null ) {
				Product product=service.selectById(id);
				if( product != null ) {
					Message<Secondcategory> message = new Message<Secondcategory>();//指定List中的数据类型
					List<Secondcategory> list = secondSerice.addPreList();
					message.setList(list);
					message.setEntity(product);
					model.addAttribute("message",message);
					return "back_page/product/product_Update";
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("假装是日志 { product-操作动作 : 修改信息前查询; 位置 : product/selectById; 类型 : 非正常操作 ; 处理 : 强制下线!");
		return "redirect:/login/exitLogin";
	}
	@RequestMapping("/add")
	public String addProduct(Product product, MultipartFile images,HttpServletRequest request,Model model) {
		int flag=0;
		if(product != null) {
			//商品名称
			if( ValidateValue.isEmpty(product.getName()) ) {
				MsgSet(product, "nameMsg", "*商品名称格式不正确!", request ,flag);
				flag+=1;
			}
			//市场价格
			if(product.getMarketPrice() == null) {
				MsgSet(product, "marketPriceMsg", "*市场价格格式不正确!", request ,flag);
				flag+=1;
			}
			//商城价格
			if(product.getShopPrice() == null) {
				MsgSet(product, "shopPriceMsg", "*商城价格格式不正常!", request ,flag);
				flag+=1;
			}
			//商品描述(保留)
			if( ValidateValue.isEmpty( product.getDescription()) ) {
				MsgSet(product, "descriptionMsg", "*商品描述信息不能为空!", request ,flag);
				flag+=1;
			}
			//图片上传
			String value = upload(product, images, request);
			if( value.equals("false")) {
				MsgSet(product, "imageMsg", "*图片上传失败!", request ,flag);
				flag+=1;
			}else if(value.equals("type")){
				MsgSet(product, "imageMsg", "*文件格式不正确,请选择正确的文件(PNG/JPG)!", request ,flag);
				flag+=1;
			}
			//判断是否继续
			if(flag>0) {
				return "back_page/product/product_Add";
			}
			try {
				service.add(product);
				return "redirect:/product/list";
			}catch (Exception e) {
				System.err.println("假装是日志 { product-操作动作 : 保存新添加的商品信息; 位置 : add()方法; 类型 : 数据库保存异常; 处理 : 提示!; ");
				e.printStackTrace();
			}
		}
		// 如果 product == null,保存异常!
		MsgSet(product, "nameMsg", "*填写的数据异常!请重新填写!", request ,flag);
		return "back_page/product/product_Add";
	}
	/**
	 * 
	 * 	响应公共方法
	 * 
	 * */
	@SuppressWarnings("unchecked")
	private void MsgSet(Product product,String msgName,String msg, HttpServletRequest request ,int flag) {
		if(flag == 0) {
			List<Secondcategory> list = secondSerice.addPreList();//二级分类信息
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put(msgName, msg);//提示消息
			
			Message<Secondcategory> message = new Message<Secondcategory>();//指定List中的数据类型
			
			message.setMap(map);//响应的信息提示
			message.setList(list);//二级分类信息
			message.setEntity(product);//响应的实体信息
			
			request.setAttribute("message",message);
		}else {
			@SuppressWarnings("rawtypes")
			Message message = (Message)request.getAttribute("message");
			@SuppressWarnings("rawtypes")
			Map map = message.getMap();
			map.put(msgName, msg);//提示消息
		}
		
	}
	/**
	*	文件上传 
	*
	*/
	private String upload(Product product, MultipartFile images, HttpServletRequest request) {
		
		String realPath = request.getSession().getServletContext().getRealPath("\\productImages\\");
		File file = new File(realPath);
		if(!file.exists()) {
			file.mkdirs();
		}
		if(images != null) {
			String filename = images.getOriginalFilename();
			if( !ValidateValue.isEmpty(filename) ) {
				//上传文件,过滤
				if( filename.endsWith(".jpg") || filename.endsWith(".png") ) {//满足其一,允许上传,否则不允许上传
					try {
						images.transferTo( new File(realPath,filename) );
						product.setImage( "productImages/"+filename.trim() );
						return "true";//上传成功
					} catch (Exception e) {
						System.err.println("假装是日志 { product-操作动作 : 图片上传; 位置 : upload()方法; 类型 : 图片上传 ; 处理 : 提示!; 类容:文件为_"+filename);
						e.printStackTrace();
					}
				}else {
					return "type";//文件类型不对,不允许上传
				}
			}else {
				return "noUpLoad";//没有上传图片!
			}
		}
		return "false";//上传失败!
	}
	/**
	 * 
	 * 	修改商品信息
	 * */
	@RequestMapping("/update")
	public String update(Product product, MultipartFile images,String image, HttpServletRequest request) {
		int flag=0;
		if(product != null) {
			//商品名称
			if( ValidateValue.isEmpty(product.getName()) ) {
				MsgSet(product, "nameMsg", "*商品名称格式不正确!", request ,flag);
				flag+=1;
			}
			//市场价格
			if(product.getMarketPrice() == null) {
				MsgSet(product, "marketPriceMsg", "*市场价格格式不正确!", request ,flag);
				flag+=1;
			}
			//商城价格
			if(product.getShopPrice() == null) {
				MsgSet(product, "shopPriceMsg", "*商城价格格式不正常!", request ,flag);
				flag+=1;
			}
			//商品描述(保留)
			if( ValidateValue.isEmpty( product.getDescription()) ) {
				MsgSet(product, "descriptionMsg", "*商品描述信息不能为空!", request ,flag);
				flag+=1;
			}
			//图片上传
			String value = upload(product, images, request);
			if( value.equals("false")) {
				MsgSet(product, "imageMsg", "*图片上传失败!", request ,flag);
				flag+=1;
			}else if(value.equals("type")){
				MsgSet(product, "imageMsg", "*文件格式不正确,请选择正确的文件(PNG/JPG)!", request ,flag);
				flag+=1;
			}else if(value.equals("noUpLoad")) {//没有上传图片
				product.setImage(image);
			}
			//判断是否继续
			if(flag>0) {
				return "back_page/product/product_Update";
			}
			try {
				service.update(product);
				return "redirect:/product/list";
			}catch (Exception e) {
				System.err.println("假装是日志 { product-操作动作 : 保存新添加的商品信息; 位置 : updat()方法; 类型 : 数据库保存更新异常; 处理 : 提示!; ");
				e.printStackTrace();
			}
		}
		// 如果 product == null,保存异常!
		MsgSet(product, "nameMsg", "*填写的数据异常!请重新填写!", request ,flag);
		return "back_page/product/product_Update";
	}
	/*
	 * 用于详细查询之前的操作
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping("/searchBefore")
	public String searcBefore(HttpServletResponse response,HttpServletRequest request,Model model){
		Map<String,Object> parameter = null;
		boolean flag=false;// false 未找到，true 找到了
		try {
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {
				if("searchProductParameter".equals(cookie.getName())){
					flag = true;
					String value = cookie.getValue();
					parameter=JsonUtil.byteStrToObj(value, HashMap.class);//得到参数
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("   LOG:跳转到商品详细查询页，获取查询参数失败！");
		}
		System.out.println("    LOG:当前获取到的参数："+JSON.toJSONString(parameter));
		if(!flag || parameter == null){//找了该cookie，并且拿到了参数 除外 的情况
			parameter=new HashMap<String, Object>();
		}
		model.addAttribute("parameter", parameter);
		model.addAttribute("twoClass", secondSerice.addPreList());
		return "back_page/product/searchProductPage";
	}
	
	/*
	 * 用于详情查询
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping("/searchProduct")
	public String searchProduct(String fromLocation,Product product,Double minPrice,Double maxPrice,String priceSort,String currPage,HttpServletRequest request,HttpServletResponse respose,Model model){
		Map<String, Object> parameter = null; //用于查询的参数
		List<Product> list = null ;
		Page page = null;
		boolean returnFlag = false;			//默认失败
		try {
			if(fromLocation != null && "1".equals(fromLocation)){
				parameter = new HashMap<String,Object>();
				if(product != null){
					if(product.getName() != null && product.getName() != ""){
						parameter.put("name", product.getName());
					}
					if(product.getDescription() != null && product.getDescription() != ""){
						parameter.put("description", product.getDescription());
					}
					if(product.getIsHot() != null){
						parameter.put("isHot", product.getIsHot());
					}else{
						parameter.put("isHot", 2);//给一个默认值
					}
					if(product.getScid() != null){
						parameter.put("scid", product.getScid());
					}else{
						parameter.put("scid", 0);//给一个默认值
					}
				}
				//价格区间
				if(minPrice != null && minPrice>=0){
					parameter.put("minPrice", minPrice);
				}
				if(maxPrice != null && maxPrice>0){
					if(minPrice == null){
						parameter.put("maxPrice", maxPrice);
					}else if(maxPrice>minPrice){
						parameter.put("maxPrice", maxPrice);
					}
				}
				//价格排序
				if(priceSort != null && (priceSort.equals("0") || priceSort.equals("1") || priceSort.equals("2"))){
					parameter.put("priceSort", priceSort);// 0:降序，1：升序，2：不限制
				}
				//分页
				Integer curpage=1;
				if(ValidateValue.isInteger(currPage)){
					curpage = Integer.valueOf(currPage);
				}
				page = new Page(curpage,service.searchProductCount(parameter),4);//当前页，总大小，每页大小
				
				parameter.put("currPage", page.getStartIndex());
				parameter.put("pageSize", page.getSizePage());
				//开始查询
				list = service.searchProduct(parameter);//查询结果
				String objToByteStr = JsonUtil.objToByteStr(parameter);
				Cookie cookie = new Cookie("searchProductParameter", objToByteStr);
				respose.addCookie(cookie);//添加或更新
				returnFlag=true;
			}else if(fromLocation != null && "0".equals(fromLocation)){
				boolean flag=false;// false 未找到，true 找到了
				Cookie[] cookies = request.getCookies();
				for (Cookie cookie : cookies) {
					if("searchProductParameter".equals(cookie.getName())){
						flag = true;
						String value = cookie.getValue();
						parameter=JsonUtil.byteStrToObj(value, HashMap.class);//得到参数
					}
				}
				if(!flag || parameter == null){//找了该cookie，并且拿到了参数 除外 的情况
					parameter=new HashMap<String, Object>();
				}
				Integer curPage = 1;
				if(ValidateValue.isInteger(currPage)){
					curPage=Integer.valueOf(currPage);
				}
				page = new Page(curPage, service.searchProductCount(parameter), 4);
				parameter.put("currPage", page.getStartIndex());
				parameter.put("pageSize", page.getSizePage());
				list = service.searchProduct(parameter);
				returnFlag = true ;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("   LOG:商品查询出现异常");
		}
		System.out.println("   LOG: 当前查询参数："+JSON.toJSONString(parameter));
		if(returnFlag){
			model.addAttribute("list", list);								 //查询到的数据
			model.addAttribute("page", page);									 //分页信息
			model.addAttribute("fromLocation", "1");									 //1:来源于详细查询 0:来源于普通查询
			return "back_page/product/product_List";
		}
		return "redirect:/product/list";
	}
}
