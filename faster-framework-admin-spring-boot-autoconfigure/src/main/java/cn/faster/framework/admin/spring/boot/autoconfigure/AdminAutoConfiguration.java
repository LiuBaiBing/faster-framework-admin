package cn.faster.framework.admin.spring.boot.autoconfigure;

import cn.faster.framework.admin.AdminConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author zhangbowen 2018/5/30 10:18
 */
@Configuration
@Import(AdminConfiguration.class)
public class AdminAutoConfiguration {
}
