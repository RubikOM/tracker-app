package com.nixsolutions.config.core;

import com.nixsolutions.config.HibernateConfig;
import com.nixsolutions.config.WebAppConfig;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApplicationInitializer implements WebApplicationInitializer {

    public void onStartup(ServletContext container) {
        container.setInitParameter(
                "contextConfigLocation", "com.nixsolutions.webService");
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();

        container.addListener(new ContextLoaderListener(ctx));
        container.addFilter("characterEncodingFilter", new CharacterEncodingFilter("UTF-8"));

        ctx.register(WebAppConfig.class);
        ctx.register(HibernateConfig.class);

        ctx.setServletContext(container);

        ServletRegistration.Dynamic dispatcherServlet = container.addServlet(
                "dispatcher", new DispatcherServlet(ctx));

        dispatcherServlet.setLoadOnStartup(1);
        dispatcherServlet.addMapping("/");
    }
}
