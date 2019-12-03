$(document).ready(function () {

    $('#create').click(function () {
        $("#create").attr("disabled", true);
        submit("create");
    });

    $("#update").click(function () {
        $("#update").attr("disabled", true);
        submit("update");
    });
    XoaLoi();
    $(document).keypress(function (e) {
        let keycode = (e.keyCode ? e.keyCode : e.which);
        if (keycode == '13') {
            if (window.location.href === "http://dms.ominext.co/user/create") {
                submit("create");
            } else {
                submit("update");
            }
        }
    });
});

function submit(url) {
    $("label.error").text("");
    $("input").removeClass("input_error");
    var user = {}
    user["id"] = $("#id").val();
    user["userName"] = $("#userName").val();
    user["userNameOld"] = $("#userNameOld").val();
    user["role"] = $("#role").val();
    user["name"] = $("#name").val();
    user["department"] = $("#departmentValue").val();
    user["createPerson"] = $("#createPerson").val();
    user["createDay"] = $("#createDay").val();

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/user/" + url,
        data: JSON.stringify(user),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            let successModal = $('#successModal');
            $("#" + url).attr("disabled", false);
            if (data.status == "200") {
                successModal.modal('show');
                setTimeout(function () {
                    window.location.href = window.location.origin + "/user/list";
                }, 900)
            } else {
                var arrError = data.message;
                for (i = 0; i < arrError.length; i++) {
                    if (arrError[i].field) {
                        var fieldId = arrError[i].field;
                        $("#" + fieldId).addClass("input_error");
                        $("#" + fieldId + "_errors").text(arrError[i].message);
                    }
                }
            }
        }
    });
}

function XoaLoi() {
    resetError('userName');
    resetError('name');
    resetError('role');
    resetError('department');
    resetError('createPerson');
}