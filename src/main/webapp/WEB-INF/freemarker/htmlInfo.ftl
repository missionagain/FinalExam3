<!DOCTYPE html>
<html>
<body>
<table border="1">
<#list allUser as UserInfo>
<tr>
  <td>${UserInfo.userId}</td>
  <td>${UserInfo.userName}</td>
  <td>${UserInfo.userPassword}</td>
</tr>
</#list>
</body>
</html>