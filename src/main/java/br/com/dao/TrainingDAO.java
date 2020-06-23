package br.com.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.entities.Training;

public class TrainingDAO {
	
	public void save(Training training) {
		
		Transaction transaction = null;
		Session session = null;
		
		try {
			session = Connection.getSessionFactory().openSession();
			
			transaction = session.beginTransaction();
			session.saveOrUpdate(training);
			transaction.commit();
		} catch (Exception e) {
			if(transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	public List<Training> findAll() {
		
		Session session = null;
		
		try {
			session = Connection.getSessionFactory().openSession();
			
			String hql = "SELECT t FROM Training t";
			
			return session
					.createQuery(hql, Training.class)
					.list();
		} catch(Exception e) {
			
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return new ArrayList<Training>();
	}
	
	public Training findOne(Integer id) {
		Session session = null;
		
		try {
			session = Connection.getSessionFactory().openSession();

			String hql = "SELECT t FROM Training t WHERE t.id = :id";

			Query query = session.createQuery(hql, Training.class);
			query.setParameter("id", id);
			
			return (Training) query.getSingleResult();
		} catch (Exception e) {
			
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return null;
	}
		
	public void remove(Integer id) {
		
		Training TrainingPlan = findOne(id);
		
		Transaction transaction = null;
		Session session = null;
		
		try {
			session = Connection.getSessionFactory().openSession();

			transaction = session.beginTransaction();
			session.remove(TrainingPlan);
			transaction.commit();
		} catch (Exception e) {
			
			if(transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
