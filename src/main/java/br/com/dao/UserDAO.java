package br.com.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.entities.User;

public class UserDAO {
	
	public void save(User user) {
		
		Transaction transaction = null;
		Session session = null;
		
		try {
			session = Connection.getSessionFactory().openSession();
			
			transaction = session.beginTransaction();
			session.saveOrUpdate(user);
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
	
	public List<User> findAll() {
		
		Session session = null;
		
		try {
			session = Connection.getSessionFactory().openSession();
			
			String hql = "SELECT u FROM User u";
			
			return session
					.createQuery(hql, User.class)
					.list();
		} catch(Exception e) {
			
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return new ArrayList<User>();
	}
	
	public User findOne(String cpf) {
		Session session = null;
		
		try {
			session = Connection.getSessionFactory().openSession();

			String hql = "SELECT u FROM User u WHERE u.cpf = :cpf";

			Query query = session.createQuery(hql, User.class);
			query.setParameter("cpf", cpf);
			
			return (User) query.getSingleResult();
		} catch (Exception e) {

		} finally {
			if (session != null) {
				session.close();
			}
		}
		return null;
	}
	
	public void remove(String cpf) {
		
		User User = findOne(cpf);
		
		Transaction transaction = null;
		Session session = null;
		
		try {
			session = Connection.getSessionFactory().openSession();

			transaction = session.beginTransaction();
			session.remove(User);
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