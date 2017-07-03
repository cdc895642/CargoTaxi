package com.cargotaxi.mvc.dao;

import com.cargotaxi.config.DatabaseConfig;
import com.cargotaxi.config.MvcConfig;
import com.cargotaxi.config.SecurityConfig;
import com.cargotaxi.config.WebConfig;
import com.cargotaxi.mvc.model.User;
import com.cargotaxi.mvc.service.UserService;
import com.cargotaxi.mvc.service.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextBootstrapper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.List;

@WebAppConfiguration

@RunWith(SpringRunner.class)//SpringJUnit4ClassRunner.class
//@BootstrapWith(value=TestContextBootstrapper.class)

@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = { DatabaseConfig.class, MvcConfig.class, SecurityConfig
                .class, WebConfig.class})//, loader = AnnotationConfigContextLoader.class
@Transactional

//@Import(MvcConfig.class)
//@SpringBootTest
//@DataJpaTest
public class UserRepositoryTest {
//    @Autowired
//    EntitiesCreator entitiesCreator;
@Autowired
UserRepository userRepository;

    @Test
    public void test(){
        User user=new User();
        user.setLogin("11111");
        user.setPassword("11111");
        userRepository.save(user);
List<User> list = userRepository.findAll();
//        entitiesCreator.create();
        System.out.print(list.size());
    }
}
