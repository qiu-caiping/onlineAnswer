package action;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Answering;
import model.College;
import model.Course;
import model.Fenlei;
import model.Kaoshi;
import model.Kaoshijilu;
import model.Shiti;
import model.User;

import org.apache.struts2.ServletActionContext;
import util.Pager;
import util.Util;

import com.opensymphony.xwork2.ActionSupport;

import dao.AnsweringDao;
import dao.CollegeDao;
import dao.CourseDao;
import dao.FenleiDao;
import dao.KaoshiDao;
import dao.KaoshijiluDao;
import dao.ShitiDao;
import dao.UserDao;

public class ManageAction extends ActionSupport{
	
	
	private static final long serialVersionUID = -4304509122548259589L;
	
	private String url = "./";
	private UserDao userDao;
	private FenleiDao fenleiDao;
	private CollegeDao collegeDao;
	private AnsweringDao answeringDao;
	private CourseDao courseDao;
	
	
	
	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	public AnsweringDao getAnsweringDao() {
		return answeringDao;
	}

	public void setAnsweringDao(AnsweringDao answeringDao) {
		this.answeringDao = answeringDao;
	}



	public FenleiDao getFenleiDao() {
		return fenleiDao;
	}

	public void setFenleiDao(FenleiDao fenleiDao) {
		this.fenleiDao = fenleiDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public CollegeDao getCollegeDao() {
		return collegeDao;
	}

	public void setCollegeDao(CollegeDao collegeDao) {
		this.collegeDao = collegeDao;
	}
	
	

	//用户登陆操作
	public void login() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Integer role = Integer.parseInt(request.getParameter("role"));
		User user = userDao.selectBean(" where username='"+username+"' and password='"+password+"' and  userlock=0  and role="+role);
		if(user!=null){
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			response.setCharacterEncoding("gbk");
			PrintWriter writer = response.getWriter();
			writer.print("<script  language='javascript'>alert('登陆成功');window.location.href='index.jsp'; </script>");
		}else{
			response.setCharacterEncoding("gbk");
			PrintWriter writer = response.getWriter();
			writer.print("<script  language='javascript'>alert('用户名或者密码错误');window.location.href='login.jsp'; </script>");
		}

	}
	
