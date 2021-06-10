$(function (){
    $("input[id*='pgBtn-']").on("click",function (){
        let filterData ={};
        filterData["color"] = $("#color").val();
        filterData["brand"] = $("#brand").val();
        filterData["title"] = $("#title").val();
        let page = $(this).prop("id").split("-")[1];

        $.ajax({
            type: "GET",
            url: "products-json",
            data: {
                page:page,
                color : filterData["color"],
                brand : filterData["brand"],
                title : filterData["title"],
                type: 'json',
            },
            success: function(response) {

                $.each( responce,function(key, card) {

                    alert(card.brand)

                })

                // $("#cards").html(response);
                // console.log(response);
            }
        });
    });
});