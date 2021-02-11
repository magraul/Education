package me.repositories;

import me.entities.Angajat;
import me.entities.Cerere;
import me.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;


public class AngajatiRepository implements Repository<Angajat>{
    @Override
    public Angajat save(Angajat elem) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(elem);
            session.getTransaction().commit();

        } catch (Exception e){

        }
        return null;
    }

    @Override
    public Angajat update(Angajat elem) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(elem);
            session.getTransaction().commit();
            return null;
        } catch (Exception e){
            return null;
        }

    }

    @Override
    public Angajat delete(int elem) throws IllegalArgumentException {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Angajat a = get(elem);
            session.beginTransaction();
            session.delete(a);
            session.getTransaction().commit();
            return null;
        } catch (Exception e){
           return null;
        }
    }

    @Override
    public Angajat get(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String HQL = "FROM Angajat WHERE id=:id";
            Query<Angajat> query = session.createQuery(HQL, Angajat.class);
            query.setParameter("id", id);

            Angajat employee = query.uniqueResult();
            return employee;

        } catch (Exception e){
            return null;
        }


    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public List<Angajat> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String HQL = "FROM Angajat where tipPersoana = 2";
            Query<Angajat> query = session.createQuery(HQL, Angajat.class);
            List<Angajat> employee = query.getResultList();
            return employee;
        } catch (Exception e){
            return null;
        }
    }

    public boolean check(String username, String parola) {


        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String HQL = "FROM Angajat WHERE username=:u AND parola=:p";
            Query<Angajat> query = session.createQuery(HQL, Angajat.class);
            query.setParameter("u", username);
            query.setParameter("p", parola);

            Angajat employee = query.uniqueResult();
            return employee != null;

        } catch (Exception e){
            return false;
        }


    }

    public Angajat findByUser(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String HQL = "FROM Angajat WHERE username=:u";
            Query<Angajat> query = session.createQuery(HQL, Angajat.class);
            query.setParameter("u", username);

            Angajat employee = query.uniqueResult();
            return employee;

        } catch (Exception e){
            return null;
        }
    }

    public Angajat getByUsername(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String HQL = "FROM Angajat WHERE username=:id";
            Query<Angajat> query = session.createQuery(HQL, Angajat.class);
            query.setParameter("id", username);

            Angajat employee = query.uniqueResult();
            return employee;

        } catch (Exception e){
            return null;
        }
    }

    public List<Cerere> findAllCereriActive() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String HQL = "FROM Cerere where status = 0";
            Query<Cerere> query = session.createQuery(HQL, Cerere.class);
            List<Cerere> employee = query.getResultList();
            return employee;
        } catch (Exception e){
            e.printStackTrace();
            return null;

        }
    }

    public void dataSent(Angajat a) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Cerere c = getCerereActivaAngajat(a);
            c.setStatus(1);
            session.update(c);
            session.getTransaction().commit();
        } catch (Exception e){

        }
    }

    private Cerere getCerereActivaAngajat(Angajat a) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String HQL = "FROM Cerere WHERE idPersoana=:id and status =:s";
            Query<Cerere> query = session.createQuery(HQL, Cerere.class);
            query.setParameter("id", a.getId());
            query.setParameter("s", 0);

            Cerere employee = query.uniqueResult();
            return employee;

        } catch (Exception e){
            return null;
        }
    }

    public List<Angajat> getAngajatiLogati() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String HQL = "FROM Angajat where statusLogare = 1 and tipPersoana = 2";
            Query<Angajat> query = session.createQuery(HQL, Angajat.class);
            List<Angajat> employee = query.getResultList();
            return employee;
        } catch (Exception e){
            e.printStackTrace();
            return null;

        }
    }

    public Angajat getByName(String angajat) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String HQL = "FROM Angajat WHERE nume=:id";
            Query<Angajat> query = session.createQuery(HQL, Angajat.class);
            query.setParameter("id", angajat);

            Angajat employee = query.uniqueResult();
            return employee;

        } catch (Exception e){
            return null;
        }
    }

    public Angajat getByNumeAndOra(String nume, String oraSosire) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String HQL = "FROM Angajat WHERE nume=:id and ora_sosire=:ora";
            Query<Angajat> query = session.createQuery(HQL, Angajat.class);
            query.setParameter("id", nume);
            query.setParameter("ora", oraSosire);

            Angajat employee = query.uniqueResult();
            return employee;

        } catch (Exception e){
            return null;
        }
    }
}
