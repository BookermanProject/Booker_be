// html 문서 로드와 함께진행
// 검색 필터링을 담당하는 전역변수
var filterdata = {};
var type = 'default';
var jwt ;

$(document).ready(async function() {
    jwt = $.cookie('jwt')
    try {
        await RenderLikeTop();
        const searchResult = await Search(0, 10);
        RenderBookList(searchResult.get('booklist'));
        RenderPagination(searchResult.get('totalPages'));
    } catch (error) {
        console.log(error);
    }
});

// 검색 기능
$("#searchbutton").on("click",async function(){
    try {
        var searchkeyword = String($("#search").val());
        var sort = null;
        var sortCategory = null;
        var selectedButton = $('.btn-group > .btn');
        sort = String(selectedButton.attr('order'));
        sortCategory = String(selectedButton.attr('category'));
        type = 'keyword'
        if (sort === "default" || sortCategory === "default") {
            filterdata = {
                query: searchkeyword
            };
        }else{
            filterdata = {
                query: searchkeyword,
                sort: sort,
                sortCategory: sortCategory
            };
        }
        const searchResult = await Search(0, 10);
        RenderBookList(searchResult.get('booklist'));
        RenderPagination(searchResult.get('totalPages'));
    } catch (error) {
        console.log(error);
    }
})

function RenderPagination(totalPages){
    if($('#pagination').data("twbs-pagination")){
        $('#pagination').twbsPagination('destroy');
    }
    window.pagObj = $('#pagination').twbsPagination({
        totalPages: totalPages,
        visiblePages: 10,
        onPageClick: async function (event, page) {
            try {
                    if(page-1 == 0){
                        await SearchUpCount();
                    }
                    // 검색 결과 가지고 오기
                    const searchResult = await Search(page-1, 10);
                    // 검색 결과기반으로 책 테이블. 페이지네이션 그리기
                    RenderBookList(searchResult.get('booklist'));
                    // 검색어 카운트 올리기

            } catch (error) {
                console.log(error);
            }
        }
    });

}
// 검색 카운트 올리기
async function SearchUpCount(){

    if(filterdata.query != null){
        await $.ajax({
            url: "/elastic/search/count",
            contentType: "application/json", // 요청의 컨텐츠 타입을 JSON으로 설정
            data: JSON.stringify(filterdata),
            type : "put"
        });
    }


    // 실시간 검색어 업데이트 하기

    await RendersearchTop()

}


function RenderBookList(list) {
    $("#rankinglist").empty();
    var table = "";
    for (var i = 0; i < list.length; i++) {
        var book = list[i];
        var bookId = book.id;
        var imgSrc = book.img_url ? book.img_url : "https://img.freepik.com/free-vector/vector-blank-book-cover-isolated-on-white_1284-41904.jpg?w=1060&t=st=1685459511~exp=1685460111~hmac=507004895a3a6a8dd37107da35e9300eea8c87a82e60f7220b928b12b81a1435";
        var bookName = book.book_name;
        var author = book.author;
        var publisher = book.publisher;
        var pub_date = book.pub_date;
        var star = book.star;
        var like_count = book.like_count;
        table += "<tr>";
        table += "<td><img class='main_img' src='" + imgSrc + "' alt='book_img'></td>";
        table += "<td>" + bookName + "</td>";
        table += "<td>" + author + "</td>";
        table += "<td>" + publisher + "</td>";
        table += "<td>" + pub_date + "</td>";
        table += "<td>" + star + "</td>";
        table += "<td class ='like_count' id=likecount" + bookId + ">" + like_count + "<i class='fa-regular fa-heart like' id=" + bookId + "></i>"  + "</td>";
        table += "</tr>";
    }
    $("#rankinglist").append(table);
}

async function Search(page, size) {
    try {
        const result = new Map(); // 데이터를 담을 Map 자료구조 생성
        let url = '';
        if (String(type) === 'default') {
            url = "/elastic/search?page=" + page + "&size=" + size;
        } else if (String(type) === 'keyword') {
            url = "/elastic/search/word?page=" + page + "&size=" + size;
        } else {
            throw new Error("Invalid type");
        }
        const data = await $.ajax({
            url: url,
            contentType: "application/json", // 요청의 컨텐츠 타입을 JSON으로 설정            type: "get",
            data: filterdata
        });
        result.set('totalPages', Math.ceil(parseInt(data.page) / 10)); // Map에 totalPages 저장
        result.set('booklist', data.books); // Map에 booklist 저장
        return result; // 결과 반환
    } catch (error) {
        console.log(error);
    }
}

