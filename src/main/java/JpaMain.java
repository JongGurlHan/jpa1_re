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

            Team team2 = new Team();
            team2.setName("team2");
            em.persist(team2);

            Member member = new Member();
            member.setUsername("관리자1");
            member.changeTeam(team1);
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("관리자2");
            member2.changeTeam(team2);
            em.persist(member2);

            em.flush();
            em.clear();

           String query = "select t from Member m join m.team t ";


            List<Team> resultList = em.createQuery(query, Team.class)
                    .getResultList();

            System.out.println("resultList = " + resultList.size());

            for (Team team : resultList) {
                System.out.println("member = " + team.getName());
            }


            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }


}
