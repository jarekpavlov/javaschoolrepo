$(function (){
    $("input[id*='pgBtn-']").on("click",function (){
        let filterData ={};
        filterData["color"] = $("#color").val();
        filterData["brand"] = $("#brand").val();
        filterData["title"] = $("#title").val();
        let page = $(this).prop("id").split("-")[1]

        $.ajax({
            type: "GET",
            url: "products-json",
            data: {
                buttonPage:page,
                color : filterData["color"],
                brand : filterData["brand"],
                title : filterData["title"],
            },
            dataType : 'json',
            success: function(filterD) {
                // alert(filterD.category);
                // alert(filterD.color);

                console.log(response);
            }
        });
    });
});