<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="jquery-1.11.2.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

		$("#submit").click(sendMessage);
		$("#mid").on('keypress', function(e) {
			if (e.keyCode == 13) {
				sendMessage();
			}
		});
		$("#logout").click(function() {
			$.post("chat", {
				logout : 'true'
			}, function(data, status) {
				alert("Logged out");
			})

		});

		function sendMessage() {
			var msge = $("#mid").val();
			var to = $("#op").val();
			$.post("chat", {
				msg : msge,
				msgTo : to
			}, function(data, status) {
				updateMsg(data);
				$("#mid").val("");
			})
		}

		function addFriendList() {
			$.post("chat", {
				getFriends : 'friends'
			}, function(data, status) {
				var obj = $.parseJSON(data);
				$.each(obj, function(index, value) {
					console.log(value);
					$('#op').append($('<option>', {
						value : value,
						text : value
					}));
				});

			})
		}
		addFriendList();

		function recieveMsg() {
			$.post("reciever", function(data, status) {
				updateMsg(data);
				setTimeout(recieveMsg, 5000);
			})
		}

	recieveMsg();

		function updateMsg(data) {
			var text = $("#id").html();
			var d = $.trim(data);
			console.log("length: " + data.length + "[" + d + "]");
			if (d != "") {
				$("#id").html(text + "<br>" + data);
			}
		}
	});
</script>
</head>
<body>
	<h3 id="name">
		You are :
		<%=((com.jeet.api.Login) session.getAttribute("login"))
					.getUserId()%>
	</h3>
	<br> Message:
	<input type="text" name="msg" id="mid" /> Send to :
	<select id='op'>
	</select>
	<br>
	<br>
	<input type="button" id='submit' value="Send" />
	<input type="button" id='logout' value="Logout" />

	<h2 id="id"></h2>

</body>
</html>