package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
import com.example.bean.MusicBean;

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
			String[] names = file2.getName().split("\\.");
			if (names.length < 2) {
				return;
			}
			String name = names[0];
			String type = names[1];
			if (type.equals("mp3")) {
				if (musicDao.findByName(name) == null) {
					try {
						MP3File f = (MP3File) AudioFileIO.read(file2);
						MP3AudioHeader audioHeader = (MP3AudioHeader) f.getAudioHeader();
						Long length = (long) (audioHeader.getTrackLength() * 1000);
						MusicBean bean = new MusicBean();
						bean.setLength(length);
						bean.setName(name);
						bean.setAddr("/bz/" + file2.getName());
						musicDao.save(bean);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (type.equals("txt")) {
				MusicBean bean = musicDao.findByName(name);
				if (bean != null) {
					BufferedReader bf = new BufferedReader(new FileReader(file2));
					String content = "";
					StringBuilder sb = new StringBuilder();
					while (content != null) {
						content = bf.readLine();
						if (content == null) {
							break;
						}
						sb.append(content.trim() + "\n");
					}
					bf.close();
					bean.setWord(sb.toString());
					musicDao.save(bean);
				}
			}
		}
	}

}
