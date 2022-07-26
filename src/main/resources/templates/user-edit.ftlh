<#import "part/main.ftlh" as t>
<@t.main user>
<h1 style="text-align: center">Редактирование пользователя ${user.name}</h1><ht>
<form action="/admin/user/edit" method="post">
    <#list roles as role>
    <div>
        <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
    </div>
</#list>
<input type="hidden" value="${user.id}" name="userId">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
<button type="submit">Сохранить</button>
</form>
</body>
</html>
</@t.main>