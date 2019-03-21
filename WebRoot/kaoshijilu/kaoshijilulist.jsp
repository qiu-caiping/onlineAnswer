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

  
   
   <title>Document</title>
 </head>
 <body onload= "countTime() ">

<input type="hidden" name="id"  id="id">
 

<br/>
 
  <div class="container">
    <div>
    <h3>测试成绩:${k }</h3>
    </div>
     <div id="table" class="mt10">
        <div class="box span10 oh">
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="list_table">
                <tr>

                   <th width="30%">题目</th>
                   <th width="30%">答案</th>
                   <th width="30%">我的答案</th>
                  <th width="10%">本题得分</th>
                    </tr>
                    
                 <c:forEach items="${list}" var="bean" >    
                <tr class="tr">
                   <td>${bean.shiti.wenti }</td>
                   <td>${bean.shiti.daan }</td>
                   <td>
                 
                    <c:if test="${bean.shiti.daan==bean.wodedaan}"> <span style="color: red;">${bean.wodedaan }</span></c:if>
                     <c:if test="${bean.shiti.daan!=bean.wodedaan}"> ${bean.wodedaan }</c:if>
                   </td>
                   <td>${bean.defen }</td>
                 </tr>
                 </c:forEach> 
              </table>
              
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="list_table">
                <tr>
                   <th width="50%"> ${pagerinfo }</th>
                    </tr>
              </table>
        </div>
     </div>
     

     
   </div> 

 </body>
 </html>
  