	//用户退出操作
	public String loginout() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		this.setUrl("login.jsp");
		return SUCCESS;
	}
	
	
	//跳转到修改密码页面
	public String changepwd() {
		this.setUrl("user/password.jsp");
		return SUCCESS;
	}
	
	//修改密码操作
	public void changepwd2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		
		HttpSession session = request.getSession();
		User u = (User)session.getAttribute("user");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		User bean = userDao.selectBean(" where username= '"+u.getUsername()+"' and password= '"+password1+"'");
		if(bean!=null){
			bean.setPassword(password2);
			userDao.updateBean(bean);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf8");response.setContentType("text/html; charset=utf8");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('修改成功');</script>");
		}else{
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf8");response.setContentType("text/html; charset=utf8");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('用户名或者密码错误');</script>");
		}
	}
	
	/*******************************管理员权限*****************************************/
	
	
	//学生信息管理
	public String userlist(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String username=request.getParameter("username");
        StringBuffer sb = new StringBuffer();
        sb.append(" where ");
        if(username!=null&&!"".equals(username) ){
        	sb.append("username like '%"+username+"%'");
        	sb.append(" and ");
        	request.setAttribute("username", username);
        }
        sb.append(" role=2 ");
        sb.append(" and ");
        sb.append(" userlock=0 order by id ");
		int currentpage = 1;
		int pagesize = 10;
		if(request.getParameter("pagenum") != null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		String where =sb.toString();
		long total = userDao.selectBeanCount(where.replaceAll(" order by id desc", ""));
		List<User> list = userDao.selectBeanList(currentpage-1, pagesize, where);
		request.setAttribute("list", list);
		String pagerinfo = Pager.getPagerNormal((int)total, pagesize, currentpage, "method!userlist", "共有"+total+"条记录");
		request.setAttribute("pagerinfo", pagerinfo);
		this.setUrl("user/userlist.jsp");
		return SUCCESS;
	}
	
	
	//跳转到添加学生页面
	public String useradd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<College> list = collegeDao.selectBeanList(0, 99," where collegelock=0 ");
		request.setAttribute("list", list);
		this.setUrl("user/useradd.jsp");
		return SUCCESS;
	}
	
	
	//添加学生操作
	public void useradd2() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String college = request.getParameter("college");
		String truename = request.getParameter("truename");
		String telephone = request.getParameter("telephone");
		String xingbie = request.getParameter("xingbie");
		String age = request.getParameter("age");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
	
		User bean=userDao.selectBean(" where username='"+username+"' and userlock=0 ");
		if(bean==null){
		bean =new User();
		bean.setCollege(collegeDao.selectBean(" where id="+college)); 
		bean.setTruename(truename);
		bean.setTelephone(telephone);
		bean.setAge(age);
		bean.setXingbie(xingbie);	
		bean.setRole(2);
		bean.setUsername(username);
		bean.setPassword(password);
		bean.setCreatetime(new Date());
		userDao.insertBean(bean);
		response.setCharacterEncoding("utf8");
		PrintWriter writer = response.getWriter();
		writer.print("<script  language='javascript'>alert('提交成功');window.location.href='method!userlist'; </script>");
		}else{
			response.setCharacterEncoding("utf8");
			PrintWriter writer = response.getWriter();
			writer.print("<script  language='javascript'>alert('该用户名已存在，请重新添加');window.location.href='method!userlist'; </script>");
		}
	}
	
	
	
	//删除学生操作
	public void userdelete() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String id = request.getParameter("id");
		User bean =userDao.selectBean(" where id= "+id);
		bean.setUserlock(1);
		userDao.updateBean(bean);
		response.setCharacterEncoding("utf8");
		PrintWriter writer = response.getWriter();
		writer.print("<script  language='javascript'>alert('删除成功');window.location.href='method!userlist'; </script>");
		
	}
	
	//跳转到更新学生页面
	public String userupdate(){
		HttpServletRequest request = ServletActionContext.getRequest(); //**
		String id = request.getParameter("id");
		User bean =userDao.selectBean(" where id= "+id);
		request.setAttribute("bean", bean);
		List<College> list = collegeDao.selectBeanList(0, 99," where collegelock=0 ");
		request.setAttribute("list", list);
		this.setUrl("user/userupdate.jsp");
		return SUCCESS;
	}

	
	//更新学生操作
	public void userupdate2() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String college = request.getParameter("college");
		String truename = request.getParameter("truename");
		String telephone = request.getParameter("telephone");
		String xingbie = request.getParameter("xingbie");
		String age = request.getParameter("age");
		String id = request.getParameter("id");
		User bean =userDao.selectBean(" where id= "+id);
		bean.setCollege(collegeDao.selectBean(" where id="+college)); 
		bean.setTruename(truename);
		bean.setTelephone(telephone);
		bean.setAge(age);
		bean.setXingbie(xingbie);	
		userDao.updateBean(bean);
		response.setCharacterEncoding("utf8");
		PrintWriter writer = response.getWriter();
		writer.print("<script  language='javascript'>alert('更新成功');window.location.href='method!userlist'; </script>");
		
	}
	
	
	//教师
	//教师信息管理
	public String t_userlist(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String username=request.getParameter("username");
        StringBuffer sb = new StringBuffer();
        sb.append(" where ");
        if(username!=null&&!"".equals(username) ){
        	sb.append("username like '%"+username+"%'");
        	sb.append(" and ");
        	request.setAttribute("username", username);
        }
        sb.append(" role=3 ");
        sb.append(" and ");
        sb.append(" userlock=0 order by id ");
		int currentpage = 1;
		int pagesize = 10;
		if(request.getParameter("pagenum") != null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		String where =sb.toString();
		long total = userDao.selectBeanCount(where.replaceAll(" order by id desc", ""));
		List<User> list = userDao.selectBeanList(currentpage-1, pagesize, where);
		request.setAttribute("list", list);
		String pagerinfo = Pager.getPagerNormal((int)total, pagesize, currentpage, "method!t_userlist", "共有"+total+"条记录");
		request.setAttribute("pagerinfo", pagerinfo);
		this.setUrl("user/t_userlist.jsp");
		return SUCCESS;
	}
	
	
	//跳转到添加教师页面
	public String t_useradd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<College> list = collegeDao.selectBeanList(0, 99," where collegelock=0 ");
		request.setAttribute("list", list);
		this.setUrl("user/t_useradd.jsp");
		return SUCCESS;
	}
	
	
	//添加教师操作
	public void t_useradd2() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		String college = request.getParameter("college");
		String truename = request.getParameter("truename");
		String telephone = request.getParameter("telephone");
		String xingbie = request.getParameter("xingbie");
		String age = request.getParameter("age");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
	
		User bean=userDao.selectBean(" where username='"+username+"' and userlock=0 ");
		if(bean==null){
		bean =new User();
		bean.setCollege(collegeDao.selectBean(" where id="+college)); 
		bean.setTruename(truename);
		bean.setTelephone(telephone);
		bean.setAge(age);
		bean.setXingbie(xingbie);	
		bean.setRole(3);
		bean.setUsername(username);
		bean.setPassword(password);
		bean.setCreatetime(new Date());
		userDao.insertBean(bean);
		response.setCharacterEncoding("utf8");
		PrintWriter writer = response.getWriter();
		writer.print("<script  language='javascript'>alert('提交成功');window.location.href='method!t_userlist'; </script>");
		}else{
			response.setCharacterEncoding("utf8");
			PrintWriter writer = response.getWriter();
			writer.print("<script  language='javascript'>alert('该用户名已存在，请重新添加');window.location.href='method!t_userlist'; </script>");
		}
		
		
	}
	
	
	
	//删除教师操作
	public void t_userdelete() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String id = request.getParameter("id");
		User bean =userDao.selectBean(" where id= "+id);
		bean.setUserlock(1);
		userDao.updateBean(bean);
		response.setCharacterEncoding("utf8");
		PrintWriter writer = response.getWriter();
		writer.print("<script  language='javascript'>alert('删除成功');window.location.href='method!t_userlist'; </script>");
		
	}
	
	//跳转到更新教师页面
	public String t_userupdate(){
		HttpServletRequest request = ServletActionContext.getRequest(); //**
		String id = request.getParameter("id");
		User bean =userDao.selectBean(" where id= "+id);
		request.setAttribute("bean", bean);
		List<College> list = collegeDao.selectBeanList(0, 99," where collegelock=0 ");
		request.setAttribute("list", list);
		this.setUrl("user/t_userupdate.jsp");
		return SUCCESS;
	}

	
	//更新教师操作
	public void t_userupdate2() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String college = request.getParameter("college");
		String truename = request.getParameter("truename");
		String telephone = request.getParameter("telephone");
		String xingbie = request.getParameter("xingbie");
		String age = request.getParameter("age");
		String id = request.getParameter("id");
		User bean =userDao.selectBean(" where id= "+id);
		bean.setCollege(collegeDao.selectBean(" where id="+college)); 
		bean.setTruename(truename);
		bean.setTelephone(telephone);
		bean.setAge(age);
		bean.setXingbie(xingbie);	
		response.setCharacterEncoding("utf8");
		PrintWriter writer = response.getWriter();
		writer.print("<script  language='javascript'>alert('更新成功');window.location.href='method!t_userlist'; </script>");
		
	}
	
	/**********************题库**********************/
	//题库列表
	public String fenleilist(){
		HttpServletRequest request = ServletActionContext.getRequest();		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		StringBuffer sb = new StringBuffer();

		sb.append("where  fenleilock=0  and  user="+user.getId()+" order by id desc ");
		String where = sb.toString();
		int currentpage = 1;
		int pagesize = 10;
		if(request.getParameter("pagenum") != null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		
		long total = fenleiDao.selectBeanCount(where.replaceAll("order by id desc", ""));
		List<Fenlei> list = fenleiDao.selectBeanList((currentpage-1)*pagesize, pagesize, where);
		request.setAttribute("list", list);
		String pagerinfo = Pager.getPagerNormal((int)total, pagesize, currentpage, "method!fenleilist", "共有"+total+"条记录");
		request.setAttribute("pagerinfo", pagerinfo);
		this.setUrl("fenlei/fenleilist.jsp");
		return SUCCESS;
	}
	
	
	//跳转到添加题库页面
	public String fenleiadd(){
		this.setUrl("fenlei/fenleiadd.jsp");
		return SUCCESS;
	}
	
	
	//添加题库操作
	public void fenleiadd2() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String name = request.getParameter("name");
		Fenlei bean = new Fenlei();
		bean.setName(name);
		bean.setUser(user);
		bean.setCreatetime(new Date());
		fenleiDao.insertBean(bean);
		response.setCharacterEncoding("utf-8");response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.print("<script  language='javascript'>alert('提交成功');window.location.href='method!fenleilist'; </script>");
		
	}
	
	
	
	//删除题库操作
	public void fenleidelete() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String id = request.getParameter("id");
		Fenlei bean =fenleiDao.selectBean(" where id= "+id);
		bean.setFenleilock(1);
		fenleiDao.updateBean(bean);
		response.setCharacterEncoding("utf-8");response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.print("<script  language='javascript'>alert('提交成功');window.location.href='method!fenleilist'; </script>");
		
	}
	
	//跳转到更新题库页面
	public String fenleiupdate(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		Fenlei bean =fenleiDao.selectBean(" where id= "+id);
		request.setAttribute("bean", bean);
		this.setUrl("fenlei/fenleiupdate.jsp");
		return SUCCESS;
	}
	
	
	//更新题库操作
	public void fenleiupdate2() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		Fenlei bean =fenleiDao.selectBean(" where id= "+id);
		bean.setName(name);
		fenleiDao.updateBean(bean);
		response.setCharacterEncoding("utf-8");response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.print("<script  language='javascript'>alert('提交成功');window.location.href='method!fenleilist'; </script>");
		
	}
	
	
	
	private ShitiDao shitiDao;


	public ShitiDao getShitiDao() {
		return shitiDao;
	}

	public void setShitiDao(ShitiDao shitiDao) {
		this.shitiDao = shitiDao;
	}
	


	//试题列表
	public String shitilist()  {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");	
		String wenti = request.getParameter("wenti");
		String fenlei = request.getParameter("fenlei");
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		if(wenti!=null&&!"".equals(wenti)){
			sb.append("wenti like '%"+wenti+"%'");
			sb.append(" and ");
			request.setAttribute("wenti", wenti);
		}
		if(fenlei!=null&&!"".equals(fenlei)){
			sb.append("fenlei.id = '"+fenlei+"'");
			sb.append(" and ");
			request.setAttribute("fenlei", fenlei);
		}
		sb.append(" shitilock=0 and  fenlei.user="+user.getId()+"  and fenlei.fenleilock=0 order by id desc");
		String where = sb.toString();
		int currentpage = 1;
		int pagesize =10;
		if(request.getParameter("pagenum")!=null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = shitiDao.selectBeanCount(where);
		request.setAttribute("fenleilist", fenleiDao.selectBeanList(0, 99, " where fenleilock=0 and user="+user.getId()+" "));
		request.setAttribute("list", shitiDao.selectBeanList((currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,currentpage, "method!shitilist", "共有" + total + "条记录"));
	    this.setUrl("shiti/shitilist.jsp");
		return SUCCESS;
	}
	

//跳转到添加试题页面
	public String shitiadd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");	
		List<Fenlei> list = fenleiDao.selectBeanList(0, 9999, " where fenleilock=0 and user="+user.getId()+" ");
		request.setAttribute("list", list);
		this.setUrl("shiti/shitiadd.jsp");
		return SUCCESS;
	}
	
	
	
//添加试题操作
	public void shitiadd2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String wenti = request.getParameter("wenti");
		String daan = request.getParameter("daan");
		String fenlei = request.getParameter("fenlei");
			Shiti bean = new Shiti();
			bean.setCreatetime(new Date());
			bean.setWenti(wenti);
			bean.setDaan(daan);
			bean.setFenlei(fenleiDao.selectBean(" where id= "+fenlei));	
			shitiDao.insertBean(bean);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf8");response.setContentType("text/html; charset=utf8");
			response.getWriter().print("<script language=javascript>alert('添加成功');window.location.href='method!shitilist';</script>");
		
		
		
	}
	
	//删除试题操作
	public void shitidelete() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		Shiti bean = shitiDao.selectBean(" where id= "+request.getParameter("id"));
		bean.setShitilock(1);
		shitiDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf8");response.setContentType("text/html; charset=utf8");
		response.getWriter().print("<script language=javascript>alert('删除成功');window.location.href='method!shitilist';</script>");
	}
	
	
	
