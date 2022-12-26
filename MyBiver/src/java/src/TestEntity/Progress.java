package src.TestEntity;



import javax.persistence.*;

@Entity

@Table(name = "progress")
public class Progress {


    @Id
    @Column(name = "id")
    private Long progress_id;

    @Column(name = "resource_id")
    private Long resource_id;

    @Column(name = "score")
    private Long score;

    @Column(name = "max_score")
    private Long max_score;

    @ManyToOne
    private Player player_id;

    @Override
    public String toString() {
        return "Progress{" +
                "progress_id=" + progress_id +
                ", resource_id=" + resource_id +
                ", score=" + score +
                ", max_score=" + max_score +
                ", player=" + player_id +
                '}';
    }


}
