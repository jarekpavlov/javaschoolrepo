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
            },
            success: function(response) {
                $("#cards").empty();

                // $.each(response, function(index, element) {
                //     alert(element.color);
                //     alert(index);
                // });

            }
        });
    });
});
$(function (){
    $("input[id*='cartBtn-']").on("click",function (){
        let productId = $(this).prop("id").split("-")[1];
        let csrfValue = $("input[name=_csrf]").val();

        $.ajax({
            type: "POST",
            url: "order/add-to-cart",
            data: {
                productId : productId,
                _csrf: csrfValue
            },
            success: function(response) {
                $("#addToCartButton").empty().append(response);
            }
        });
    });
});
