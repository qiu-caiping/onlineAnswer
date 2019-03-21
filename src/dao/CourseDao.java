package dao;

import java.util.List;

import model.Course;


public interface CourseDao  {
	
	
	
	public void insertBean(Course Course);
	
	public void deleteBean(Course Course);
	
	public void updateBean(Course Course);

	public Course selectBean(String where);
	
	public List<Course> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
