package play_and_guess;

import java.util.*;

class timekeeper extends Thread{
   private int stopTime;
   private boolean reverse = false;
   private boolean timeUp = false;

   timekeeper(int stopTime){
      this.stopTime = stopTime;
   }

   public void setReverse(){
      reverse = true;
   }

   public void run(){

      String date;
      Date beginTime = new Date();
      while(true){
         long during = new Date().getTime() - beginTime.getTime();
         Date duringTime = new Date();
         duringTime.setTime(during);

         if(this.action(during / 1000)) break;

         try{
            Thread.sleep(1000);
            //使用sleep()方法使程式休眠1分鐘，那麼每一秒獲得系統時間並顯示，從而實現電子鐘的功能
            // System.out.println(i);
         }
         catch(Exception e){
         }
      }
   }
   public boolean action(long second){
      System.out.println(second);
      if(second == stopTime){
         return true;
      }
      return false;
   }

   public int getStoptime(){
      return stopTime;
   }
   public void setStoptime(long time){
	   stopTime = (int)time;
   }
}

