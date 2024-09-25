package src;

import src.entity.EntityBean;
import src.entity.EntityBeanProcessor;
import src.entity.create.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.ProviderUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
    Получил persistence-unit name (из аннотации получил)
    Создал по нему PersistenceUnitInfo
    Создал по PersistenceUnitInfo - DataSource
    Создал BeanEntityMap
    Создал по DataSource и BeanEntityMap - EntityManagerFactory

*/

public class BiverProvider implements PersistenceProvider {

    public BiverProvider() {
    }

    @Override
    public EntityManagerFactory createEntityManagerFactory(String s, Map map) {
        try {
            DataSourcePool dataSourcePool = DataSourcePool.create("jdbc:postgresql://localhost:5432/postgres",
                    "admin", "password");
            List<EntityBeanCreator> creatorList = new ArrayList<>();
            creatorList.add(new PrimitiveEntityBeanCreator());
            creatorList.add(new AfterTableEntityBeanCreator());
            creatorList.add(new LinkedTableEntityTableCreator());
            EntityBeanChainCreator entityBeanChainCreator = new EntityBeanChainCreator(creatorList);

            EntityBeanProcessor entityBeanProcessor = new EntityBeanProcessor();
            Map<Class<?>, EntityBean> beans = entityBeanProcessor.createEntityBeanMap("src.TestEntity");
            entityBeanChainCreator.start(beans.values().stream().toList());
            return new EntityManagerFactoryImpl(dataSourcePool, beans);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public EntityManagerFactory createContainerEntityManagerFactory(PersistenceUnitInfo persistenceUnitInfo, Map map) {
        return null;
    }

    @Override
    public void generateSchema(PersistenceUnitInfo persistenceUnitInfo, Map map) {

    }

    @Override
    public boolean generateSchema(String s, Map map) {
        return false;
    }

    @Override
    public ProviderUtil getProviderUtil() {
        return null;
    }
}
