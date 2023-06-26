$(document).ready(function () {
    getEventList();
    getCalender();
});

function getCalender() {
    $.datepicker.setDefaults($.datepicker.regional['ko']);

    $( ".calender" ).datepicker({
        changeMonth: true,
        changeYear: true,
        nextText: '다음 달',
        prevText: '이전 달',
        dayNames: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'],
        dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
        monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
        monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
        dateFormat: "yymmdd",
        maxDate: 0                       // 선택할수있는 최소날짜, ( 0 : 오늘 이후 날짜 선택 불가)
    });
}

function searchEventList() {
    console.log("책 가져오기 클릭")
    try {
        $.ajax({
            url: "/api/event/searchDat",
            type: "get",
            data: {
            },
            dataType: "JSON",
            success: function(response){
                let rows = response.result;
                console.log(rows);
                $("#bookList").empty();
                for(let i =0; i<rows.length; i++){
                    let print = rows[i].split("<>");
                    console.log(print);
                    let bookId = print[0];
                    let bookNm = print[1];
                    let temp_html = `<tr>
                                         <td><input type="checkbox" id ="chkEvent${i}"></td>
                                         <td>${bookId}</td>
                                         <td>${bookNm}</td>
                                         <td><input type="text" id ="txtTotalCnt${i}"></td>
                                         <td><input type="text" id ="txtCnt${i}"></td>
                                         <td><input type="text" id ="txtReason${i}"></td>
                                         <td><input type="text" class ="calender" id="eventDate${i}"></td>
                                         <td><input type="text" id="eventTime${i}"></td>
                                         <td><input type="checkbox" id ="chkValid${i}"></td>
                                    </tr>`
                    $("#bookList").append(temp_html);
                }
                getCalender();

            },
            error : function (request, status, error) {
                console.log("error code : " + request.status + "\n message : " + request.responseText + "\n error : " + error)
                alert("error : " + error)
            }
        })
    } catch (error) {
        console.log(error);
    }
}

function getPreEventList() {
    console.log("이벤트 불러오기 클릭")
    try {
        let getDate = $("#searchToggle option:checked").text();
        console.log(getDate);

        $.ajax({
            url: "/api/event/getPreEventList",
            type: "get",
            data: {
                "searchDat": getDate
            },
            dataType: "JSON",
            success: function(response){
                let val = response.result;
                console.log(val);
                $("#bookList").empty();
                for(let i =0; i<val.length; i++){
                    let rows = val[i];
                    let bookCnt = rows["bookCnt"];
                    console.log(rows);
                    let bookId = rows["bookId"];
                    let bookTotalCnt = rows["bookTotalCnt"];
                    let eventDate = rows["eventDate"];
                    let eventTime = rows["eventTime"];
                    let eventId = rows["id"];
                    let isValid = rows["isvalid"];
                    let reason = rows["reason"];
                    let temp_html = `<tr>
                                         <td><input type="checkbox" id ="chkEvent${i}"></td>
                                         <td>${bookId}</td>
                                         <td>BOOKNM</td>
                                         <td><input type="text" id ="txtTotalCnt${i}" value="${bookTotalCnt}"></td>
                                         <td><input type="text" id ="txtCnt${i}" value="${bookCnt}"></td>
                                         <td><input type="text" id ="txtReason${i}" value="${reason}"></td>
                                         <td><input type="text" class ="calender" id="eventDate${i}" value="${eventDate}"></td>
                                         <td><input type="text" id="eventTime${i}" value="${eventTime}"></td>
                                         <td><input type="checkbox" id ="chkValid${i}" value="${isValid}"></td>
                                    </tr>`
                    $("#bookList").append(temp_html);
                    getCalender();

                }
            },
            error : function (request, status, error) {
                console.log("error code : " + request.status + "\n message : " + request.responseText + "\n error : " + error)
                alert("error : " + error)
            }
        })
    } catch (error) {
        console.log(error);
    }
}

function saveEvent() {
    console.log("이벤트 저장하기 클릭")
    try {
        let table = document.getElementById("eventTable");
        let getListRow = table.rows.length;
        $.ajax({
            url: "/elastic/liketop",
            type: "get",
            data: {},
            dataType: "JSON",
            success: function(response){
                let rows = response["result"];
                $("#bookList").empty();
                rows.forEach(i => {
                    let bookId = i["bookId"];
                    let temp_html = `<tr>
                                         <td><input type="checkbox" id ="chkEvent${i}"></td>
                                         <td>${bookId}</td>
                                         <td><input type="text" id ="txtTotalCnt${i}"></td>
                                         <td><input type="text" id ="txtCnt${i}"></td>
                                         <td><input type="text" id ="txtReason${i}"></td>
                                         <td><input type="text" id="eventDate${i}"></td>
                                         <td><input type="text" id="eventTime${i}"></td>
                                         <td><input type="checkbox" id ="chkValid${i}"></td>
                                    </tr>`
                    $("#bookList").append(temp_html);
                });
            },
            error : function (request, status, error) {
                console.log("error code : " + request.status + "\n message : " + request.responseText + "\n error : " + error)
                alert("error : " + error)
            }
        })
    } catch (error) {
        console.log(error);
    }
}

function getEventList() {

    $.ajax({
        url : "/api/event/getEventDat",
        type: "GET",
        data: {},
        dataType: "JSON",
        success: function(response){
            console.log(response);
            let rows = response.result;
            console.log(rows);

            $("#searchToggle").empty();
            // rows.forEach(i => {
            //     let datList = i;
            //     let temp_html = `<option value="${i}">${datList}</option>`
            //     $("#searchToggle").append(temp_html);
            // });
            for(let i =0; i<rows.length; i++){
                let datList = rows[i];
                let temp_html = `<option value="${i}">${datList}</option>`
                $("#searchToggle").append(temp_html);
            }
        },
        error : function (request, status, error) {
            console.log("error code : " + request.status + "\n message : " + request.responseText + "\n error : " + error)
            alert("error : " + error)
        }
    })

}