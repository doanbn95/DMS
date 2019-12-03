$(document).ready(function () {
    resetError('newPassword');
    $('#changeBtn').click(function () {
        changePassword();
    });
    $('#forgotBtn').click(function () {
        let email = $('#email').val();
        if (email != '') {
            forgotPass();
        } else {
            $('#email_errors').text('Vui lòng nhập email nhân viên');
            $('#email').addClass('input_error');
        }
    });
    resetError('email');
});

function changePassword() {
    let password = {};
    password['newPassword'] = $('#newPassword').val();
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: "/changePassword",
        dataType: 'JSON',
        data: JSON.stringify(password),
        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data.status == '200') {
                window.location.href = window.location.origin + "/home";
            } else {
                let arrError = data.message;
                for (i = 0; i < arrError.length; i++) {
                    if (arrError[i].field) {
                        let fieldId = arrError[i].field;
                        $("#" + fieldId).addClass("input_error");
                        $("#" + fieldId + "_errors").text(arrError[i].message);
                    }
                }
            }
        }
    });
}

function forgotPass() {
    let mail = {};
    mail['email'] = $('#email').val();
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: "/forgotPassword",
        dataType: 'JSON',
        data: JSON.stringify(mail),
        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data.status =='200') {
                window.location.href = window.location.origin + "/login";
            } else {
                let arrError = data.message;
                for (i = 0; i < arrError.length; i++) {
                    if (arrError[i].field) {
                        let fieldId = arrError[i].field;
                        $("#" + fieldId).addClass("input_error");
                        $("#" + fieldId + "_errors").text(arrError[i].message);
                    }
                }
            }
        }
    });
}

function resetError(id) {
    $('#' + id).focusin(function () {
        $(this).val('');
    });
    $('#' + id).keydown(function () {
        $('#' + id).removeClass('input_error');
        $('#' + id + '_errors').text('');
    });
    $('#' + id).change(function () {
        hideError(id);
    });
}

function hideError(id) {
    $('#' + id).removeClass('input_error');
    $('#' + id + '_errors').text('');
}
