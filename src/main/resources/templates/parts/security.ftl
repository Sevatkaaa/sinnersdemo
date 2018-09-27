<#assign
known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    message = "Welcome, " + user.getUsername()
    isAdmin = user.isAdmin()
    isLogged = true
    >
<#else>
    <#assign
    message = "Not loged in"
    isAdmin = false
    isLogged = false
    >
</#if>