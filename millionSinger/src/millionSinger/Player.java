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

import com.sun.media.jfxmedia.events.NewFrameEvent;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import play_and_guess.guess;
import sun.net.www.content.text.plain;

import java.util.concurrent.*;

public class Player extends JFrame {
	
	// UI declaration
	private JLabel NT_30,NT_20,NT_10,NT_0;
	private JButton	million,btn1,btn2,btn3;
	//Color 
	private final Color color_usable_btn = Color.getHSBColor(0.60f,1.0f,0.8f);
	private final Color color_useless_btn = Color.getHSBColor(0.60f,0.3f,0.8f);
	private final Color color_NT_label = Color.getHSBColor(0.54f,1.0f,0.8f);
	private final Color color_selected_NT_label = Color.pink;
	
	// variable declaration
	private final String[] Category_List = {"百萬大歌星","周杰倫精選","KTV精選","最洗腦歌曲"};
	private final String[] Song_List_type0 = {"背叛","你，好不好","在我的歌聲裡"};
	private final String[] Song_List_type1 = {"告白氣球","淘汰","聽見下雨的聲音"};
	private final String[] Song_List_type2 = {"你的愛是什麼形狀","修煉愛情","小幸運"};
	private final String[] Song_List_type3 = {"ppap","念你","姊姊"};
	private final String[][] Song_List = {Song_List_type0,Song_List_type1,Song_List_type2,Song_List_type3};
	
	// Game control variables
	private int[] Usable_Category = {1,1,1}; // 1代表該category可以使用, 0則代表已經選過
	private int selected_category = -1;
	private int song_index = -1;
	private int game_stage = -1;
	
	//ActionListener
	private final categoryHandler category_handler = new categoryHandler();
	private final selectSongHandler song_handler = new selectSongHandler();
	
	// User variables
	private int moneyEarnedIndex = 0;	// max = 3
	private int moneyOfStage[] = {0, 10, 20, 30}; 
	
	private void initGameStage(){
		this.game_stage = 1;
	}
	
	public Player(){
		super("Million Singer");
		this.initGameStage();
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1; gbc.weighty = 1; // whether change size with frame
		gbc.fill = GridBagConstraints.NONE; // whether auto fill gbc
		gbc.anchor = GridBagConstraints.CENTER; // how to put the item in the container
		
		gbc.gridx = 0; gbc.gridy = 0; // location of gbc
		gbc.gridwidth = 10; gbc.gridheight = 3; // size of gbc , this is for title
		million = new JButton(Category_List[0]);
		million.setFont(new Font("新細明體", Font.BOLD,48));
		million.setPreferredSize(new Dimension(600,60));
		million.setBackground(color_useless_btn);
		add(million,gbc);
		
		gbc.gridwidth = 4; gbc.gridheight = 3; //size for label element
		gbc.gridx = 0;
		
		gbc.gridy = 3;
		NT_30 = new JLabel(String.format("NT$%d", moneyOfStage[3]),SwingConstants.CENTER);
		NT_30.setOpaque(true);
		NT_30.setForeground(Color.WHITE);
		NT_30.setFont(new Font("Calibri", Font.BOLD,36));
		NT_30.setPreferredSize(new Dimension(200,40));
		NT_30.setBackground(color_NT_label);
		add(NT_30,gbc);
	
		gbc.gridy = 6;
		NT_20 = new JLabel(String.format("NT$%d", moneyOfStage[2]),SwingConstants.CENTER);
		NT_20.setOpaque(true);
		NT_20.setForeground(Color.WHITE);
		NT_20.setFont(new Font("Calibri", Font.BOLD,36));
		NT_20.setPreferredSize(new Dimension(200,40));
		NT_20.setBackground(color_NT_label);
		add(NT_20,gbc);
		
		gbc.gridy = 9;
		NT_10 = new JLabel(String.format("NT$%d", moneyOfStage[1]),SwingConstants.CENTER);
		NT_10.setOpaque(true);
		NT_10.setForeground(Color.WHITE);
		NT_10.setFont(new Font("Calibri", Font.BOLD,36));
		NT_10.setPreferredSize(new Dimension(200,40));
		NT_10.setBackground(color_NT_label);
		add(NT_10,gbc);
		
		gbc.gridy = 12;
		NT_0 = new JLabel(String.format("NT$%d", moneyOfStage[0]),SwingConstants.CENTER);
		NT_0.setOpaque(true);
		NT_0.setForeground(Color.WHITE);
		NT_0.setFont(new Font("Calibri", Font.BOLD,36));
		NT_0.setPreferredSize(new Dimension(200,40));
		NT_0.setBackground(color_selected_NT_label);
		add(NT_0,gbc);
		
		gbc.gridwidth = 6 ; gbc.gridheight = 4;
		gbc.gridx = 4;
		
		gbc.gridy = 3;
		btn1 = new JButton(Category_List[1]);
		btn1.setFont(new Font("新細明體", Font.BOLD,40));
		btn1.setPreferredSize(new Dimension(300,60));
		btn1.setBackground(color_useless_btn);
		add(btn1,gbc);
		
		gbc.gridy = 8;
		btn2 = new JButton(Category_List[2]);
		btn2.setFont(new Font("新細明體", Font.BOLD,40));
		btn2.setPreferredSize(new Dimension(300,60));
		btn2.setBackground(color_useless_btn);
		add(btn2,gbc);
		
		gbc.gridy = 12;
		btn3 = new JButton(Category_List[3]);
		btn3.setFont(new Font("新細明體", Font.BOLD,40));
		btn3.setPreferredSize(new Dimension(300,60));
		btn3.setBackground(color_useless_btn);
		add(btn3,gbc);
		
		// 更新Button是否可選取
		refreshBtnVisibility();
	}
	
