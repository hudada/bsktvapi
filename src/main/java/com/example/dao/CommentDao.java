package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.bean.CommentBean;
import com.example.bean.SongBean;

public interface CommentDao extends JpaRepository<CommentBean, Long> {

    @Query("from CommentBean b where b.sid=:sid")
    List<CommentBean> findBySid(@Param("sid") Long sid);
    
}
