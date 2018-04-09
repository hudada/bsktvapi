package com.example.apicontroller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import com.example.bean.SongBean;
import com.example.bean.UserBean;
import com.example.dao.CommentDao;
import com.example.dao.SongDao;
import com.example.dao.UserDao;
import com.example.utils.ResultUtils;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/comment")
public class ApiCommentController {

	@Autowired
	private UserDao userDao;
	@Autowired
	private SongDao songDao;
	@Autowired
	private CommentDao commentDao;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public BaseBean<CommentBean> add(HttpServletRequest request) {
		String uid= request.getParameter("uid");
		String sid= request.getParameter("sid");
		String msg= request.getParameter("msg");
		CommentBean bean = new CommentBean();
		bean.setUid(Long.parseLong(uid));
		bean.setSid(Long.parseLong(sid));
		bean.setMsg(msg);
		bean.setTime(System.currentTimeMillis());
		return ResultUtils.resultSucceed(commentDao.save(bean));
	}


	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public BaseBean<List<CommentBean>> list(HttpServletRequest request) {
		String sid= request.getParameter("sid");
		List<CommentBean> list = commentDao.findBySid(Long.parseLong(sid));
		for (CommentBean commentBean : list) {
			commentBean.setUname(userDao.findOne(commentBean.getUid()).getUserName());
		}
		return ResultUtils.resultSucceed(list);
	}
	
}
