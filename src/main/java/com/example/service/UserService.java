package com.example.service;

import java.util.Map;

import com.example.dto.LoginFormDTO;
import com.example.dto.RegisterFormDTO;
import com.example.dto.ResetPwdFormDTO;
import com.example.dto.UserDTO;

public interface UserService {

	
	
	public Map<Integer, String> getCountries();
	
	public Map<Integer, String> getStates(Integer countryId);
	
	public Map<Integer, String> getCities(Integer stateId);
	
	public boolean duplicateEmailCheck(String email);
	public boolean saveUser(RegisterFormDTO regFormDTO);
	
	public UserDTO login(LoginFormDTO loginFormDTO);
	
	public boolean resetPwd(ResetPwdFormDTO resetPwdDTO);
	
	//public UserDTO getUserByEmail(String email);
	
	
	
}
