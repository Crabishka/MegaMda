package src.TestEntity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "currency")
public class Currency {

    @Id
    @Column(name = "id")
    private Long currency_id;

    private Long resource_id;

    private String name;

    private Long count;



    public Currency() {

    }

    public Currency(Long id, Long resourceId, String name, Long count) {
        this.currency_id = id;
        this.resource_id = resourceId;
        this.name = name;
        this.count = count;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "currency_id=" + currency_id +
                ", resource_id=" + resource_id +
                ", name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
