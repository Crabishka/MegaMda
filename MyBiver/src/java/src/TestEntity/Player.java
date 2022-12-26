package src.TestEntity;



import javax.persistence.*;
import java.util.List;

@Entity

@Table(name = "player")
public class Player {

    @Id
    @Column(name = "id")
    private Long player_id;

    @Column(name = "nickname")
    private String nickname;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(joinColumns = @JoinColumn(name = "player_id"), inverseJoinColumns = @JoinColumn(name = "currency_id"))
    private List<Currency> currency;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(joinColumns = @JoinColumn(name = "player_id"), inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> item;

    @OneToMany(cascade = {CascadeType.MERGE})
    @JoinTable(joinColumns = @JoinColumn(name = "player_id"), inverseJoinColumns = @JoinColumn(name = "progress_id"))
    private List<Progress> progress;


    @Override
    public String toString() {
        return "Player{" +
                "id=" + player_id +
                ", nickname='" + nickname + '\'' +
                ", currencies=" + currency +
                ", items=" + item +
                ", progresses=" + progress +
                '}';
    }
}
