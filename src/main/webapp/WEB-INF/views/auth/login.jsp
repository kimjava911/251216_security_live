<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- taglib --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%-- security -> jsp taglib --%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>로그인</title>
</head>
<body>
    <h1>로그인</h1>

    <%-- 메시지 표시 영역 --%>
    <c:if test="${not empty errorMessage}">
        <p>${errorMessage}</p>
    </c:if>
    <c:if test="${not empty logoutMessage}">
        <p>${logoutMessage}</p>
    </c:if>

    <%-- action -> .loginProcessingUrl("/auth/login"), POST --%>
    <form action="<c:url value="/auth/login" />" method="post">
        <input name="username" placeholder="username">
        <input name="password" type="password" placeholder="username">
        <%-- CSRF -> Spring Security POST -> CSRF Token -> 403 --%>
        <input hidden="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <button>로그인</button>
    </form>

    <p>
        <a href="<c:url value="/auth/signup" />">회원가입</a>
        <a href="<c:url value="/" />">홈으로</a>
    </p>
</body>
</html>
