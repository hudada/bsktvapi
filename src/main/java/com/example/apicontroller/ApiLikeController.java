package com.example.apicontroller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.example.bean.CommentBean;
import com.example.bean.LikeBean;
import com.example.bean.SongBean;
import com.example.bean.UserBean;
import com.example.dao.LikeDao;
import com.example.dao.SongDao;
import com.example.dao.UserDao;
import com.example.utils.ResultUtils;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/like")
public class ApiLikeController {

	@Autowired
	private UserDao userDao;
	@Autowired
	private SongDao songDao;
	@Autowired
	private LikeDao likeDao;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public BaseBean<LikeBean> add(HttpServletRequest request) {
		String uid= request.getParameter("uid");
		String likeUid= request.getParameter("likeUid");
		LikeBean bean = new LikeBean();
		bean.setUid(Long.parseLong(uid));
		bean.setLikeUid(Long.parseLong(likeUid));
		bean.setType(0);
		likeDao.save(bean);
		LikeBean bean1 = new LikeBean();
		bean1.setUid(Long.parseLong(likeUid));
		bean1.setLikeUid(Long.parseLong(uid));
		bean1.setType(1);
		likeDao.save(bean1);
		return ResultUtils.resultSucceed("");
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public BaseBean<LikeBean> find(HttpServletRequest request) {
		String uid= request.getParameter("uid");
		String likeUid= request.getParameter("likeUid");
		LikeBean bean = likeDao.findByUidAndLikeUid(Long.parseLong(uid),
				Long.parseLong(likeUid));
		return ResultUtils.resultSucceed(bean);
	}
	
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public BaseBean<LikeBean> del(HttpServletRequest request) {
		String uid= request.getParameter("uid");
		String likeUid= request.getParameter("likeUid");
		try {
			likeDao.delete(likeDao.findOneByUidAndLikeUid(Long.parseLong(uid),
					Long.parseLong(likeUid), 0).getId());
			likeDao.delete(likeDao.findOneByUidAndLikeUid(Long.parseLong(likeUid),
					Long.parseLong(uid), 1).getId());
			return ResultUtils.resultSucceed("");
		}catch (Exception e) {
			return ResultUtils.resultError("");
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public BaseBean<List<SongBean>> list(HttpServletRequest request) {
		String uid= request.getParameter("uid");
		List<LikeBean> list = likeDao.findByUid(Long.parseLong(uid));
		List<SongBean> reList = new ArrayList<>();
		for (LikeBean likeBean : list) {
			List<SongBean> songBeans = songDao.findByUid(likeBean.getLikeUid());
			for (SongBean songBean : songBeans) {
				songBean.setUname(userDao.findOne(songBean.getUid()).getUserName());
			}
			reList.addAll(songBeans);
		}
		return ResultUtils.resultSucceed(reList);
	}

	
}
