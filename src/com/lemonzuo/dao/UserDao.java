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
	 * �ж��û��Ƿ��Ѿ�����
	 * @param userName �û���
	 * @return true�Ѵ��� false������
	 */
	public boolean userExist(String userName) {
		//�����ʶ��
		boolean flag = false;
		//��������
		ResultSet result = null;
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//��������
		util.getConnection();
		//sql���
		String sql = "select * from user where user_user_name = ? ";
		//ִ��sql��䷵�ؽ��
		result = util.executeQuery(sql, userName);
		try {
			//�ж��Ƿ��ѯ������
			if(result.next()) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//�ر�����
			util.close();
		}
		return flag;
	}
	
	/**
	 * �жϹ���Ա�Ƿ��Ѿ�����
	 * @param userName �û���
	 * @return true�Ѵ��� false������
	 */
	public boolean adminExist(String userName) {
		//�����ʶ��
		boolean flag = false;
		//��������
		ResultSet result = null;
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//��������
		util.getConnection();
		//����sql���
		String sql = "select * from admin where admin_user_name = ? ";
		//ִ��sql��䷵�ؽ��
		result = util.executeQuery(sql, userName);
		try {
			//�ж��Ƿ��ѯ������
			if(result.next()) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//�ر�����
			util.close();
		}
		return flag;
	}
	
	/**
	 * �����û�
	 * @param from �û���Ϣ
	 * @return true����ɹ� false����ʧ��
	 */
	public boolean addUser(Form from) {
		//�����ʶ��
		boolean flag = false;
		//���������Ӱ�������
		int num = 0;
		//sql���
		String sql = "INSERT INTO user(user_user_name, user_user_pass, user_name,"
				+ " user_user_sex, user_age, user_phone, user_addres, user_email) VALUES "
				+ "(?,?,?,?,?,?,?,?) ";
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//��������
		util.getConnection();
		//ִ����䷵�ؽ��
		num =  util.executeUpdate(sql, from.userName,from.userPass,from.name,from.sex,from.userAge,
				from.userMobile,from.userAdress,from.userEmail);
		//�ж��Ƿ�����ɹ�
		if(num > 0) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * �û���¼
	 * @param userName �û���
	 * @param userPass ����
	 * @return true��¼�ɹ� false��¼ʧ��
	 */
	public boolean userLogin(String userName,String userPass) {
		//�����ʶ��
		boolean flag = false;
		//��������
		ResultSet result;
		//SQL���
		String sql = "select * from user where user_user_name= ? and user_user_pass = ? ";
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//��������
		util.getConnection();
		//ִ�в����ؽ��
		result = util.executeQuery(sql, userName,userPass);
		try {
			//�ж��Ƿ��ѯ���������
			if(result.next()) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//�Ͽ�����
			util.close();
		}
		return flag;
	}
	
	/**
	 * ����Ա��¼
	 * @param userName �û���
	 * @param userPass ����
	 * @return true��¼�ɹ� false��¼ʧ��
	 */
	public boolean adminLogin(String userName,String userPass) {
		//�����ʶ��
		boolean flag = false;
		//��������
		ResultSet result;
		//SQL���
		String sql = "select * from admin where admin_user_name= ? and admin_user_pass = ? ";
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//��������
		util.getConnection();
		//ִ�в����ؽ��
		result = util.executeQuery(sql, userName,userPass);
		try {
			//�ж��Ƿ��ѯ���������
			if(result.next()) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//�ر�����
			util.close();
		}
		return flag;
	}
	
	/**
	 * ��ȡ�ʻ��������
	 * @param flowerName ����
	 * @return �ʻ��������
	 */
	public Vector<Vector<String>> getFlowerList(String flowerName) {
		//�����άVector����
		Vector<Vector<String>> list= new Vector<Vector<String>>();
		//��������
		ResultSet result = null;
		//���
		int i = 1;
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//����SQL���
		String sql =  "select * from flower ";
		//��������
		util.getConnection();
		//�жϻ����Ƿ�Ϊ��
		if ( !"".equals(flowerName) && flowerName != null) {
			//flowerName�޸�SQL���
			sql += "where flower_name like ? ";
		}
		//����ID����
		sql += "ORDER BY flower_id ";
		//ִ��SQL��䷵�ؽ��
		if ( !"".equals(flowerName) && flowerName != null) {
			result = util.executeQuery(sql, "%"+flowerName+"%" );
		} else {
			result = util.executeQuery(sql);
		}
		try {
			//�ж��Ƿ����������
			while (result.next()) {
				//����flowers����һ������
				Vector<String> flowers =  new Vector<String>();
				//��������ӵ�flowers��
				flowers.add(String.valueOf(i++));
				flowers.add(result.getString("flower_name"));
				flowers.add(result.getString("flower_price"));
				flowers.add(result.getString("flower_count"));
				flowers.add(result.getString("flower_sale"));
				//��������ӵ�list��
				list.add(flowers);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//�ر�����
			util.close();
		}
		return list;
	}
	
	/**
	 * �ж�Ԥ����ʻ��Ƿ��Ѿ������ڹ��ﳵ��
	 * @param userName �û���
	 * @param flowerName �ʻ�����
	 * @return true�Ѵ��� false������
	 */
	public boolean flowerExistShopCar(String userName,String flowerName) {
		//�����ʶ��
		boolean flag = false;
		//��������
		ResultSet result = null;
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//����SQL���
		String sql =  "select * from shop_car where user_name = ? and flower_name = ?  ";
		//��������
		util.getConnection();
		//ִ�в����ؽ��
		result = util.executeQuery(sql, userName,flowerName);
		try {
			//�жϵ�ǰ�ʻ��Ƿ��Ѿ������ڹ��ﳵ��
			if(result.next()) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//�ر�����
			util.close();
		}
		return flag;
	}
	
	/**
	 * �����ʻ�ID
	 * @param flowerName �ʻ�����
	 * @return �ʻ�ID
	 */
	public String getFlowerId(String flowerName) {
		String flowerID = "";
		//��������
		ResultSet result = null;
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//����SQL���
		String sql =  "select flower_id from flower where flower_name = ? ";
		//��������
		util.getConnection();
		//ִ�з��ؽ��
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
	 * �����ʻ������
	 * @param flowerName �ʻ�����
	 * @return �ʻ����
	 */
	public String getFlowerNum(String flowerName) {
		String flowerNum = "";
		//��������
		ResultSet result = null;
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//����SQL���
		String sql =  "select flower_count from flower where flower_name = ? ";
		//��������
		util.getConnection();
		//ִ�з��ؽ��
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
	
	//������֤
		public boolean matchNum(String num) {
			boolean flag = false;
			String pattern = "[1-9]\\d*";
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(num);
			flag = m.matches();
			return flag;
		}
	
	/**
	 * ���ʻ����빺�ﳵ
	 * @param userName �û���
	 * @param flowerName �ʻ�����
	 * @param flowerNum �ʻ�����
	 * @return true����ɹ� false���ʧ��
	 */
	public boolean setToShopCar(String userName,String flowerName,String flowerNum,String time) {
		//�����ʶ��
		boolean flag = false;
		//������ӳɹ���ʶ
		int num = 0;
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//����SQL���
		String sql =  "insert into shop_car (user_name,flower_name,collect_num,add_time) values (?,?,?,?) ";
		//��������
		util.getConnection();
		num = util.executeUpdate(sql, userName , flowerName , flowerNum, time);
		if(num > 0) {
			flag = true;
		}
		return flag;
	}
	
	//��ȡ��ǰʱ��
	public String getTime() {
		String time = "";
		Date day=new Date();    
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		time = df.format(day);
		return time;
	}
	
	/**
	 * ��ȡ���ﳵ�������
	 * @param flowerName ����
	 * @return ���ﳵ�������
	 */
	public Vector<Vector<String>> getShopCar(String userName) {
		//�����άVector����
		Vector<Vector<String>> list= new Vector<Vector<String>>();
		//��������
		ResultSet result = null;
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//����SQL���
		String sql =  "select * from shop_car where user_name = ? ";
		//���
		int i = 1;
		//��������
		util.getConnection();
		//ִ��SQL��䷵�ؽ��
			result = util.executeQuery(sql, userName);
		try {
			//�ж��Ƿ����������
			while (result.next()) {
				//����flowers����һ������
				Vector<String> flowers =  new Vector<String>();
				//��������ӵ�flowers��
				flowers.add(String.valueOf(i++));
				flowers.add(result.getString("flower_name"));
				flowers.add(result.getString("collect_num"));
				flowers.add(result.getString("add_time"));
				flowers.add(result.getString("last_modify_time"));
				//��������ӵ�list��
				list.add(flowers);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//�ر�����
			util.close();
		}
		return list;
	}
	
	/**
	 * �û��޸�����
	 * @param userName �û���
	 * @param FlowerName ����
	 * @param FlowerNum  Ԥ�޸ĵ�����
	 * @return true�޸ĳɹ� false�޸�ʧ��
	 */
	public boolean userModifyShopCarFlowerNum(String userName , String FlowerName , String FlowerNum ,String time) {
		//�����ʶ��
		boolean flag = false;
		//��Ӱ������
		int num;
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//����SQL���
		String sql = " UPDATE shop_car SET collect_num = ? , last_modify_time = ? WHERE user_name = ? and flower_name = ? ";
		//��������
		util.getConnection();
		//ִ�в����ؽ��
		num = util.executeUpdate(sql, FlowerNum,time,userName,FlowerName);
		//�ж��Ƿ��޸ĳɹ�
		if(num > 0) {
			flag = true;
		}
		//�ر�����
		util.close();
		return flag;
	}
	
	/**
	 * ɾ�����ﳵ�е���Ʒ
	 * @param userName �û���
	 * @param flowerName ����
	 * @return trueɾ���ɹ� falseɾ��ʧ��
	 */
	public boolean userDeleteShopCarFlower( String userName, String flowerName ) {
		//�����ʶ��
		boolean flag = false;
		//��Ӱ������
		int num;
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//����SQL���
		String sql = "DELETE FROM shop_car WHERE user_name = ? and flower_name = ?";
		//��������
		util.getConnection();
		//ִ�в����ؽ��
		num = util.executeUpdate(sql,userName,flowerName);
		//�ж��Ƿ��޸ĳɹ�
		if(num > 0) {
			flag = true;
		}
		//�ر�����
		util.close();
		return flag;
	}
	
	/**
	 * ����Ա�޸��ʻ�����
	 * @param FlowerName ����
	 * @param FlowerNum  ���
	 * @param FlowerPrice �۸�
	 * @return true�޸ĳɹ� false�޸�ʧ��
	 */
	public boolean AdminModifyFlowerDetil(String FlowerName , String FlowerNum,String FlowerPrice) {
		//�����ʶ��
		boolean flag = false;
		//��Ӱ������
		int num;
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//����SQL���
		String sql = " UPDATE flower SET flower_price = ? , flower_count = ? WHERE flower_name = ? ";
		//��������
		util.getConnection();
		//ִ�в����ؽ��
		num = util.executeUpdate(sql, FlowerPrice,FlowerNum,FlowerName);
		//�ж��Ƿ��޸ĳɹ�
		if(num > 0) {
			flag = true;
		}
		//�ر�����
		util.close();
		return flag;
	}	
	
	/**
	 * �¼��ʻ�
	 * @param flowerName ����
	 * @return trueɾ���ɹ� falseɾ��ʧ��
	 */
	public boolean AdminDeleteFlower(String flowerName ) {
		//�����ʶ��
		boolean flag = false;
		//��Ӱ������
		int num;
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//����SQL���
		String sql = "DELETE FROM flower WHERE flower_name = ?";
		//��������
		util.getConnection();
		//ִ�в����ؽ��
		num = util.executeUpdate(sql,flowerName);
		//�ж��Ƿ��޸ĳɹ�
		if(num > 0) {
			flag = true;
		}
		//�ر�����
		util.close();
		return flag;
	}

	/**
	 * �ж��û��Ƿ��Ѿ�����
	 * @param flowerName �ʻ���
	 * @return true�Ѵ��� false������
	 */
	public boolean FlowerExist(String flowerName) {
		//�����ʶ��
		boolean flag = false;
		//��������
		ResultSet result = null;
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//��������
		util.getConnection();
		//sql���
		String sql = "select * from flower where flower_name = ? ";
		//ִ��sql��䷵�ؽ��
		result = util.executeQuery(sql, flowerName);
		try {
			//�ж��Ƿ��ѯ������
			if(result.next()) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//�ر�����
			util.close();
		}
		return flag;
	}
	
	/**
	 * �����ʻ�
	 * @param flowerName ����
	 * @param flowerCount ���
	 * @param flowerPrice �۸�
	 * @return
	 */
	public boolean AdminAddFlower(String flowerName,String flowerCount,String flowerPrice) {
		//�����ʶ��
		boolean flag = false;
		//���������Ӱ�������
		int num = 0;
		//sql���
		String sql = "INSERT INTO flower(flower_name, flower_count, flower_price,"
				+ " flower_sale) VALUES (?,?,?,?) ";
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//��������
		util.getConnection();
		//ִ����䷵�ؽ��
		num =  util.executeUpdate(sql, flowerName , flowerCount , flowerPrice , 0 );
		//�ж��Ƿ�����ɹ�
		if(num > 0) {
			flag = true;
		}
		util.close();
		return flag;
	}	
	
	/**
	 * ��ʱ����Ϊ�������
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
	 * ��������
	 * @param userName �û���
	 * @param orderID �������
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
	 * д����ϸ������Ϣ
	 * @param orderID ����ID 
	 * @param flowerName �ʻ���
	 * @param flowerNum ����
	 * @return
	 */
	public boolean CreateOrderList(String orderID, String flowerName , String flowerNum) {
		//�����ʶ��
		boolean flag = false;
		//���������Ӱ�������
		int num = 0;
		//SQL���
		String sql = "INSERT INTO order_detial ( order_id , flower_name , num ) VALUES (?,?,?) ";
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//��������
		util.getConnection();
		//ִ����䷵�ؽ��
		num =  util.executeUpdate(sql, orderID , flowerName , flowerNum );
		//�ж��Ƿ�����ɹ�
		if(num > 0) {
			flag = true;
		}
		util.close();
		return flag;
	}
	
	/**
	 * �û������б�
	 * @param userName �û���
	 * @return �û������б�
	 */
	public Vector<Vector<String>> UserOrderList(String userName) {
		//�����άVector����
		Vector<Vector<String>> list= new Vector<Vector<String>>();
		//��������
		ResultSet result = null;
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//����SQL���
		String sql =  "select * from `order` where user_name = ? ";
		//���
		int i = 1;
		//��������
		util.getConnection();
		//ִ��SQL��䷵�ؽ��
			result = util.executeQuery(sql, userName);
		try {
			//�ж��Ƿ����������
			while (result.next()) {
				//���嶩������һ������
				Vector<String> order =  new Vector<String>();
				//��������ӵ�flowers��
				order.add(String.valueOf(i++));
				order.add(result.getString("order_id"));
				order.add(result.getString("order_condition"));
				order.add(result.getString("order_time"));
				//��������ӵ�list��
				list.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//�ر�����
			util.close();
		}
		return list;
	}
	
	/**
	 * �û�������ϸ�б�
	 * @param userName �û���
	 * @return �û������б�
	 */
	public Vector<Vector<String>> OrderDetial(String orderID) {
		//�����άVector����
		Vector<Vector<String>> list= new Vector<Vector<String>>();
		//��������
		ResultSet result = null;
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//����SQL���
		String sql =  "select * from `order_detial` where order_id = ? ";
		//���
		int i = 1;
		//��������
		util.getConnection();
		//ִ��SQL��䷵�ؽ��
			result = util.executeQuery(sql, orderID);
		try {
			//�ж��Ƿ����������
			while (result.next()) {
				//���嶩������һ������
				Vector<String> order =  new Vector<String>();
				//��������ӵ�flowers��
				order.add(String.valueOf(i++));
				order.add(result.getString("flower_name"));
				order.add(result.getString("num"));
				//��������ӵ�list��
				list.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//�ر�����
			util.close();
		}
		return list;
	}
	
	/**
	 * ɾ�����ﳵ�е���Ʒ
	 * @param userName �û���
	 * @param flowerName ����
	 * @return trueɾ���ɹ� falseɾ��ʧ��
	 */
	public boolean userDeleteOrder( String userName, String orderID) {
		//�����ʶ��
		boolean flag = false;
		//��Ӱ������
		int num;
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//����SQL���
		String sql = "DELETE FROM `order` WHERE user_name = ? and order_id = ?";
		//��������
		util.getConnection();
		//ִ�в����ؽ��
		num = util.executeUpdate(sql,userName,orderID);
		//�ж��Ƿ��޸ĳɹ�
		if(num > 0) {
			flag = true;
		}
		//�ر�����
		util.close();
		return flag;
	}
	
	/**
	 * �ջ�
	 * @param userName �û���
	 * @param orderID  ������
	 * @return true �ջ��ɹ� false �ջ��ɹ�
	 */
	public boolean UserConfirmGetOrder(String userName ,String orderID) {
		//�����ʶ��
		boolean flag = false;
		//��Ӱ������
		int num;
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//����SQL���
		String sql = " UPDATE `order` SET order_condition = ?  WHERE user_name = ? and order_id = ? ";
		//��������
		util.getConnection();
		//ִ�в����ؽ��
		num = util.executeUpdate(sql, 4,userName,orderID);
		//�ж��Ƿ��޸ĳɹ�
		if(num > 0) {
			flag = true;
		}
		//�ر�����
		util.close();
		return flag;
	}
	
	/**
	 * ɾ�������ʻ�ѡ��
	 * @param OrderID ������
	 * @param FlowerName ����
	 * @return
	 */
	public boolean UserDelOrderFlower(String OrderID,String FlowerName) {
		//�����ʶ��
		boolean flag = false;
		//��Ӱ������
		int num;
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//����SQL���
		String sql = " DELETE  FROM `order_detial`  WHERE order_id = ? and flower_name = ? ";
		//��������
		util.getConnection();
		//ִ�в����ؽ��
		num = util.executeUpdate(sql, OrderID,FlowerName);
		//�ж��Ƿ��޸ĳɹ�
		if(num > 0) {
			flag = true;
		}
		//�ر�����
		util.close();
		return flag;
	}
	
	/**
	 * �޸Ķ����ʻ�����
	 * @param OrderID ������
	 * @param FlowerName �ʻ���
	 * @param newNum Ԥ�޸�����
	 * @return true�޸ĳɹ� false�޸�ʧ��
	 */
	public boolean UserModifyOrderFlowerNum(String OrderID,String FlowerName,String newNum) {
		//�����ʶ��
		boolean flag = false;
		//��Ӱ������
		int num;
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//����SQL���
		String sql = " UPDATE order_detial SET num = ? WHERE order_id = ? and flower_name = ? ";
		//��������
		util.getConnection();
		//ִ�в����ؽ��
		num = util.executeUpdate(sql, newNum , OrderID , FlowerName );
		//�ж��Ƿ��޸ĳɹ�
		if(num > 0) {
			flag = true;
		}
		//�ر�����
		util.close();
		return flag;
	}
	
	/** �ж϶������ʻ��Ƿ�ȫ��ɾ��
	 * �ж��ʻ��Ƿ���ڱ�������
	 * @param orderID ������
	 * @return true���ֱ�ɾ�� falseȫ����ɾ��
	 */
	public boolean orderExistFlower(String orderID) {
		//�����ʶ��
		boolean flag = false;
		//��������
		ResultSet result = null;
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//��������
		util.getConnection();
		//SQL���
		String sql = "select * from order_detial where order_id =  ? ";
		//ִ��SQL��䷵�ؽ��
		result = util.executeQuery(sql, orderID);
		try {
			//�ж��Ƿ��ѯ������
			if(result.next()) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//�ر�����
			util.close();
		}
		return flag;
	}
	
	/**
	 * ���ж���
	 * @return ���ж���
	 */
	public Vector<Vector<String>> allOrderList() {
		//�����άVector����
		Vector<Vector<String>> list= new Vector<Vector<String>>();
		//��������
		ResultSet result = null;
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//����SQL���
		String sql =  "select * from `order` ";
		//���
		int i = 1;
		//��������
		util.getConnection();
		//ִ��SQL��䷵�ؽ��
			result = util.executeQuery(sql);
		try {
			//�ж��Ƿ����������
			while (result.next()) {
				//���嶩������һ������
				Vector<String> order =  new Vector<String>();
				//��������ӵ�flowers��
				order.add(String.valueOf(i++));
				order.add(result.getString("order_id"));
				order.add(result.getString("order_condition"));
				order.add(result.getString("user_name"));
				//��������ӵ�list��
				list.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//�ر�����
			util.close();
		}
		return list;
	}
	
	/**
	 * ���¿��������ɹ�
	 * @param orderID ������
	 * @param FlowerName ����
	 * @param FlowerNum ����
	 * @return true���¿��������ɹ� false���¿�������ʧ��
	 */
	public boolean disTrobuteFlower(String FlowerName,String FlowerNum) {
		boolean flag = false;
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//����SQL���
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
	 * ��ʶ�����ʻ��Ƿ����
	 * @param FlowerName
	 * @param orderID
	 * @return true����ɹ� false���ʧ��
	 */
	public boolean disTrobuteFlowerFlag(String FlowerName,String orderID) {
		boolean flag = false;
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//����SQL���
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
	 * ���
	 * @param orderID ������
	 */
	public void modifyOrderCondition(String orderID) {
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//����SQL���
		String sql =  "UPDATE `order` SET order_condition = ? where order_id = ? ";
		util.getConnection();
		util.executeUpdate(sql, "���" ,orderID );
		util.close();
	}
	
	/**
	 * �ж϶�����Ʒ�Ƿ�ȫ�����
	 * @param orderID ������
	 * @return true ������� false ȫ�����
	 */
	public boolean orderExam(String orderID) {
		//�����ʶ��
		boolean flag = false;
		//��������
		ResultSet result = null;
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//��������
		util.getConnection();
		//SQL���
		String sql = "select * from order_detial where order_id = ? and flag =  ? ";
		//ִ��SQL��䷵�ؽ��
		result = util.executeQuery(sql, orderID,1);
		try {
			//�ж��Ƿ��ѯ������
			if(result.next()) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//�ر�����
			util.close();
		}
		return flag;
	}
	
	/**
	 * ���Ͷ���
	 * @param orderID ������
	 */
	public void OrderCondition(String orderID) {
		//����JDBCUtil����
		JDBCUtil util = new JDBCUtil("root","980724","localhost","3306","flower");
		//����SQL���
		String sql =  "UPDATE `order` SET order_condition = ? where order_id = ? ";
		util.getConnection();
		util.executeUpdate(sql, "����" ,orderID );
		util.close();
	}
	
}

