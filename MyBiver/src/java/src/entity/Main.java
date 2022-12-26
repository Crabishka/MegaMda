package src.entity;


import src.BiverProvider;
import src.EntityManagerImpl;
import src.Persistence;
import src.TestEntity.Currency;
import src.TestEntity.Item;
import src.TestEntity.Player;
import src.TestEntity.Progress;
import src.entity.create.*;

import javax.persistence.EntityManagerFactory;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {
//        List<EntityBeanCreator> creatorList = new ArrayList<>();
//        creatorList.add(new PrimitiveEntityBeanCreator());
//        creatorList.add(new AfterTableEntityBeanCreator());
//        creatorList.add(new LinkedTableEntityTableCreator());
//        EntityBeanChainCreator entityBeanChainCreator = new EntityBeanChainCreator(creatorList);
//        EntityBeanProcessor entityBeanProcessor = new EntityBeanProcessor();
//        List<EntityBean> beans = entityBeanProcessor.createEntityBeanMap("src.TestEntity").values().stream().toList();
//        Map<Integer, List<String>> queries = entityBeanChainCreator.start(beans);
//        BiverProvider biverProvider = new BiverProvider();
//        final EntityManagerFactory entityManagerFactory = biverProvider.createEntityManagerFactory("default-name", null);
//        EntityManagerImpl entityManager = (EntityManagerImpl) entityManagerFactory.createEntityManager();
//
//        Mapper<Player> playerMapper = new Mapper<>();
//        List<Player> players = playerMapper.resultSetIntoObject(entityManager.findAll(Player.class));
//        for (Player player : players){
//            System.out.println(player.toString());
//        }
//
//        Mapper<Item> itemMapper = new Mapper<>();
//        List<Item> items = itemMapper.resultSetIntoObject(entityManager.findAll(Item.class));
//        for (Item item  : items){
//            System.out.println(item.toString());
//        }
//
//        Mapper<Currency> currencyMapper = new Mapper<>();
//        List<Currency> currencies = currencyMapper.resultSetIntoObject(entityManager.findAll(Currency.class));
//        for (Currency currency : currencies){
//            System.out.println(currency.toString());
//        }
//
//        Mapper<Progress> progressMapper = new Mapper<>();
//        List<Progress> progresses = progressMapper.resultSetIntoObject(entityManager.findAll(Progress.class));
//        for (Progress progress : progresses){
//            System.out.println(progress.toString());
//        }


    }
}


