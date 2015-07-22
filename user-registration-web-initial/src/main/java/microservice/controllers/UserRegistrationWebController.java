package microservice.controllers;

import javax.validation.Valid;

import microservice.controllers.vo.RegistrationRequest;
import microservice.controllers.vo.RegistrationResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserRegistrationWebController {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${user_registration_url}")
	private String userRegistrationUrl;

	@RequestMapping(value = {"/register.html"}, method = {RequestMethod.GET})
	public String beginRegister(){
		return "register";
	}

	@RequestMapping(value = "/register.html", method = RequestMethod.POST)
	public String register(@Valid() @ModelAttribute("registration") RegistrationRequest request,
			BindingResult bindingResult, RedirectAttributes redirectAttributes){
		if (bindingResult.getErrorCount() != 0){
			return "register";
		}

		ResponseEntity<RegistrationResponse> response = null;
		try{
			response = restTemplate.postForEntity(
					userRegistrationUrl
					, new RegistrationRequest(request.getEmailAddress(), request.getPassword())
					, RegistrationResponse.class);
		}catch(HttpClientErrorException e) {
			if(HttpStatus.CONFLICT.compareTo(e.getStatusCode()) <= 0){
				bindingResult.rejectValue("emailAddress"
						, "duplicate.email.address"
						, "Email address already registered");
				return "register";
			}
		}

		if(HttpStatus.OK.compareTo(response.getStatusCode()) >= 0){
			redirectAttributes.addAttribute("emailAddress", request.getEmailAddress());
			return "redirect:registrationconfirmation.html";
		}

		return "register";
	}

	@RequestMapping(value = "/registrationconfirmation.html", method = RequestMethod.GET)
	public String registrationconfirmation(@RequestParam String emailAddress, Model model){
		model.addAttribute("emailAddress", emailAddress);
		return "registrationconfirmation";
	}

}
