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
                $.each(response.products, function(index, element) {
                    let imgSource;
                    let addButton;
                    if (element.imgName == null) {
                        imgSource = "/MmsPr/resources/images/picture-tag.png";
                    } else {
                        imgSource = "/MmsPr/pictures/"+element.imgName;
                    }
                    if (response.role!=="ROLE_EMPLOYEE"){
                        addButton = '<form id="productCartForm" action="/MmsPr/order/add-to-cart?id='+element.id+'" method="get" >'+
                                        '<div class="row">'+
                                            '<div class="col">'+
                                               '<input type="button" class="btn btn-info btn-sm" id="cartBtn-'+element.id+'" value="Add to Cart"/>'+
                                            '</div>'+
                                        '</div>'+
                                    '</form>';
                    } else {
                        addButton = '<div class="row">'+
                                        '<div class="col">'+
                                           ' <a class="btn btn-info btn-sm" href="/MmsPr/admin/product/edit?id='+element.id+'">Edit</a>'+
                                        '</div>'+
                                        '<div class="col">'+
                                           ' <a class="btn btn-info btn-sm" href="/MmsPr/admin/product/delete?id='+element.id+'">Delete</a>'+
                                        '</div>'+
                                    '</div>';
                    }
                     let oneCard = '<div class="card text-left" style="width: 14rem">'+
                                       '<img class="card-img-top"  src="'+imgSource+'" alt="Card image cap">'+
                                       '<div class="card-body">'+
                                         '<div class="row">'+
                                             '<div class="col">'+
                                                 '<h6>'+element.brand+'</h6>'+
                                                    element.price +'€'+
                                             '</div>'+
                                             '<div class="col">'+
                                                 '<h6 class="text-muted">'+element.title+'</h6>'+
                                                 '<small>quantity:&nbsp;'+element.quantity+'</small>'+
                                             '</div>'+
                                         '</div>'+
                                       '</div>'
                                        +addButton+
                                   '</div>';
                    $("#cards").append(oneCard);
                });
            }
        });
    });
});
$(document).on('click',"input[id*='cartBtn-']", function(){
        let productId = $(this).prop("id").split("-")[1];

        $.ajax({
            type: "GET",
            url: "order/add-to-cart",
            data: {
                productId : productId,
            },
            success: function(response) {
                $("#addToCartButton").empty().append(response);
            }
        });
    });

