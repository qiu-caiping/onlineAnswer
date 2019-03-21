package dao.impl;

import java.sql.SQLException;
import java.util.List;

import model.Course;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.CourseDao;

public class CourseDaoImpl extends HibernateDaoSupport implements  CourseDao{


	public void deleteBean(Course Course) {
		this.getHibernateTemplate().delete(Course);
		
	}

	public void insertBean(Course Course) {
		this.getHibernateTemplate().save(Course);
		
	}

	@SuppressWarnings("unchecked")
	public Course selectBean(String where) {
		List<Course> list = this.getHibernateTemplate().find("from Course " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from Course "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<Course> selectBeanList(final int start,final int limit,final String where) {
		return (List<Course>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<Course> list = session.createQuery("from Course "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(Course Course) {
		this.getHibernateTemplate().update(Course);
		
	}
	
	
}
