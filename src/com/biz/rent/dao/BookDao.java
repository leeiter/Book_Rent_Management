package com.biz.rent.dao;

import java.util.List;

import com.biz.rent.persistence.BookDTO;

public interface BookDao {
	
	public String getMaxBCode();
	
	public List<BookDTO> selectAll();
	
	public BookDTO findById(String b_code);
	
	public BookDTO findBySName(String b_name);
	public List<BookDTO> findByName(String b_name);
	
	public BookDTO findBySAuther(String b_auther);
	public List<BookDTO> findByAuther(String b_auther);
	
	public int insert(BookDTO bookDTO);
	public int update(BookDTO bookDTO);
	public int delete(String b_code);

}