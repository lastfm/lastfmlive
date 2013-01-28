<html>
    <head>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.0/jquery.min.js" type="text/javascript"></script>
        <script src="/lastfmlive/resources/js/userEntry.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="/lastfmlive/resources/css/style.css" />
    </head>
    <body>
        <div id="lastfmBadge"><span>Last.fm</span>&nbsp;</div>
        <div id="contentPane">
            <div id="entryForm">
            <h1>Add My Music</h1>
            <p>Enter your Last.fm username here to have information about your musical taste sent directly to the DJ</p>
            <form action="add" method="GET">
                <input name="user" type="text" style="font-size:48pt" size="8" />
                <input type="submit" value="Add" />
            </form>
            <div class='statusLine'>${status!""}</div>
            <div class='recentUsers'>Some users currently contributing their musical taste: <#list users?reverse as user>${user.name} </#list> </div>
            </div>
        </div>
    </body>
</html>
