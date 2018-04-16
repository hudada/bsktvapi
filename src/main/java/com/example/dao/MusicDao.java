package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.bean.MusicBean;
import com.example.bean.SongBean;

public interface MusicDao extends JpaRepository<MusicBean, Long> {
	@Query("from MusicBean b where b.name=:name")
	MusicBean findByName(@Param("name") String name);
}
