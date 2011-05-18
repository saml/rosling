<#macro title>
    <title>Title</title>
</#macro>

<#macro meta>
    <!-- meta tags here -->
</#macro>

<#macro css>
    <!-- stylesheets -->
</#macro>

<#macro js>
    <!-- javascripts -->
</#macro>

<#macro additional_head>
    <!-- more head-->
</#macro>

<#macro content>
    Hello World
</#macro>

<#macro body>
    <div id="wrap">
        <div id="header">
            <div id="nav"></div>
        </div>
        <div id="content">
            <@content/>
        </div>
        <div id="footer"></div>
    </div>

</#macro>

<#macro html>
    <!doctype html>
    <html>
    <head>
        <metadata charset="utf-8"/>
        <@title/>
        <@meta/>
        <@css/>
        <@js/>
        <@additional_head/>
    </head>
    <body>
        <@body/>
    </body>
    </html>
</#macro>
