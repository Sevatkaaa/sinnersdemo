<#include "parts/security.ftl">
<#import "parts/common.ftl" as c>

<@c.page>
    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/main/search" class="form-inline">
                <input type="text" name="filter" class="form-control" value="${filter?ifExists}" placeholder="Enter search type"/>
                <input type="text" name="filterMsg" class="form-control" value="${filterMsg?ifExists}" placeholder="Enter sin to search"/>
                <button type="submit" class="btn btn-primary ml-2">Search</button>
            </form>
        </div>
    </div>
    <a class="btn btn-primary" data-toggle="collapse" href="#collapseSin" role="button" aria-expanded="false" aria-controls="collapseExample">
        Add sin
    </a>
    <div class="collapse" id="collapseSin">
        <div class="form-group mt-3">
            <form method="post" action="/main/add">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <div class="form-group">
                    <input type="text" name="sinType" required=true class="form-control" placeholder="Type">
                </div>
                <div class="form-group">
                    <input type="number" name="sinWeight" required=true class="form-control" placeholder="Level">
                </div>
                <div class="form-group">
                    <input type="text" name="sinDescription" required=true class="form-control" placeholder="Description">
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary ml-2">Add</button>
                </div>
            </form>
        </div>
    </div>
    <h4 class="mt-3">Sins</h4>
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
                <#if isAdmin>

                    <form method="get" action="/main/del" class="form-inline">
                        <button type="submit" class="btn btn-primary ml-2">Delete</button>
                        <input type="hidden" name="descr" value="${sin.description?ifExists}">
                        <input type="hidden" name="type" value="${sin.type?ifExists}">
                    </form>
                </#if>
                <form method="post" action="/main/like" class="form-inline">
                                                            <button type="submit" class="btn btn-primary ml-2">Like</button>
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                                                            <input type="hidden" name="descr" value="${sin.description?ifExists}">
                                                            <input type="hidden" name="type" value="${sin.type?ifExists}">
                                                            <#--<input type="hidden" name="id" value="${sin.id?ifExists}">-->
                                                        </form>
                <i>Liked by <#list sin.likedBy as like>${like.username}<#sep>, <#else>no one(</#list>

                </i>
            </div>
        </div>

    <#else>
        No such sins

    </#list>
</div>
</@c.page>