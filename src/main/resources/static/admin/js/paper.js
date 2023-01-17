$(function () {
    paperManager.init();
});

var paperManager = {
    myEditor: null,
    categoryId: 0,
    title: "",
    init: function () {
        paperManager.getList();
        paperManager.getCategoryList();
        paperManager.registerEvent();
        $("#showExample").on("click",function() {
           $("#mdExample").fadeToggle("fast","linear");
        });
    },
    getList: function (pageNum) {
        $.getJSON("/admin/paper/list/"+paperManager.categoryId+"/"+ (pageNum || 1),{"title":paperManager.title},function (resp) {
            if (resp.code == 200) {
                var pageInfo = resp.data;
                if (pageInfo.records.length > 0) {
                    var htmlArr = [];
                    for (var i=0; i< pageInfo.records.length; i++) {
                        var paper = pageInfo.records[i];

                        htmlArr.push("<tr>");
                        htmlArr.push("<td class='mail-select'><div class='checkbox checkbox-primary'><input id='checkbox_"+paper.id+"' type='checkbox'><label for='checkbox_"+paper.id+"'></label> </div></td>");
                        htmlArr.push("<td><a href='/"+paper.paperUrl+"' target='_blank'>"+paper.title+"</a></td> ");
                        htmlArr.push("<td>"+paper.categoryId+"</td>");
                        htmlArr.push("<td>"+(paper.tags || '')+"</td>");
                        htmlArr.push("<td>"+(paper.status == 1 ? '显示' : '隐藏')+"</td>");
                        if (paper.imgUrl.indexOf("material") > -1) {
                            htmlArr.push("<td><img src='/portal/images/random/"+paper.imgUrl+"' width='30' height='30' alt=''></td> ");
                        } else {
                            htmlArr.push("<td><img src='"+paper.imgUrl+"' width='30' height='30' alt=''></td> ");
                        }
                        htmlArr.push("<td>"+(paper.publishDate || '')+"</td>");
                        htmlArr.push("<td>"+paper.createTime+"</td>");
                        htmlArr.push("<td class='actions'><button type='button' class='btn btn-info waves-effect m-b-5 edit' data-id='"+paper.id+"'>编辑</button>");
                        htmlArr.push("  <button type='button' class='btn btn-danger waves-effect m-b-5 delete' data-id='"+paper.id+"'>删除</button></td>");
                        htmlArr.push("</tr>");

                    }
                    $("#paperTable").find("tbody").html(htmlArr.join(""));
                    paperManager.setPageBar(pageInfo);
                    paperManager.bindClick();
                } else {
                    $("#paperTable").find("tbody").html("<tr><td colspan='9' align='center'>暂无数据</td></tr>");
                    $("#pageBar").html("");
                }
            } else {
                swal("查询失败", resp.msg,"error");
            }
        });
    },
    setPageBar: function (pageInfo) {
        var html = "<div class='col-xs-7' id='totalCount'></div>";
            html += "<div class='col-xs-5'>";
            html += "<div class='btn-group pull-right'>";
            html += "<button type='button' class='btn btn-default waves-effect' id='preBtn'><i class='fa fa-chevron-left'></i></button>";
            html += "<button type='button' class='btn btn-default waves-effect' id='nextBtn'><i class='fa fa-chevron-right'></i></button>";
            html += "</div>";
            html += "</div>";
        $("#pageBar").html(html);
        $("#totalCount").text("当前第 "+ pageInfo.current +"/" + pageInfo.pages + " 页 - 总共 " + pageInfo.total + " 条记录");

        $("#preBtn").on("click",function () {
            if (!pageInfo.hasPreviousPage) {
                return;
            }
            paperManager.getList(pageInfo.current - 1);
        });

        $("#nextBtn").on("click",function () {
            if (!pageInfo.hasNextPage) {
                return;
            }
            paperManager.getList(pageInfo.current + 1);
        });
    },
    bindClick: function () {
        $(".edit").on("click",function () {
            var id = $(this).data("id");
            $.getJSON("/admin/paper/get/"+id,function (resp) {
                if (resp.code == 200) {

                    for(var key in resp.data) {
                        $("#" + key).val(resp.data[key]);
                    }

                    paperManager.createEditor(function() {
                        var paper = resp.data;
                        for(var key in paper) {

                            if(key == "tags") {
                                $("#tags").tagsinput('add', paper[key]);
                            } else if(key == "status") {
                                $("#showStatus").prop('checked', paper[key] == 1).change()
                            } else {
                                $("#" + key).val(paper[key]);
                            }

                        }
                        paperManager.myEditor.setMarkdown(paper.content);
                    });

                    paperManager.createFileComponent();

                    $("#saveUI").modal("show");
                } else {
                    swal("查询失败", resp.msg,"error");
                }
            })
        });

        $(".delete").on("click",function () {
            var that = this;
            swal({
                    title: "确定删除吗？",
                    text: "",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    closeOnConfirm: false
                },
                function(){
                    var id = $(that).data("id");
                    $.post("/admin/paper/delete/"+id,null,function (resp) {
                        if (resp.code == 200) {
                            swal("删除成功", "","success");
                            paperManager.getList();
                        } else {
                            swal("删除失败", resp.msg,"error");
                        }
                    },"json");
                });
        });
    },
    registerEvent: function () {

        $("#checkboxAll").on("click",function () {
            $("#paperTable").find("input[type='checkbox']").prop("checked",$(this).prop("checked"));
        });

        $("#deletesBtn").on("click",function () {
            var idArr = [];
            $("#paperTable").find("tbody").find("input[type='checkbox']:checked").each(function (index,domEle) {
                var id = $(domEle).attr("id").split("_")[1];
                idArr.push(id);
            });

            if (idArr.length > 0) {
                swal({
                        title: "确定删除吗？",
                        text: "",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "确定",
                        cancelButtonText: "取消",
                        closeOnConfirm: false
                    },
                    function(){
                        $.post("/admin/paper/deleteBatch/"+idArr.join(","),null,function (resp) {
                            if (resp.code == 200) {
                                swal("删除成功", "","success");
                                $("#checkboxAll").prop("checked",false);
                                paperManager.getList();
                            } else {
                                swal("删除失败", resp.msg,"error");
                            }
                        },"json");
                    });
            } else {
                swal("删除失败", "请勾选文章后进行删除","error");
            }
        });

        $("#showBtn").on("click",function () {

            $("#saveUI").modal("show");

            paperManager.createEditor(function() {
                $("#paperForm").get(0).reset();
                $("#tags").tagsinput('removeAll');
                $("input[type='hidden']").val("");
            });

            paperManager.createFileComponent();
        });

        $("#submitBtn").on("click",function () {
            var parameter = {
                "id": $("#id").val(),
                "title": $("#title").val(),
                "categoryId": $("#categoryId").val(),
                "tags": $("#tags").val(),
                "status": ($("#showStatus").prop("checked") ? 1: 0 ),
                "imgUrl": $("#imgUrl").val(),
                "content": paperManager.myEditor.getMarkdown()
            };

            $.post("/admin/paper/save",parameter,function (resp) {
                if (resp.code == 200) {
                    $("#saveUI").modal("hide");
                    swal("保存成功", "","success");
                    paperManager.getList();
                } else {
                    swal("保存失败", resp.msg,"error");
                }
            },"json");
        });

        $("#importUIBtn").on("click",function () {
            $("#path").val("");
            $("#importUI").modal("show");
        });

        // md 文件导入事件
        $("#importBtn").on("click",function () {
            var that = this;
            var path = $("#path").val();
            if (!path) {
                swal("导入目录不能为空", "","error");
                return;
            }
            var index = layer.load(1);
            $(that).prop("disabled",true);
           $.post("/admin/paper/importFiles",{"path": path},function (resp) {
               layer.close(index);
               $(that).prop("disabled",false);
               if (resp.code == 200) {
                   $("#importUI").modal("hide");
                   swal("导入成功", "","success");
                   window.location.reload(true);
               } else {
                   swal("导入失败", resp.msg,"error");
               }
           },"json");
        });

        // 查询事件
        $("#queryBtn").on("click",function () {
            paperManager.title = $("#query").val();
            paperManager.getList();
        });
    },
    getCategoryList: function () {
        $.getJSON("/admin/category/listAll",function (resp) {
           if (resp.code == 200) {
               var htmlArr = ["<a href='javascript:void(0)' class='list-group-item no-border category active' data-id='0'><span class='fa fa-circle text-default pull-right'></span>全部</a>"];

               for (var i=0; i<resp.data.length; i++) {
                   var category = resp.data[i];
                   htmlArr.push("<a href='javascript:void(0)' class='list-group-item no-border category' data-id='"+category.id+"'><span class='fa fa-circle "+category.color+" pull-right'></span>"+category.name+"</a>");
               }

               $("#categoryType").html(htmlArr.join(""));

               var htmlArr2 = ["<option value=''>--请选择--</option>"];
               for (var j=0; j<resp.data.length; j++) {
                   var category2 = resp.data[j];
                   htmlArr2.push("<option value='"+category2.id+"'>"+category2.name+"</option>");
               }

               $("#categoryId").html(htmlArr2.join(""));

               $(".category").on("click",function () {
                  if ($(this).hasClass("active") ) {
                      return;
                  }

                  $(this).parent().children().removeClass("active");
                  $(this).addClass("active");
                  paperManager.categoryId = $(this).data("id");
                  paperManager.getList();

               });

           } else {
               swal("查询失败", resp.msg,"error");
           }
        });
    },
    createEditor: function (callback) {
        $("#editorContainer").html("<div class='col-lg-12' id='my-editormd'></div>");
        paperManager.myEditor = editormd("my-editormd", {
            width   : "100%",
            height  :  document.documentElement.clientHeight - 260,
            syncScrolling : "single",
            path    : "/admin/assets/plugins/editormd/lib/",
            //placeholder: "内容写在这里~~",
            toolbarIcons : function() {
                return ["undo","redo","bold","del","italic","quote","ucwords","uppercase","lowercase","h1","h2","h3","h4","h5","h6","list-ul","list-ol","hr","link","reference-link","image","code","preformatted-text","code-block","table","datetime","pagebreak","watch","unwatch","preview","clear","search"]
            },
            codeFold : true,
            imageUpload : true,
            imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
            imageUploadURL : "/admin/mdUploadfile",
            onload: function () {
                callback();
            }
        });
    },
    createFileComponent: function () {
        $("#fileContainer").html("<div style='text-align: center;line-height: 80px;'>需要配置七牛云参数</div>");
        $("#btnContainer").html("预览图<br><br><span id='uploadBtn' style='cursor: pointer;color: #6e8cd7;'>点击上传</span>");
        $("#uploadBtn").dropzone({
            url: "/admin/uploadfile", //上传地址
            method: "POST", //方式
            addRemoveLinks: true,
            maxFiles: 10,
            maxFilesize: 5,
            uploadMultiple: false,
            parallelUploads: 100,
            previewsContainer: false,
            acceptedFiles: ".jpg, .jpeg,.png",
            success: function(file, resp, e) {
                if (resp.code == 200) {
                    $("#fileContainer").html("<img src='"+resp.data+"' style='width:100%;height:80px'/>");
                    $("#imgUrl").val(resp.data);
                } else {
                    swal("文件上传失败", resp.msg,"error");
                    // 重新创建，否则需要刷新页面才能继续上传文件
                    paperManager.createFileComponent();
                }
            }
        });
    }
}