package dao;

import java.util.List;

import model.College;



public interface CollegeDao {

	
	//插入新纪录
	public void insertBean(College bean);
	
	//根据id删除纪录
	public void deleteBean(College bean);
	
	//根据id更新纪录
	public void updateBean(College bean);

	//获取信息列表,带查询功能，start 表示当前页，limit表示每页显示的条数 start=1,lmit=10
	public List<College> selectBeanList(final int start,final int limit,final String where);
	
	
	//查询记录的总的条数
	public long selectBeanCount(final String where);
	
	//查询信息
	public College selectBean(String where);
	

}
