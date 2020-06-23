package br.com.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.entities.Exercise;

public class ExerciseDAO {
	
	public void save(Exercise exercise) {
		
		Transaction transaction = null;
		Session session = null;
		
		try {
			session = Connection.getSessionFactory().openSession();
			
			transaction = session.beginTransaction();
			session.saveOrUpdate(exercise);
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
	
	public List<Exercise> findAll() {
		
		Session session = null;
		
		try {
			session = Connection.getSessionFactory().openSession();
			
			String hql = "SELECT e FROM Exercise e";
			
			return session
					.createQuery(hql, Exercise.class)
					.list();
		} catch(Exception e) {
			
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return new ArrayList<Exercise>();
	}
	
	public Exercise findOne(Integer id) {
		Session session = null;
		
		try {
			session = Connection.getSessionFactory().openSession();

			String hql = "SELECT e FROM Exercise e WHERE e.id = :id";

			Query query = session.createQuery(hql, Exercise.class);
			query.setParameter("id", id);
			
			return (Exercise) query.getSingleResult();
		} catch (Exception e) {
			
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return null;
	}
	
	public Exercise findOneByTitle(String title) {
		Session session = null;
		
		try {
			session = Connection.getSessionFactory().openSession();

			String hql = "SELECT e FROM Exercise e WHERE e.title = :title";

			Query query = session.createQuery(hql, Exercise.class);
			query.setParameter("title", title);
			
			return (Exercise) query.getSingleResult();
		} catch (Exception e) {
			
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return null;
	}
	
	public void remove(Integer id) {
		
		Exercise exercise = findOne(id);
		
		Transaction transaction = null;
		Session session = null;
		
		try {
			session = Connection.getSessionFactory().openSession();

			transaction = session.beginTransaction();
			session.remove(exercise);
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
