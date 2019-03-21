package dao;

import java.util.List;

import model.Shiti;


public interface ShitiDao  {
	
	
	
	public void insertBean(Shiti Shiti);
	
	public void deleteBean(Shiti Shiti);
	
	public void updateBean(Shiti Shiti);

	public Shiti selectBean(String where);
	
	public List<Shiti> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
