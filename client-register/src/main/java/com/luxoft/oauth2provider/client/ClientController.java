package com.luxoft.oauth2provider.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/client")
public class ClientController {

	@Autowired
	private ClientRegistrationService clientRegistrationService;


	@ModelAttribute("allScopes")
	public Scope[] populateTypes() {
		return Scope.values();
	}

	@GetMapping("/register")
	public ModelAndView register(ModelAndView mv) {
		mv.setViewName("client/register");
		mv.addObject("registry", new BasicClientInfo());

		return mv;
	}


	@PostMapping("/save")
	public ModelAndView save(@Valid BasicClientInfo clientDetails,
	                         BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return new ModelAndView("client/register");
		}

		System.out.println(clientDetails);

		ApplicationClientDetails app = new ApplicationClientDetails();

		app.setName(clientDetails.getName());
		app.addRedirectUri(clientDetails.getRedirectUri());
		app.setClientId(UUID.randomUUID().toString());
		app.setClientSecret(UUID.randomUUID().toString());
		app.setAccessTokenValidity(3000);
		app.addScope("read_profile");
		app.setGrantTypes(Arrays.asList(clientDetails.getScopes())
				.stream()
				.map(Enum::toString)
				.collect(Collectors.toSet())
		);

		clientRegistrationService.addClientDetails(app);

		ModelAndView mv = new ModelAndView("redirect:/client/dashboard");
		mv.addObject("applications", clientRegistrationService.listClientDetails());

		return mv;
	}

	@GetMapping("/remove")
	public ModelAndView remove(@RequestParam(value = "client_id", required = true) String clientId) {
		clientRegistrationService.removeClientDetails(clientId);

		ModelAndView mv = new ModelAndView("redirect:/client/dashboard");
		mv.addObject("applications", clientRegistrationService.listClientDetails());

		return mv;
	}

	@GetMapping("/dashboard")
	public ModelAndView dashboard(ModelAndView mv) {
		mv.addObject("applications", clientRegistrationService.listClientDetails());

		return mv;
	}
}
