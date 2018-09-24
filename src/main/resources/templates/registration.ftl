<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page>
<@l.login "/registration", "/login", "Register", "Sign in", "Register new user"/>
    ${message?ifExists}
</@c.page>