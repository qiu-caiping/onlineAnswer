package dao;

import java.util.List;

import model.Answering;


public interface AnsweringDao  {
	
	
	
	public void insertBean(Answering Answering);
	
	public void deleteBean(Answering Answering);
	
	public void updateBean(Answering Answering);

	public Answering selectBean(String where);
	
	public List<Answering> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
