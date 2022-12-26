package src.entity.create;



import src.entity.EntityBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    Класс, который содержит в себе обработчики для EntityBean и запускает их, получай SqlQuery
 */
public class EntityBeanChainCreator {

    List<EntityBeanCreator> entityBeanCreators;

    public EntityBeanChainCreator(List<EntityBeanCreator> entityBeanCreators) {
        this.entityBeanCreators = entityBeanCreators;
    }

    public Map<Integer, List<String>> start(List<EntityBean> entityBeans) {
        Map<Integer, List<String>> result = new HashMap<>();
        for (int i = 0; i < entityBeanCreators.size(); i++) {
            result.put(i, new ArrayList<>());
        }
        for (int i = 0; i < entityBeanCreators.size(); i++) {
            for (EntityBean bean : entityBeans){
                result.get(i).addAll(entityBeanCreators.get(i).doWork(bean));
            }
        }
        return result;
    }
}
