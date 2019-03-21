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
 <!doctype html>
 <html lang="zh-CN">
 <head>
   <link rel="stylesheet" href="css/common.css">
   <link rel="stylesheet" href="css/main.css">
   <script type="text/javascript" src="js/jquery.min.js"></script>
   <script type="text/javascript" src="js/colResizable-1.3.min.js"></script>
   <script type="text/javascript" src="js/common.js"></script>
   
   <script type="text/javascript">
      $(function(){  
        $(".list_table").colResizable({
          liveDrag:true,
          gripInnerHtml:"<div class='grip'></div>", 
          draggingClass:"dragging", 
          minWidth:30
        }); 
        
      }); 
   </script>
   <script language="javascript" type="text/javascript">
function checkform()
{
	 if (document.getElementById('user0id').value=="")
	{
		alert("教师不能为空");
		return false;
	}

	return true;
	
}
</script>
   
   <title>Document</title>
 </head>
 <body >

  <div class="container">
   
    
     <div id="table" class="mt10">
        <div class="box span10 oh">
         <table width="100%" border="0" cellpadding="0" cellspacing="0" class="list_table">
                  <tr>
                   <th width="100%" align="left">留言信息列表</th>
                    </tr>
                    </table>
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="list_table">
              
                <tr >
                  
                    <th width="10%" >学生</th>
                    <th width="30%" >内容</th>
                     <th width="10%" >教师</th>
                       <th width="30%" >回复内容</th>
                    <th width="auto" >添加时间</th>
                    </tr>
                    
                <c:forEach items="${list}" var="bean">
                <tr class="tr">
                    <td >${bean.user.username }</td>
                     <td >${bean.mywords }</td>
                       <td >@${bean.user0.username }</td>
                         <td >${bean.rescontent }</td>
                    <td >${bean.createDate }</td>
                  
                 </tr>
                 </c:forEach> 
              </table>
              
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="list_table">
                <tr>
                   <th width="50%" align="left"> ${pagerinfo }</th>
                    </tr>
              </table>
              
            
               <form action="method!answeringadd" method="post" onsubmit="return checkform()" >
                 
               @教师 ： <select style="width:250px" class="input-text lh30" name="user0" id="user0id"> 
                  <option value="">--请选择--</option>
                  <c:forEach items="${userlist}" var="bean2">
                  <option value="${bean2.id }">姓名：  ${bean2.truename}，编号：  ${bean2.username} ，学院：  ${bean2.college.name}</option>
                  </c:forEach>
                 </select>
                <br/>
     <textarea rows="7" cols="100" name="mywords" ></textarea>
     <div align="left"><span class="STYLE10">
       <input type="submit" class="btn btn82 btn_save2" value="发送" />
     </span></div>
     </form>
          
              
        </div>
     </div>
    
   </div> 
   
 </body>
 </html>
  