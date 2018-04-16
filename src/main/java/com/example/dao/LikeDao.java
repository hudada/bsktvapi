package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.bean.LikeBean;
import com.example.bean.MusicBean;
import com.example.bean.SongBean;
import com.example.bean.UserBean;

public interface LikeDao extends JpaRepository<LikeBean, Long> {

	@Query("from LikeBean b where b.uid=:uid and b.type=0")
    List<LikeBean> findByUid(@Param("uid") Long uid);
	
	@Query("from LikeBean b where b.uid=:uid and b.likeUid=:likeUid and b.type=0")
	LikeBean findByUidAndLikeUid(@Param("uid") Long uid,
			@Param("likeUid") Long likeUid);
	
	@Query("from LikeBean b where b.uid=:uid and b.likeUid=:likeUid and b.type=:type")
	LikeBean findOneByUidAndLikeUid(@Param("uid") Long uid,
			@Param("likeUid") Long likeUid,
			@Param("type") int type);
}
