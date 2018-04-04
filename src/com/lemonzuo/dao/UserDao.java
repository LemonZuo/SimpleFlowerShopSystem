package com.lemonzuo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lemonzuo.model.Form;
import com.lemonzuo.util.JDBCUtil;

public class UserDao {

	/**
	 * 判断用户是否已经存在
	 * @param userName 用户名
	 * @return true已存在 false不存在
	 */
	public boolean userExist(String userName) {
		//定义标识符
		boolean flag = false;
		//定义结果集
		ResultSet result = null;
		//创建JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//建立连接
		util.getConnection();
		//sql语句
		String sql = "select * from user where user_user_name = ? ";
		//执行sql语句返回结果
		result = util.executeQuery(sql, userName);
		try {
			//判断是否查询到数据
			if(result.next()) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//关闭连接
			util.close();
		}
		return flag;
	}
	
	/**
	 * 判断管理员是否已经存在
	 * @param userName 用户名
	 * @return true已存在 false不存在
	 */
	public boolean adminExist(String userName) {
		//定义标识符
		boolean flag = false;
		//定义结果集
		ResultSet result = null;
		//创建JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//建立连接
		util.getConnection();
		//定义sql语句
		String sql = "select * from admin where admin_user_name = ? ";
		//执行sql语句返回结果
		result = util.executeQuery(sql, userName);
		try {
			//判断是否查询到数据
			if(result.next()) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//关闭连接
			util.close();
		}
		return flag;
	}
	
