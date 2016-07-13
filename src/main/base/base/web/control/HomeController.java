package base.web.control;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import base.bean.SearchBean;
import base.dao.IMobiDAO;

@Controller
@RequestMapping({ "/", "/home" })
public class HomeController {

	private static final int DISPLAY_PAGE_NUM = 1;
	@Autowired
	IMobiDAO mMobiDAO;// 用@component

	@RequestMapping(method = RequestMethod.GET)
	public String home(Model model) {
		return "home";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(SearchBean searchBean, Model model) {
		String view = mMobiDAO.searchMobi(searchBean.getContent(),
				DISPLAY_PAGE_NUM,model);
		return view;
	}

	@RequestMapping(value = "/searchListPage/{content}/{page}", method = RequestMethod.GET)
	public String searchListPage(@PathVariable String content,
			@PathVariable int page, Model model)
			throws UnsupportedEncodingException {
		content = new String(content.getBytes("ISO-8859-1"), "utf8");
		String view = mMobiDAO.searchMobi(content, page,model);
		return view;
	}

}
