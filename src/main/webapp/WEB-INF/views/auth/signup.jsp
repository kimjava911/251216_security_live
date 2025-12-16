<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- taglib --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%-- security -> jsp taglib --%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>회원가입</title>
</head>
<body>
    <h1>회원가입</h1>

    <%-- 메시지 표시 영역 --%>
    <c:if test="${not empty errorMessage}">
        <p>${errorMessage}</p>
    </c:if>

    <form action="<c:url value="/auth/signup" />" method="post">
        <input name="username" placeholder="username">
        <input name="password" type="password" placeholder="password">
        <%-- CSRF -> Spring Security POST -> CSRF Token -> 403 --%>
        <input hidden="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <%--        --%>
        <button>가입하기</button>
    </form>

    <p>
        <a href="<c:url value="/" />">홈으로</a>
    </p>
</body>
</html>
