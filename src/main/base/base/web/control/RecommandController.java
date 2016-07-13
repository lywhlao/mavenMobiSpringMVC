package base.web.control;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import base.bean.ContentSimilarBean;
import base.bean.MobiBean;
import base.bean.ResponseData;
import base.bean.UserBean;
import base.service.RecommendService;
import base.util.Constent;
import base.util.StringUtil;

@Controller
@SessionAttributes("userBean")
public class RecommandController {

	@Autowired
	RecommendService mRecommendService;

	/** 记录下载
	 * @param content
	 * @param userBean
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/recordDownload", method = RequestMethod.POST)
	@ResponseBody
	public String recordDownload(String content,@ModelAttribute UserBean userBean, HttpServletRequest request) {
		String identifier = getIdentifier(userBean, request);
		mRecommendService.recordDowload(content, identifier);
		return Constent.RECORD_SUCCESS;
	}

	/**
	 * 获得“猜你喜欢”中的内容
	 * @param userBean
	 * @return
	 */
	@RequestMapping(value = "/getRecommendContent", produces="application/json",method = RequestMethod.POST)
	@ResponseBody
	public ResponseData<List<ContentSimilarBean>> getRecommendContent(@ModelAttribute UserBean userBean) {
		String userName = userBean.getUserName();
		return mRecommendService.getRecommendContentByUser(userName);
	}
	
	/**
	 * 获得“热门推荐”中的内容
	 * @param userBean
	 * @return
	 */
	@RequestMapping(value = "/getHotContent", produces="application/json",method = RequestMethod.POST)
	@ResponseBody
	public ResponseData<List<MobiBean>> getHotContent() {
		return mRecommendService.getHotContent();
	}
	
	/**
	 * 获得“随便看看”中的内容
	 * @param userBean
	 * @return
	 */
	@RequestMapping(value = "/getRandomContent", produces="application/json",method = RequestMethod.POST)
	@ResponseBody
	public ResponseData<List<MobiBean>> getRandomContent() {
		return mRecommendService.getRandomContent();
	}

	@ModelAttribute
	private UserBean getUserBean() {
		return new UserBean();
	}

	/**
	 * 获取用户标示，优先获取用户名，再获取ip
	 * @param userBean
	 * @param httpServletRequest
	 * @return
	 */
	private String getIdentifier(UserBean userBean,HttpServletRequest httpServletRequest) {
		String userName = userBean.getUserName();
		if (!StringUtil.isEmpty(userName)) {
			return userName;
		}
		String ipString = httpServletRequest.getRemoteAddr();
		return ipString;
	}
}
