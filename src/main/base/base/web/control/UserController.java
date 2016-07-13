package base.web.control;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import base.bean.UserBean;
import base.service.UserService;
import base.util.Constent;

@Controller
@SessionAttributes("userBean")
public class UserController {

	@Autowired
	@Qualifier("CustomValidator")
	Validator mValidator;

	@Autowired
	UserService mUserService;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register() {
		return "/user/register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String submitRegister(@Valid UserBean userBean, Errors errors,Model model) {
		if (errors.hasErrors()||!mUserService.createUser(userBean, model)) {
			return Constent.ERROR_PAGE;
		}
		return "redirect:/home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(UserBean userBean){
		return "/user/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String submitLogin(UserBean userBean,Model model) {
		String view = mUserService.login(userBean,model);
		return view;
	}

}
