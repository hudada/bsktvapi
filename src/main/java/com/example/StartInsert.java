package com.example;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.dao.MusicDao;
import com.example.dao.UserDao;

@Component
@Order(value = 1)
public class StartInsert implements CommandLineRunner {

	@Autowired
	private MusicDao musicDao;
	@Value("${bs.bzPath}")
	private String location;

	@Override
	public void run(String... arg0) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("mm:ss");
		String xx = format.format(195*1000);
		File file = new File(location);
		File[] files = file.listFiles();
		for (File file2 : files) {
			String aa = file2.getName();
			long tt = file2.length();
			try {  
		        MP3File f = (MP3File)AudioFileIO.read(file2);  
		        MP3AudioHeader audioHeader = (MP3AudioHeader)f.getAudioHeader();  
		        int ii = audioHeader.getTrackLength();
		        System.out.println(audioHeader.getTrackLength());     
		    } catch(Exception e) {  
		        e.printStackTrace();  
		    } 
		}
	}

}
