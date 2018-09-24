<#import "parts/common.ftl" as c>
<@c.page>
<div><a href="/main">Back to main page</a></div>
List of all users
<table>
    <thead>
    <tr>
        <th>Name</th>
        <th>Role</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
        <#list users as user>
        <tr>
            <td>${user.username}</td>
            <td><#list user.roles as role>${role}<#sep>, </#list></td>
            <td><a href="/user/${user.id}">Edit</a></td>
        </tr>
        </#list>
    </tbody>
</table>
<div><a href="/main">Back to main page</a></div>
</@c.page>
