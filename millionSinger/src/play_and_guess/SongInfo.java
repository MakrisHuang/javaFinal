package play_and_guess;

import org.json.*;

import java.io.*;
import java.util.Scanner;


public class SongInfo{
	private static JSONObject songinfo;
	
	public SongInfo() {
		try {
			Scanner scanner = new Scanner(new File("bin/media/song.json"));
			String content = scanner.useDelimiter("\\Z").next();
			System.out.println(content);
			
			songinfo = new JSONObject(content);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static JSONObject getSonginfo(){
		return songinfo;
	}


}
