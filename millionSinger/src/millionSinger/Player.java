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
	
	private final JLabel title,NT_50,NT_40,NT_30,NT_20;
	private final JButton btn1,btn2,btn3;
	
	private final String[] Song_List_type1 = {"Song1","Song2","Song3"};
	private final String[] Song_List_type2 = {"Song4","Song5","Song6"};
	private final String[] Song_List_type3 = {"Song7","Song8","Song9"};
	private final String[][] Song_List = {Song_List_type1,Song_List_type2,Song_List_type3};
	
	//Game control variables
	private int game_type = -1;
	private int song_index = -1;
	public Player(){
		super("Million Singer");
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		// 
		gbc.weightx = 1; gbc.weighty = 1; // whether change size with frame
		gbc.fill = GridBagConstraints.NONE; // whether auto fill gbc
		gbc.anchor = GridBagConstraints.CENTER; // how to put the item in the container
		
		gbc.gridx = 0; gbc.gridy = 0; // location of gbc
		gbc.gridwidth = 10; gbc.gridheight = 3; // size of gbc , this is for title
		title = new JLabel("百萬大歌星",SwingConstants.CENTER);
		title.setForeground(Color.WHITE);
		title.setOpaque(true);
		title.setFont(new Font("微軟正黑體", Font.BOLD,48));
		title.setPreferredSize(new Dimension(600,60));
		title.setBackground(Color.getHSBColor(0.54f,1.0f,0.8f));
		add(title,gbc);
		
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
		btn1 = new JButton("周杰倫精選");
		btn1.setOpaque(true);
		btn1.setForeground(Color.WHITE);
		btn1.setFont(new Font("微軟正黑體", Font.BOLD,40));
		btn1.setPreferredSize(new Dimension(300,60));
		btn1.setBackground(Color.getHSBColor(0.80f,1.0f,0.8f));
		add(btn1,gbc);
		
		gbc.gridy = 8;
		btn2 = new JButton("KTV精選");
		btn2.setOpaque(true);
		btn2.setForeground(Color.WHITE);
		btn2.setFont(new Font("微軟正黑體", Font.BOLD,40));
		btn2.setPreferredSize(new Dimension(300,60));
		btn2.setBackground(Color.getHSBColor(0.80f,1.0f,0.8f));
		add(btn2,gbc);
		
		gbc.gridy = 12;
		btn3 = new JButton("最洗腦歌曲");
		btn3.setOpaque(true);
		btn3.setForeground(Color.WHITE);
		btn3.setFont(new Font("微軟正黑體", Font.BOLD,40));
		btn3.setPreferredSize(new Dimension(300,60));
		btn3.setBackground(Color.getHSBColor(0.80f,1.0f,0.8f));
		add(btn3,gbc);
	
		btn1.addActionListener(new btn1Handler());
		btn2.addActionListener(new btn2Handler());
		btn3.addActionListener(new btn3Handler());
	}
	
	private class btn1Handler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if (game_type == -1 ){
				game_type = 0 ;
				btn1.setText(Song_List[game_type][0]);
				btn2.setText(Song_List[game_type][1]);
				btn3.setText(Song_List[game_type][2]);
			}
			else{
				song_index = 0 ;
				System.out.print(song_index);
			}
		}	
	}
	
	private class btn2Handler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if (game_type == -1 ){
				game_type = 1 ;
				btn1.setText(Song_List[game_type][0]);
				btn2.setText(Song_List[game_type][1]);
				btn3.setText(Song_List[game_type][2]);
			}
			else{
				song_index = 1 ;
				System.out.print(song_index);
			}
		}	
	}
	
	private class btn3Handler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if (game_type == -1 ){
				game_type = 2 ;
				btn1.setText(Song_List[game_type][0]);
				btn2.setText(Song_List[game_type][1]);
				btn3.setText(Song_List[game_type][2]);
			}
			else{
				song_index = 2 ;
				System.out.print(song_index);
			}
		}	
	}
}
