package com.biz.rent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.biz.rent.persistence.RentBookDTO;
import com.biz.rent.persistence.RentVO;

public interface RentBookDao {
	
	public List<RentBookDTO> selectAll();
	
	public RentBookDTO findById(long rent_seq);
	
	public RentVO viewFindById(long seq);
	
	public RentBookDTO returnRent(@Param("rent_bcode") String rent_bcode);
	public List<RentBookDTO> noReturnRent();
	
	public int insert(RentBookDTO rentBookDTO);
	public int update(RentBookDTO rentBookDTO);
	public int delete(long rent_seq);

}