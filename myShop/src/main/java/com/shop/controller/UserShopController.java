package com.shop.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.pojo.Category;
import com.shop.pojo.Orderitem;
import com.shop.pojo.Orders;
import com.shop.pojo.Product;
import com.shop.pojo.Secondcategory;
import com.shop.pojo.User;
import com.shop.service.UserShopService;
import com.shop.utils.JsonUtil;
import com.shop.utils.Message;
import com.shop.utils.Page;
import com.shop.utils.ResState;
import com.shop.utils.ValidateValue;

/**
 * midy 2-25
 * 首页商品展示，商品查找，订单等逻辑部分
 **/

@Controller
@RequestMapping("/userShop")
public class UserShopController {
	
	@Autowired
	private UserShopService userShopService;
	/**
	 *	 网站首页展示数据的渲染
	 **/
	@RequestMapping("/index")
	public String index(Model model,HttpServletRequest request){
		
		List<Category> oneList = userShopService.getOneClass();											//获取所有的一级分类List
		if( oneList==null || oneList.size()<1 ) 														{//如果一级分类不存在（数据库中的数据为空的情况）
			return "frontEnd/index";
		}
		ArrayList< HashMap<String,Object> > arrayList = new ArrayList< HashMap<String,Object> >();		//整体数据块，存放所有一级分类洗信息，二级分类信息，以及对应的商品信息的List集合
		oneFor:for( Category one:oneList ) {															//遍历所有的一级分类，并且在找寻下属的二级分类和商品信息
			List<Secondcategory> twoClass = userShopService.getTwoClass(one);							//根据一级分类获取对应的所有的二级分类信息List；
			if( twoClass != null && twoClass.size()>0 ) {												//判断属于一级分类的二级分类是否存在，如果不存在则过滤掉这个二级分类List所对应的一级分类(不满足时，该一级分类下没有二级分类，那么就不执行这部代码，直接开始进入下一个循环)
				/*
				 * 这部分代码用来筛选，该一级分类下的所有二级分类下的商品数量是否满足给定的商品数量要求
				 **/
				int numberFlag = 0;																		//统计该一级分类下的所有二级分类下的商品数量，初始值为零
				for(Secondcategory twoTest:twoClass){
					numberFlag += userShopService.getProductNum(twoTest);
				}
				if(numberFlag<6) {
					continue oneFor;
				}																						//将局部数据块 放入整体数据块
				HashMap<String,Object> dataBlock = new HashMap<>();										//局部数据块，在二级分类存在的情况下，创建一个 放一级分类下属所有数据的 map数据块
				arrayList.add(dataBlock);																//整体数据块 放入 局部数据块
				/*
				 * 初始化局部数据块
				 **/
				dataBlock.put("oneClass", one);															//该一级分类对象	入 局部数据块
				dataBlock.put("twoClass", twoClass);													//该一级分类 下  所有 二级分类信息（list)	入 局部数据块
				ArrayList<Product> productList = new ArrayList<Product>();								//创建一个商品List	用来存放商品信息
				dataBlock.put("product", productList);													//该一级分类下商品信息List	入 局部数据块
				
				twoFor:for(Secondcategory two:twoClass) {												//遍历二级分类List，目的：数据库找到该分类下所对应商品，并入商品List
					List<Product> product=userShopService.getProductInfo(two);							//去数据库找寻二级分类对应的商品的list
					/*
					 * 初始化该一级分类下 局部数据块中的 商品List
					 **/
					if( product !=null && product.size()>0) {											//如果该二级分类下对应的产品为空 则忽略掉这个二级分类
						for(Product p:product) {
							if(productList.size()<10) {
								productList.add(p);
							}else {
								break twoFor;															//当集合中存满了10个商品信息时，就不再添加商品到productList，结束该二级分类循环
							}
						}
					}	
				}
				/**
				 * 	这个地方可以再次筛选，去掉不想要的块,保留部分信息进行信息展示（暂时保留这块）
				 **/
				while(twoClass.size()>6) {																//这个部分控制二级分类的显示长度,最多不超过6个
						twoClass.remove(twoClass.size()-1);
				}
			}
		}
		/*	测试数据使用  */
		/*
		for( HashMap<String,Object> map:arrayList ) {//第一层拿到一个一级分类的map数据块
			Category oneClass=(Category)map.get("oneClass");//从数据块中拿到该一级分类的对象
			System.out.println("一级分类的ID："+oneClass.getId()+"   ；一级分类名称："+oneClass.getName());
			@SuppressWarnings("unchecked")
			List<Secondcategory> twoClass = (List<Secondcategory>)map.get("twoClass");//从数据块中拿到该一级分类的二级分类List
			for(Secondcategory se:twoClass){
				System.out.println("	二级分类名称为："+se.getName()+"   ;所属一级分类ID:"+se.getCid()+"	;该二级分类ID为："+se.getId());
			}
			@SuppressWarnings("unchecked")
			ArrayList<Product> productList=(ArrayList<Product>)map.get("product");
			System.out.println("	商品集合的长度："+productList.size());
			for(Product p:productList) {
				System.out.println("		商品名称："+p.getName()+" ；商品对应的所属二级分类ID："+p.getScid());
			}
		}
		*/
		model.addAttribute("data",arrayList);
		getUserLoginInfo(request);
		return "frontEnd/index";
	}
	/**
	 *	根据ID查询商品信息
	 *	shopId	商品的ID
	 **/
	@RequestMapping("/shopInfoById")
	public String shopInfoById(Model model, String shopId,HttpServletRequest request) {
		if( ValidateValue.isInteger(shopId) ) {
			Product product = userShopService.productInfoById(shopId);
			if(	product != null) {
				model.addAttribute("productInfo", product);
				getClassInfo(model);
				getUserLoginInfo(request);
				return "frontEnd/productInfo";
			}
		}
		System.err.println("假装是日志 { userShop-操作动作 : 根据ID查找商品，然后显示到页面 ; 位置 : /userShop/shopInfoById; 类型 : 非正常操作 ; 处理 : 强制下线!");
		return "redirect:/login/exitLogin";
	}
	/**
	 * 	搜索信息的
	 * keyword		关键字（name字段）
	 * currentPage	当前页
	 * minPrice		价格下限
	 * maxPrice		价格上限
	 * isHot		是否热门
	 * description	商品描述
	 * priceSort	价格排序（升序，降序）
	 **/
	@RequestMapping("/searchShop")
	public String searchShop(String keyword,String currentPage,String minPrice,String maxPrice,String isHot,String description,String priceSort,String scid,Model model,HttpServletRequest request){
		getClassInfo(model);
		getUserLoginInfo(request);
		
		Map<String, Object> parameter= new HashMap<String, Object>();
		if( !ValidateValue.isEmpty(keyword) ) {
			parameter.put("name",keyword);
		}																	//最低价
		if( ValidateValue.isNumber(minPrice) ) {
			parameter.put("minPrice",Double.parseDouble(minPrice));
		}
																				//最高价
		if( ValidateValue.isNumber(maxPrice) ) {
			parameter.put("maxPrice",Double.parseDouble(maxPrice));
		}																	//是否热门
		if( ValidateValue.isInteger(isHot) ) {
			parameter.put("isHot",Integer.parseInt(isHot));
		}else {
			parameter.put("isHot",2);
		}																	//描述
		if( !ValidateValue.isEmpty(description) ) {
			parameter.put("description",description);
		}															//价格排序
		if(ValidateValue.isInteger(priceSort)) {
			parameter.put("priceSort",priceSort);
		}																	//二级分类商品
		if(ValidateValue.isInteger(scid)) {
			parameter.put("scid",scid);
		}else {																	//如果为空，或空串，或不是数值，给定默认值0
			parameter.put("scid",0);
		}
		//分页设置
		int curPage = ValidateValue.isInteger(currentPage)?Integer.parseInt(currentPage):1;
		Page page = new Page(curPage,userShopService.getProductCount(parameter), 8);
		parameter.put("currPage", page.getStartIndex());
		parameter.put("pageSize", page.getSizePage());
		//用于渲染页面的数据 
		List<Product> productList = userShopService.getProductList(parameter);
		model.addAttribute("productList", productList);
		model.addAttribute("page", page);
		model.addAttribute("parameter", parameter);
		return "frontEnd/searchShop";
	}
	/**
	 *	用于添加商品到购物车  存放在Cookie 中
	 * 	productId 	商品ID
	 * 	count		商品数量
	 *  statu		操作类型（0:减少数量；1:添加/包含重复添加(暂时保留);2：删除；3：批量删除；0,1,2 都为单项操作；3为多项操作）
	 *  
	 *  会先从cookie中找到购物车的数据，然后在操作后同步到数据库，如果COOKie中没有，则从数据库中读取出来，操作后保存到数据库，并刷新到cookie中
						 * 	添加 商品到购物车，有三种情况：
						 * 	情况1：购物车为空，需要初始化一个Cookie，然后再在其中添加商品信息
						 * 	情况2：购物车存在，但是购物车中已添加的商品列中，没有当前要添加的商品的项
						 * 	情况3：购物车存在，但是购物车中商品列中已经存在当前商品的项
						 * 		附加：
						 * 			在情况1下和情况2：下添加初始化商品信息，避免每次渲染数据都要查询数据库和以后完善信息查询数据库，增加系统开销
	 **/
	@RequestMapping("/addToShopCar")
	public String addToShopCar( String productId, String count, String statu, HttpServletRequest request,HttpServletResponse response, Model model ) {
		System.out.println("  LOG:开始处理添加商品到购物车");
		System.out.println("  LOG:当前传入参数：productId : "+productId+" ; statu : "+statu+"  ; count : "+count );
		User user = null;														//要来标记在用户登录的情况下，数据库中有没有购物车信息,查询了数据库，但是结果为空则为 true
		Cookie shoopCarCookie = null;
		List<Orderitem> carList = null;
		//这段代码,用获取购物车数据
		Object attribute = null;							//如果已经登录了,则从数据库中检索该用户的购物车数据
		if( (attribute=request.getSession().getAttribute("user") ) != null && (attribute instanceof User)){
			user = (User)attribute;
		}
		if( user==null || 9 != user.getState() ){//用户未登录 或 登录了但 数据库中没有数据  或  登录了数据库有数据（9为已经同步了数据的标志）（第一次的时候同步一下两端的购物车）    就执行该部分代码，试着从Cookie中查找购物车数据
			Cookie[] cookies = request.getCookies();
			if( cookies != null ) {
				cookiesFor:for( int i=cookies.length-1; i >= 0; i--) {							//循环从所有的Cookie中找到名为“shopCar”的cookie
					if( cookies[i] != null && "shopCar".equals( cookies[i].getName() ) ) {		//找到名为 ‘shopCar’的Cookie，并进行逻辑处理
						try{
							carList=JsonUtil.byteStrToObj2( cookies[i].getValue(), Orderitem.class );
							if( carList != null ){
								shoopCarCookie = cookies[i];									//cookie中的‘数据可用’的情况下，记录下该Cookie
								if( user != null ){												//用户登录了，但是从数据库未找到购物车数据，但是Cookie中存在，则把Cookie中的数据同步到数据库
									SynchShopCar(carList,user);
									cookies[i].setValue(null);
									cookies[i].setMaxAge(0);
									response.addCookie(cookies[i]);
									user.setState(9);
									request.getSession().setAttribute("user", user);
								}
							}
						}catch(Exception e){//暂时保留
							carList = null;
							shoopCarCookie = null;
						}
						break cookiesFor;
					}
				}
			}
		}
		if( user != null ){
			carList=synchShopCar(null,user,"sel");
		}
		if( !ValidateValue.isEmpty(statu) ) {									//提交状态验证
			if( "1".equals(statu) || "0".equals(statu) ) {						//cookie 数据更新部分,包含添加商品到购物和重复添加（修改已有的数量） 
				System.err.println("  LOG:选择了状态：1，添加/修改商品项");
				int pId = ValidateValue.isInteger( productId )?Integer.parseInt( productId ):0;
				int pNumber = ValidateValue.isInteger( count )?Integer.parseInt( count ):0;
				if( pNumber != 0 && pId != 0 ) {
					if(carList==null){											//未找到任何购物车数据，初始化一个购物车对象
						System.err.println("  LOG { 情况1：Cookie未找到购物车信息，数据库中也未找到购物车信息 }");
						//初始化一个订单项对象
						Orderitem ordItm = new Orderitem();					
						ordItm.setProductId( pId );	
						ordItm.setCount( pNumber );
						//ordItm.setProduct( userShopService.productInfoById( productId.trim() ) );
						//ordItm.setSubtotal( ordItm.getCount()*ordItm.getProduct().getShopPrice() );
						//初始化一个购物车信息对象
						carList = new ArrayList<Orderitem>();				
						carList.add(ordItm);
						if( user!=null ){
							//同步到数据库，添加新的订单项
							synchShopCar(ordItm,user,"add");
						}else{
							//初始化购物车Cookie对象
							shoopCarCookie = new Cookie( "shopCar", JsonUtil.objToByteStr(carList) );
							shoopCarCookie.setMaxAge(60*60*24*3);
							response.addCookie(shoopCarCookie);
						}
					}else{
						boolean isExistItemFlag = false;						// 标记是否找到订单项
						carListFor:for( Orderitem item:carList ) {				// 情况2（重复添加商品到购物车）
							if( item.getProductId() == pId ) {
								System.err.println("  LOG { 情况2：重复添加商品 }");
								int countNew=item.getCount()+pNumber;
								if( countNew < 0 ) {
									//直接忽略本次添加！
									System.out.println("  LOG { 数据异常警告！传入参数存在问题 ！ count："+count+"  大于现有的商品数量！");	
								}else if(countNew >0){
									item.setCount( countNew );
									synchShopCar(item,user,"upd");
								}else if( countNew == 0 ) {
									carList.remove(item);
									synchShopCar(item,user,"del");
								}
								if(user == null){
									if( shoopCarCookie!=null){				//更新到Cookie中
										if(carList.size()>0){
											shoopCarCookie.setValue( JsonUtil.objToByteStr(carList) );
										}else{
											shoopCarCookie.setValue(null);
											shoopCarCookie.setMaxAge(0);
										}
										response.addCookie(shoopCarCookie);
									}else if( shoopCarCookie==null && carList.size()>0){
										shoopCarCookie = new Cookie( "shopCar", JsonUtil.objToByteStr(carList) );
										shoopCarCookie.setMaxAge(60*60*24*3);
										response.addCookie(shoopCarCookie);
									}
								}
								isExistItemFlag = true;										//通知找到了
								break carListFor;
							}
						}
						if(!isExistItemFlag){
							System.err.println("  LOG { 情况3：添加新商品到购物车 }");
							//初始化一个订单项对象
							Orderitem ordItm = new Orderitem();						//创建一个订单项对象
							ordItm.setProductId( pId );								//Math.abs(a)，在添加的时候不会存在为负数的情况
							ordItm.setCount( pNumber );
							//ordItm.setProduct(userShopService.productInfoById(productId.trim()));
							//ordItm.setSubtotal(ordItm.getCount()*ordItm.getProduct().getShopPrice());
							//更新到内存
							carList.add(ordItm);
							//更新到数据库
							synchShopCar(ordItm,user,"add");
							//更新到Cookie
							if(user ==null ){
								if(shoopCarCookie!=null){
									shoopCarCookie.setValue( JsonUtil.objToByteStr(carList) );
									response.addCookie(shoopCarCookie);
								}else if(shoopCarCookie==null){
									shoopCarCookie = new Cookie( "shopCar", JsonUtil.objToByteStr(carList) );
									shoopCarCookie.setMaxAge(60*60*24*3);
									response.addCookie(shoopCarCookie);
								}
							}
						}
					}
				}
			}else if( "2".equals(statu) ){												//2：删除单个商品项
				System.err.println("  LOG { 选择了状态：2，删除单个商品项 }");
				int pId = ValidateValue.isInteger( productId )?Integer.parseInt( productId ):0;
				if( carList != null && pId != 0 ) {
					carList:for( Orderitem item:carList ) {								//循环找到当前商品
						if( item.getProductId() == pId ) {
							carList.remove( item );										
							synchShopCar(item,user,"del");								//更新数据库
							System.out.println("  开始执行删除："+JsonUtil.objToByteStr(item));
							if(user==null){
								if(shoopCarCookie!=null){									//更新到Cookie中
									if(carList.size()>0){
										shoopCarCookie.setValue( JsonUtil.objToByteStr(carList) );
									}else{
										shoopCarCookie.setValue(null);
										shoopCarCookie.setMaxAge(0);
									}
									response.addCookie(shoopCarCookie);
								}else if(shoopCarCookie==null && carList.size()>0){
									shoopCarCookie = new Cookie( "shopCar", JsonUtil.objToByteStr(carList) );
									shoopCarCookie.setMaxAge(60*60*24*3);
									response.addCookie(shoopCarCookie);
								}
							}
							break carList;
						}
					}
				}
			}else if( "3".equals(statu) ) {												//3：批量删除
				System.err.println("    LOG { 选择了状态：3，批量删除商品项 }");
				if( !ValidateValue.isEmpty(productId) && carList != null) {
					try{
						String[] idArray=productId.trim().split(",");
						for(String id:idArray) {
							if( ValidateValue.isInteger(id) ) {
								int pid = Integer.parseInt(id);
								Orderitem:for( Orderitem item:carList ) {						
									if( item.getProductId() == pid ) {
										carList.remove( item );
										synchShopCar(item,user,"del");
										break Orderitem;
									}
								}
							}else {
								System.err.println("	LOG { 批量删除商品项,遇到错误参数 }	productId:"+id );
							}
						}
						if( user==null ){
							if(shoopCarCookie!=null){
								if(carList.size()>0){
									shoopCarCookie.setValue( JsonUtil.objToByteStr(carList) );
								}else{
									shoopCarCookie.setValue(null);
									shoopCarCookie.setMaxAge(0);
								}
								response.addCookie(shoopCarCookie);
							}else if(shoopCarCookie==null && carList.size()>0){
								shoopCarCookie = new Cookie( "shopCar", JsonUtil.objToByteStr(carList) );
								shoopCarCookie.setMaxAge(60*60*24*3);
								response.addCookie(shoopCarCookie);
							}
						}
					}catch(Exception e){
						System.out.println("    LOG { 选择批量删除商品项 失败}");
					}
				}
			}
		}
		//前端页面 初始化需要渲染数据 部分
		System.err.println("  LOG { 开始初始化 carList 购物车对象...} " );	
		if( carList == null || carList.size()<1 ) {							//如果购物车长度小于1 ，那么认为购物为空
			System.err.println("  LOG:carList	购物车对象为空 ( 当前购物车数据为空 )长度小于1	; 当前购物车数据为空" );
		}else {
			fullOrderData(model, carList);
		}
		getClassInfo(model);
		getUserLoginInfo(request);
		return "frontEnd/shopCar";
	}
	/**
	 * 填充表单数,总计
	 **/
	private Orders fullOrderData(Model model, List<Orderitem> carList) {
		double total = 0.0;
		for (Orderitem ordItm: carList) {
			ordItm.setProduct(userShopService.productInfoById(ordItm.getProductId()));
			ordItm.setSubtotal(ordItm.getCount()*ordItm.getProduct().getShopPrice());
			total +=ordItm.getSubtotal();
		}
		Orders order = new Orders();
		order.setTotal(total);
		model.addAttribute("order", order);
		model.addAttribute("orderItem", carList);
		for( Orderitem item : carList ) {
			System.err.println("  LOG { 商品名称："+item.getProduct().getName()+" 	数量"+item.getCount()+" 	单价："+item.getProduct().getShopPrice()+" 	小计："+item.getSubtotal()+" }" );
		}
		System.err.println("  LOG:总计："+total);
		return order;
	}
	/**
	  * 用于异步的AJAX 购物车的操作接口 
	  * statu:1  >修改商品数量，增加
	  * statu:2  >修改商品数量，减少
	  * 暂时保留Statu字段功能（目前没有什么大用）
	  *	响应状态：
	  *	0——失败！ 
	  * 1——成功！
	 **/
	@RequestMapping("/changeToShopCar")
	@ResponseBody
	public Message<Object> changeToShopCar( String productId, String count, String statu, HttpServletRequest request,HttpServletResponse response ) {
		System.out.println("  LOG: { 开始处理修改购物车中商品数量 }");
		System.out.println("  LOG: AJAX 购物车的操作接口{  当前传入参数：productId : "+productId+" ; statu : "+statu+"  ; count : "+count+"  }" );
		Message<Object> responseMsg = new Message<Object>();
		List<Orderitem> carList = null;
		boolean flagIsExistItem = false;
		User user = null;
		Cookie shoopCarCookie = null;
		//这段代码,用获取购物车数据
		Object attribute = null;							//如果已经登录了,则从数据库中检索该用户的购物车数据
		if( (attribute=request.getSession().getAttribute("user") ) != null && (attribute instanceof User)){
			user = (User)attribute;
		}
		if( user==null || 9 != user.getState() ){//用户未登录 或 登录了但 数据库中没有数据  或  登录了数据库有数据（9为已经同步了数据的标志）（第一次的时候同步一下两端的购物车）    就执行该部分代码，试着从Cookie中查找购物车数据
			Cookie[] cookies = request.getCookies();
			if( cookies != null ) {
				cookiesFor:for( int i=cookies.length-1; i >= 0; i--) {							//循环从所有的Cookie中找到名为“shopCar”的cookie
					if( cookies[i] != null && "shopCar".equals( cookies[i].getName() ) ) {		//找到名为 ‘shopCar’的Cookie，并进行逻辑处理
						try{
							carList=JsonUtil.byteStrToObj2( cookies[i].getValue(), Orderitem.class );
							if( carList != null ){
								shoopCarCookie = cookies[i];									//cookie中的‘数据可用’的情况下，记录下该Cookie
								if( user != null ){												//用户登录了，但是从数据库未找到购物车数据，但是Cookie中存在，则把Cookie中的数据同步到数据库
									SynchShopCar(carList,user);
									cookies[i].setValue(null);
									cookies[i].setMaxAge(0);
									response.addCookie(cookies[i]);
									user.setState(9);
									request.getSession().setAttribute("user", user);
								}
							}
						}catch(Exception e){//暂时保留
							carList = null;
							shoopCarCookie = null;
						}
						break cookiesFor;
					}
				}
			}
		}
		if( user != null ){
			carList=synchShopCar(null,user,"sel");
		}
		try {
			if( !ValidateValue.isEmpty(statu) ) {									//提交状态验证
				if( "1".equals(statu) || "2".equals(statu) ) {
					System.out.println("  LOG { 选择了状态："+statu+"，修改商品项数量 } ");
					int pId = ValidateValue.isInteger( productId )?Integer.parseInt( productId ):0;
					int pNumber = ValidateValue.isInteger( count )?Integer.parseInt( count ):0;
					if( pNumber > 0 && pId != 0 ) {
						if(carList!=null && carList.size()>0){
							itemFor:for (Orderitem item : carList) {
								if( item.getProductId() == pId ) {				//找到对应Cookie
									item.setCount(pNumber);						//更新（内存）
									synchShopCar(item,user,"upd");
									if(user == null){
										if(shoopCarCookie!=null){					//更新到Cookie中
											if(carList.size()>0){
												shoopCarCookie.setValue( JsonUtil.objToByteStr(carList) );
											}else{
												shoopCarCookie.setValue(null);
												shoopCarCookie.setMaxAge(0);
											}
											response.addCookie(shoopCarCookie);
										}else if(shoopCarCookie==null && carList.size()>0){//冗余
											shoopCarCookie = new Cookie( "shopCar", JsonUtil.objToByteStr(carList) );
											shoopCarCookie.setMaxAge(60*60*24*3);
											response.addCookie(shoopCarCookie);
										}
									}
									System.out.println("  LOG { 商品数量变化 }"+item.getCount()+" ——>"+pNumber);
									flagIsExistItem = true;
									break itemFor;
								}
							}
						}
					}
				}
			}
			if(flagIsExistItem) {
				responseMsg.setCode("1");							// 1:成功1
				return responseMsg;
			}else {
				responseMsg.setCode("0");							// 0:失败！
				return responseMsg;
			}
		}catch (Exception e) {
			System.out.println("  LOG:AJAX失败原因 参考其他信息！");
			responseMsg.setCode("0");							// 0:失败！
			return responseMsg;
		}
	}
   /**
	* 订单信息
	* currentPageOne，currentPageTwo，currentPageThr 这是三个小块的分页信息
	* localtion ：这个参数用来记录当前浏览在哪个模块，
	**/
	@RequestMapping("orderInfo")
	public String orderInfo( String currentPageOne,String currentPageTwo,String currentPageThr,String localtion, HttpServletRequest request, Model model){
		Object userObj = request.getSession().getAttribute("user");
		//用户未登录,转向登录页面
		if(userObj == null) {
			model.addAttribute("target", "userShop/orderInfo");
			return "login";
		}
		User user=(User)userObj;
		//User user = userShopService.getUser(1);
		Integer curPageOne = ValidateValue.isInteger(currentPageOne)?Integer.parseInt(currentPageOne):1;
		Integer curPageTwo = ValidateValue.isInteger(currentPageTwo)?Integer.parseInt(currentPageTwo):1;
		Integer curPageThr = ValidateValue.isInteger(currentPageThr)?Integer.parseInt(currentPageThr):1;
		
		Page pageOne = new Page(curPageOne, userShopService.getOrderCount(user.getId(),1,1), 10);
		Page pageTwo = new Page(curPageTwo, userShopService.getOrderCount(user.getId(),2,3), 10);
		Page pageThr = new Page(curPageThr, userShopService.getOrderCount(user.getId(),4,5), 10);

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("valOne", user.getId());
		//未支付订单
		if( pageOne.getCount()>0 ) {//减少数据块开销
			map.put("valTwo", 1);
			map.put("valThr", 1);
			map.put("startPage", pageOne.getStartIndex());
			map.put("sizePaze", pageOne.getSizePage());
			List<Orders> unpaid = userShopService.getOrderList(map, model);
			model.addAttribute("unpaid", unpaid);
		}
		//未收货订单
		if( pageTwo.getCount()>0 ) {
			map.put("valTwo", 2);
			map.put("valThr", 3);
			map.put("startPage", pageTwo.getStartIndex());
			map.put("sizePaze", pageTwo.getSizePage());
			List<Orders> openOrder = userShopService.getOrderList(map, model);
			model.addAttribute("openOrder", openOrder);
		}
		//未支付订单
		if( pageThr.getCount()>0 ) {
			map.put("valTwo", 4);
			map.put("valThr", 5);
			map.put("startPage", pageThr.getStartIndex());
			map.put("sizePaze", pageThr.getSizePage());
			List<Orders> receivedOrder = userShopService.getOrderList(map, model);
			model.addAttribute("receivedOrder", receivedOrder);
		}
		//不同页面的分页信息
		model.addAttribute("pageOne", pageOne);
		model.addAttribute("pageTwo", pageTwo);
		model.addAttribute("pageThr", pageThr);
		//这个参数用来告诉位置的
		if( !ValidateValue.isEmpty(localtion) ) {
			model.addAttribute("changeLocaltion", localtion);
		}else{
			model.addAttribute("changeLocaltion", "midy");
		}
		getClassInfo(model);
		getUserLoginInfo(request);
		return "frontEnd/orderList";
	}
	/**
	  *  查看订单详情 
	 **/
	@RequestMapping("orderItemInfo")
	public String orderItemInfo(String orderUUID, Model model, HttpServletRequest request) {
		//数据的简单验证
		if( !ValidateValue.isEmpty(orderUUID) && orderUUID.length()>31) {
			Orders orderInfo = userShopService.selectOrderInfo(orderUUID);
			if( orderInfo != null ) {
				model.addAttribute("orderInfo", orderInfo);
				List<Orderitem> oilInfo = userShopService.selectOrderItemInfo(orderUUID);
				model.addAttribute("oilInfo", oilInfo);
				return "frontEnd/orderItem";
			}
		}
		getClassInfo(model);
		getUserLoginInfo(request);
		return "redirect:userShop/orderInfo";
	}
	/**
	 * 生成订单之前的操作 （商品信息的确定）
	 * productId	商品ID
	 * number		商品数量
	 **/
	@RequestMapping("formSubmitBefore")
	public String formSubmitBefore(String productId,String number, Model model, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("    LOG: 生成订单之前 的操作 （商品信息的确定）");
		System.out.println("    LOG: productId="+productId);
		Object userObj = request.getSession().getAttribute("user");
		//用户未登录,转向登录页面-并在保存当前请求参数
		if(userObj == null) {
			System.out.println("    LOG: 用户未登录！");
			//数据的备份操作
			HashMap<String,String> parameterMap = new HashMap<String, String>();
			parameterMap.put("productId", productId);
			parameterMap.put("number", number);
			response.addCookie(new Cookie("formSubmitBeforeParameter", JsonUtil.objToByteStr(parameterMap)));
			//完成登录操作后，跳转目标参数
			model.addAttribute("target", "userShop/formSubmitBefore");	//此参数用于引导登录成功后跳转页面的URL
			return "login";
		}
		String returnPage="redirect:addToShopCar";						//跳转页面的参数
		
		//参数为空的时，再去cookie中找一下，看有没有参数
		if( ValidateValue.isEmpty(productId) ) {						
			Cookie[] cookies = request.getCookies();
			if(cookies!=null){
				cookieFor:for (Cookie cookie : cookies) {
					if("formSubmitBeforeParameter".equals(cookie.getName())){//找到cookie中的参数
						@SuppressWarnings("unchecked")
						Map<String, String> parameterMap = JsonUtil.byteStrToObj(cookie.getValue(), HashMap.class);
						productId = parameterMap.get("productId");
						number = parameterMap.get("number");
						break cookieFor;
					}
				}
			}
			//再次校验，是否继续往下执行
			if(ValidateValue.isEmpty(productId)){
				return returnPage;//直接返回购物车页面
			}
		}
		/*传过来的参数进行处理——去掉没被选商品项*/
		User user = null;
		List<Orderitem> carList = null;
		if(userObj instanceof User){
			user = (User)userObj;
		}else{
			return 	returnPage;
		}
		productId = trimStr(productId);//简单处理参数
		if(number==null){	//非直接购买
			if( user!=null && 9 != user.getState() ){//如果没有同步过数据，会同步一次数据
				Cookie[] cookies = request.getCookies();
				if( cookies != null ) {
					cookiesFor:for( int i=cookies.length-1; i >= 0; i--) {	
						if( cookies[i] != null && "shopCar".equals( cookies[i].getName() ) ) {
							try{
								carList=JsonUtil.byteStrToObj2( cookies[i].getValue(), Orderitem.class );
								if( carList != null ){
									SynchShopCar(carList,user);
									cookies[i].setValue(null);
									cookies[i].setMaxAge(0);
									response.addCookie(cookies[i]);
									user.setState(9);
									request.getSession().setAttribute("user", user);
								}
							}catch(Exception e){
								return 	returnPage;
							}
							break cookiesFor;
						}
					}
				}
			}
			carList=synchShopCar(null,user,"sel");
			if( carList == null ){
				return 	returnPage;
			}
			if( productId.length() > 1 ) {						//当前为处理多个
				System.out.println("    LOG: 开始逻辑处理: 多个商品检查");
				String[] IDs = productId.split(",");
				for (Orderitem item : carList) {
					boolean idFlag=false;						//未找到 
					IDs:for(String id:IDs) {
						if( item.getProductId() == Integer.parseInt(id)) {
							idFlag=true;						//该商品被选中
							break IDs;
						}
					}
					if(!idFlag) {								//未找到的情况进一步处理
						carList.remove(item);
					}
				}
			}else{												//处理单个
				Integer id=Integer.parseInt(productId);
				for (Orderitem item : carList) {
					if(item.getProductId() != id) {
						carList.remove(item);
					}
				}
			}
		}else{	//直接购买
			System.out.println("    LOG: 遍历cookies 找名为 shopCar 的cookie，该cookie 不存在（开始执行直接购买代码）");
			if( ValidateValue.isInteger(productId) && ValidateValue.isInteger(number) ) {
				Orderitem ordItm = new Orderitem();					//创建一个订单项对象
				ordItm.setProductId( Integer.parseInt(productId) );	
				ordItm.setCount( Integer.parseInt(number) );
				ordItm.setProduct(userShopService.productInfoById(productId.trim()));
				ordItm.setSubtotal(ordItm.getCount()*ordItm.getProduct().getShopPrice());
				carList = new ArrayList<Orderitem>();				//创建一个订单项的List集合
				carList.add(ordItm);
			}else {
				System.err.println("    LOG: 参数  不符合要求！");
				return returnPage;
			}
		}
		request.getSession().setAttribute("order", fullOrderData( model, carList));
		request.getSession().setAttribute("orderItem", carList);//真正生成订单前先在那还存放在Session作用于域中（然后生成订单就用AJAX）
		getClassInfo(model);		//商品分类信息
		getUserLoginInfo(request);	//登录的用户信息
		return 	"frontEnd/submitOrder";
	}
	/**
	 * 客户填写订单信息后,生成订单 并落库
	 * AJAX 来实现
	 **/
	@SuppressWarnings("unchecked")
	@RequestMapping("addOrder")
	@ResponseBody
	public Message<Object> addOrder(String phoneNO,String userName,String userAddr,String payMethod, Model model, HttpServletRequest request, HttpServletResponse response) {
		System.err.println("    LOG: 客户填写订单信息，后生产订单");
		Message<Object> res = new Message<Object>("f");// code: s——执行成功+ f——失败 ;
		//参数处理,这里仅仅 进行了简单的验证
		if( !ValidateValue.valiPhone(phoneNO) ) {
			res.setEntity(new ResState("f1","  *无效手机号！"));
			return res;
		}
		if( ValidateValue.isEmpty(userName) ) {
			res.setEntity(new ResState("f2","  *用户名不能为空！"));
			return res;
		}
		if( ValidateValue.isEmpty(userAddr) ) {
			res.setEntity(new ResState("f3","  *地址不能为空！"));
			return res;
		}
		if( ValidateValue.isEmpty(payMethod) ) {
			res.setEntity(new ResState("f4","  *请选择支付方式！"));
			return res;
		}

		Object orderObj = request.getSession().getAttribute("order");
		Object orderItemObj = request.getSession().getAttribute("orderItem");
		Object userObj = request.getSession().getAttribute("user");
		if( orderObj == null || orderItemObj == null || userObj == null) {//此情况应该回到购物车，重新生成相关信息
			res.setEntity(new ResState("f0","  	响应超时"));
			return res;
		}
		Orders order = (Orders)orderObj;
		List<Orderitem> orderItem = (List<Orderitem>)orderItemObj;
		User user = (User)userObj;
		String  orderId= UUID.randomUUID().toString().replaceAll("-", "");
		
		order.setId(orderId);			//订单号
		order.setName(userName);		//收货人
		order.setPhone(phoneNO);		//收货人手机号
		order.setUid(user.getId());		//下单人ID
		order.setAddr(userAddr);		//收货人地址
		order.setOrdertime(new Date());	//下单时间
		order.setState(2);				// 状态2：已经支付，未发货
		order.setWay(payMethod);;		// 支付方式
		
		try {
			userShopService.addOrderInfo(order,orderItem);//这样便于数
			clearUserShopCar( user,request );	//清购物车临时数据
			res.setCode("s");
		}catch (Exception e) {//在数据保存的异常处理
			System.err.println("    LOG: 保存订单信息异常");
			e.printStackTrace();
			res.setEntity(new ResState("ff","  	*响应超时"));
			return res;
		}
		return res;
	}
	/**
	 * 订单生成后，清空购车车
	 * @RequestMapping("clearUserShopCar")
	 **/
	public void clearUserShopCar(  User user ,HttpServletRequest request ) {
		//清内存
		HttpSession session = request.getSession();
		Object order = session.getAttribute("order");
		Object orderItem = session.getAttribute("orderItem");
		if( order != null ) {
			session.removeAttribute("order");
		}
		if( orderItem != null ) {
			session.removeAttribute("orderItem");
		}
		//清数据库中的购物车
		synchShopCar(null,user,"clear");
	}
	/**
	 * 去掉字符串两端的',' 
	 **/
	private String trimStr(String productId) {
		if(productId == null) {
			return null;
		}
		if( productId.charAt(0)==',' ) {
			productId = productId.substring(1, productId.length());
		}else if( productId.charAt( productId.length()-1 )==',' ){
			productId = productId.substring(0, productId.length()-1);
		}
		return productId;
	}
	/**
	 *	获取一二级分类信息并且放置到model中 
	 **/
	private void getClassInfo(Model model) {
		/*
		 *	 先数据块查询一二级分类信息
		 **/
		List<Category> oneList = userShopService.getOneClass();										//获取所有的一级分类List
		ArrayList< HashMap<String,Object> > dataList = new ArrayList< HashMap<String,Object> >();	//整体数据块，存放所有一级分类洗信息，二级分类信息，以及对应的商品信息的List集合
		if( oneList !=null &&  oneList.size()>0) {
			for( Category one:oneList ) {
				List<Secondcategory> twoClass = userShopService.getTwoClass(one);					//根据一级分类获取对应的所有的二级分类信息List；
				// 此处可以控制，用于筛选用于展示的一二级分类
				if( twoClass.size()>0 ) {															//这里仅仅限制了数据为空的,只有当一二级数据都存在的时候才会放入List中，避免了渲染数据的时候出现异常；
					HashMap<String, Object> dataBlock = new HashMap<String, Object>();
					dataBlock.put("oneClass", one);
					dataBlock.putIfAbsent("twoClass", twoClass);
					dataList.add(dataBlock);
				}
			}
			model.addAttribute("data", dataList);
		}
	}
	/**
	 *	获取用户信息并且放置到model中 
	 **/
	private void getUserLoginInfo(HttpServletRequest request) {
		Object user = request.getSession().getAttribute("user");
		if( user != null ) {
			request.setAttribute("user", user);
		}
	}
	//同步到购物车到数据库 或 获取数据库中的购物车数据
	private List<Orderitem> synchShopCar(Orderitem orderItm,User user,String operationType){
		if(user == null || operationType == null){
			return null;
		}
		if( "sel".equals(operationType) ){			//查询该用户的购物车数据
			return userShopService.getShopCarItm(user);
		}else if( "clear".equals(operationType)){	//更新该用户的购物车数据
			userShopService.delShopCarItemAll(user); //清空该购物车
		}else if(orderItm!=null && "add".equals(operationType)){	//添加该用户的购物车数据
			userShopService.addShopCarItem(user, orderItm);
		}else if(orderItm!=null && "del".equals(operationType)){	//删除该用户的购物车数据
			userShopService.deleteShopCarItem(user, orderItm);
		}else if(orderItm!=null && "upd".equals(operationType)){	//更新该用户的购物车数据
			userShopService.updateShopCarItem(user, orderItm);
		}
		return null;
	}
	/**
	 * 同步Coookie中购物车数据到数据库
	 * 同时兼具中对购物车中数据的整理！防止出现同一商品两个订单项的情况
	 **/
	private void SynchShopCar(List<Orderitem> orderItem,User user){
		if( user==null || orderItem==null || orderItem.size()<1) return;
		String orderId = userShopService.addOrGetOrderId(user);
		for(Orderitem item:orderItem) {
			List<Orderitem> selectByExample2 = userShopService.selectOrderInfo(item, orderId);
			if(selectByExample2==null || selectByExample2.size()<1){//没有该商品项
				item.setOrderId(orderId);
				userShopService.addShopCarItem(item);
			}else{//存在商品项
				if(selectByExample2.size()==1){
					selectByExample2.get(0).setCount( selectByExample2.get(0).getCount()+item.getCount() );
					userShopService.updateShopCarItem(selectByExample2.get(0));
				}else{
					int num=0;
					for (Orderitem orderitem2 : selectByExample2) {
						num+=orderitem2.getCount();
						userShopService.deleteShopCarItem(orderitem2);
					}
					item.setCount(num+item.getCount());
					item.setOrderId(orderId);
					userShopService.addShopCarItem(item);
				}
			}
		}
	}
	@RequestMapping("changeOrderState")
	@ResponseBody
	public ResState changeOrderState(String orderId){
		ResState res = new ResState("0");
		if (orderId == null || orderId.trim().length()>30 ) return res;
		Orders orderInfo = userShopService.selectOrderInfo(orderId);
		if(orderInfo != null){
			try{
				switch (orderInfo.getState()) {
				case 1:
					orderInfo.setState(2);
					userShopService.updateOrderByKey(orderInfo);
					res.setCode("2");
					break;
				case 3:
					orderInfo.setState(4);
					userShopService.updateOrderByKey(orderInfo);
					res.setCode("4");
					break;
				default:
					res.setCode(String.valueOf(orderInfo.getState()));
					break;
				}
			}catch(Exception e){
				System.out.println("   LOG:更改状态失败！");
			}
		}
		return res;
	}
}
