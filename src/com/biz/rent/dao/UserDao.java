package com.biz.rent.dao;

import java.util.List;

import com.biz.rent.persistence.UserDTO;

public interface UserDao {
	
	public String getMaxUCode();
	
	public List<UserDTO> selectAll();
	
	public UserDTO findById(String u_code);
	
	public UserDTO findBySName(String u_name);
	public List<UserDTO> findByName(String u_name);
	
	public UserDTO findBySTel(String u_tel);
	public List<UserDTO> findByTel(String u_tel);
	
	public int insert(UserDTO userDTO);
	public int update(UserDTO userDTO);
	public int delete(String u_code);
	
}