package com.example.apicontroller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.bean.BaseBean;
import com.example.bean.MusicBean;
import com.example.bean.SongBean;
import com.example.bean.UserBean;
import com.example.dao.MusicDao;
import com.example.dao.SongDao;
import com.example.dao.UserDao;
import com.example.utils.ResultUtils;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/music")
public class ApiMusicController {

	@Autowired
	private UserDao userDao;
	@Autowired
	private SongDao songDao;
	@Autowired
	private MusicDao musicDao;
	
	@Value("${bs.bzPath}")
	private String location;
	@Autowired
	private ResourceLoader resourceLoader;

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public BaseBean<List<MusicBean>> list(HttpServletRequest request) {
		List<MusicBean> beans = musicDao.findAll();
		return ResultUtils.resultSucceed(beans);
	}
	
	@RequestMapping(value = "/word", method = RequestMethod.POST)
	public BaseBean<MusicBean> word(HttpServletRequest request) {
		String name= request.getParameter("name");
		MusicBean beans = musicDao.findByName(name);
		return ResultUtils.resultSucceed(beans);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/bz/{filename:.+}")
	public ResponseEntity<?> getFile(@PathVariable String filename) {
		try {
			return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(location, filename).toString()));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
}
