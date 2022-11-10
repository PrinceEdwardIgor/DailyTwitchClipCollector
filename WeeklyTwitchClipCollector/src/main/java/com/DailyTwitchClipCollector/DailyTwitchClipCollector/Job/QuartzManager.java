package com.DailyTwitchClipCollector.DailyTwitchClipCollector.Job;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class QuartzManager {

    @Autowired
    ApplicationContext applicationContext;
    private Scheduler scheduler;

    @Bean
    public SpringBeanJobFactory springBeanJobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();

        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(springBeanJobFactory(applicationContext));
//        factory.setQuartzProperties(quartzProperties());
        return factory;
    }

//    public Properties quartzProperties() throws IOException {
//        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
//        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
//        propertiesFactoryBean.afterPropertiesSet();
//        return propertiesFactoryBean.getObject();
//    }

    public void sceduleJob(JobDetail job, Trigger trigger){
        try{
            this.scheduler = schedulerFactoryBean().getScheduler();
            this.scheduler.scheduleJob(job, trigger);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start(){
        try{
            this.scheduler.start();
        }catch (SchedulerException e){
            e.printStackTrace();
        }
    }
}
