let eventtoggle = $("#event_toggle");
let searchtoggle = $("#search_toggle");
let liketoggle = $("#like_toggle");


$(document).on("click", eventtoggle, function(e){

    e.preventDefault();

    $("#event").css("display", "block");
    $("#search_ranking").css("display", "none");
    $("#like_ranking").css("display", "none");



})


$(document).on("click", searchtoggle, function(e){

    e.preventDefault();

    $("#event").css("display", "none");
    $("#search_ranking").css("display", "block");
    $("#like_ranking").css("display", "none");



})

$(document).on("click", liketoggle, function(e){

    e.preventDefault();

    $("#event").css("display", "none");
    $("#search_ranking").css("display", "none");
    $("#like_ranking").css("display", "block");



})