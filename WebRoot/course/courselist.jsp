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
   <script language="JavaScript" src="<%=path %>/js/public.js" type="text/javascript"></script>
		
        <script language="javascript">
           
           
           function down1(fujianPath,fujianYuashiMing)
           {
               var url="<%=path %>/updown/updown.jsp?fujianPath="+fujianPath+"&fujianYuashiMing="+fujianYuashiMing;
		       url=encodeURI(url); 
               url=encodeURI(url); 
               window.open(url,"_self");
           }
       </script>
   <title>Document</title>
 </head>
 <body >

<div id="search_bar" class="mt10">
<form action="method!courselist" method="post">
       <div class="box">
          <div class="box_border">
            <div class="box_top">
            <b class="pl15">标题</b>
            <input type="text" name="biaoti"  value="${biaoti }" class="input-text lh25" size="10">
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
                   <th width="100%" align="left">课程信息列表</th>
                    </tr>
                    </table>
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="list_table">
                <tr>
                   <th width="10%">题目</th>
                   <th width="20%">文件资源</th>
                   <th width="10%">发布教师</th>
                    <th width="15%">添加时间</th>
                   <th width="25%">操作</th>
                    </tr>
                    
                <c:forEach items="${list}" var="bean"> 
                <tr class="tr">
                <td>${bean.biaoti }</td>
                <td>
                   ${bean.fujianYuanshiming }
						 <a href="#" onclick="down1( '${bean.fujian }','${bean.fujianYuanshiming }')" style="font-size: 11px;color: red">下载此文件</a>
                   </td>
                    <td>${bean.user.username }</td>
                <td>${bean.createtime }</td>
                   <td>  
                   <form name="form1" action="">
                   <input type="button"  class="btn btn82 btn_del" value="删除"   onclick="location.href='method!coursedelete?id=${bean.id }'"/>
                    <input type="button"  class="btn btn82 btn_config" value="修改"   onclick="location.href='method!courseupdate?id=${bean.id }'"/>
                     <input type="button"  class="btn btn82 btn_count" value="详情"   onclick="location.href='method!courseupdate3?id=${bean.id }'"/>
                   </form>
                   </td>
                 </tr>
                 </c:forEach> 
              </table>
              
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
  