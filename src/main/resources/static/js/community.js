
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
        success:function (){
            window.open("www.baidu.com")
        },
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
function like(){

}

    layui.use('element', function(){
    var $ = layui.jquery
    ,element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块

    //触发事件
    var active = {
    tabAdd: function(){
    //新增一个Tab项
    element.tabAdd('demo', {
    title: '新选项'+ (Math.random()*1000|0) //用于演示
    ,content: '内容'+ (Math.random()*1000|0)
    ,id: new Date().getTime() //实际使用一般是规定好的id，这里以时间戳模拟下
})
}
    ,tabChange: function(){
    //切换到指定Tab项
    element.tabChange('demo', '22'); //切换到：用户管理
}
};

    $('.site-demo-active').on('click', function(){
    var othis = $(this), type = othis.data('type');
    active[type] ? active[type].call(this, othis) : '';
});

    //Hash地址的定位
    var layid = location.hash.replace(/^#test=/, '');
    element.tabChange('test', layid);
    element.on('tab(test)', function(elem){
    location.hash = 'test='+ $(this).attr('lay-id');
});

});


function addLike(){
    var questionId = $("#question_id").val();
    var userId = $("#account_id").val();
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