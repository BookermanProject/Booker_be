$(function () {
    $.ajax({
        url: "/api/event",
        type: "get",
        data: {},
        success: function (data) {
           console.log(data)
            // tabledraw(data)
        }, error: function () {

        }
    })
})


function tabledraw(data) {
    $("#mypagetablebody").empty();
    for (var i = 0; i < data.length; i++) {
        var table = '';
        table += "<tr>";
        table += "<th scope=\"row\">"+i+"</th>";
        table += "<td>"+i+"</td>";
        table += "<td>"+i+"</td>";
        table += "<td>"+i+"</td>";
        table += "</tr>";
    }
    $("#mypagetablebody").append(table)
}
