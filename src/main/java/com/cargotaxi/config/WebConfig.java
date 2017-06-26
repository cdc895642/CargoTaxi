package com.cargotaxi.config;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support
        .AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support
        .AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.EnumSet;


public class WebConfig extends
        AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws
            ServletException {
        //create the root Spring application context
        AnnotationConfigWebApplicationContext rootContext = new
                AnnotationConfigWebApplicationContext();
        rootContext.register(SecurityConfig.class, DatabaseConfig.class);

        servletContext.addListener(new ContextLoaderListener(rootContext));

        //Create the dispatcher servlet's Spring application context
        AnnotationConfigWebApplicationContext servletAppContext = new
                AnnotationConfigWebApplicationContext();
        servletAppContext.register(MvcConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet
                (servletAppContext);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);

        //encoding russian symbols
        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType
                .REQUEST, DispatcherType.FORWARD);

        CharacterEncodingFilter characterEncodingFilter = new
                CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        FilterRegistration.Dynamic characterEncoding = servletContext
                .addFilter("characterEncoding", characterEncodingFilter);
        characterEncoding.addMappingForUrlPatterns(dispatcherTypes, true, "/*");
        //end encoding

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet
                ("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[0];
    }

    @Override
    protected String[] getServletMappings() {
        return new String[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[0];
    }
}