	/**
	 * 新增用户
	 * @param from 用户信息
	 * @return true插入成功 false插入失败
	 */
	public boolean addUser(Form from) {
		//定义标识符
		boolean flag = false;
		//定义接收所影响的行数
		int num = 0;
		//sql语句
		String sql = "INSERT INTO user(user_user_name, user_user_pass, user_name,"
				+ " user_user_sex, user_age, user_phone, user_addres, user_email) VALUES "
				+ "(?,?,?,?,?,?,?,?) ";
		//创建JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//建立连接
		util.getConnection();
		//执行语句返回结果
		num =  util.executeUpdate(sql, from.userName,from.userPass,from.name,from.sex,from.userAge,
				from.userMobile,from.userAdress,from.userEmail);
		//判断是否操作成功
		if(num > 0) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 用户登录
	 * @param userName 用户名
	 * @param userPass 密码
	 * @return true登录成功 false登录失败
	 */
	public boolean userLogin(String userName,String userPass) {
		//定义标识符
		boolean flag = false;
		//定义结果集
		ResultSet result;
		//SQL语句
		String sql = "select * from user where user_user_name= ? and user_user_pass = ? ";
		//创建JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//建立连接
		util.getConnection();
		//执行并返回结果
		result = util.executeQuery(sql, userName,userPass);
		try {
			//判断是否查询到相关数据
			if(result.next()) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//断开连接
			util.close();
		}
		return flag;
	}
	
	/**
	 * 管理员登录
	 * @param userName 用户名
	 * @param userPass 密码
	 * @return true登录成功 false登录失败
	 */
	public boolean adminLogin(String userName,String userPass) {
		//定义标识符
		boolean flag = false;
		//定义结果集
		ResultSet result;
		//SQL语句
		String sql = "select * from admin where admin_user_name= ? and admin_user_pass = ? ";
		//创建JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//建立连接
		util.getConnection();
		//执行并返回结果
		result = util.executeQuery(sql, userName,userPass);
		try {
			//判断是否查询到相关数据
			if(result.next()) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//关闭连接
			util.close();
		}
		return flag;
	}
	
	/**
	 * 获取鲜花相关数据
	 * @param flowerName 花名
	 * @return 鲜花相关数据
	 */
	public Vector<Vector<String>> getFlowerList(String flowerName) {
		//定义二维Vector对象
		Vector<Vector<String>> list= new Vector<Vector<String>>();
		//定义结果集
		ResultSet result = null;
		//序号
		int i = 1;
		//定义JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//定义SQL语句
		String sql =  "select * from flower ";
		//建立连接
		util.getConnection();
		//判断花名是否为空
		if ( !"".equals(flowerName) && flowerName != null) {
			//flowerName修改SQL语句
			sql += "where flower_name like ? ";
		}
		//根据ID排序
		sql += "ORDER BY flower_id ";
		//执行SQL语句返回结果
		if ( !"".equals(flowerName) && flowerName != null) {
			result = util.executeQuery(sql, "%"+flowerName+"%" );
		} else {
			result = util.executeQuery(sql);
		}
		try {
			//判断是否有相关数据
			while (result.next()) {
				//定义flowers接收一行数据
				Vector<String> flowers =  new Vector<String>();
				//将数据添加到flowers中
				flowers.add(String.valueOf(i++));
				flowers.add(result.getString("flower_name"));
				flowers.add(result.getString("flower_price"));
				flowers.add(result.getString("flower_count"));
				flowers.add(result.getString("flower_sale"));
				//将数据添加到list中
				list.add(flowers);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//关闭连接
			util.close();
		}
		return list;
	}
	
	/**
	 * 判断预添加鲜花是否已经存在于购物车中
	 * @param userName 用户名
	 * @param flowerName 鲜花名字
	 * @return true已存在 false不存在
	 */
	public boolean flowerExistShopCar(String userName,String flowerName) {
		//定义标识符
		boolean flag = false;
		//定义结果集
		ResultSet result = null;
		//定义JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//定义SQL语句
		String sql =  "select * from shop_car where user_name = ? and flower_name = ?  ";
		//建立连接
		util.getConnection();
		//执行并返回结果
		result = util.executeQuery(sql, userName,flowerName);
		try {
			//判断当前鲜花是否已经存在于购物车中
			if(result.next()) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//关闭连接
			util.close();
		}
		return flag;
	}
	
	/**
	 * 返回鲜花ID
	 * @param flowerName 鲜花花名
	 * @return 鲜花ID
	 */
	public String getFlowerId(String flowerName) {
		String flowerID = "";
		//定义结果集
		ResultSet result = null;
		//定义JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//定义SQL语句
		String sql =  "select flower_id from flower where flower_name = ? ";
		//建立连接
		util.getConnection();
		//执行返回结果
		result = util.executeQuery(sql, flowerName);
		try {
			if(result.next()) {
				flowerID = result.getString("flower_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.close();
		}
		return flowerID;
	}
	
	/**
	 * 返回鲜花库存量
	 * @param flowerName 鲜花花名
	 * @return 鲜花库存
	 */
	public String getFlowerNum(String flowerName) {
		String flowerNum = "";
		//定义结果集
		ResultSet result = null;
		//定义JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//定义SQL语句
		String sql =  "select flower_count from flower where flower_name = ? ";
		//建立连接
		util.getConnection();
		//执行返回结果
		result = util.executeQuery(sql, flowerName);
		try {
			if(result.next()) {
				flowerNum = result.getString("flower_count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.close();
		}
		return flowerNum;
	}
	
	//数字验证
		public boolean matchNum(String num) {
			boolean flag = false;
			String pattern = "[1-9]\\d*";
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(num);
			flag = m.matches();
			return flag;
		}
	
	/**
	 * 将鲜花加入购物车
	 * @param userName 用户名
	 * @param flowerName 鲜花花名
	 * @param flowerNum 鲜花数量
	 * @return true加入成功 false添加失败
	 */
	public boolean setToShopCar(String userName,String flowerName,String flowerNum,String time) {
		//定义标识符
		boolean flag = false;
		//定义添加成功标识
		int num = 0;
		//定义JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//定义SQL语句
		String sql =  "insert into shop_car (user_name,flower_name,collect_num,add_time) values (?,?,?,?) ";
		//建立连接
		util.getConnection();
		num = util.executeUpdate(sql, userName , flowerName , flowerNum, time);
		if(num > 0) {
			flag = true;
		}
		return flag;
	}
	
	//获取当前时间
	public String getTime() {
		String time = "";
		Date day=new Date();    
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		time = df.format(day);
		return time;
	}
	
	/**
	 * 获取购物车相关数据
	 * @param flowerName 花名
	 * @return 购物车相关数据
	 */
	public Vector<Vector<String>> getShopCar(String userName) {
		//定义二维Vector对象
		Vector<Vector<String>> list= new Vector<Vector<String>>();
		//定义结果集
		ResultSet result = null;
		//定义JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//定义SQL语句
		String sql =  "select * from shop_car where user_name = ? ";
		//序号
		int i = 1;
		//建立连接
		util.getConnection();
		//执行SQL语句返回结果
			result = util.executeQuery(sql, userName);
		try {
			//判断是否有相关数据
			while (result.next()) {
				//定义flowers接收一行数据
				Vector<String> flowers =  new Vector<String>();
				//将数据添加到flowers中
				flowers.add(String.valueOf(i++));
				flowers.add(result.getString("flower_name"));
				flowers.add(result.getString("collect_num"));
				flowers.add(result.getString("add_time"));
				flowers.add(result.getString("last_modify_time"));
				//将数据添加到list中
				list.add(flowers);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//关闭连接
			util.close();
		}
		return list;
	}
	
	/**
	 * 用户修改数据
	 * @param userName 用户名
	 * @param FlowerName 花名
	 * @param FlowerNum  预修改的数量
	 * @return true修改成功 false修改失败
	 */
	public boolean userModifyShopCarFlowerNum(String userName , String FlowerName , String FlowerNum ,String time) {
		//定义标识符
		boolean flag = false;
		//受影响行数
		int num;
		//创建JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//定义SQL语句
		String sql = " UPDATE shop_car SET collect_num = ? , last_modify_time = ? WHERE user_name = ? and flower_name = ? ";
		//创建连接
		util.getConnection();
		//执行并返回结果
		num = util.executeUpdate(sql, FlowerNum,time,userName,FlowerName);
		//判断是否修改成功
		if(num > 0) {
			flag = true;
		}
		//关闭连接
		util.close();
		return flag;
	}
	
	/**
	 * 删除购物车中的商品
	 * @param userName 用户名
	 * @param flowerName 花名
	 * @return true删除成功 false删除失败
	 */
	public boolean userDeleteShopCarFlower( String userName, String flowerName ) {
		//定义标识符
		boolean flag = false;
		//受影响行数
		int num;
		//创建JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//定义SQL语句
		String sql = "DELETE FROM shop_car WHERE user_name = ? and flower_name = ?";
		//创建连接
		util.getConnection();
		//执行并返回结果
		num = util.executeUpdate(sql,userName,flowerName);
		//判断是否修改成功
		if(num > 0) {
			flag = true;
		}
		//关闭连接
		util.close();
		return flag;
	}
	
	/**
	 * 管理员修改鲜花数据
	 * @param FlowerName 花名
	 * @param FlowerNum  库存
	 * @param FlowerPrice 价格
	 * @return true修改成功 false修改失败
	 */
	public boolean AdminModifyFlowerDetil(String FlowerName , String FlowerNum,String FlowerPrice) {
		//定义标识符
		boolean flag = false;
		//受影响行数
		int num;
		//创建JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//定义SQL语句
		String sql = " UPDATE flower SET flower_price = ? , flower_count = ? WHERE flower_name = ? ";
		//创建连接
		util.getConnection();
		//执行并返回结果
		num = util.executeUpdate(sql, FlowerPrice,FlowerNum,FlowerName);
		//判断是否修改成功
		if(num > 0) {
			flag = true;
		}
		//关闭连接
		util.close();
		return flag;
	}	
	
	/**
	 * 下架鲜花
	 * @param flowerName 花名
	 * @return true删除成功 false删除失败
	 */
	public boolean AdminDeleteFlower(String flowerName ) {
		//定义标识符
		boolean flag = false;
		//受影响行数
		int num;
		//创建JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//定义SQL语句
		String sql = "DELETE FROM flower WHERE flower_name = ?";
		//创建连接
		util.getConnection();
		//执行并返回结果
		num = util.executeUpdate(sql,flowerName);
		//判断是否修改成功
		if(num > 0) {
			flag = true;
		}
		//关闭连接
		util.close();
		return flag;
	}

	/**
	 * 判断用户是否已经存在
	 * @param flowerName 鲜花名
	 * @return true已存在 false不存在
	 */
	public boolean FlowerExist(String flowerName) {
		//定义标识符
		boolean flag = false;
		//定义结果集
		ResultSet result = null;
		//创建JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//建立连接
		util.getConnection();
		//sql语句
		String sql = "select * from flower where flower_name = ? ";
		//执行sql语句返回结果
		result = util.executeQuery(sql, flowerName);
		try {
			//判断是否查询到数据
			if(result.next()) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//关闭连接
			util.close();
		}
		return flag;
	}
	
	/**
	 * 新增鲜花
	 * @param flowerName 花名
	 * @param flowerCount 库存
	 * @param flowerPrice 价格
	 * @return
	 */
	public boolean AdminAddFlower(String flowerName,String flowerCount,String flowerPrice) {
		//定义标识符
		boolean flag = false;
		//定义接收所影响的行数
		int num = 0;
		//sql语句
		String sql = "INSERT INTO flower(flower_name, flower_count, flower_price,"
				+ " flower_sale) VALUES (?,?,?,?) ";
		//创建JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//建立连接
		util.getConnection();
		//执行语句返回结果
		num =  util.executeUpdate(sql, flowerName , flowerCount , flowerPrice , 0 );
		//判断是否操作成功
		if(num > 0) {
			flag = true;
		}
		util.close();
		return flag;
	}	
	
	/**
	 * 以时间作为订单编号
	 * @return
	 */
	public String CreateOrderID() {
		String orderID = "";
		Date day=new Date();    
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		orderID = df.format(day);
		return orderID;
	}
	
	public String orderTime() {
		String orderTime = "";
		Date day=new Date();    
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		orderTime = df.format(day);
		return orderTime;		
	}
	/**
	 * 创建订单
	 * @param userName 用户名
	 * @param orderID 订单编号
	 * @return
	 */
	public boolean CreateOrder(String userName,String orderID) {
		boolean flag = false;
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		util.getConnection();
		String sql = "INSERT INTO `order`(order_id,user_name,order_time,order_condition) values(?,?,?,?) ";
		int num = util.executeUpdate(sql, orderID,userName,this.orderTime(),1);
		if(num > 0) {
			flag = true;
		}
		util.close();
		return flag;
	}
	
	/**
	 * 写入详细订单信息
	 * @param orderID 订单ID 
	 * @param flowerName 鲜花名
	 * @param flowerNum 数量
	 * @return
	 */
	public boolean CreateOrderList(String orderID, String flowerName , String flowerNum) {
		//定义标识符
		boolean flag = false;
		//定义接收所影响的行数
		int num = 0;
		//SQL语句
		String sql = "INSERT INTO order_detial ( order_id , flower_name , num ) VALUES (?,?,?) ";
		//创建JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//建立连接
		util.getConnection();
		//执行语句返回结果
		num =  util.executeUpdate(sql, orderID , flowerName , flowerNum );
		//判断是否操作成功
		if(num > 0) {
			flag = true;
		}
		util.close();
		return flag;
	}
	
	/**
	 * 用户订单列表
	 * @param userName 用户名
	 * @return 用户订单列表
	 */
	public Vector<Vector<String>> UserOrderList(String userName) {
		//定义二维Vector对象
		Vector<Vector<String>> list= new Vector<Vector<String>>();
		//定义结果集
		ResultSet result = null;
		//定义JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//定义SQL语句
		String sql =  "select * from `order` where user_name = ? ";
		//序号
		int i = 1;
		//建立连接
		util.getConnection();
		//执行SQL语句返回结果
			result = util.executeQuery(sql, userName);
		try {
			//判断是否有相关数据
			while (result.next()) {
				//定义订单接收一行数据
				Vector<String> order =  new Vector<String>();
				//将数据添加到flowers中
				order.add(String.valueOf(i++));
				order.add(result.getString("order_id"));
				order.add(result.getString("order_condition"));
				order.add(result.getString("order_time"));
				//将数据添加到list中
				list.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//关闭连接
			util.close();
		}
		return list;
	}
	
	/**
	 * 用户订单详细列表
	 * @param userName 用户名
	 * @return 用户订单列表
	 */
	public Vector<Vector<String>> OrderDetial(String orderID) {
		//定义二维Vector对象
		Vector<Vector<String>> list= new Vector<Vector<String>>();
		//定义结果集
		ResultSet result = null;
		//定义JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//定义SQL语句
		String sql =  "select * from `order_detial` where order_id = ? ";
		//序号
		int i = 1;
		//建立连接
		util.getConnection();
		//执行SQL语句返回结果
			result = util.executeQuery(sql, orderID);
		try {
			//判断是否有相关数据
			while (result.next()) {
				//定义订单接收一行数据
				Vector<String> order =  new Vector<String>();
				//将数据添加到flowers中
				order.add(String.valueOf(i++));
				order.add(result.getString("flower_name"));
				order.add(result.getString("num"));
				//将数据添加到list中
				list.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//关闭连接
			util.close();
		}
		return list;
	}
	
	/**
	 * 删除购物车中的商品
	 * @param userName 用户名
	 * @param flowerName 花名
	 * @return true删除成功 false删除失败
	 */
	public boolean userDeleteOrder( String userName, String orderID) {
		//定义标识符
		boolean flag = false;
		//受影响行数
		int num;
		//创建JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//定义SQL语句
		String sql = "DELETE FROM `order` WHERE user_name = ? and order_id = ?";
		//创建连接
		util.getConnection();
		//执行并返回结果
		num = util.executeUpdate(sql,userName,orderID);
		//判断是否修改成功
		if(num > 0) {
			flag = true;
		}
		//关闭连接
		util.close();
		return flag;
	}
	
	/**
	 * 收货
	 * @param userName 用户名
	 * @param orderID  订单号
	 * @return true 收货成功 false 收货成功
	 */
	public boolean UserConfirmGetOrder(String userName ,String orderID) {
		//定义标识符
		boolean flag = false;
		//受影响行数
		int num;
		//创建JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//定义SQL语句
		String sql = " UPDATE `order` SET order_condition = ?  WHERE user_name = ? and order_id = ? ";
		//创建连接
		util.getConnection();
		//执行并返回结果
		num = util.executeUpdate(sql, 4,userName,orderID);
		//判断是否修改成功
		if(num > 0) {
			flag = true;
		}
		//关闭连接
		util.close();
		return flag;
	}
	
	/**
	 * 删除订单鲜花选项
	 * @param OrderID 订单号
	 * @param FlowerName 花名
	 * @return
	 */
	public boolean UserDelOrderFlower(String OrderID,String FlowerName) {
		//定义标识符
		boolean flag = false;
		//受影响行数
		int num;
		//创建JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//定义SQL语句
		String sql = " DELETE  FROM `order_detial`  WHERE order_id = ? and flower_name = ? ";
		//创建连接
		util.getConnection();
		//执行并返回结果
		num = util.executeUpdate(sql, OrderID,FlowerName);
		//判断是否修改成功
		if(num > 0) {
			flag = true;
		}
		//关闭连接
		util.close();
		return flag;
	}
	
	/**
	 * 修改订单鲜花数量
	 * @param OrderID 订单号
	 * @param FlowerName 鲜花名
	 * @param newNum 预修改数量
	 * @return true修改成功 false修改失败
	 */
	public boolean UserModifyOrderFlowerNum(String OrderID,String FlowerName,String newNum) {
		//定义标识符
		boolean flag = false;
		//受影响行数
		int num;
		//创建JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//定义SQL语句
		String sql = " UPDATE order_detial SET num = ? WHERE order_id = ? and flower_name = ? ";
		//创建连接
		util.getConnection();
		//执行并返回结果
		num = util.executeUpdate(sql, newNum , OrderID , FlowerName );
		//判断是否修改成功
		if(num > 0) {
			flag = true;
		}
		//关闭连接
		util.close();
		return flag;
	}
	
	/** 判断订单中鲜花是否全部删除
	 * 判断鲜花是否存在本订单中
	 * @param orderID 订单号
	 * @return true部分被删除 false全部被删除
	 */
	public boolean orderExistFlower(String orderID) {
		//定义标识符
		boolean flag = false;
		//定义结果集
		ResultSet result = null;
		//创建JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//建立连接
		util.getConnection();
		//SQL语句
		String sql = "select * from order_detial where order_id =  ? ";
		//执行SQL语句返回结果
		result = util.executeQuery(sql, orderID);
		try {
			//判断是否查询到数据
			if(result.next()) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//关闭连接
			util.close();
		}
		return flag;
	}
	
	/**
	 * 所有订单
	 * @return 所有订单
	 */
	public Vector<Vector<String>> allOrderList() {
		//定义二维Vector对象
		Vector<Vector<String>> list= new Vector<Vector<String>>();
		//定义结果集
		ResultSet result = null;
		//定义JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//定义SQL语句
		String sql =  "select * from `order` ";
		//序号
		int i = 1;
		//建立连接
		util.getConnection();
		//执行SQL语句返回结果
			result = util.executeQuery(sql);
		try {
			//判断是否有相关数据
			while (result.next()) {
				//定义订单接收一行数据
				Vector<String> order =  new Vector<String>();
				//将数据添加到flowers中
				order.add(String.valueOf(i++));
				order.add(result.getString("order_id"));
				order.add(result.getString("order_condition"));
				order.add(result.getString("user_name"));
				//将数据添加到list中
				list.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//关闭连接
			util.close();
		}
		return list;
	}
	
	/**
	 * 更新库存和销量成功
	 * @param orderID 订单号
	 * @param FlowerName 花名
	 * @param FlowerNum 数量
	 * @return true更新库存和销量成功 false更新库存和销量失败
	 */
	public boolean disTrobuteFlower(String FlowerName,String FlowerNum) {
		boolean flag = false;
		//定义JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//定义SQL语句
		int num = 0;
		String sql =  "UPDATE flower SET flower_count = flower_count-? , flower_sale = flower_sale + ? where flower_name = ? ";
		util.getConnection();
		num = util.executeUpdate(sql, FlowerNum,FlowerNum,FlowerName);
		if( num > 0 ) {
			flag = true;
		}
		util.close();
		return flag;
	}
	
	/**
	 * 标识订单鲜花是否配货
	 * @param FlowerName
	 * @param orderID
	 * @return true配货成功 false配货失败
	 */
	public boolean disTrobuteFlowerFlag(String FlowerName,String orderID) {
		boolean flag = false;
		//定义JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//定义SQL语句
		int num = 0;
		String sql =  "UPDATE order_detial SET flag = ? where flower_name = ? and order_id = ? ";
		util.getConnection();
		num = util.executeUpdate(sql, 2 , FlowerName , orderID );
		if( num > 0 ) {
			flag = true;
		}
		util.close();
		return flag;
	}
	
	/**
	 * 配货
	 * @param orderID 订单号
	 */
	public void modifyOrderCondition(String orderID) {
		//定义JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//定义SQL语句
		String sql =  "UPDATE `order` SET order_condition = ? where order_id = ? ";
		util.getConnection();
		util.executeUpdate(sql, "配货" ,orderID );
		util.close();
	}
	
	/**
	 * 判断订单商品是否全部配货
	 * @param orderID 订单号
	 * @return true 部分配货 false 全部配货
	 */
	public boolean orderExam(String orderID) {
		//定义标识符
		boolean flag = false;
		//定义结果集
		ResultSet result = null;
		//创建JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//建立连接
		util.getConnection();
		//SQL语句
		String sql = "select * from order_detial where order_id = ? and flag =  ? ";
		//执行SQL语句返回结果
		result = util.executeQuery(sql, orderID,1);
		try {
			//判断是否查询到数据
			if(result.next()) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//关闭连接
			util.close();
		}
		return flag;
	}
	
	/**
	 * 派送订单
	 * @param orderID 订单号
	 */
	public void OrderCondition(String orderID) {
		//定义JDBCUtil对象
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//定义SQL语句
		String sql =  "UPDATE `order` SET order_condition = ? where order_id = ? ";
		util.getConnection();
		util.executeUpdate(sql, "派送" ,orderID );
		util.close();
	}
	
}

