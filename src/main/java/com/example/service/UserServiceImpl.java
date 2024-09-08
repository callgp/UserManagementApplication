package com.example.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.dto.LoginFormDTO;
import com.example.dto.RegisterFormDTO;
import com.example.dto.ResetPwdFormDTO;
import com.example.dto.UserDTO;
import com.example.entities.CityEntity;
import com.example.entities.CountryEntity;
import com.example.entities.StateEntity;
import com.example.entities.UserEntity;
import com.example.repo.CityRepo;
import com.example.repo.CountryRepo;
import com.example.repo.StateRepo;
import com.example.repo.UserRepo;

public class UserServiceImpl implements UserService {
	@Autowired
	private CountryRepo countryRepo;
	@Autowired
	private StateRepo stateRepo;
	@Autowired
	private CityRepo cityRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private EmailService emailService;

	@Override
	public Map<Integer, String> getCountries() {
		List<CountryEntity> countriesList = countryRepo.findAll();

		Map<Integer, String> countryMap = new HashMap<>();

		countriesList.stream().forEach(c -> {
			countryMap.put(c.getCountryId(), c.getCountryName());
		});
		return countryMap;

	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {
		// TODO Auto-generated method stub

		List<StateEntity> statesListForCountryId = stateRepo.findByCountryId(countryId);
		Map<Integer, String> statesMap = new HashMap<>();
		statesListForCountryId.forEach(s -> {

			statesMap.put(s.getStateId(), s.getStateName());

		});
		return statesMap;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {
		// TODO Auto-generated method stub
		Map<Integer, String> citiesMap = new HashMap<>();
		List<CityEntity> cityListOnStateId = cityRepo.findByStateId(stateId);
		cityListOnStateId.forEach(c -> {
			citiesMap.put(c.getCityId(), c.getCityName());
		});
		return citiesMap;
	}

	@Override
	public boolean duplicateEmailCheck(String email) {
		// TODO Auto-generated method stub
		UserEntity byEmail = userRepo.findByEmail(email);
		return byEmail != null ;
	}

	@Override
	public boolean saveUser(RegisterFormDTO regFormDTO) {
		// TODO Auto-generated method stub
		UserEntity userEntity=new UserEntity();
		BeanUtils.copyProperties(regFormDTO, userEntity);
		
		CountryEntity country = countryRepo.findById(regFormDTO.getCountryId()).orElse(null);
		userEntity.setCountry(country);
		
		StateEntity state = stateRepo.findById(regFormDTO.getStateId()).orElse(null);
		userEntity.setState(state);
		
		CityEntity city = cityRepo.findById(regFormDTO.getCityId()).orElse(null);
		userEntity.setCity(city);
		
		String randomPwd = generateRandomPwd();
		userEntity.setPwd(randomPwd);
		userEntity.setPwdUpdated("No");
		
		
		
	   UserEntity savedUser = userRepo.save(userEntity);
	   if(null != savedUser.getUserId()) {
		   String subject ="your account created";
		   String body="your temporary pwd to login is "+randomPwd;
		   String to=regFormDTO.getEmail();
		   
		   emailService.sendEmail(subject, body, to);
		   return true;
	   }
		
		return false;
	}

	@Override
	public UserDTO login(LoginFormDTO loginFormDTO) {
		// TODO Auto-generated method stub
		
		UserEntity userEntity = userRepo.findByEmailAndPwd(loginFormDTO.getEmail(), loginFormDTO.getPwd());
		
		if(userEntity != null) {
			UserDTO userDTO= new UserDTO();
			BeanUtils.copyProperties(userEntity, userDTO);
			return userDTO;
			
		}
		
		return null;
	}

	@Override
	public boolean resetPwd(ResetPwdFormDTO resetPwdDTO) {
		// TODO Auto-generated method stub
		String email = resetPwdDTO.getEmail();
		
		UserEntity entity = userRepo.findByEmailAndPwd(email, email);
		entity.setPwd(resetPwdDTO.getNewPwd());
		entity.setPwdUpdated("Yes");
		userRepo.save(entity);
		return true;
	}
	
	private String generateRandomPwd() {
		String upperCaseLetters="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerCaseLetters="abcdefghijklmnopqrstuvwxyz";
		
		String alphabets=upperCaseLetters+lowerCaseLetters;
		Random random=new Random();
		
		StringBuffer gneratedPwd=new StringBuffer();
		
		for(int i=0;i<5;i++) {
			
			int randomInt = random.nextInt(alphabets.length());
			gneratedPwd.append(alphabets.charAt(randomInt));
		}
		System.out.println("value is "+gneratedPwd.toString());
		return gneratedPwd.toString();
	}

}
