package com.sanyo.quote.helper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.context.support.GenericXmlApplicationContext;

/*
 * Created by luan
 * on Feb 1, 2016
 * 
 * This Class is used to open database connection and execute complicated query that we cannot use JPA easily.
 */
public class ConnectionUtil {
	private EntityManagerFactory emf;
	private EntityManager em;
	ConnectionUtil instances;

	public void startConnection() {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:datasource-tx-jpa.xml");
		ctx.refresh();
		emf = (EntityManagerFactory) ctx.getBean("emf");
		em = emf.createEntityManager();
		em.getTransaction().begin();
		ctx.close();
	}

	public void closeConnection() {
		em.getTransaction().commit();
		emf.close();
	}

	public EntityManager getEntityManager() {
		return em;
	}
	
	public Query getQueryObj(String queryStr) {
		try {
			startConnection();
			EntityManager em = getEntityManager();
			Query query = em.createQuery(queryStr);
			return query;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeConnection();
		}
		return null;
	}

}
