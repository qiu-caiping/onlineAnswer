package dao.impl;

import java.sql.SQLException;
import java.util.List;

import model.Answering;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.AnsweringDao;









public class AnsweringDaoImpl extends HibernateDaoSupport implements  AnsweringDao{


	public void deleteBean(Answering Answering) {
		this.getHibernateTemplate().delete(Answering);
		
	}

	public void insertBean(Answering Answering) {
		this.getHibernateTemplate().save(Answering);
		
	}

	@SuppressWarnings("unchecked")
	public Answering selectBean(String where) {
		List<Answering> list = this.getHibernateTemplate().find("from Answering " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from Answering "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<Answering> selectBeanList(final int start,final int limit,final String where) {
		return (List<Answering>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<Answering> list = session.createQuery("from Answering "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(Answering Answering) {
		this.getHibernateTemplate().update(Answering);
		
	}
	
	
}
