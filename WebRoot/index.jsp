<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
if((model.User)session.getAttribute("user")==null){
	response.sendRedirect("login.jsp");
	return;
}
%>
 <%
org.springframework.web.context.WebApplicationContext app = org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
dao.FenleiDao fenleiDao = (dao.FenleiDao)app.getBean("fenleiDao");
List<model.Fenlei> list = fenleiDao.selectBeanList(0,9999," where fenleilock=0 ");

%>
<!doctype html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="css/common.css">
  <link rel="stylesheet" href="css/style.css">
  <script type="text/javascript" src="js/jquery.min.js"></script>
  <script type="text/javascript" src="js/jquery.SuperSlide.js"></script>
  <script type="text/javascript">
  $(function(){
      $(".sideMenu").slide({
         titCell:"h3", 
         targetCell:"ul",
         defaultIndex:0, 
         effect:'slideDown', 
         delayTime:'500' , 
         trigger:'click', 
         triggerTime:'150', 
         defaultPlay:true, 
         returnDefault:false,
         easing:'easeInQuint',
         endFun:function(){
              scrollWW();
         }
       });
      $(window).resize(function() {
          scrollWW();
      });
  });
  function scrollWW(){
    if($(".side").height()<$(".sideMenu").height()){
       $(".scroll").show();
       var pos = $(".sideMenu ul:visible").position().top-38;
       $('.sideMenu').animate({top:-pos});
    }else{
       $(".scroll").hide();
       $('.sideMenu').animate({top:0});
       n=1;
    }
  } 

var n=1;
function menuScroll(num){
  var Scroll = $('.sideMenu');
  var ScrollP = $('.sideMenu').position();
  /*alert(n);
  alert(ScrollP.top);*/
  if(num==1){
     Scroll.animate({top:ScrollP.top-38});
     n = n+1;
  }else{
    if (ScrollP.top > -38 && ScrollP.top != 0) { ScrollP.top = -38; }
    if (ScrollP.top<0) {
      Scroll.animate({top:38+ScrollP.top});
    }else{
      n=1;
    }
    if(n>1){
      n = n-1;
    }
  }
}
  </script>

</head>
<body>
    <div class="top" align="center">
      <div id="top_t" >
        <div class="fl" style="padding-top:15px " >
        <span style="font-size: 35px; font-weight: bold;color: white;"> 高校在线答疑系统
        </span>
        </div>
        <div id="photo_info" class="fr" >
          <div id="photo" class="fl">
            <a href="#"><img src="images/a.png" alt="" width="60" height="60"></a>
            
          </div>
          <div style="margin:40px;padding-right:20px " align="center">
              <a href="method!changepwd" target="right"><span style="font-size: 15px;font-weight: bold;color: white;">密码修改&nbsp;||</span></a>
              <a href="method!loginout" ><span style="font-size: 15px;font-weight: bold;color: white;">退出&nbsp;</span></a>
          </div>
        </div>
      </div>
      <div id="side_here">
        <div id="side_here_l" class="fl"></div>
        <div id="here_area" class="fl">
                  
                   当前角色： <c:if test="${user.role==1}">系统管理员</c:if>
			     <c:if test="${user.role==2}">学生</c:if>
			     <c:if test="${user.role==3}">教师</c:if>
					&nbsp;&nbsp;&nbsp;&nbsp;
	         当前用户：${user.username }		
        </div>
      </div>
    </div>
    
    <!--管理员  -->
    <c:if test="${user.role==1}">
    <div class="side">
        <div class="sideMenu" style="margin:0 auto">
        
          <h3>学院管理</h3>
          <ul>
            <li><a href="method!collegeadd" target="right">添加学院</a></li>
            <li><a href="method!collegelist" target="right">学院管理</a></li>
          </ul>
          
           <h3>学生管理</h3>
          <ul>
            <li><a href="method!useradd" target="right">添加学生</a></li>
            <li><a href="method!userlist" target="right">学生管理</a></li>
          </ul>
          
           <h3>教师管理</h3>
          <ul> 
            <li><a href="method!t_useradd" target="right">添加教师</a></li>
            <li><a href="method!t_userlist" target="right">教师管理</a></li> 
          </ul>
          
           <h3>课程信息管理</h3>
          <ul>
             <li><a href="method!courselist" target="right">课程信息管理</a></li>
          </ul>
          
           <h3>留言</h3>
          <ul>
            <li><a href="method!answeringlist2" target="right">留言</a></li>
          </ul>
         
         
       </div>
    </div>
    </c:if>
    
    
    <!--学生  -->
    <c:if test="${user.role==2}">
     <div class="side">
        <div class="sideMenu" style="margin:0 auto">
          <h3>个人信息</h3>
          <ul>
            <li><a href="method!userlist2" target="right">个人信息</a></li>
            
          </ul>
         <h3>参加测试</h3>
          <ul>
             <%
        for(model.Fenlei bean:list){
          %>
        <li><a href="method!kaoshiadd2?fenlei=<%=bean.getId() %>" target="right"><%=bean.getName() %></a></li>
        <% 
        }
        %>  
          </ul>
          <h3>测试结果</h3>
          <ul>
             <%
        for(model.Fenlei bean:list){
          %>
        <li><a href="method!kaoshijilulist?fenlei=<%=bean.getId() %>" target="right"><%=bean.getName() %>测试结果</a></li>
        <% 
        }
        %>  
          </ul>
          
          <h3>课程信息</h3>
          <ul>
            <li><a href="method!courselist2" target="right">课程信息</a></li>
          </ul>
          
          
           <h3>留言</h3>
          <ul>
            <li><a href="method!answeringlist" target="right">留言</a></li>
          </ul>
          
       </div>
    </div>
    </c:if>
    
    
    
    <!--教师  -->
     <c:if test="${user.role==3}">
     <div class="side">
        <div class="sideMenu" style="margin:0 auto">
         <h3>学生管理</h3>
          <ul>
            <li><a href="method!userlist" target="right">学生管理</a></li>
          </ul>
        
          <h3> 题库管理</h3>
          <ul>
            <li><a href="method!fenleiadd" target="right">添加题库</a></li>
            <li><a href="method!fenleilist" target="right">题库管理</a></li>
          </ul>
          
         <h3> 试题管理</h3>
          <ul>
            <li><a href="method!shitiadd" target="right">添加试题</a></li>
            <li><a href="method!shitilist" target="right">试题管理</a></li>
          </ul>
          
           <h3>课程信息管理</h3>
          <ul>
            <li><a href="method!courseadd" target="right">课程信息添加</a></li>
             <li><a href="method!courselist" target="right">课程信息管理</a></li>
          </ul>
          
           <h3>留言</h3>
          <ul>
            <li><a href="method!answeringlist2" target="right">留言</a></li>
          </ul>
        
       </div>
    </div>
    </c:if>
    
    <div class="main">
       <iframe name="right" id="rightMain" src="main.jsp" frameborder="no" scrolling="auto" width="100%" height="auto" allowtransparency="true"></iframe>
    </div>
    <div class="bottom">
      <div id="bottom_bg">版权</div>
    </div>
    <div class="scroll">
          <a href="javascript:;" class="per" title="使用鼠标滚轴滚动侧栏" onclick="menuScroll(1);"></a>
          <a href="javascript:;" class="next" title="使用鼠标滚轴滚动侧栏" onclick="menuScroll(2);"></a>
    </div>
</body>

</html>
   
 