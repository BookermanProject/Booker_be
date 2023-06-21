let baseurl = "http://127.0.0.1:8080"

$("#event").on("click",function(){
    alert(0);
})

$("#realtime").on("click",function(){
    $.ajax({
        url : "/realtime",
        type : "post",
        data : {},
        success : function(data){
            $("#rankhead").empty();

            $("#rankhead").append("<tr><th>순위</th><th>책이름</th></tr>");
            
            $("#rankbox").empty();
            for(var i =0; i<data.length; i++){
                $("#rankbox").append("<tr><td>"+(i+1)+"</td><td>"+data[i] +"</td><td>")
            }
        },
        error : function (data){
            console.log(data)
        }
    })
})

$("#likecount").on("click",function(){
    $.ajax({
        url : "/liketop",
        type : "post",
        data : {},
        success : function(data){
            $("#rankhead").empty();

            $("#rankhead").append("<tr><th>순위</th><th>책이름</th><th>좋아요</th></tr>");


            $("#rankbox").empty();
            for(var i =0; i<data.length; i++){
                $("#rankbox").append("<tr><td>"+(i+1)+"</td><td>"+data[i].bookName+"</td><td>"+data[i].likeCount+"</td></tr>")
            }
        },
        error : function (data){

        }
    })
})


$("#searchbutton").on("click",function(){
    var searchkeyword = $("#searchbox").val();

    $("#rankinglist").empty();

    $.ajax({
        url : "/elastic/search/word",
        type : "post",
        data : {"keyword": searchkeyword},
        success : function(data){
            console.log(data);
            // for(var i = 0; i<data.length; i++){
            //
            // }
        },
        error : function(data){
            console.log(data)
        }
    })
})

// 자동 완성 기능 

const $search = document.querySelector("#search");
const $autoComplete = document.querySelector(".autocomplete");

let dataList = [];

let nowIndex = 0;

$search.onkeyup = (event) => {

    const value = $search.value.trim();

    console.log(value)

    $.ajax({
        url : baseurl + "/elastic/automaker",
        type : "get",
        data : {"query": value},
        success : function(data){
            console.log(data);
            dataList = data;
        },
        contentType : "application/json; charset=utf-8",

        error : function(error){
            console.log(error)
        }
    })
    // 검색어

    // 자동완성 필터링
    const matchDataList = value
        ? dataList.filter((label) => label.includes(value))
        : [];

    switch (event.keyCode) {
        // UP KEY
        case 38:
            nowIndex = Math.max(nowIndex - 1, 0);
            break;

        // DOWN KEY
        case 40:
            nowIndex = Math.min(nowIndex + 1, matchDataList.length - 1);
            break;

        // ENTER KEY
        case 13:
            document.querySelector("#search").value = matchDataList[nowIndex] || "";

            // 초기화
            nowIndex = 0;
            matchDataList.length = 0;
            break;

        // 그외 다시 초기화
        default:
            nowIndex = 0;
            break;
    }

    // 리스트 보여주기
    showList(matchDataList, value, nowIndex);
};


const showList = (data, value, nowIndex) => {
    // 정규식으로 변환
    const regex = new RegExp(`(${value})`, "g");

    $autoComplete.innerHTML = data
        .map(
            (label, index) => `
      <div class='${nowIndex === index ? "active" : ""}'>
        ${label.replace(regex, "<mark>$1</mark>")}
      </div>
    `
        )
        .join("");
};

