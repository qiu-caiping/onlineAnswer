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
	
	 if (document.getElementById('xzdaanid').value=="")
	{
		alert("请完成所有题目");
		return false;
	}
	 
	 
	return true;	
}
</script>
  
   
   <title>Document</title>
 </head>
 <body onload= "countTime() ">
 <form name="fom"  method="post" action="method!kaoshiadd3"  onsubmit="return checkform()">
<input type="hidden" name="id"  id="id">
 

<br/>
 
  <div class="container">
    <div><h3>每题20分</h3></div>
     <div id="table" class="mt10">
        <div class="box span10 oh">
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="list_table">
                <tr>
                   <th width="10%">题号</th>
                   <th width="80%">题目</th>
                   <th width="10%">答案</th>
                    </tr>
                    
                 <c:forEach items="${list1}" var="bean" varStatus="v">    
                <tr class="tr">
                   <td>第${v.index+1 }题</td>
                   <td>${bean.wenti }</td>
                   
                    <td>
                   <input  name='xzid${v.index }' type="hidden"  value="${bean.id }" />
                   <span class="fl">
                      <div class="select_border"> 
                        <div class="select_containers "> 
                            <select class="select" name="xzdaan${v.index }" id="xzdaanid" class="input-text lh30">
                             <option value="">请选择</option>
                            <option value="A">A</option>
                            <option value="B">B</option>
                             <option value="C">C</option>
                              <option value="D">D</option>
                            </select>
                            </div> 
                      </div> 
                    </span>
                   </td>
                   
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
     
     
 
 
     
     <div align="center">
      <input type="submit" class="btn btn82 btn_save2" value="提交"> 
     </div>
     
   </div> 
   </form>
 </body>
 </html>
  