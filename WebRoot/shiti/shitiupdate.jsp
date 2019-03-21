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
   <meta charset="UTF-8">
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
   <title></title>
 </head>
 <body>
 <form action="method!shitiupdate2" method="post"  >
   <input type="hidden" name="id" value="${bean.id }"/>
  <div class="container">
    
     <div id="forms" class="mt10">
        <div class="box">
          <div class="box_border">
            <div class="box_top"><b class="pl15">修改试题</b></div>
            <div class="box_center">
             
               <table class="form_table pt15 pb15" width="100%" border="0" cellpadding="0" cellspacing="0">

                 <tr>
                  <td class="td_right">试题:</td>
                  <td class="">
                    <textarea name="wenti"  cols="30" rows="10" class="textarea">${bean.wenti }</textarea>
                  </td>
                 </tr>
                
                <tr >
                  <td class="td_right">答案:</td>
                  <td class="">
                   <textarea name="daan"  cols="30" rows="10" class="textarea">${bean.daan }</textarea>
                  </td>
                 </tr>
                
                 <tr>
                   <td class="td_right">&nbsp;</td>
                   <td class="">
                     <input type="submit" name="submit" class="btn btn82 btn_save2" value="保存"> 
                    <input type="button"  class="btn btn82 btn_res" value="返回" onclick="window.history.go(-1);"/> 
                   </td>
                 </tr>
               </table>
               
            </div>
          </div>
        </div>
     </div>
   </div> 
   </form>
 </body>
 </html>
  