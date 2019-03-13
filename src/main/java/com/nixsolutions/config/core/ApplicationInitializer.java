package com.nixsolutions.config.core;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import com.nixsolutions.config.HibernateConfig;
import com.nixsolutions.config.SpringConfiguration;
import com.nixsolutions.config.WebAppConfig;
import com.nixsolutions.config.security.SecurityConfig;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApplicationInitializer implements WebApplicationInitializer {
    // TODO import DB data or release app on some free resource for gf to use.
    // TODO rebuild jsp to thymeleaf
    // jdbc:h2:./home/NIX/rubinskyi/Desktop/tracker-app/database/main;DB_CLOSE_DELAY=-1

    private static final String DISPATCHER_SERVLET_NAME = "dispatcher";
    private static final String DISPATCHER_SERVLET_MAPPING = "/";

    @Override
    public void onStartup(ServletContext container) {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(SpringConfiguration.class);
        ctx.register(SecurityConfig.class);
        ctx.register(WebAppConfig.class);
        ctx.register(HibernateConfig.class);
        ctx.setServletContext(container);

        ServletRegistration.Dynamic dispatcherServlet = container.addServlet(
                DISPATCHER_SERVLET_NAME, new DispatcherServlet(ctx));

        dispatcherServlet.setLoadOnStartup(1);
        dispatcherServlet.addMapping(DISPATCHER_SERVLET_MAPPING);

        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);

        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        FilterRegistration.Dynamic characterEncoding = container.addFilter("characterEncoding", characterEncodingFilter);
        characterEncoding.addMappingForUrlPatterns(dispatcherTypes, true, "/*");
    }
}
