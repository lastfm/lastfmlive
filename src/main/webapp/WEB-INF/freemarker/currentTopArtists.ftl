<html>
    <head>
        <title>Top artists</title>
        <link rel="stylesheet" type="text/css" href="/lastfmlive/resources/css/style.css" />
        <meta http-equiv="refresh" content="10" />
    </head>
    <body>
        <div id="lastfmBadge"><span>Last.fm</span>&nbsp;</div>
        <div id="columnContainer">
            <div id="artistChart" class="column">
                <h1>Top artists</h1>
                <table>
                <#list artistChart as artist>
                    <tr class='${["nrm","alt"][artist_index%2]}'>
                        <td class="display">${artist.name}<br /><div class="tagbox" style="font-size:0.8em;"><#if artist.tags??><#list artist.tags?chunk(3)?first as tag><span class="tag">${tag}</span></#list></#if></div></td>
                        <td class="imageCell"><img src="${artist.imageUrl}" class="rowImage" /></td>
                    </tr>
                </#list>
                </table>
            </div>
            <div id="users" class="column">
                <h1>Users present (${users?size})</h1>
                <table>
                <#list users?reverse as user>
                    <tr class='${["nrm","alt"][user_index%2]}'><td class="display">+${user.name} (${user.realName} - ${user.age})</td><td class="imageCell"><img src="${user.imageUrl}" class="rowImage" /></td></tr>
                </#list>
                </table>
            </div>
            <div id="tweets" class="column">
                <h1>Recent tweets (#lastfmlive)</h1>
                <table>
                <#list tweets?reverse as tweet>
                    <tr class='${["nrm","alt"][tweet_index%2]}'><td class="tweet">"${tweet.message}" - <em class="twitterUser">${tweet.twitterUser}</em></td></tr>
                </#list>
                </table>
                <h1>Join in</h1>
                <div class="columnContent">
                    Tweet #lastfmlive including +yourLastfmUserName to add yourself to the board.<br /><br /> Information about your favourite tracks and artists will be sent to the DJ
                </div>
            </div>
        </div>
    </body>
</html>