//跳转到更新试题页面
	public String shitiupdate() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Shiti bean = shitiDao.selectBean(" where id= "+request.getParameter("id"));
		request.setAttribute("bean", bean);
		this.setUrl("shiti/shitiupdate.jsp");
		return SUCCESS;
	}
	
//更新试题操作
	public void shitiupdate2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String wenti = request.getParameter("wenti");
		String daan = request.getParameter("daan");
		Shiti bean = shitiDao.selectBean(" where id= "+request.getParameter("id"));;
		bean.setWenti(wenti);
		bean.setDaan(daan);
		shitiDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf8");response.setContentType("text/html; charset=utf8");
		response.getWriter().print("<script language=javascript>alert('操作成功');window.location.href='method!shitilist';</script>");
	}
	
	

//查看试题操作
	public String shitiupdate3() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Shiti bean = shitiDao.selectBean(" where id= "+request.getParameter("id"));
		request.setAttribute("bean", bean);
		this.setUrl("shiti/shitiupdate3.jsp");
		return SUCCESS;
	}
	

	
	
	
	
	/*******************************用户权限*****************************************/
	//个人信息管理
	public String userlist2(){
		HttpServletRequest request = ServletActionContext.getRequest();
		int currentpage = 1;
		int pagesize = 10;
		if(request.getParameter("pagenum") != null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");	
		String where =" where userlock=0 and id="+user.getId()+" order by id desc "; 
		List<User> list = userDao.selectBeanList((currentpage - 1) * pagesize, pagesize, where);
		request.setAttribute("list", list);
		this.setUrl("user/userlist2.jsp");
		return SUCCESS;
	}
	
	//跳转到测试页面
	public String kaoshiadd() {
		this.setUrl("kaoshi/kaoshiadd.jsp");
		return SUCCESS;
	}
	
	
	//num随机的数量
	private static List<Shiti> suiji(List<Shiti> list,int num){
		Collections.shuffle(list);
		List<Shiti> list2 = new ArrayList<Shiti>();
		if(list.size()<=num){
			num = list.size();
		}
		for(int i=0;i<num;i++){
			list2.add(list.get(i));
		}
		return list2;
	}
	
	
	
//确认开始测试操作
	public String kaoshiadd2() throws IOException   {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");	
		String fenlei = request.getParameter("fenlei");
		Kaoshijilu k=kaoshijiluDao.selectBean(" where kaoshi.user.id="+user.getId()+" and shiti.fenlei.id="+fenlei+" ");
		if(k!=null){
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf8");
			PrintWriter writer = response.getWriter();
			writer.print("<script  language='javascript'>alert('您已经参加过本题库测试，无权再次参加');window.location.href='main.jsp'; </script>");
			return null;

		}

		long s = shitiDao.selectBeanCount(" where fenlei.id="+fenlei+"  and shitilock=0  ");
		if(s<5){
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf8");
			PrintWriter writer = response.getWriter();
			writer.print("<script  language='javascript'>alert(' 试题数量未达到，本题库测试，未开放');window.location.href='main.jsp'; </script>");
			return null;
		}
		List<Shiti> list1 = ManageAction.suiji(shitiDao.selectBeanList(0, 9999, " where  fenlei.id="+fenlei+" and shitilock=0 "), 5);//5道试题
		request.setAttribute("list1", list1);
		request.setAttribute("fenlei", fenlei);
		this.setUrl("kaoshi/kaoshiadd2.jsp");
		return SUCCESS;

	}
	
	
	
	private KaoshiDao kaoshiDao;
	
	private KaoshijiluDao kaoshijiluDao;


	public KaoshiDao getKaoshiDao() {
		return kaoshiDao;
	}

	public void setKaoshiDao(KaoshiDao kaoshiDao) {
		this.kaoshiDao = kaoshiDao;
	}

	public KaoshijiluDao getKaoshijiluDao() {
		return kaoshijiluDao;
	}

	public void setKaoshijiluDao(KaoshijiluDao kaoshijiluDao) {
		this.kaoshijiluDao = kaoshijiluDao;
	}
	
	

	
	
	//随机卷提交操作
	public String kaoshiadd3() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();

		HttpSession session  = request.getSession();
		User user = (User) session.getAttribute("user");
		
		Kaoshi bean = new Kaoshi();
		bean.setCreatetime(new Date());
				
		bean.setUser(user);
		kaoshiDao.insertBean(bean);

		for(int i=0;i<5;i++){
			Shiti shiti = shitiDao.selectBean(" where id= "+request.getParameter("xzid"+i));
			String wodedaan = request.getParameter("xzdaan"+i);
			Kaoshijilu jilu = new Kaoshijilu();
			jilu.setCreatetime(new Date());
			if(shiti.getDaan().equals(wodedaan)){
				jilu.setDefen(20);
				bean.setFenshu(bean.getFenshu()+20);
				kaoshiDao.updateBean(bean);
			}
			jilu.setKaoshi(bean);
			jilu.setShiti(shiti);
			jilu.setWodedaan(wodedaan);
			kaoshijiluDao.insertBean(jilu);
		}

		this.setUrl("kaoshi/kaoshiadd3.jsp");
		return SUCCESS;

	}
	
	//查看本次测试
	public String kaoshijilulist() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String fenlei = request.getParameter("fenlei");
        StringBuffer sb = new StringBuffer();
        sb.append(" where ");
        
        sb.append(" kaoshi.user.id="+user.getId()+" and shiti.fenlei.id="+fenlei+" order by id ");
		int currentpage = 1;
		int pagesize = 15;
		if(request.getParameter("pagenum") != null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		String where =sb.toString();
		long total = kaoshijiluDao.selectBeanCount(where.replaceAll(" order by id desc", ""));
		List<Kaoshijilu> list = kaoshijiluDao.selectBeanList((currentpage-1)*pagesize, pagesize, where);
		request.setAttribute("list", list);
		Kaoshijilu k=kaoshijiluDao.selectBean(" where kaoshi.user.id="+user.getId()+" and shiti.fenlei.id="+fenlei+" ");
		if(k==null){
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf8");
			PrintWriter writer = response.getWriter();
			writer.print("<script  language='javascript'>alert('您未参加过测试');window.location.href='main.jsp'; </script>");
			return null;

		}
		request.setAttribute("k", k.getKaoshi().getFenshu());
		String pagerinfo = Pager.getPagerNormal((int)total, pagesize, currentpage, "method!kaoshijilulist", "共有"+total+"条记录");
		request.setAttribute("pagerinfo", pagerinfo);
		this.setUrl("kaoshijilu/kaoshijilulist.jsp");
		return SUCCESS;
	}
	
	
	
	
	
	
	
	
	/********************学院模块************************/
	//学院信息列表
	public String collegelist(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String name = request.getParameter("name");
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		if(name !=null &&!"".equals(name)){
			sb.append(" name like '%"+name+"%' ");
			sb.append(" and ");
			request.setAttribute("name", name);
		}
		sb.append(" collegelock=0  order by id desc ");
		String where = sb.toString();
		int currentpage = 1;
		int pagesize = 10;
		if(request.getParameter("pagenum") != null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		long total = collegeDao.selectBeanCount(where.replaceAll("order by id desc", ""));
		List<College> list = collegeDao.selectBeanList((currentpage-1)*pagesize, pagesize, where);
		request.setAttribute("list", list);
		String pagerinfo = Pager.getPagerNormal((int)total, pagesize, currentpage, "method!collegelist", "共有"+total+"条记录");
		request.setAttribute("pagerinfo", pagerinfo);
		this.setUrl("college/collegelist.jsp");
		return SUCCESS;
	}
	
	
	//跳转到添加学院页面
	public String collegeadd() throws IOException{
		this.setUrl("college/collegeadd.jsp");
		return SUCCESS;
	}
	
	
	//添加学院操作
	public void collegeadd2() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String name = request.getParameter("name");
		College bean=collegeDao.selectBean(" where name='"+name+"' and collegelock=0 ");
		if(bean==null){
		bean = new College();
		bean.setName(name);
		bean.setCreatetime(new Date());
		collegeDao.insertBean(bean);
		response.setCharacterEncoding("utf-8");response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.print("<script  language='javascript'>alert('添加成功');window.location.href='method!collegelist'; </script>");
		}else{
			response.setCharacterEncoding("utf-8");response.setContentType("text/html; charset=utf-8");
			PrintWriter writer = response.getWriter();
			writer.print("<script  language='javascript'>alert('该学院名，已经存在请重新添加');window.location.href='method!collegeadd'; </script>");
		}
	}
	
	
	
	//删除学院操作
	public void collegedelete() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String id = request.getParameter("id");
		College bean =collegeDao.selectBean(" where id= "+id);
		bean.setCollegelock(1);
		collegeDao.updateBean(bean);
		response.setCharacterEncoding("utf-8");response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.print("<script  language='javascript'>alert('删除成功');window.location.href='method!collegelist'; </script>");
		
	}
	
	
	//跳转到学院修改页面
	public String collegeupdate() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		College bean =collegeDao.selectBean(" where id= "+id);
		request.setAttribute("bean", bean);
		this.setUrl("college/collegeupdate.jsp");
		return SUCCESS;
	}
	
	
	//学院修改操作
	public void collegeupdate2() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		College bean =collegeDao.selectBean(" where id= "+id);
		bean.setName(name);
		collegeDao.updateBean(bean);
		response.setCharacterEncoding("utf-8");response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.print("<script  language='javascript'>alert('修改成功');window.location.href='method!collegelist'; </script>");
		
	}
	
	
	//学生留言列表(学生)
	public String answeringlist(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");	
		int currentpage = 1;
		int pagesize = 15;
		if(request.getParameter("pagenum") != null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		
		String where =" where user="+user.getId()+" order by id desc ";
		
		long total =  answeringDao.selectBeanCount(where);
		List<Answering> list =  answeringDao.selectBeanList((currentpage-1)*pagesize, pagesize, where);
		request.setAttribute("list", list);
		List<User> userlist = userDao.selectBeanList(0, 99, " where role=3 and userlock=0");
		request.setAttribute("userlist", userlist);
		String pagerinfo = Pager.getPagerNormal((int)total, pagesize, currentpage, "method!answeringlist", "共有"+total+"条记录");
		request.setAttribute("pagerinfo", pagerinfo);
		this.setUrl("answering/answeringlist.jsp");
		return SUCCESS;
	}
	
	
	//添加留言操作
	public void answeringadd() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
     
		String mywords = request.getParameter("mywords");
		String user0 = request.getParameter("user0");
	    HttpSession session = request.getSession();
	    User user = (User)session.getAttribute("user");	
	    user=userDao.selectBean("where id="+user.getId()+" ");
	    
		Answering bean = new Answering();
		bean.setMywords(mywords);	
		bean.setCreateDate(new Date());
		bean.setUser(user);
		bean.setUser0(userDao.selectBean(" where id="+user0));
		answeringDao.insertBean(bean);	
		response.setCharacterEncoding("utf8");
		PrintWriter writer = response.getWriter();
		writer.print("<script  language='javascript'>alert('发送成功');window.location.href='method!answeringlist'; </script>");
		
	}
	
	
	//教师回复列表(教师)
	public String answeringlist2(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");	
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		if(user.getRole()==1){
			sb.append("  1=1 order by id desc ");
		}
		if(user.getRole()==3){
			sb.append("  user0="+user.getId()+" order by id desc ");
		}
		String where = sb.toString();
		int currentpage = 1;
		int pagesize = 15;
		if(request.getParameter("pagenum") != null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		long total =  answeringDao.selectBeanCount(where);
		List<Answering> list =  answeringDao.selectBeanList((currentpage-1)*pagesize, pagesize, where);
		request.setAttribute("list", list);
		String pagerinfo = Pager.getPagerNormal((int)total, pagesize, currentpage, "method!answeringlist2", "共有"+total+"条记录");
		request.setAttribute("pagerinfo", pagerinfo);
		this.setUrl("answering/answeringlist2.jsp");
		return SUCCESS;
	}
	
	//跳转到回复页面
	public String answeringupdate(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		Answering bean =answeringDao.selectBean(" where id= "+id);
		request.setAttribute("bean", bean);
		this.setUrl("answering/answeringupdate.jsp");
		return SUCCESS;
	}
	
	
	//回复操作
	public void answeringupdate2() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String rescontent = request.getParameter("rescontent");
		String id = request.getParameter("id");
		Answering bean =answeringDao.selectBean(" where id= "+id);
		bean.setRescontent(rescontent);
		answeringDao.updateBean(bean);
		response.setCharacterEncoding("utf8");
		PrintWriter writer = response.getWriter();
		writer.print("<script  language='javascript'>alert('提交成功');window.location.href='method!answeringlist2'; </script>");
		
	}
	

	//删除留言操作
	public void answeringdelete() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String id = request.getParameter("id");
		Answering bean =answeringDao.selectBean(" where id= "+id);
		answeringDao.deleteBean(bean);
		response.setCharacterEncoding("utf-8");response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.print("<script  language='javascript'>alert('删除成功');window.location.href='method!answeringlist2'; </script>");
		
	}
	
	/*************课程信息****************/
	//课程信息管理
	public String courselist(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");	
		String biaoti=request.getParameter("biaoti");
        StringBuffer sb = new StringBuffer();
        sb.append(" where ");
        if(biaoti!=null&&!"".equals(biaoti) ){
        	sb.append("biaoti like '%"+biaoti+"%'");
        	sb.append(" and ");
        	request.setAttribute("biaoti", biaoti);
        }
        if(user.getRole()!=3){
        	sb.append(" courselock=0 order by id desc");
        }
        if(user.getRole()==3){
        sb.append(" courselock=0 and  user="+user.getId()+" order by id desc");
        }
		int currentpage = 1;
		int pagesize = 10;
		if(request.getParameter("pagenum") != null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		String where =sb.toString();
		long total = courseDao.selectBeanCount(where.replaceAll(" order by id desc", ""));
		List<Course> list = courseDao.selectBeanList((currentpage-1)*pagesize, pagesize, where);
		request.setAttribute("list", list);
		String pagerinfo = Pager.getPagerNormal((int)total, pagesize, currentpage, "method!courselist", "共有"+total+"条记录");
		request.setAttribute("pagerinfo", pagerinfo);
		this.setUrl("course/courselist.jsp");
		return SUCCESS;
	}
	
	
	
	//跳转到添加课程
	public String courseadd(){
		this.setUrl("course/courseadd.jsp");
		return SUCCESS;
	}
	

	//添加课程操作
	public void courseadd2()throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");	
		String biaoti = request.getParameter("biaoti");
		String jianjie = request.getParameter("jianjie");
		String fujian = request.getParameter("fujian");
		String fujianYuanshiming = request.getParameter("fujianYuanshiming");
		
		Date createtime=new Date();
		Course bean=courseDao.selectBean(" where biaoti='"+biaoti+"' and courselock=0 ");
		if(bean==null){

			    bean=new Course();
			    bean.setBiaoti(biaoti);
			    bean.setJianjie(jianjie);
			    bean.setFujian(fujian);
				bean.setFujianYuanshiming(fujianYuanshiming);
				bean.setUser(user);
				bean.setCreatetime(createtime);
				courseDao.insertBean(bean);
				response.setCharacterEncoding("utf8");
				PrintWriter writer=response.getWriter();
				writer.print("<script language='javascript'>alert('提交成功');window.location.href='method!courselist'; </script> ");
			
		}
		else{
			response.setCharacterEncoding("utf8");
			PrintWriter writer=response.getWriter();
			writer.print("<script language='javascript'>alert('提交失败，该课程已经添加过');window.location.href='method!courselist'; </script> ");
		
		}
	   }
	
	
	//删除课程操作
	public void coursedelete() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String id = request.getParameter("id");
		Course bean =courseDao.selectBean(" where id= "+id);
		bean.setCourselock(1);
		courseDao.updateBean(bean);
		response.setCharacterEncoding("utf8");
		PrintWriter writer = response.getWriter();
		writer.print("<script  language='javascript'>alert('提交成功');window.location.href='method!courselist'; </script>");
		
	}
	
	//跳转到更新课程页面
	public String courseupdate(){
		HttpServletRequest request = ServletActionContext.getRequest(); 
		String id = request.getParameter("id");
		Course bean =courseDao.selectBean(" where id= "+id);
		request.setAttribute("bean", bean);
		
		this.setUrl("course/courseupdate.jsp");
		return SUCCESS;
	}

	
	//更新课程操作
	public void courseupdate2() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String biaoti = request.getParameter("biaoti");
		String jianjie = request.getParameter("jianjie");
		String fujian = request.getParameter("fujian");
		String fujianYuanshiming = request.getParameter("fujianYuanshiming");
		Date createtime=new Date();
		String id = request.getParameter("id");
		Course bean =courseDao.selectBean(" where id= "+id);
		bean.setBiaoti(biaoti);
	    bean.setJianjie(jianjie);
	    bean.setFujian(fujian);
		bean.setFujianYuanshiming(fujianYuanshiming);
		bean.setCreatetime(createtime);	
		courseDao.updateBean(bean);
		response.setCharacterEncoding("utf8");
		PrintWriter writer = response.getWriter();
		writer.print("<script  language='javascript'>alert('修改成功');window.location.href='method!courselist'; </script>");
		
	}
	
	//跳转到查看课程详情
	public String courseupdate3(){
		HttpServletRequest request = ServletActionContext.getRequest(); 
		String id = request.getParameter("id");
		Course bean =courseDao.selectBean(" where id= "+id);
		request.setAttribute("bean", bean);
		this.setUrl("course/courseupdate3.jsp");
		return SUCCESS;
	}
	
	
	//课程信息管理
	public String courselist2(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String biaoti=request.getParameter("biaoti");
        StringBuffer sb = new StringBuffer();
        sb.append(" where ");
        if(biaoti!=null&&!"".equals(biaoti) ){
        	sb.append("biaoti like '%"+biaoti+"%'");
        	sb.append(" and ");
        	request.setAttribute("biaoti", biaoti);
        }
        	sb.append(" courselock=0 order by id desc");
        
		int currentpage = 1;
		int pagesize = 10;
		if(request.getParameter("pagenum") != null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		String where =sb.toString();
		long total = courseDao.selectBeanCount(where.replaceAll(" order by id desc", ""));
		List<Course> list = courseDao.selectBeanList((currentpage-1)*pagesize, pagesize, where);
		request.setAttribute("list", list);
		String pagerinfo = Pager.getPagerNormal((int)total, pagesize, currentpage, "method!courselist2", "共有"+total+"条记录");
		request.setAttribute("pagerinfo", pagerinfo);
		this.setUrl("course/courselist2.jsp");
		return SUCCESS;
	}
	
	
}
