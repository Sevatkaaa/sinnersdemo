<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page>
    <div>
        <@l.logout />
        <span><a href="/user">Users list</a></span>
    </div>
    <div>
        <form method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="text" name="sinType" placeholder="Тип греха">
            <input type="number" name="sinWeight" placeholder="Тяжесть греха">
            <input type="text" name="sinDescription" placeholder="Описание греха">
            <button type="submit">Зацени мой грешок</button>
        </form>
    </div>
    <div>Список грехов</div>
    <form method="get" action="/main">
        <input type="text" name="filter" value="${filter?ifExists}" />
        <button type="submit">Чё я натворил</button>
    </form>
    <#list sins as sin>
        <div>
            <span>${sin.description}</span>
            <b>${sin.type}</b>
            <i>${sin.weight}</i>
            <strong>${sin.authorName}</strong>
        </div>
    <#else>
    No such sins
    </#list>
</@c.page>