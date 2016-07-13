package base.web.control;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import base.bean.MobiBean;
import base.bean.UserBean;
import base.service.EmailService;
import base.util.Constent;
import base.util.FileUtil;
import base.util.StringUtil;

@Controller
@SessionAttributes("userBean")
public class EmailController {

	@Autowired
	EmailService emailService;
	
	@RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
	@ResponseBody
	public String sendEmail(MobiBean mobiBean,@ModelAttribute UserBean userBean) throws MessagingException {
		if(StringUtil.isEmpty(userBean.getUserName())){
			return Constent.NOT_LOGIN;
	    }
		if(!FileUtil.fileExist(mobiBean.getContent())){	
	    	return Constent.RESOURCE_NOT_FOUND;
		}
		emailService.sendMobiEmail(mobiBean,userBean);
		return Constent.SEND_EMAIL_OK;
	}
	
	 @ModelAttribute
	   public UserBean getDefaultUserBean() {
	       return new UserBean(); 
	   }
	
}
