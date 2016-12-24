package play_and_guess;

import java.io.File;

import com.sun.corba.se.spi.orb.StringPair;
import com.sun.istack.internal.FinalArrayList;
import com.sun.prism.paint.Stop;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sun.net.www.content.text.plain;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Platform;
import javafx.scene.control.TextField;


public class guess extends Application {
	private String song;
	private int guessTime;
	private static Stage stage;
	private boolean showStage = false;
	
    @Override
    public void start(final Stage stage) throws Exception {
    	if(!showStage) return;
    	guess.stage = stage;
        stage.setTitle("百萬大歌星");
        System.out.println(song);

        Platform.setImplicitExit(false);
        
        // 使用 StackPane 可使影片在中間播放，使用 Group 則會靠左上對齊
        StackPane root = new StackPane();
        // AnchorPane root = new AnchorPane();
        
        File file = new File("bin/media/MV/" + song + ".mp4");
        String MEDIA_URL = file.toURI().toString();

        Media media = new Media(MEDIA_URL); // 影片路徑
        MediaPlayer player = new MediaPlayer(media);
        MediaView mv = new MediaView(player);

        file = new File("bin/media/sound/Beep_Short.mp3");
        MEDIA_URL = file.toURI().toString();
        Media beep = new Media(MEDIA_URL); // 影片路徑
        MediaPlayer beep_sound = new MediaPlayer(beep);

        // mv.fitWidthProperty().set(1920); // 手動設定解析度 (寬度)
        // mv.fitHeightProperty().set(1080); // 手動設定解析度 (高度)

        // 自動設定解析度
        mv.fitWidthProperty().bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
        mv.fitHeightProperty().bind(Bindings.selectDouble(mv.sceneProperty(), "height"));

        mv.setPreserveRatio(true); // 視窗縮放的時候，必須維持比例

        root.getChildren().add(mv);

        // 警告符號
        Image img = new Image(guess.class.getResourceAsStream("../media/image/warning.png"));
        ImageView imgView = new ImageView(img);
        
        imgView.setFitHeight(32);
        imgView.setFitWidth(32);

        Button warning = new Button("", imgView);
        warning.setStyle("-fx-font-size: 32px;-fx-background-color: rgba(0,0,0,0)");
        StackPane.setMargin(warning, new Insets(10, 10, 10, 10));
        StackPane.setAlignment(warning, Pos.BOTTOM_LEFT);
        
        // root.getChildren().add(warning);

        Scene scene = new Scene(root, 600, 400, Color.BLACK); // 設定寬高，背景顏色為黑色
        root.setStyle("-fx-background-color: #000000;"); // 背景黑
        stage.setScene(scene);
        stage.setFullScreen(true); // 設定全螢幕
        stage.show();

        // player.setMute(true); // 靜音
        // player.setRate(10); // 播放速度快10倍

        // player.setCycleCount(MediaPlayer.INDEFINITE); // 循環播放 
        player.play(); // 開始播放


        // threadRound timer =  new threadRound();
        MV_time mv_thread = new MV_time(guessTime, root, mv, player);
        mv_thread.start();

        warning_time warning_thread = new warning_time(guessTime, root, warning, beep_sound);
        warning_thread.start();

//        stage.close();
    }
    public void setInfo(String song, int guessTime){
    	this.song = song;
    	this.guessTime = guessTime;
    }
    
    public static void stopGame(){
    	guess.stage.close();
    }
    
    public void stageWork(){
    	showStage = true;
    }
}

class MV_time extends timekeeper{
   private StackPane root;
   private MediaView mv;
   private MediaPlayer player;

   MV_time(int stoptime, StackPane root, MediaView mv, MediaPlayer player){
      super(stoptime);
      this.root = root;
      this.mv = mv;
      this.player = player;
   }
   @Override
   public boolean action(long second){
      System.out.println("目前秒數：" + second);
      if(second == super.getStoptime()){
        Platform.runLater(new Runnable() {
            @Override 
            public void run() {
                player.stop();
                root.getChildren().remove(mv);
                Answer_time answer = new Answer_time(60, root);
                answer.start();
            }
        });
         System.out.println("stop!!");
         return true;
      }
      return false;
   }
}

