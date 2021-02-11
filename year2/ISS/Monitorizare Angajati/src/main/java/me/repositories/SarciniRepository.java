package me.repositories;

import me.entities.Angajat;
import me.entities.Sarcina;
import me.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class SarciniRepository implements Repository<Sarcina> {
    @Override
    public Sarcina save(Sarcina elem) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(elem);
            session.getTransaction().commit();

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Sarcina update(Sarcina elem) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(elem);
            session.getTransaction().commit();

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Sarcina delete(int elem) throws IllegalArgumentException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Sarcina a = get(elem);
            session.beginTransaction();
            session.delete(a);
            session.getTransaction().commit();
            return null;
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public Sarcina get(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String HQL = "FROM Sarcina WHERE id=:id";
            Query<Sarcina> query = session.createQuery(HQL, Sarcina.class);
            query.setParameter("id", id);

            Sarcina employee = query.uniqueResult();
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
    public List<Sarcina> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String HQL = "FROM Sarcina";
            Query<Sarcina> query = session.createQuery(HQL, Sarcina.class);
            List<Sarcina> sarcinas = query.getResultList();
            return sarcinas;
        } catch (Exception e){
            return null;
        }
    }

    public Sarcina getByTitluSiAngajat(String titlu, int idAngajat) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String HQL = "FROM Sarcina WHERE titlu=:t and idPersoana=:i";
            Query<Sarcina> query = session.createQuery(HQL, Sarcina.class);
            query.setParameter("t", titlu);
            query.setParameter("i", idAngajat);

            Sarcina s = query.uniqueResult();
            return s;

        } catch (Exception e){
            return null;
        }
    }
}
