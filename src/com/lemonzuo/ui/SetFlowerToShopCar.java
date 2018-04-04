package com.lemonzuo.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.lemonzuo.dao.UserDao;

@SuppressWarnings("serial")
public class SetFlowerToShopCar extends JFrame {

	private JPanel contentPane;
	private JTextField textID;
	private JTextField textName;
	private JTextField textNum;
	public static String FLOWER_NAME;
	public static String FLOWER_ID;
	public static String FLOWER_NUM;
	public static String USER_NAME = UserIndex.USER_NAME;

	/**
	 * Create the frame.
	 */
	public SetFlowerToShopCar() {

		//����
		setTitle("\u6DFB\u52A0\u8D2D\u7269\u8F66");
		//Ĭ�Ϲرշ�ʽ
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 403, 320);
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
		
		//�����ǩ
		JLabel labTitle = new JLabel("\u6DFB\u52A0\u5230\u8D2D\u7269\u8F66");
		labTitle.setForeground(new Color(255, 51, 153));
		labTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labTitle.setFont(new Font("����", Font.PLAIN, 18));
		labTitle.setBounds(156, 10, 108, 39);
		contentPane.add(labTitle);
		
		//ID��ǩ
		JLabel lbFlowerId = new JLabel("\u9C9C\u82B1ID:");
		lbFlowerId.setHorizontalAlignment(SwingConstants.CENTER);
		lbFlowerId.setFont(new Font("����", Font.PLAIN, 16));
		lbFlowerId.setBounds(66, 53, 65, 26);
		contentPane.add(lbFlowerId);
		
		//������ǩ
		JLabel labFlowerName = new JLabel("\u9C9C\u82B1\u540D\u5B57");
		labFlowerName.setHorizontalAlignment(SwingConstants.CENTER);
		labFlowerName.setFont(new Font("����", Font.PLAIN, 16));
		labFlowerName.setBounds(66, 89, 65, 26);
		contentPane.add(labFlowerName);
		
		//������ǩ
		JLabel labNum = new JLabel("\u6DFB\u52A0\u6570\u91CF");
		labNum.setHorizontalAlignment(SwingConstants.CENTER);
		labNum.setFont(new Font("����", Font.PLAIN, 16));
		labNum.setBounds(66, 137, 65, 26);
		contentPane.add(labNum);
		
		//�ύ��ť
		JButton btnSubmit = new JButton("\u786E\u8BA4\u6DFB\u52A0");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//��ȡ������Ŀ
				String num = textNum.getText();
				UserDao userDao = new UserDao();
				//��ȡ����
				FLOWER_NAME = UserIndex.FLOWER_NAME;
				//��ȡ������Ŀ
				FLOWER_NUM = UserIndex.FLOWER_NUM;
				String time = userDao.getTime();
				//��ȡʱ��
				if("".equals(num) || num == null) {
					JOptionPane.showMessageDialog(SetFlowerToShopCar.this, "����д����", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				} else if(userDao.matchNum(num)) {
					//Ԥ������������ݿ�����бȶ�
					if(Integer.parseInt(FLOWER_NUM) >= Integer.parseInt(num)) {
						//��������
						boolean flag = userDao.setToShopCar(USER_NAME, FLOWER_NAME, num, time);
						if( flag ) {
							JOptionPane.showMessageDialog(SetFlowerToShopCar.this, "��ӳɹ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
							SetFlowerToShopCar.this.dispose();
						} else {
							JOptionPane.showMessageDialog(SetFlowerToShopCar.this, "����ʧ��", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
						}
					} else {
						//�����Ŀ���ڿ����
						JOptionPane.showMessageDialog(SetFlowerToShopCar.this, "��治���޷����", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
					}
					
				} else {
					//�����ַ�
					JOptionPane.showMessageDialog(SetFlowerToShopCar.this, "��������Ϊ������", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		});
		btnSubmit.setBounds(67, 192, 93, 39);
		contentPane.add(btnSubmit);
		
		//���ð�ť
		JButton btnReSet = new JButton("\u91CD\u7F6E\u6570\u91CF");
		btnReSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textNum.setText("");
			}
		});
		btnReSet.setBounds(216, 192, 93, 39);
		contentPane.add(btnReSet);
		
		//���ó�ʼ����
		FLOWER_NAME = UserIndex.FLOWER_NAME;
		FLOWER_ID = UserIndex.FLOWER_ID;
		
		//ID��ʾ��
		textID = new JTextField();
		textID.setBounds(156, 57, 153, 21);
		textID.setMargin(new Insets(0,5,0,0));
		if(FLOWER_ID != null) {
			textID.setText(FLOWER_ID);
		} else {
			textID.setText("");
		}
		textID.setBackground(Color.WHITE);
		textID.setEditable(false);
		contentPane.add(textID);
		textID.setColumns(10);
		
		//������ʾ��
		textName = new JTextField();
		textName.setColumns(10);
		textName.setBounds(156, 93, 153, 21);
		contentPane.add(textName);
		//�����ڱ߾�
		textName.setMargin(new Insets(0,5,0,0));
		if(FLOWER_NAME != null) {
			textName.setText(FLOWER_NAME);
		} else {
			textName.setText("");
		}
		textName.setBackground(Color.white);
		//��ֹ�༭
		textName.setEditable(false);
		
		textNum = new JTextField();
		textNum.setColumns(10);
		//����ڱ߾�
		textNum.setMargin(new Insets(0,5,0,0));
		textNum.setText("");
		textNum.setBounds(156, 141, 153, 21);
		contentPane.add(textNum);
	}
}
