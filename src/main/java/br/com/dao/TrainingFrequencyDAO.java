package br.com.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.entities.TrainingFrequency;

public class TrainingFrequencyDAO {
	
	public void save(TrainingFrequency UserTraining) {
		
		Transaction transaction = null;
		Session session = null;
		
		try {
			session = Connection.getSessionFactory().openSession();
			
			transaction = session.beginTransaction();
			session.saveOrUpdate(UserTraining);
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
	
	public List<TrainingFrequency> findAll() {
		
		Session session = null;
		
		try {
			session = Connection.getSessionFactory().openSession();
			
			String hql = "SELECT t FROM TrainingFrequency t";
			
			return session
					.createQuery(hql, TrainingFrequency.class)
					.list();
		} catch(Exception e) {
			
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return new ArrayList<TrainingFrequency>();
	}
	
	public TrainingFrequency findOne(Integer id) {
		Session session = null;
		
		try {
			session = Connection.getSessionFactory().openSession();

			String hql = "SELECT t FROM TrainingFrequency t WHERE t.id = :id";

			Query query = session.createQuery(hql, TrainingFrequency.class);
			query.setParameter("id", id);
			
			return (TrainingFrequency) query.getSingleResult();
		} catch (Exception e) {
			
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return null;
	}
		
	public void remove(Integer id) {
		
		TrainingFrequency userTraining = findOne(id);
		
		Transaction transaction = null;
		Session session = null;
		
		try {
			session = Connection.getSessionFactory().openSession();

			transaction = session.beginTransaction();
			session.remove(userTraining);
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
