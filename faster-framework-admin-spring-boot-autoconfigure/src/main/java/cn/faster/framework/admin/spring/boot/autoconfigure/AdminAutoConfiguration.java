package cn.faster.framework.admin.spring.boot.autoconfigure;

import cn.faster.framework.admin.AdminConfiguration;
import cn.faster.framework.core.auth.admin.ShiroFilter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zhangbowen 2018/5/30 10:18
 */
@Configuration
@Import(AdminConfiguration.class)
public class AdminAutoConfiguration {
}
