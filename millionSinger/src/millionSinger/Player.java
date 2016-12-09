package millionSinger;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Player extends JFrame {
	
	// UI declaration
	private final JLabel NT_50,NT_40,NT_30,NT_20;
	private final JButton	million,btn1,btn2,btn3;
	//Color 
	private final Color color_usable_btn = Color.getHSBColor(0.60f,1.0f,0.8f);
	private final Color color_useless_btn = Color.getHSBColor(0.60f,0.3f,0.8f);
	// variable declaration
	private final String[] Category_List = {"百萬大歌星","周杰倫精選","KTV精選","最洗腦歌曲"};
	private final String[] Song_List_type0 = {"million1","million2","million3"};
	private final String[] Song_List_type1 = {"Song1","Song2","Song3"};
	private final String[] Song_List_type2 = {"Song4","Song5","Song6"};
	private final String[] Song_List_type3 = {"Song7","Song8","Song9"};
	private final String[][] Song_List = {Song_List_type0,Song_List_type1,Song_List_type2,Song_List_type3};
	// Game control variables
	private int[] Usable_Button_List = {0,1,1,1}; // to record which category can play
	private int selected_category = -1;
	private int song_index = -1;
	private int game_stage = -1;
	//ActionListener
	private final categoryHandler category_handler = new categoryHandler();
	private final selectSongHandler song_handler = new selectSongHandler();
	// User variables
	private int moneyEarned = 0;
	private int moneyOfStage[] = {20, 30, 40, 50}; 
	
	private void initState(){
		this.game_stage = 1;
	}
	
	public Player(){
		super("Million Singer");
		// initialize environment
		this.initState();
		
		this.setLayout(new GridBagLayout());
		//
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1; gbc.weighty = 1; // whether change size with frame
		gbc.fill = GridBagConstraints.NONE; // whether auto fill gbc
		gbc.anchor = GridBagConstraints.CENTER; // how to put the item in the container
		
		gbc.gridx = 0; gbc.gridy = 0; // location of gbc
		gbc.gridwidth = 10; gbc.gridheight = 3; // size of gbc , this is for title
		million = new JButton(Category_List[0]);
		million.setForeground(Color.WHITE);
		million.setOpaque(true);
		million.setFont(new Font("新細明體", Font.BOLD,48));
		million.setPreferredSize(new Dimension(600,60));
		million.setBackground(color_useless_btn);
		add(million,gbc);
		
		gbc.gridwidth = 4; gbc.gridheight = 3; //size for label element
		gbc.gridx = 0;
		
		gbc.gridy = 3;
		NT_50 = new JLabel("NT$50",SwingConstants.CENTER);
		NT_50.setOpaque(true);
		NT_50.setForeground(Color.WHITE);
		NT_50.setFont(new Font("Calibri", Font.BOLD,36));
		NT_50.setPreferredSize(new Dimension(200,40));
		NT_50.setBackground(Color.getHSBColor(0.54f,1.0f,0.8f));
		add(NT_50,gbc);
	
		gbc.gridy = 6;
		NT_40 = new JLabel("NT$40",SwingConstants.CENTER);
		NT_40.setOpaque(true);
		NT_40.setForeground(Color.WHITE);
		NT_40.setFont(new Font("Calibri", Font.BOLD,36));
		NT_40.setPreferredSize(new Dimension(200,40));
		NT_40.setBackground(Color.getHSBColor(0.54f,1.0f,0.8f));
		add(NT_40,gbc);
		
		gbc.gridy = 9;
		NT_30 = new JLabel("NT$30",SwingConstants.CENTER);
		NT_30.setOpaque(true);
		NT_30.setForeground(Color.WHITE);
		NT_30.setFont(new Font("Calibri", Font.BOLD,36));
		NT_30.setPreferredSize(new Dimension(200,40));
		NT_30.setBackground(Color.getHSBColor(0.54f,1.0f,0.8f));
		add(NT_30,gbc);
		
		gbc.gridy = 12;
		NT_20 = new JLabel("NT$30",SwingConstants.CENTER);
		NT_20.setOpaque(true);
		NT_20.setForeground(Color.WHITE);
		NT_20.setFont(new Font("Calibri", Font.BOLD,36));
		NT_20.setPreferredSize(new Dimension(200,40));
		NT_20.setBackground(Color.getHSBColor(0.54f,1.0f,0.8f));
		add(NT_20,gbc);
		
		gbc.gridwidth = 6 ; gbc.gridheight = 4;
		gbc.gridx = 4;
		
		gbc.gridy = 3;
		btn1 = new JButton(Category_List[1]);
		btn1.setOpaque(true);
		btn1.setForeground(Color.WHITE);
		btn1.setFont(new Font("新細明體", Font.BOLD,40));
		btn1.setPreferredSize(new Dimension(300,60));
		btn1.setBackground(color_useless_btn);
		add(btn1,gbc);
		
		gbc.gridy = 8;
		btn2 = new JButton(Category_List[2]);
		btn2.setOpaque(true);
		btn2.setForeground(Color.WHITE);
		btn2.setFont(new Font("新細明體", Font.BOLD,40));
		btn2.setPreferredSize(new Dimension(300,60));
		btn2.setBackground(color_useless_btn);
		add(btn2,gbc);
		
		gbc.gridy = 12;
		btn3 = new JButton(Category_List[3]);
		btn3.setOpaque(true);
		btn3.setForeground(Color.WHITE);
		btn3.setFont(new Font("新細明體", Font.BOLD,40));
		btn3.setPreferredSize(new Dimension(300,60));
		btn3.setBackground(color_useless_btn);
		add(btn3,gbc);
		
		//enable the button refer from Usable_Button_List
		enableButton(Usable_Button_List);
	}
	
	// handlers
	private class categoryHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if (e.getSource() == million)	selected_category = 0 ;
			if (e.getSource() == btn1) 		selected_category = 1 ;
			if (e.getSource() == btn2) 		selected_category = 2 ;
			if (e.getSource() == btn3)	 	selected_category = 3 ;
			btn1.setText(Song_List[selected_category][0]);
			btn2.setText(Song_List[selected_category][1]);
			btn3.setText(Song_List[selected_category][2]);
			
			btn1.setBackground(color_usable_btn);
			btn2.setBackground(color_usable_btn);
			btn3.setBackground(color_usable_btn);
			
			btn1.removeActionListener(category_handler);
			btn2.removeActionListener(category_handler);
			btn3.removeActionListener(category_handler);
			
			btn1.addActionListener(song_handler);
			btn2.addActionListener(song_handler);
			btn3.addActionListener(song_handler);
		}
	}	

	private class selectSongHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if (e.getSource() == btn1) song_index = 1;
			if (e.getSource() == btn2) song_index = 2;
			if (e.getSource() == btn3) song_index = 3;	
			
			btn1.setText(Category_List[1]);
			btn2.setText(Category_List[2]);
			btn3.setText(Category_List[3]);
			
			btn1.removeActionListener(song_handler);
			btn2.removeActionListener(song_handler);
			btn3.removeActionListener(song_handler);
			
			//How to create SongPlayer QAQ
			
			//enableButton(Usable_Button_List);
		}
	}
	
	// update state
	private void resetEnvirVaris(){
		this.selected_category = -1;
		this.song_index = -1;
	}
	
	// when new round, update stage
	private void updateState(int stage, int newMoney){
		this.game_stage += 1;
	}
	
	// change Usable_Button_List -> use it when a category finished
	public void setUsableButtonList(int index,int value ){
		this.Usable_Button_List[index] = value;
	}
	// refer form Usable_Button_List and enable the button 
	//	set different color for usable and useless button
	public void enableButton(int[] useable_button_list){
		if (useable_button_list[0] == 1){
			million.addActionListener(category_handler);
			million.setBackground(color_usable_btn);
		}
		else million.setBackground(color_useless_btn);
		
		if (useable_button_list[1] == 1) {
			btn1.addActionListener(category_handler);
			btn1.setBackground(color_usable_btn);
		}
		else btn1.setBackground(color_useless_btn);
		
		if (useable_button_list[2] == 1) {
			btn2.addActionListener(category_handler);
			btn2.setBackground(color_usable_btn);
		}
		else btn2.setBackground(color_useless_btn);
	
		if (useable_button_list[3] == 1) {
			btn3.addActionListener(category_handler);
			btn3.setBackground(color_usable_btn);
		}
		else btn3.setBackground(color_useless_btn);
	}
	// hide items in the Frame
	public void hidePlayer(){
		million.setVisible(false);
		btn1.setVisible(false);
		btn2.setVisible(false);
		btn3.setVisible(false);
		NT_50.setVisible(false);
		NT_40.setVisible(false);
		NT_30.setVisible(false);
		NT_20.setVisible(false);
	}
	// show items in the Frame
	public void showPlayer(){
		million.setVisible(true);
		btn1.setVisible(true);
		btn2.setVisible(true);
		btn3.setVisible(true);
		NT_50.setVisible(true);
		NT_40.setVisible(true);
		NT_30.setVisible(true);
		NT_20.setVisible(true);
	}
}
