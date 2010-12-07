package nl.gridshore.companyhr.query;

import com.googlecode.objectify.ObjectifyFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Jettro Coenradie
 */
public class ObjectifyFactoryFactory implements FactoryBean<ObjectifyFactory>, InitializingBean {
    private ObjectifyFactory factory;

    public void afterPropertiesSet() throws Exception {
        factory = new ObjectifyFactory();
        for (Class<?> clazz : classes) {
            factory.register(clazz);
        }
    }

    private List<Class<?>> classes;

    public void setRegisteredClasses(List<Class<?>> classes) {
        Assert.notNull(classes, "Cannot register nothing, please provide some classes to register with Objectify");
        this.classes = classes;
    }

    public ObjectifyFactory getObject() throws Exception {
        return this.factory;
    }

    public Class<?> getObjectType() {
        return ObjectifyFactory.class;
    }

    public boolean isSingleton() {
        return true;
    }
}
