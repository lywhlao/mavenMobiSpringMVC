package base.dao;

import org.springframework.ui.Model;

public interface IMobiDAO {

	public String searchMobi(String content,int currentPage,Model model);
}
