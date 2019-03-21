<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="zh-CN">
<head>
<script language="javascript" type="text/javascript"> 

		
function registershow(){
		var now = new Date(); 
		var t = now.getTime()+''; 
		window.showModalDialog("register.jsp?t="+t, null, 
		'dialogWidth:500px;dialogHeight:600px;help:no;unadorned:no;resizable:no;status:no;scroll:no');
	}
	
</script>

    <link rel="stylesheet" href="css/login.css">
    <script type="text/javascript" src="js/jquery.min.js"></script>

</head>
<body>
<form method="post"  action="method!login">


	<div id="login_center">
		<div id="login_area">
		<span style="font-size: 70px;font-weight: bold;color: white; margin-left:39%;">
   高校在线答疑系统
    </span>
			<div id="login_form">
				
					<div id="login_tip">
						用户登录&nbsp;&nbsp;
					</div>
					<div><input type="text" name="username" class="username"></div>
					<div><input type="password" name="password" class="pwd"></div>
					<div id="btn_area">
						<input type="submit" name="submit" id="sub_btn" value="登&nbsp;&nbsp;录">&nbsp;&nbsp;
						角色:
                        <select  name="role" id="roleid" class="select"> 
                         <option value="1">管理员</option>
                         <option value="2">学生</option>
                         <option value="3">教师</option>
                         </select>
					</div>
				
			</div>
		</div>
	</div>
	<div id="login_bottom">
		
	</div>
	</form>
</body>
</html>