package com.lemonzuo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCUtil {
	//JDBC ����
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	//���ݿ��û���
	public String userName;
	//���ݿ�����
	public String userPass;
	//���ݿ�url
	public String mySqlUrl;
	//���ݿ�˿�
	public String port;
	//���ݿ���
	public String dataBaseName;
	//JDBC URL
	public String URL;
	//Connection����
	public Connection con;
	//PreparedStatement����
	public PreparedStatement ps = null;
	//�����
	public ResultSet result = null;
	//��Ӱ�������
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
	 * ���ݿ����ӵĳ�ʼ��
	 */
	public void getConnection() {
		try {
			//1����������
			Class.forName(DRIVER);
			//2����������
			con = DriverManager.getConnection(URL, userName, userPass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * ִ�в�ѯ
	 * @param sql SQL���
	 * @param obj SQL����
	 * @return ��ѯ���Ľ����
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
	 * ִ����ɾ�Ĳ���
	 * @param sql SQL���
	 * @param obj SQL����
	 * @return ��Ӱ�������
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
	 * �����ݿ�Ͽ�����
	 */
	public void close() {
		//�رս����
		if(result != null) {
			try {
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//�ر�PreparedStatement
		if(ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//�ر�����
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