	// handlers
	private class categoryHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if (e.getSource() == btn1) 		selected_category = 0 ;
			if (e.getSource() == btn2) 		selected_category = 1 ;
			if (e.getSource() == btn3)	 	selected_category = 2 ;
			System.out.printf("Selected category: %d\n", selected_category);
			
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
			
			// 因為要選歌曲，故使每個Button都能夠被按
			btn1.setEnabled(true);
			btn2.setEnabled(true);
			btn3.setEnabled(true);
		}
	}	

	private class selectSongHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if (e.getSource() == btn1) song_index = 0;
			if (e.getSource() == btn2) song_index = 1;
			if (e.getSource() == btn3) song_index = 2;	
			
			triggerMVPlayer(song_index);
		}
	}
	
	public void triggerMVPlayer(int song_index){
		// new the MV Player here
		System.out.println(String.format("Selected song: %d", song_index));
		GuessRunnable guessRunnable = new GuessRunnable();
		FutureTask<Boolean> futureTask = new FutureTask<Boolean>(guessRunnable);
		
		Platform.runLater(futureTask);
		try {
			Boolean answerResult = futureTask.get();
			if (answerResult){
				System.out.println("Right answer");
			}else{
				System.out.println("Wrong answer");
			}
			
			updateState(answerResult);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public class GuessRunnable implements Callable<Boolean>{
		@Override
		public Boolean call(){
			Boolean result = false;
			try {
    			guess game = new guess();
    			game.stageWork();
    			game.setInfo(Song_List[selected_category][song_index], 20); // 傳歌名＋停止秒數
    			game.start(new Stage());
    			
    			result = guess.getAnswerResult();
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
			return result;
		}
	}
	
	// when new round, update stage
	private void updateState(boolean wonTheGame){
		System.out.println("updateState() begin");
		this.game_stage += 1;
		System.out.printf("New stage: %d\n", this.game_stage);
		
		// 更新錢的數量
		if (wonTheGame && (moneyEarnedIndex + 1) <= moneyOfStage.length)	
			moneyEarnedIndex += 1;
		
		// 選定目前所獲得的錢
		switch (moneyEarnedIndex){
			case 0:
				NT_30.setBackground(color_NT_label);
				NT_20.setBackground(color_NT_label);
				NT_10.setBackground(color_NT_label);
				NT_0.setBackground(color_selected_NT_label);
				break;
			case 1:
				NT_30.setBackground(color_NT_label);
				NT_20.setBackground(color_NT_label);
				NT_10.setBackground(color_selected_NT_label);
				NT_0.setBackground(color_NT_label);
				break;
			case 2: 
				NT_30.setBackground(color_NT_label);
				NT_20.setBackground(color_selected_NT_label);
				NT_10.setBackground(color_NT_label);
				NT_0.setBackground(color_NT_label);
				break;
			case 3:
				NT_30.setBackground(color_selected_NT_label);
				NT_20.setBackground(color_NT_label);
				NT_10.setBackground(color_NT_label);
				NT_0.setBackground(color_NT_label);
				break;
			default:
				break;
		}
		
		// after finish the game, reset all variables
		resetEnvirVaris();
		refreshBtnVisibility();
		System.out.println("updateState() end");
	}
	
	// update state
	private void resetEnvirVaris(){
		// 使選過的類別不能再選
		setUsableButtonList(this.selected_category);
		
		this.selected_category = -1;
		this.song_index = -1;
		
		// 重新設定類別名稱
		btn1.setText(Category_List[1]);
		btn2.setText(Category_List[2]);
		btn3.setText(Category_List[3]);
		
		// 移除song_handler
		btn1.removeActionListener(song_handler);
		btn2.removeActionListener(song_handler);
		btn3.removeActionListener(song_handler);
		
		// 重新加入category_handler
		btn1.addActionListener(category_handler);
		btn2.addActionListener(category_handler);
		btn3.addActionListener(category_handler);
	}
	
	// 當某個歌曲種類選過後，就不能再選，此時category的value設為-1
	public void setUsableButtonList(int index){
		this.Usable_Category[index] = 0;
	}
	
	// refer form Usable_Button_List and enable the button 
	//	set different color for usable and useless button
	public void refreshBtnVisibility(){
		if (this.Usable_Category[0] == 1) {
			btn1.addActionListener(category_handler);
			btn1.setBackground(color_usable_btn);
		}
		else btn1.setEnabled(false);
		
		if (this.Usable_Category[1] == 1) {
			btn2.addActionListener(category_handler);
			btn2.setBackground(color_usable_btn);
		}
		else btn2.setEnabled(false);
	
		if (this.Usable_Category[2] == 1) {
			btn3.addActionListener(category_handler);
			btn3.setBackground(color_usable_btn);
		}
		else btn3.setEnabled(false);
	}
}
