package com.sprpa.openapi.vo.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sprpa.openapi.vo.DataVO;

@Repository
public class DataDAO {
	@Autowired
    SqlSession sqlSession;
    
    String namespace = "mapper.";
    
	public int insertData(DataVO dataVO) {
		int result = sqlSession.insert(namespace+"insertData", dataVO);
	    return result;
	}
}
