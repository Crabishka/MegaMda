package src;

import src.entity.EntityBean;
import src.entity.EntityBeanProcessor;
import src.entity.create.*;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 Отвечает за начала всего цирка
 */
public class Persistence {

    public static EntityManagerImpl start() {
        List<EntityBeanCreator> creatorList = new ArrayList<>();
        creatorList.add(new PrimitiveEntityBeanCreator());
        creatorList.add(new AfterTableEntityBeanCreator());
        creatorList.add(new LinkedTableEntityTableCreator());
        EntityBeanChainCreator entityBeanChainCreator = new EntityBeanChainCreator(creatorList);
        EntityBeanProcessor entityBeanProcessor = new EntityBeanProcessor();
        List<EntityBean> beans = entityBeanProcessor.createEntityBeanMap("com.example.application.entity").values().stream().toList();
        Map<Integer, List<String>> queries = entityBeanChainCreator.start(beans);
        BiverProvider biverProvider = new BiverProvider();
        final EntityManagerFactory entityManagerFactory = biverProvider.createEntityManagerFactory("default-name", null);
        return (EntityManagerImpl) entityManagerFactory.createEntityManager();
    }
}
