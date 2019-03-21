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
   <script language="javascript" type="text/javascript">
function checkform()
{
	 if (document.getElementById('wentiid').value=="")
	{
		alert("试题不能为空");
		return false;
	}
	 if (document.getElementById('daanid').value=="")
	{
		alert("答案不能为空");
		return false;
	}

	 if (document.getElementById('fenleiid').value=="")
	{
		alert("所属题库不能为空");
		return false;
	}
	
	return true;
	
}
</script>
   <title></title>
 </head>
 <body>
  <form action="method!shitiadd2" method="post"  onsubmit="return checkform()">
  
  <div class="container">
   
     <div id="forms" class="mt10">
        <div class="box">
          <div class="box_border">
            <div class="box_top"><b class="pl15">添加试题</b></div>
             <span style="font-size: 30px;font-weight: bold;color: RED; margin-left:0%;">
             一个题库必须添加5个以上的题目，才可形成测试题，题型为选择题，每题20分
    </span>
            <div class="box_center">
             
               <table class="form_table pt15 pb15" width="100%" border="0" cellpadding="0" cellspacing="0">

                 <tr>
                  <td class="td_right">问题:</td>
                  <td class="">
                    <textarea name="wenti" id="wentiid" cols="30" rows="10" class="textarea"></textarea>
                  </td>
                 </tr>
                
                <tr >
                  <td class="td_right">答案:</td>
                    <td class="">
                     <textarea name="daan" id="daanid" cols="30" rows="10" class="textarea"></textarea>
                 <span style="color: red;">请输入A、B、C、D，</span>
                  </td>
                 </tr>
                
        
                 
                  <tr>
                  <td class="td_right">所属题库:</td>
                  <td class="">
 
                    <span class="fl">
                      <div class="select_border"> 
                        <div class="select_containers "> 
                       <select style="width:100px"  name="fenlei" id="fenleiid"> 
         <option value="">--请选择--</option>
         <c:forEach items="${list}" var="bean2">
          <option value="${bean2.id }">${bean2.name}</option>
         </c:forEach>
         </select>

                        </div> 
                      </div> 
                    </span>
                  </td>
                 </tr>
               
                
                 <tr>
                   <td class="td_right">&nbsp;</td>
                   <td class="">
                     <input type="submit" name="submit" class="btn btn82 btn_save2" value="保存"> 
                    <input type="reset"  class="btn btn82 btn_res" value="重置"> 
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
  