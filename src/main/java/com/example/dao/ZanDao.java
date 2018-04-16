package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.bean.SongBean;
import com.example.bean.UserBean;
import com.example.bean.ZanBean;

public interface ZanDao extends JpaRepository<ZanBean, Long> {

    @Query("from ZanBean b where b.sid=:sid and b.uid=:uid")
    ZanBean findById(@Param("sid") Long sid,@Param("uid") Long uid);
    
}
