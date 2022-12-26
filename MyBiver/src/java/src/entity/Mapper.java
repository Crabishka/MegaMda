package src.entity;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Mapper<T> {

    public List<T> resultSetIntoObject(List<Object> objects){
        List<T> result = new ArrayList<>();
        for (Object object : objects){
            result.add((T) object);
        }
        return result;
    }


}