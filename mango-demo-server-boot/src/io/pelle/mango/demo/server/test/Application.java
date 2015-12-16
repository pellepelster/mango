package io.pelle.mango.demo.server.test;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import( value = { MangoDemoApplicationContext.class } )
public class Application {

	
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public BasicDataSource dataSource() {

    	BasicDataSource dataSource = new BasicDataSource();
    		dataSource.setUsername("sa");
    		dataSource.setPassword("");
    		dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
    		dataSource.setUrl("jdbc:hsqldb:mem:example");

    	return dataSource;
    }

}