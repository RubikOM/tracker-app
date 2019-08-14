package com.rubinskyi.config;

import com.rubinskyi.service.UserService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

public class SpringTestConfigWithMockUser extends SpringTestConfig {

    @Bean
    @Primary
    public UserService mockUserService() {
        return Mockito.mock(UserService.class);
    }

}
