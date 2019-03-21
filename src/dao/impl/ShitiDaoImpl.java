package dao.impl;

import java.sql.SQLException;
import java.util.List;

import model.Shiti;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.ShitiDao;









public class ShitiDaoImpl extends HibernateDaoSupport implements  ShitiDao{


	public void deleteBean(Shiti Shiti) {
		this.getHibernateTemplate().delete(Shiti);
		
	}

	public void insertBean(Shiti Shiti) {
		this.getHibernateTemplate().save(Shiti);
		
	}

	@SuppressWarnings("unchecked")
	public Shiti selectBean(String where) {
		List<Shiti> list = this.getHibernateTemplate().find("from Shiti " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from Shiti "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<Shiti> selectBeanList(final int start,final int limit,final String where) {
		return (List<Shiti>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<Shiti> list = session.createQuery("from Shiti "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(Shiti Shiti) {
		this.getHibernateTemplate().update(Shiti);
		
	}
	
	
}
