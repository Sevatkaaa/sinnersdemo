<#import "parts/common.ftl" as c>
<@c.page>
    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/main" class="form-inline">
                <input type="text" name="filter" class="form-control" value="${filter?ifExists}" placeholder="Enter search type"/>
                <button type="submit" class="btn btn-primary ml-2">Чё я натворил</button>
            </form>
        </div>
    </div>
    <a class="btn btn-primary" data-toggle="collapse" href="#collapseSin" role="button" aria-expanded="false" aria-controls="collapseExample">
        Add new sin
    </a>
    <div class="collapse" id="collapseSin">
        <div class="form-group mt-3">
            <form method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <div class="form-group">
                    <input type="text" name="sinType" required=true class="form-control" placeholder="Тип греха">
                </div>
                <div class="form-group">
                    <input type="number" name="sinWeight" required=true class="form-control" placeholder="Тяжесть греха">
                </div>
                <div class="form-group">
                    <input type="text" name="sinDescription" required=true class="form-control" placeholder="Описание греха">
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary ml-2">Зацени мой грешок</button>
                </div>
            </form>
        </div>
    </div>
    <h4 class="mt-3">Грешки</h4>
<div class="card-columns">
    <#list sins as sin>
        <div class="card my-4">
            <div class="m-2">
                <span>${sin.description}</span>
                <b>${sin.type}</b>
                <i>${sin.weight}</i>
            </div>
            <div class="card-footer text-muted">
                by ${sin.authorName}
            </div>
        </div>
    <#else>
    No such sins

    </#list>
</div>
</@c.page>