class warning_time extends timekeeper{
   private StackPane root;
   private Button warning;
   private MediaPlayer beep_sound;

   warning_time(int stoptime, StackPane root, Button warning, MediaPlayer beep_sound){
      super(stoptime);
      this.root = root;
      this.warning = warning;
      this.beep_sound = beep_sound;
   }
   @Override
   public boolean action(long second){
      if(super.getStoptime() - second < 10){
         Platform.runLater(new Runnable() {
             @Override 
             public void run() {
               if(root.getChildren().size() == 1){
                  beep_sound.play();
                  root.getChildren().add(warning);
               }
               else {
                  beep_sound.stop();
                  root.getChildren().remove(warning);
               }
             }
         });
         if(second == super.getStoptime()) return true;
      }
      return false;
   }
}

class Answer_time extends timekeeper{
    private StackPane root;
    private Button warning;
    private MediaPlayer beep_sound;
    private FlowPane flow = new FlowPane();
    private BorderPane borderLayout = new BorderPane();
    private long currentTime;
    
    // 歌曲資訊
    private String ans = "";
    private String lyrics = "";
    private int [] help;
    

    Text guessTime = new Text("60");


    Answer_time(int stoptime, StackPane root){
        super(stoptime);
        this.root = root;
        lyrics = "等一個人出現";
        
        // 放入計時器
        guessTime.setFill(Color.WHITE);
        guessTime.setStyle("-fx-font: 50px Tahoma;");
        StackPane.setMargin(guessTime, new Insets(50, 50, 10, 10));
        StackPane.setAlignment(guessTime, Pos.TOP_RIGHT);
        root.getChildren().add(guessTime);

        // flow.setPadding(new Insets(5, 5, 5, 5));
        flow.setVgap(10);
        flow.setHgap(10);

        Button[] buttons = new Button[lyrics.length()];

        for (int i = 0; i < lyrics.length(); i++) {
            Image img = new Image(guess.class.getResourceAsStream("../media/image/star.png"));
            ImageView imgView = new ImageView(img);
            
            imgView.setFitHeight(32);
            imgView.setFitWidth(32);
            buttons[i] = new Button("", imgView);
            buttons[i].setStyle("-fx-font-size: 32px;-fx-background-color: #000000;");
            flow.getChildren().add(buttons[i]);
        }
        // StackPane.setMargin(flow, new Insets(300, 0, 0, 200));
        // flow.setStyle("-fx-border-color: white");
        flow.setMaxHeight(370);
        flow.setMaxWidth(88 * lyrics.length());
        flow.setAlignment(Pos.CENTER);

        // borderLayout.setCenter(flow);
        // root.getChildren().add(borderLayout);
        root.getChildren().add(flow);

        // 填答案區
        FlowPane ans_flow = new FlowPane();
        ans_flow.setAlignment(Pos.CENTER);
        TextField ansField = new TextField();
        ansField.setMaxWidth(40 * lyrics.length());
        ans_flow.getChildren().add(ansField);

        Button ans_send = new Button("送出");

        ans_send.setOnAction(e -> { //用lambda語法省略實作EventHandler介面
            System.out.println("輸入的答案：" + ansField.getText());
            ans = ansField.getText();
            checkAns();
        });

        ans_flow.setMargin(ans_send, new Insets(0, 0, 0, 30));   
        ans_flow.getChildren().add(ans_send);
        flow.getChildren().add(ans_flow);
        // root.getChildren().add(ans);

        // 提示區
        Image img_help0 = new Image(guess.class.getResourceAsStream("../media/image/help0.png"));
        ImageView imgView_help0 = new ImageView(img_help0);
        imgView_help0.setFitHeight(180);
        imgView_help0.setFitWidth(180);
        Button[] help = new Button[3];
        help[0] = new Button("", imgView_help0);
        help[0].setStyle("-fx-background-color: #000000;");
        help[0].setOnAction(e -> { //用lambda語法省略實作EventHandler介面
            root.getChildren().remove(flow);
            flow.getChildren().clear();
            // for(int i = 0; i < 3; i ++){

            // }
            Button help1 = new Button("三選一");
            root.getChildren().add(help1);
        });
        StackPane.setMargin(help[0], new Insets(10, 10, 50, 100));
        StackPane.setAlignment(help[0], Pos.BOTTOM_LEFT);
        root.getChildren().add(help[0]);


        Image img_help1 = new Image(guess.class.getResourceAsStream("../media/image/help1.png"));
        ImageView imgView_help1 = new ImageView(img_help1);
        imgView_help1.setFitHeight(180);
        imgView_help1.setFitWidth(180);
        help[1] = new Button("", imgView_help1);
        help[1].setStyle("-fx-background-color: #000000;");
        help[1].setOnAction(e -> { //用lambda語法省略實作EventHandler介面
            flow.getChildren().clear();
            for (int i = 0; i < lyrics.length(); i++) {
                Image img = new Image(guess.class.getResourceAsStream("../media/image/star.png"));
                ImageView imgView = new ImageView(img);
                
                imgView.setFitHeight(32);
                imgView.setFitWidth(32);
                if(i == 5){
                    buttons[i] = new Button("現");
                }
                else buttons[i] = new Button("", imgView);

                buttons[i].setStyle("-fx-font-size: 32px;-fx-background-color: #000000;-fx-text-fill: white;");
                flow.getChildren().add(buttons[i]);
            }
            flow.getChildren().add(ans_flow);
        });
        StackPane.setMargin(help[1], new Insets(10, 10, 50, 0));
        StackPane.setAlignment(help[1], Pos.BOTTOM_CENTER);
        root.getChildren().add(help[1]);


        Image img_help2 = new Image(guess.class.getResourceAsStream("../media/image/help2.png"));
        ImageView imgView_help2 = new ImageView(img_help2);
        imgView_help2.setFitHeight(180);
        imgView_help2.setFitWidth(180);
        help[2] = new Button("", imgView_help2);
        help[2].setStyle("-fx-background-color: #000000;");
        help[2].setOnAction(e -> { //用lambda語法省略實作EventHandler介面

        });
        StackPane.setMargin(help[2], new Insets(10, 100, 50, 10));
        StackPane.setAlignment(help[2], Pos.BOTTOM_RIGHT);
        root.getChildren().add(help[2]);
    }

    @Override
    public boolean action(long second){
    	currentTime = second;

        Platform.runLater(new Runnable() {
            @Override 
            public void run() {
                guessTime.setText(String.valueOf(60 - second));
            }
        });
        if(60 == second){
            Platform.runLater(new Runnable() {
                @Override 
                public void run() {
                    checkAns();
                }
            });
        }
        if(super.getStoptime() + 2 ==  second){
            Platform.runLater(new Runnable() {
                @Override 
                public void run() {
                	guess.stopGame();
                }
            });
        	return true;
        }
        return false;
    }
    public void checkAns(){
        if(ans.equals(lyrics)){
            root.getChildren().clear();
            Text checkAns = new Text("成功");
            checkAns.setStyle("-fx-font: 300px Tahoma;");
            checkAns.setFill(Color.WHITE);
            root.getChildren().add(checkAns);
        }
        else{
            root.getChildren().clear();
            Text checkAns = new Text("失敗");
            checkAns.setStyle("-fx-font: 300px Tahoma;");
            checkAns.setFill(Color.WHITE);
            root.getChildren().add(checkAns); 
        }
        super.setStoptime(currentTime + 1);
    }
}