<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>指定された顧客は見つかりません</title>
</head>
<body>
<h2>指定された顧客は見つかりません</h2>
<c:url value="/customer" var="url"/>
<a href="${url}">一覧画面へ戻る</a>
</body>
</html>
