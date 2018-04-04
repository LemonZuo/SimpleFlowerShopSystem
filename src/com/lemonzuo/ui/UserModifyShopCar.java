package com.lemonzuo.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.lemonzuo.dao.UserDao;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class UserModifyShopCar extends JFrame {

	private JPanel contentPane;
	private JTextField textID;
	private JTextField textFlowerName;
	private JTextField textFlowerNum;
	private String ID;
	private String flowerName;
	private String flowerNum;
	public static Vector<String> DATA;
	public static String USER_NAME = UserShopCar.USER_NAME;

	/**
	 * Create the frame.
	 */
	public UserModifyShopCar() {
		//���������ڹر�
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				//��ת���ﳵ
				new UserShopCar();
			}
		});
		

		
		//����
		//�������
		setTitle("\u4FEE\u6539");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//����ɼ�
		setVisible(true);
		//��������
		setResizable(false);
		//�������
		setLocationRelativeTo(null);
		
		//����
		JLabel labTitle = new JLabel("\u4FE1\u606F\u4FEE\u6539");
		labTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labTitle.setFont(new Font("����", Font.PLAIN, 20));
		labTitle.setBounds(10, 29, 414, 39);
		contentPane.add(labTitle);
		
		//ID��ǩ
		JLabel labID = new JLabel("\u5E8F\u53F7\uFF1A");
		labID.setHorizontalAlignment(SwingConstants.CENTER);
		labID.setFont(new Font("����", Font.PLAIN, 14));
		labID.setBounds(57, 91, 61, 39);
		contentPane.add(labID);
		
		//������ǩ
		JLabel labFlowerName = new JLabel("\u9C9C\u82B1\uFF1A");
		labFlowerName.setHorizontalAlignment(SwingConstants.CENTER);
		labFlowerName.setFont(new Font("����", Font.PLAIN, 14));
		labFlowerName.setBounds(57, 140, 61, 39);
		contentPane.add(labFlowerName);
		
		//��Ŀ��ǩ
		JLabel labFlowerNum = new JLabel("\u6570\u91CF\uFF1A");
		labFlowerNum.setHorizontalAlignment(SwingConstants.CENTER);
		labFlowerNum.setFont(new Font("����", Font.PLAIN, 14));
		labFlowerNum.setBounds(57, 189, 61, 39);
		contentPane.add(labFlowerNum);
		
		//ID��ʾ��
		textID = new JTextField();
		textID.setBounds(130, 96, 205, 30);
		contentPane.add(textID);
		textID.setColumns(10);
		//�����ڱ߾�
		textID.setMargin(new Insets(0,5,0,0));
		//���ñ༭
		textID.setEditable(false);
		textID.setBackground(Color.white);
		
		//������ʾ��
		textFlowerName = new JTextField();
		textFlowerName.setColumns(10);
		textFlowerName.setBounds(130, 149, 205, 30);
		textFlowerName.setMargin(new Insets(0,5,0,0));
		textFlowerName.setEditable(false);
		textFlowerName.setBackground(Color.WHITE);
		contentPane.add(textFlowerName);
		
		//������д��
		textFlowerNum = new JTextField();
		textFlowerNum.setColumns(10);
		textFlowerNum.setBounds(130, 198, 205, 30);
		textFlowerNum.setMargin(new Insets(0,5,0,0));
		contentPane.add(textFlowerNum);
		
		//��ʼ������
		setData();
		
		//���ð�ť
		JButton btnReset = new JButton("\u91CD\u7F6E");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFlowerNum.setText(flowerNum);
			}
		});
		btnReset.setBounds(264, 256, 99, 39);
		contentPane.add(btnReset);
		
		//�ύ��ť
		JButton btnSubmit = new JButton("\u786E\u8BA4");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ʵ���޸�
				modify();
			}
		});
		btnSubmit.setBounds(88, 256, 99, 39);
		contentPane.add(btnSubmit);
		
	}
	
	//�޸ķ���
	public void modify() {
		//��ȡ��������
		String newFlowerNum = textFlowerNum.getText();
		UserDao userDao = new UserDao();
		String time = userDao.getTime();
		//�޸���ʾ
		//userOption �����Ƿ�����޸�
		int userOption = JOptionPane.showConfirmDialog(UserModifyShopCar.this, "�Ƿ��޸�", "��ʾ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (userOption == 0) {
			//�ж�������Ƿ�Ϊ����
			if(userDao.matchNum(newFlowerNum)) {
				//�����ݿ���в���
				if( Integer.parseInt(newFlowerNum) < Integer.parseInt(userDao.getFlowerNum(flowerName))) { //Ԥ�޸�����С�ڿ����
					//ִ�и��²���
					if( userDao.userModifyShopCarFlowerNum(USER_NAME, flowerName, newFlowerNum,time) ) {
						JOptionPane.showMessageDialog(UserModifyShopCar.this, "�޸ĳɹ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
						//�رյ�ǰ����
						UserModifyShopCar.this.dispose();
					} else {
						//���ݿ��쳣
						JOptionPane.showMessageDialog(UserModifyShopCar.this, "�޸�ʧ��", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					//��治����ʾ
					JOptionPane.showMessageDialog(UserModifyShopCar.this, "��治���޷��޸�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				//�����������ַǷ��ַ�
				JOptionPane.showMessageDialog(UserModifyShopCar.this, "������Ϸ�����", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	public void setData() {
		//��ȡԤ�޸ĵ�����
		DATA = UserShopCar.DATA;
		//�û�δѡ���ʻ�
		if (DATA.isEmpty()) {
			
		} else {
			ID = DATA.get(0);
			flowerName = DATA.get(1);
			flowerNum = DATA.get(2);
			
			//����Ϣ���������
			textID.setText(ID);
			textFlowerName.setText(flowerName);
			textFlowerNum.setText(flowerNum);
		}
		
	}
}
