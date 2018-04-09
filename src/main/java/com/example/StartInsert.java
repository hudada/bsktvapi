package com.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

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
		File file = new File(location);
		File[] files = file.listFiles();
		for (File file2 : files) {
			try {  
		        AudioFileFormat aff = AudioSystem.getAudioFileFormat(file2);  
		        Map props = aff.properties();  
		        long total = 0;
				if (props.containsKey("duration")) {  
		            total = (long) Math.round((((Long) props.get("duration")).longValue()) / 1000);  
		        }  
				long xx = total/1000;
		    } catch (Exception e) {  
		        e.printStackTrace();  
		    }  
		}
	}

}
