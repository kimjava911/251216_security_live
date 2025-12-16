<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- taglib --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%-- security -> jsp taglib --%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Security</title>
</head>
<body>
    <h1>Hello Security</h1>

    <%-- 로그인하지 않은 사용자에게만 표시 (isAnonymous()) --%>
    <sec:authorize access="isAnonymous()">
        <p>로그인이 필요합니다</p>
        <a href="<c:url value="/auth/login" />">로그인</a>
        <a href="<c:url value="/auth/signup" />">회원가입</a>
    </sec:authorize>

    <%-- 로그인 사용자에게만 표시 (isAuthenticated()) --%>
    <sec:authorize access="isAuthenticated()">
        <p>환영합니다.
            <sec:authentication property="name" />님!
        </p>
        <p>권한 : <sec:authentication property="authorities" /></p>
        <%-- Admin 권한을 갖고 있는 사용자에게만 표시 --%>
        <sec:authorize access="hasRole('ADMIN')">
            <a href="<c:url value="/auth/admin" />">관리자 페이지</a>
        </sec:authorize>
        <a href="<c:url value="/memo" />">내 메모</a>
        <form action="<c:url value="/auth/logout" />" method="post">
            <input hidden="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <button>로그아웃</button>
        </form>
    </sec:authorize>
</body>
</html>
