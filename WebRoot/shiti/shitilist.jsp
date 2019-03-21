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
 <body >
<div id="search_bar" class="mt10">
<form action="method!shitilist" method="post">
       <div class="box">
          <div class="box_border">
            <div class="box_top">
            <b class="pl15">题库</b>
           <select name="fenlei" class="input-text lh30">
                   <option value="" >全部</option>
                 <c:forEach items="${fenleilist}"  var="bean2">
                    <option value="${bean2.id }" <c:if test="${fenlei== bean2.id }">selected</c:if>>${bean2.name }</option>
                 </c:forEach>
              </select>
            <input type="submit"  class="btn btn82 btn_search" value="查询">   
            </div>
          </div>
        </div>
        </form>
      </div>
 
  <div class="container">
   
     <div id="table" class="mt10">
        <div class="box span10 oh">
         <table width="100%" border="0" cellpadding="0" cellspacing="0" class="list_table">
                   
           
                    
                  <tr>
                   <th width="100%" align="left">试题列表</th>
                    </tr>
                    </table>
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="list_table">
                <tr>
                   <th width="60%">问题</th>
                   <th width="10%">答案</th>
                    <th width="10%">所属题库</th>
                   <th width="20%">操作</th>
                    </tr>
                    
                <c:forEach items="${list}" var="bean"> 
                <tr class="tr">
                <td>${bean.wenti }</td>
                   <td>${bean.daan }</td>
                    <td>${bean.fenlei.name }</td>
                   <td>  
                   <form name="form1" action="">
                   <input type="button"  class="btn btn82 btn_del" value="删除"   onclick="javascript:if(confirm('确定要删除此信息吗？')){location.href='method!shitidelete?id=${bean.id }';return true;}return false;"  />
                    <input type="button"  class="btn btn82 btn_config" value="修改"   onclick="location.href='method!shitiupdate?id=${bean.id }'"/>
                   </form>
                   </td>
                 </tr>
                 </c:forEach> 
              </table>
                <br/> <br/>
             
              
              
             <table width="100%" border="0" cellpadding="0" cellspacing="0" class="list_table">
                <tr>
                   <th width="50%" align="left"> ${pagerinfo }</th>
                    </tr>
              </table>

              
              
              
        </div>
     </div>
   
  

   </div> 
   
 </body>
 </html>
  