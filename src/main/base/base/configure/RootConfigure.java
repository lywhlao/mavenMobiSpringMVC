package base.configure;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import base.dao.IMobiDAO;
import base.dao.IRecommandDAO;
import base.dao.IUserDAO;
import base.daoimpl.MobiDAOImpl;
import base.daoimpl.RecommandDAOimpl;
import base.daoimpl.UserDAOImpl;

//配置非web方面的bean
@Configuration
public class RootConfigure {

	// JDBC数据源
	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/mobi");
		ds.setUsername("root");
		ds.setPassword("laojiaqi");
		return ds;
	}

	// 设置Jdbc模板，这里的dataSouce会自动注入（上方那个dataSource）
	@Bean
	public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	// 设置具体的Reposity
	@Bean
	public IMobiDAO getMobiDAO(JdbcTemplate jdbcTemplate) {
		return new MobiDAOImpl(jdbcTemplate);
	}

	@Bean
	public IUserDAO getUserDAO(JdbcTemplate jdbcTemplate) {
		return new UserDAOImpl(jdbcTemplate);
	}
	
    @Bean	
	public IRecommandDAO getRecommandDAO(JdbcTemplate jdbcTemplate){
		return new RecommandDAOimpl(jdbcTemplate);
	}
	

	// 邮件发送
	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
		mailSenderImpl.setHost("smtp.163.com");
		mailSenderImpl.setPort(25);
		mailSenderImpl.setUsername("jasonxtu@163.com");
		mailSenderImpl.setPassword("laojiaqi0219");
       
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", true);
		properties.put("mail.smtp.starttls.enable", true);
		properties.put("mail.smtp.ssl.trust", "smtp.163.com");
		properties.put("mail.smtp.timeout", 25000);

		mailSenderImpl.setJavaMailProperties(properties);
		return mailSenderImpl;
	}

	
	//校验
	@Bean(name="CustomValidator")
	public org.springframework.validation.Validator getValidator() {
		return new LocalValidatorFactoryBean();
	}


}
