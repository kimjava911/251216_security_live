<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>내 메모</title>
</head>
<body>
    <h1>내 메모 목록</h1>

    <p>
        <a href="<c:url value="/memo/new" />">새 메모 작성</a>
        <a href="<c:url value="/" />">홈으로</a>
    </p>

</body>
</html>
