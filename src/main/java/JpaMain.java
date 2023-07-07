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

            Address address = new Address("BUSAN", "street", "1000");

            Member member = new Member();
            member.setUsername("member1");
            member.setAddress(address);
            em.persist(member);

            Address copyAddress = new Address(address.getCity(), address.getStreet() , address.getZipcode());

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAddress(copyAddress);
            em.persist(member2);

            member2.getAddress().setCity("SEOUL");




            em.persist(member);

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }


}
