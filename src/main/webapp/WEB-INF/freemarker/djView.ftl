<html>
    <head>
        <title>Last.fm Live - DJ View</title>
        <link rel="stylesheet" type="text/css" href="/lastfmlive/resources/css/style.css" />
        <meta http-equiv="refresh" content="10" />
    </head>
    <body>
        <div id="columnContainer">
            <div class="column">
                <div class="djColumnHeader">Top Artists</div>
                <table>
                    <#list artists as artist>
                    <tr class='${["nrm","alt"][artist_index%2]}'>
                        <td class="dj">${artist.name}
                            <div class="tagbox"><#if artist.tags?? && artist.tags?size!=0><#list artist.tags?chunk(3)?first as tag><span class="tag">${tag}</span></#list></#if></div>
                        </td>
                    </tr>
                    </#list>
                </table>
            </div>
            <div class="column">
                <div class="djColumnHeader">Top Tracks</div>
                <table>
                    <#list tracks as track>
                    <tr class='${["nrm","alt"][track_index%2]}'><td class="dj">${track.artist} - ${track.title}</td></tr>
                    </#list>
                </table>
            </div>
            <div class="column">
                <div class="djColumnHeader">Top Tags</div>
                <div class="tagview">
                    <#if tags?? >
                    <#list tags as tag><span class="tag" style="font-size:${(50-tag_index)/30}em;">${tag}</span>&nbsp; </#list>
                    </#if>
                </div>
                <div class="djColumnHeader">Recent Tweets</div>
                <table>
                <#if tweets??>
                <#list tweets?reverse as tweet>
                    <tr class='${["nrm","alt"][tweet_index%2]}'><td class="tweet">"${tweet.message}" - <em class="twitterUser">${tweet.twitterUser}</em></td></tr>
                </#list>
                </#if>
                </table>
            </div>
        </div>
    </body>
</html>