<#include "base.ftl">

    <#macro title>
        <title>${(entry.title)!"Missing Title"} - rosling example blog</title>
    </#macro>

    <#macro content>
        <div class="author">${(entry.author)!"Missing Author"}</div>
        <div class="pub-date">Last Modified: ${(entry.lastModified.time?datetime)!"Missing Last Modified"}
        </div>
        <div class="entry">
            ${(entry.text)!"Missing Text"}
        </div>
    </#macro>

    <@html/>