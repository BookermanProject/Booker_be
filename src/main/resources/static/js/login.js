
$(document).ready(function () {
    let signup = $(".links").find("li").find("#signup");
    let signin = $(".links").find("li").find("#signin");
    let reset = $(".links").find("li").find("#reset");

    let first_input = $(".form").find(".first-input");
    let hidden_input = $(".form").find(".input__block").find("#repeat__password");
    let signin_btn = $(".form").find(".signin__btn");


    //----------- sign up ---------------------
    signup.on("click", function (e) {
        e.preventDefault();
        $(this).parent().parent().siblings("h1").text("SIGN UP");
        $(this).parent().css("opacity", "1");
        $(this).parent().siblings().css("opacity", ".6");
        first_input.removeClass("first-input__block").addClass("signup-input__block");
        $("#input_area").css("display", "block");
        hidden_input.css({
            "opacity": "1",
            "display": "block"
        });
        $("#sign_in").attr('id', "sign_up");
        signin_btn.text("Sign up");
    });


    //----------- sign in ---------------------
    signin.on("click", function (e) {
        e.preventDefault();
        $(this).parent().parent().siblings("h1").text("SIGN IN");
        $(this).parent().css("opacity", "1");
        $(this).parent().siblings().css("opacity", ".6");
        first_input.addClass("first-input__block")
            .removeClass("signup-input__block");
        $("#input_area").css("display", "none");
        hidden_input.css({
            "opacity": "0",
            "display": "none"
        });
        $("#sign_up").attr('id', "sign_in");
        signin_btn.text("Sign in");
    });


})


$(document).on("click", "#sign_up", function(e){
    e.preventDefault();
    var userId = $('input[name=userId]').val();
    var password = $('input[name=password]').val();
    var address = $('input[name=address]').val();
    $.ajax({
        url: '/api/users/signup',
        type: 'post',
        data: JSON.stringify({
            "userId": userId,
            "password": password,
            "address": address
        }),
        contentType : "application/json; charset=utf-8",

        success: function (data) {
            alert("회원가입 성공")
        },
        error: function (error) {
            alert(error)
            console.log(error.message);
        }
    });

})




//--- 로그인   ---------

$(document).on("click", "#sign_in", function(e){
    var userId = $('input[name=userId]').val();
    var password = $('input[name=password]').val();
    $.ajax({
        url: '/api/users/login',
        type: 'post',
        data: JSON.stringify({
            "userId": userId,
            "password": password,
            "jwt": $.cookie('jwt')
        }),
        contentType : "application/json; charset=utf-8",

        success: function (data) {
            alert("로그인 성공")
            $.cookie('jwt',data.message);
            location.href = "/main"
            localStorage.setItem('login','true');
        },
        error: function (error) {
            alert("로그인 실패")
            console.log(error);
        }
    });
})