// 검색 조건 설정 함수
$(document).ready(function() {
    $('.dropdown-menu .dropdown-item').on('click', function() {
        var selectedText = $(this).text();  // 선택된 항목의 텍스트 가져오기
        var category = $(this).attr('category');  // 선택된 항목의 category 값 가져오기
        var order = $(this).attr('order');  // 선택된 항목의 order 값 가져오기
        var button = $(this).closest('.btn-group').find('.btn');  // 부모 btn-group의 자식 버튼 선택하기
        button.text(selectedText);  // 버튼의 텍스트 변경
        button.attr('category', category);  // 버튼의 category 속성 값 변경
        button.attr('order', order);  // 버튼의 order 속성 값 변경
    });
});

$("#realtime").on("click",function(){
    RendersearchTop()
})

// 실시간 검색어 그리기

async function RendersearchTop(){
    await $.ajax({
        url : "/elastic/searchtop",
        type : "get",
        data : {},
        success : function(data){
            $("#rankhead").empty();
            $("#rankbox").empty();
            $("#rankhead").append("<tr><th>순위</th><th>책이름</th><th>검색횟수</th></tr>");
            for(var i =0; i<data.length; i++){
                $("#rankbox").append("<tr><td>"+(i+1)+"</td><td>"+data[i].bookName+"</td><td>"+data[i].searchCount+"</td></tr>")
            }
        },
        error : function (data){
            console.log(data)
        }
    })
}

// 좋아요 탑텐 그리기

async function RenderLikeTop(){
    await $.ajax({
        url : "/elastic/liketop",
        type : "get",
        data : {},
        success : function(data){
            $("#rankhead").empty();
            $("#rankhead").append("<tr><th>순위</th><th>책이름</th><th>좋아요</th></tr>");
            $("#rankbox").empty();
            for(var i =0; i<data.length; i++){
                $("#rankbox").append("<tr><td>"+(i+1)+"</td><td>"+data[i].bookName+"</td><td>"+data[i].likeCount+"</td></tr>")
            }
        },
        error : function (data){}
    })
}

// 이벤트 목록 불러오기

async function RenderEventList(){
    await $.ajax({
        url : "/elastic/liketop",
        type : "get",
        data : {},
        success : function(data){
            $("#rankhead").empty();
            $("#rankhead").append("<tr><th>책이름</th><th>나눔권수</th><th>신청</th></tr>");
            $("#rankbox").empty();
            for(var i =0; i<data.length; i++){
                $("#rankbox").append("<tr><td>"data[i]"</td><td>"+data[i].bookName+"</td><td>"+data[i].likeCount+"</td></tr>")
            }
        },
        error : function (data){}
    })
}


$("#likecount").on("click",function(){
    $.ajax({
        url : "/elastic/liketop",
        type : "get",
        data : {},
        success : function(data){
            $("#rankhead").empty();
            $("#rankbox").empty();
            $("#rankhead").append("<tr><th>순위</th><th>책이름</th><th>좋아요</th></tr>");
            for(var i =0; i<data.length; i++){
                $("#rankbox").append("<tr><td>"+(i+1)+"</td><td>"+data[i].bookName+"</td><td>"+data[i].likeCount+"</td></tr>")
            }
        },
        error : function (data){
            console.log(data);
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

    $.ajax({
        url : "/elastic/automaker",
        type : "get",
        data : {"query": value},
        success : function(data){
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


// 좋아요 기능
$(document).on("click", ".fa-heart.like", function() {

    var bookid = $(this).attr("id"); // 클릭한 요소의 id 값을 가져와서 bookid에 저장
    $.ajax({
        url : "/api/mysql/book/" + bookid + "/like",
        type : "put",
        data : {},
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Content-type","application/json");
            xhr.setRequestHeader("Authorization", jwt);
        },
        success : function(data){
            var like_td = "#likecount" + data.id
            var likeCountElement = $(like_td);
            likeCountElement.text(data.like_count);
            likeCountElement.append("<i class='fa-solid fa-heart' id=" + data.id + "></i>")
        },

        error : function (error){
            console.log(error);
        }
    })
})

$(function(){
    if(localStorage.getItem('login') == 'true'){
        $("#loginline").html("</li> <li class=\"nav-item\"><a class=\"nav-link\" href=\"login\"><i class=\"fa-solid fa-user\"></i></a></li><li class='nav-item'><a class='nav-link' id='logout'>Logout</a>");
    }
    $("#logout").on("click",function (){
        $.post("api/users/logout/redis",{token: jwt},function(){
            location.href="/login";
        });
    })
})