package com.example.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.dto.LoginFormDTO;
import com.example.dto.QuoteApiResponseDTO;
import com.example.dto.RegisterFormDTO;
import com.example.dto.ResetPwdFormDTO;
import com.example.dto.UserDTO;
import com.example.service.DashboardService;
import com.example.service.UserService;

@Controller
public class UserController {
	
	@Autowired
private UserService userService;
	

	@Autowired
	private DashboardService dashboardService;
	
	@GetMapping("/register")
	public String loadRegisteredPage(Model model) {
		
		Map<Integer, String> countriesMap = userService.getCountries();
		
		model.addAttribute("countries",countriesMap);
		RegisterFormDTO registerFormDTO=new RegisterFormDTO();
		
		model.addAttribute("registerForm",registerFormDTO );
		
		return "register";
		
	}
	
	@GetMapping("/states/{countryId}")
	@ResponseBody
	public Map<Integer, String> getStates(@PathVariable Integer countryId, Model model) {
		
		Map<Integer, String> statesMap = userService.getStates(countryId);
		
	//	model.addAttribute("states",statesMap);
		//RegisterFormDTO registerFormDTO=new RegisterFormDTO();
		
		//model.addAttribute("registerForm",registerFormDTO );
		
		return statesMap;
		
	}
	
	
	@GetMapping("/states/{countryId}")
	@ResponseBody
	public Map<Integer, String> getCities(@PathVariable Integer stateId, Model model) {
		
		Map<Integer, String> citiesMap = userService.getCities(stateId);
		
		//model.addAttribute("cities",citiesMap);
		//RegisterFormDTO registerFormDTO=new RegisterFormDTO();
		
		//model.addAttribute("registerForm",registerFormDTO );
		
		return citiesMap;
		
	}
	
	
	@PostMapping("/register")
	public String handleRegistration(RegisterFormDTO regusterFormDTO, Model model) {
		
        boolean duplicateEmailCheckStatus = userService.duplicateEmailCheck(regusterFormDTO.getEmail());
		if(duplicateEmailCheckStatus) {
			model.addAttribute("emsg","duplicate email founc");
		} else {
			boolean saveUser = userService.saveUser(regusterFormDTO);
			if(saveUser) {
				//user saved
				model.addAttribute("smsg","registration success..check your email for temp pwd");
			}else {
				//failed to save
				model.addAttribute("emsg","registration failed");
			}
			
		}
		model.addAttribute("countries",userService.getCountries());
		return "register";
		
	}
	

	@GetMapping("/")
	public String loadLoginIndexPage(Model model) {
		LoginFormDTO loginFormDTO=new LoginFormDTO();
		model.addAttribute("loginFormDTO", loginFormDTO);
		return "login";
	}
	
	
	@PostMapping("/login")
	public String handleUserLogin(LoginFormDTO loginFormDTO, Model model) {
		UserDTO userDTO = userService.login(loginFormDTO);
		
		if( userDTO==null) {
			model.addAttribute("emsg","invalid credentials");
		} else {
			
			String pwdUpdated = userDTO.getPwdUpdated();
			if("Yes".equals(pwdUpdated)) {
				//dashborard
			return	"redirect:dashboard";
			} else {
				//reset pwd page
				return "redirect:rest-pwd-page?email="+userDTO.getEmail();
			}
		}
		return "login";
	}
	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		QuoteApiResponseDTO quoteApiResponseDTO = dashboardService.getQuote();
		model.addAttribute("quote",quoteApiResponseDTO);
		return "dashboard";
	}
	@GetMapping("/rest-pwd-page")
	public String loadResetPwdPage(@RequestParam("email") String email, Model model) {
		//QuoteApiResponseDTO quoteApiResponseDTO = dashboardService.getQuote();
	//	model.addAttribute("quote",quoteApiResponseDTO);
		
		ResetPwdFormDTO resetPwdFormDTO=new ResetPwdFormDTO();
		resetPwdFormDTO.setEmail(email);
		return "resetPwd";
	}
	
	@PostMapping("/resetPwd")
	public String handleResetPwdPage(@RequestParam("email") String email, Model model) {
		//QuoteApiResponseDTO quoteApiResponseDTO = dashboardService.getQuote();
	//	model.addAttribute("quote",quoteApiResponseDTO);
		
	//	ResetPwdFormDTO resetPwdFormDTO=new ResetPwdFormDTO();
		//resetPwdFormDTO.setEmail(email);
		boolean resetPwd = userService.resetPwd(null);
		if(resetPwd) {
			return "redirect:dashboard";
		}
		return "resetPwd";
	}
}
