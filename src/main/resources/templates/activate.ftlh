<#import "part/main.ftlh" as t>
<@t.main user>
    <h1 style="text-align: center">Активация аккаунта</h1>
    <hr>
    <h3 style="text-align: center">
        <#if activate>
            Поздравляю! Ваша электронная почта
            была успешно подтверждена.
            <br>
            <a href="/login">Теперь вы можете авторизироваться</a>
            <#else>
            Ключ не был найден! Ошибка подтверждения электронной почты.
        </#if>
    </h3>
</@t.main>