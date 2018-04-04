package com.lemonzuo.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings({ "serial", "unused" })
//TODO AdminMain
public class AdminMain extends JFrame {

	private JPanel contentPane;
	public static String USER_NAME = Login.USER_NAME;


	/**
	 * Create the frame.
	 */
	public AdminMain() {
		

		//����
		
		setTitle("�������ĺ�̨����ҳ��");
		//���ڹرշ�ʽ
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 300);
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
		JLabel labTitle = new JLabel("�������ĵ�ҹ���");
		labTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labTitle.setForeground(new Color(102, 204, 255));
		labTitle.setFont(new Font("����", Font.PLAIN, 18));
		labTitle.setBounds(10, 22, 264, 31);
		contentPane.add(labTitle);
		
		//��������
		JButton btnOrderControl = new JButton("��������");
		btnOrderControl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO ��������
				new AdminOrderControl();
			}
		});
		btnOrderControl.setBounds(89, 73, 109, 41);
		contentPane.add(btnOrderControl);
		
		//�ʻ�����
		JButton btnGoodsControl = new JButton("�ʻ�����");
		btnGoodsControl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//ҳ����ת�ʻ�����ҳ��
				new AdminFlowerControl();
				//�رյ�ǰ����
//				AdminMain.this.dispose();
			}
		});
		btnGoodsControl.setBounds(89, 124, 109, 41);
		contentPane.add(btnGoodsControl);
		
		//�˳�ϵͳ
		JButton btnExit = new JButton("�˳�ϵͳ");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AdminMain.this.dispose();
			}
		});
		btnExit.setBounds(89, 186, 109, 41);
		contentPane.add(btnExit);
	}
}
