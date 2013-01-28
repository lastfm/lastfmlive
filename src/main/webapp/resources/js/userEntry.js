addUser = function (userName) {
    $.ajax({url: "/addUser/"+userName});
};
$(document).ready(function() {
    $("#username").keypress(function(event) {
        if (event.which==13)
        {
            userName=$("#username").prop("value");
            addUser(userName);
            $("#username").prop("value","");
            alert("Musical taste for "+userName+" sent to the DJ");
        }
    });
});
