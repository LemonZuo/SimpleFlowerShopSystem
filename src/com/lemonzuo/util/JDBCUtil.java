package com.lemonzuo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCUtil {
	//JDBC 驱动
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	//数据库用户名
	public String userName;
	//数据库密码
	public String userPass;
	//数据库url
	public String mySqlUrl;
	//数据库端口
	public String port;
	//数据库名
	public String dataBaseName;
	//JDBC URL
	public String URL;
	//Connection对象
	public Connection con;
	//PreparedStatement对象
	public PreparedStatement ps = null;
	//结果集
	public ResultSet result = null;
	//受影响的行数
	int num = 0;
	
	public JDBCUtil(String userName, String userPass, String mySqlUrl, String port, String dataBaseName) {
		this.userName = userName;
		this.userPass = userPass;
		this.mySqlUrl = mySqlUrl;
		this.port = port;
		this.dataBaseName = dataBaseName;
		this.URL = "jdbc:mysql://"+this.mySqlUrl+":"+port+"/"+this.dataBaseName+"?useSSL=false";
	}
	
	/**
	 * 数据库链接的初始化
	 */
	public void getConnection() {
		try {
			//1、加载驱动
			Class.forName(DRIVER);
			//2、建立连接
			con = DriverManager.getConnection(URL, userName, userPass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 执行查询
	 * @param sql SQL语句
	 * @param obj SQL参数
	 * @return 查询到的结果集
	 */
	@SuppressWarnings("finally")
	public ResultSet executeQuery(String sql,Object...obj) {
			try {
				ps = con.prepareStatement(sql);
				for(int i = 0;i < obj.length;i++) {
					ps.setObject(i + 1, obj[i]);
				}
				result = ps.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				return result;
			}
	}
	
	/**
	 * 执行增删改操作
	 * @param sql SQL语句
	 * @param obj SQL参数
	 * @return 受影响的行数
	 */
	@SuppressWarnings("finally")
	public int executeUpdate(String sql,Object...obj) {
		try {
			ps = con.prepareStatement(sql);
			for(int i = 0;i < obj.length;i++) {
				ps.setObject(i + 1, obj[i]);
			}
			num = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			return num;
		}
	}
	
	/**
	 * 与数据库断开连接
	 */
	public void close() {
		//关闭结果集
		if(result != null) {
			try {
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//关闭PreparedStatement
		if(ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//关闭连接
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
