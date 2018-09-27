<#macro login path redirect name redirectName action>
    <div class="mb-4">${action}</div>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> User Name : </label>
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
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" class="btn btn-primary mr-2">${name}</button>
        <a href="${redirect}">${redirectName}</a>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" class="btn btn-primary mr-2">Sign Out</button>
    </form>
</#macro>