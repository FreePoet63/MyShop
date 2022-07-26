<#import "part/main.ftlh" as p>
<@p.main user>
<h1 style="text-align: center">Регистрация</h1>
<hr>
<form action="/registration" method="post">
    <div class="form-group">
        <label for="name">Имя пользователя</label>
        <input type="text" class="form-control" required id="name" name="name">
    </div>
    <div class="form-group">
        <label for="email">Электронная почта</label>
        <input type="email" class="form-control" required id="email" name="email">
    </div>
    <div class="form-group">
        <label for="password">Пароль</label>
        <input type="password" class="form-control" required id="password" name="password">
    </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button type="submit" style="width: 100%" class="btn btn-dark">Зарегистрироваться</button>
</form>
<#if errorMessage??>
    <h2 style="color: red">${errorMessage}</h2>
</#if>
</@p.main>