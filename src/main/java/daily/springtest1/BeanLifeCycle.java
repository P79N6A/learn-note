package daily.springtest1;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;

/**
 * Created by wb-zj373670 on 2018/4/9.
 */
public class BeanLifeCycle{

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanLifeCycle.class);
    public static  void lifeCycleInBeanFactory(){



        Resource res = new ClassPathResource("resources/spring.xml");
        try {
            File file = res.getFile();
        }catch (Exception e){
            e.printStackTrace();
        }

        BeanFactory bf = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader((DefaultListableBeanFactory)bf);
        reader.loadBeanDefinitions(res);
        ((ConfigurableBeanFactory)bf).addBeanPostProcessor(new MyBeanPostProcessor());
        ((ConfigurableBeanFactory)bf).addBeanPostProcessor(new MyInstantiationAwareBeanPostProcessor());

        Car car1 = (Car)bf.getBean("car");
        car1.introduce();
        car1.setColor("红色");

        Car car2 = (Car)bf.getBean("car");
        car2.introduce();

        ((DefaultListableBeanFactory)bf).destroySingletons();
    }



    public static void main(String[] args){
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext();
        classPathXmlApplicationContext.setClassLoader(BeanLifeCycle.class.getClassLoader());

        Resource resource = classPathXmlApplicationContext.getResource("resources/spring.xml");
        classPathXmlApplicationContext.refresh();

        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) classPathXmlApplicationContext.getBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions(resource);


//        lifeCycleInBeanFactory();
    }

}


