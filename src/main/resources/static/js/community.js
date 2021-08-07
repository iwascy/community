
/*
*问题的评论
 */
function post(){
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    var accountId = $("#account_id").val();
    if(!content){
        alert("回复内容不能为空。")
        return;
    }
    $.ajax({
        type:"POST",
        url:"/question/"+questionId+"/comment",
        contentType:'application/json',
        data:JSON.stringify({
            "commentator":accountId,
            "questionId":questionId,
            "content":content,
            "type":0
        }),
        dataType:"json"
    });
    setTimeout(function (){

        window.location.reload();
    }, 200);

}
/*
*二级评论
 */
function second(){
    var questionId = $("#question_id").val();
    var content = $("#second_content").val();
    var accountId = $("#account_id").val();
    var type = $("#comment_type").val();
    $.ajax({
        type:"POST",
        url:"/question/"+questionId+"/comment",
        contentType:'application/json',
        data:JSON.stringify({
            "commentator":accountId,
            "questionId":questionId,
            "content":content,
            "type":type
        }),
        success:function (response){
            console.log(response);
        },
        dataType:"json"
    });
}

/*
*查看评论开启与关闭
 */
function collapseComment(e){
    var id = e.getAttribute("data-id")
    var comments = $("#comment-"+id);
    var collapse = e.getAttribute("data-collapse");
    if(collapse){
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
    }else{
        comments.addClass("in")
        e.setAttribute("data-collapse","in")
    }
}

/*
*回复评论开启与关闭
 */
function replyComment(e){
    var id = e.getAttribute("reply-id")
    var comments = $("#reply-"+id);
    var replyCollapse = e.getAttribute("reply-collapse");
    if(replyCollapse){
        comments.removeClass("in");
        e.removeAttribute("reply-collapse");
    }else{
        comments.addClass("in")
        e.setAttribute("reply-collapse","in")
    }
}

/*
*点赞
 */
function addPraise(e) {
    var user = $("#userId").val();
    var id = e.getAttribute("question-id");
    var question = $("#question-" + id);
    var box = document.getElementById('question-' + id);
    var count ;
    $.ajax({
        type: "POST",
        url: "/praise",
        data: JSON.stringify({
            "user": user,
            "question": id,
        }),
        contentType: "application/json",
        dataType:"json",
        cache:false,
        async:false,
        success:function (result) {
            count = result.count;
        }
    });
    var status = e.getAttribute("ifLike");
    if (status || question.hasClass("like")) {
        question.removeClass("like");
        e.removeAttribute("ifLike");
        box.innerText = '赞同 ' + (count);
    } else {
        question.addClass("like")
        e.setAttribute("ifLike", "like")
        box.innerText = '赞同 ' + (count);
    }
}

function follow(e){
    //var status = $("#follow-status").val();
    var user = $("#id").val();
    var userFollowed = $("#user-followed").val();
    var fw = $("#fw");
    var fwbox = document.getElementById('fw');
    $.ajax({
        type:"POST",
        url:"/addFollow",
        data:({
            "user":user,
            "userFollowed":userFollowed,
        }),
    });
    var status = e.getAttribute("ifLike");
    if(status == 'like' || fw.hasClass('like')){
        fw.removeClass("like");
        e.removeAttribute("ifLike");
        fwbox.innerText='关注';
    }else{
        fw.addClass("like")
       fwbox.innerText='已关注';
        e.setAttribute("ifLike","like")
    }
}

function readNotification(){
    var user = $("#userId").val();
    $.ajax({
        type:"GET",
        url:"/notification/"+user,
        data:({
            "status":true,
        })

    });
}