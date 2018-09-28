<#macro login path redirect name redirectName action>
    <div class="mb-4">
        ${message?ifExists}
    </div>
    <div class="mb-4">
        ${action}
    </div>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> User Name: </label>
            <div class="col-sm-4">
                <input type="text" required=true name="username" class="form-control" placeholder="Enter your name"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Password: </label>
            <div class="col-sm-4">
                <input type="password" required=true name="password" class="form-control" placeholder="Enter your password"/>
            </div>
        </div>
        <#if name == "Register">
            <div class="form-group row">
                <label class="col-sm-2 col-form-label"> Email: </label>
                <div class="col-sm-4">
                    <input type="email" required=true name="email" class="form-control" placeholder="Enter your email"/>
                </div>
            </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" class="btn btn-primary mr-2">${name}</button>
        <a href="${redirect}">${redirectName}</a>
    </form>
</#macro>

<#macro login_bar>
    <a href="/login"><button type="submit" class="btn btn-primary mr-2">Sign in</button></a>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" class="btn btn-primary mr-2">Sign out</button>
    </form>
</#macro>