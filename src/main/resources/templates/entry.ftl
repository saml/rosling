<#include "base">

    <#macro title>
        <title>${title} - rosling example blog</title>
    </#macro>

    <#macro content>
        <div class="author">${entry.author}</div>
        <div class="pub-date">Last Modified: ${entry.lastModified?datetime}
        </div>
        <div class="entry">
            ${entry.text}
        </div>
    </#macro>