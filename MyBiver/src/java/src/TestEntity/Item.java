package src.TestEntity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

@Table(name = "item")
public class Item {

    @Id
    @Column(name = "id")
    private Long item_id;

    @Column(name = "resource_Id")
    private Long resource_Id;

    @Column(name = "count")
    private Long count;

    @Column(name = "level")
    private Long level;

    @Override
    public String toString() {
        return "Item{" +
                "id=" + item_id +
                ", resource_Id=" + resource_Id +
                ", count=" + count +
                ", level=" + level +
                '}';
    }
}
