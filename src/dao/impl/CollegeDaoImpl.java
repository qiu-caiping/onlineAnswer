package dao.impl;

import java.sql.SQLException;
import java.util.List;


import model.College;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.CollegeDao;



public class CollegeDaoImpl extends HibernateDaoSupport implements CollegeDao {

	

	public void deleteBean(College bean) {
		this.getHibernateTemplate().delete(bean);
		
	}

	public void insertBean(College bean) {
		this.getHibernateTemplate().save(bean);
		
	}

	@SuppressWarnings("unchecked")
	public College selectBean(String where) {
		List<College> list = this.getHibernateTemplate().find("from College "+where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public long selectBeanCount(final String where) {
		long count = (Long)this.getHibernateTemplate().find(" select count(*) from College  "+where).get(0);
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<College> selectBeanList(final int start,final int limit,final String where) {
		return (List<College>)this.getHibernateTemplate().executeFind(new HibernateCallback(){

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				List<College> list = session.createQuery(" from College"+where).setFirstResult(start).setMaxResults(limit).list();
				return list;
			}
		});
		
	}

	public void updateBean(College bean) {
		this.getHibernateTemplate().update(bean);
		
	}
	
	
	
	
	
	
	
}
