import jpql.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        //EntityManagerFactory는 하나만 생성해서 애플리케이션 전체에서 공유
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        //db작업 단위마다 EntityManager 만든다. 쓰레드간에 공유x(사용하고 버려야한다.)
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            Team team1 = new Team();
            team1.setName("team1");
            em.persist(team1);


            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team1);
            em.persist(member);

            em.flush();
            em.clear();


            List<Team> result = em.createQuery("select t from Member m join m.team t", Team.class)
                    .getResultList();

            Team team = result.get(0);

            System.out.println("team = " + team.getName());

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }


}
