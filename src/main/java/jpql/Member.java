package jpql;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Data
@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

//    @Embedded
//    private Period workPeriod;
//
//    @Embedded
//    private Address address;

    public void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }








}
