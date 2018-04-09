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
import com.example.bean.SongBean;
import com.example.bean.UserBean;
import com.example.dao.SongDao;
import com.example.dao.UserDao;
import com.example.utils.ResultUtils;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/user")
public class ApiUserController {

	@Autowired
	private UserDao userDao;
	@Autowired
	private SongDao songDao;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public BaseBean<UserBean> add(HttpServletRequest request) {
		UserBean userBean = new UserBean();
		userBean.setUserName(request.getParameter("name"));
		userBean.setPwd(request.getParameter("pwd"));
		if (userDao.findUserByUserName(userBean.getUserName()) == null) {
			return ResultUtils.resultSucceed(userDao.save(userBean));
		} else {
			return ResultUtils.resultError("该账号已存在！");
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public BaseBean<UserBean> userLogin(HttpServletRequest request) {
		UserBean userBean = new UserBean();
		userBean.setUserName(request.getParameter("name"));
		userBean.setPwd(request.getParameter("pwd"));
		UserBean select = userDao.findUserByUserNameAndPwd(userBean.getUserName(), userBean.getPwd());
		if (select == null) {
			return ResultUtils.resultError("账号或密码错误");
		} else {
			return ResultUtils.resultSucceed(select);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public BaseBean<List<SongBean>> getUser(@PathVariable String id) {
		List<SongBean> list = songDao.findByUid(Long.parseLong(id));
		return ResultUtils.resultSucceed(list);
	}
	
}
