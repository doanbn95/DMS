const INDUALDEVICE = '/individualdevice/list';

$(document).ready(function () {

    var table = $('#table').DataTable(
        {
            "lengthChange": false,
            "pageLength": 20,
            "language": {
                "info": "_START_ - _END_ of _TOTAL_",
                "infoEmpty": "Không có bản ghi hiển thị",
                "infoFiltered": "",
                "paginate": {
                    "next": ">>",
                    "previous": "<<"
                },
                zeroRecords: "Xin lỗi, không có kết quả nào tương ứng với tìm kiếm của bạn",
                "emptyTable": "Không tồn tại bản ghi"
            },
            "order": [0, "desc"],


        }
    );

    $('input:checkbox').on('change', function () {

        var status = $('input:checkbox[name="status"]:checked').map(function () {
            return this.value;
        }).get().join('|');

        table.column(1).search(status, true, false, false).draw(false);
    });

    $("#text_search").on("keyup", function () {
        table.search(this.value).draw();
    });
    $(document).scannerDetection({
        timeBeforeScanTest: 200, // wait for the next character for upto 200ms
        startChar: [120], // Prefix character for the cabled scanner (OPL6845R)
        endChar: [13], // be sure the scan is complete if key 13 (enter) is detected
        avgTimeByChar: 40, // it's not a barcode if a character takes longer than 40ms
        onComplete: function (barcode, qty) {
            $('#text_search').val(barcode);
            table.search(barcode).draw();
        }
    });

    $('ul.nav li.dropdown').hover(function () {
        $(this).find('.dropdown-menu').stop(true, true).delay(200).fadeIn(500);
    }, function () {
        $(this).find('.dropdown-menu').stop(true, true).delay(200).fadeOut(500);
    });

    $(".nav a").each(function () {
        let url = window.location.href;
        if (this.href === url || (this.href.replace("list", "") + "create") === url || (this.href.replace("list", "edit") === url.substring(0, url.lastIndexOf('/')))) {
            $(this).closest("li").addClass("selected");
        }
    });

    $('#DeleteButton').click(function () {
        var id = $('#ModalDelete').data('id');
        deleteDevice(id);
    });
    $('#staffEmail').focusin(function () {
        var emailOld = $('#staffEmail').val();
        $("#staffEmail").focusout(function () {
            var email = $("#staffEmail").val();
            if (email != "" || email != emailOld) {
                var mailRequest = {};
                mailRequest["email"] = email;
                getStaff(mailRequest);
            } else {
                $("#staffName").val('');
                $("#staffDepartment").val('');
                $("#staffId").val('');

                $('#staffName').removeClass('markValue');
                $('#staffDepartment').removeClass('markValue');
                $('#staffDepartmentNone').children().remove();
            }
        });
    });
    $("#staffEmail").keydown(function () {
        $("#staffEmail_errors").text("");
        $("#staffEmail").removeClass("input_error");

        $('#staffName').removeClass('markValue');
        $('#staffDepartment').removeClass('markValue');

    });
    $('#login-submit-body').click(function () {
        changePassword();
    });

});

//Show Modal
function showModal(id) {
    var modal = $('#ModalDelete');
    modal.data('id', id);
    modal.modal('show');
}


//Delete Device
function deleteDevice(id) {
    $.ajax({
            type: 'GET',
            contentType: 'application/json',
            url: "delete/" + id,
            dataType: 'JSON',
            cache: false,
            timeout: 600000,
            success: function () {
                $('#ModalDelete').modal('toggle');
                window.location.reload();
            },
            error: function (e) {
            }
        }
    );
}

//Close Modal
function CloseModal(id) {
    $('#' + id).modal('toggle');
}

//Set staff
function getStaff(mailRequest) {
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: "/staff/getemail",
        dataType: 'JSON',
        data: JSON.stringify(mailRequest),
        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data.status == "200") {
                $("#staffName").val(data.staff.name);
                $("#staffDepartment").val(data.staff.department);
                $("#staffDepartmentValue").val(data.staff.department);
                $("#staffEmail").val(data.staff.email);
                $("#staffId").val(data.staff.id);
                $('#staffDepartment_errors').text("");
                $('#staffDepartment').removeClass('input_error');
                $('#staffEmail').removeClass('input_error');
                $('#staffEmail_errors').text('');
                $('#staffName').removeClass('input_error');
                $('#staffName_errors').text('');
                $('#staffName').addClass('markValue');
                $('#staffDepartment').addClass('markValue');
            } else {
                $("#staffName").val('');
                $("#staffDepartment").val('');
                $("#staffDepartmentValue").val('');
                $('#staffDepartment_errors').text("");
                $('#staffDepartment').removeClass('input_error');
                $('#staffEmail').removeClass('input_error');
                $('#staffEmail_errors').text('');
                $('#staffName').removeClass('input_error');
                $('#staffName_errors').text('');
                $('#staffName').removeClass('markValue');
                $('#staffDepartment').removeClass('markValue');
                $('#staffEmail').removeClass('markValue');

                $('#staffDepartmentNone').children().remove();
            }
        }
    });
}

function resetError(id) {
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

function getValueMaster(type) {
    $.ajax(
        {
            type: 'GET',
            contentType: 'application/json',
            url: "/master/" + type,
            dataType: 'JSON',
            cache: false,
            timeout: 600000,
            success: function (data) {
                $('#detailTypeLabel').children().remove();
                $('#detailTypeContent').children().remove();
                $('#detailTypeLabel').append('<label class="title_form" for="detailType">Thiết bị</label><label class="compulsory">*</label>');
                $('#detailTypeContent').append('<select class="create_and_edit_form" id="detailType" onchange="changeElement(\'detailType\',\'Thiết bị\')" style="margin-bottom: 15px;"></select>');
                $('#detailType').append('<option value="">Thiết bị</option>');
                console.log(data);
                if (data != null) {
                    for (var i = 0; i < data.length; i++) {
                        let $option = $('<option/>', {
                            value: data[i].value,
                            text: data[i].value,
                        });
                        $('#detailType').append($option);
                    }
                }
                $('#detailType').append('<option value="Khác">Khác</option>');
                $('#detailTypeContent').append('<div id="detailTypeNone"></div>');
                $('#detailTypeContent').append('<label id="detailType_errors" class="error"></label>');

            }, error: function () {
                return null;
            }
        }
    );

}

function changeElement(id, place) {
    let value = $('#' + id).val();
    $('#' + id + 'None').children().remove();
    $('#' + id + '_errors').text('');
    $('#' + id).removeClass('input_error');
    if (value == 'Khác') {
        //Append
        $("#" + id + 'Value').val('');
        $('#' + id + 'None').append('<input type="text" id="' + id + 'Select' + '" onchange="setElement(' + id + ')"  class="create_and_edit_form" style="margin-top: 15px;" autofocus="autofocus" placeholder="' + place + '">');
        $('#' + id + 'Select').change(function () {
            setElement(id);
        });
    } else {
        $('#' + id + 'None').children().remove();
        $('#' + id + 'Value').val(value);
    }

}

function setElement(id) {
    id = id.toString();
    $('#' + id + '_errors').text('');
    $('#' + id).removeClass('input_error');
    $('#' + id + 'Value').val('');
    // let value = $('#' + id + 'Select').val();
    $('#' + id + 'Select').keydown(function () {
        $('#' + id + '_errors').text('');
        $('#' + id).removeClass('input_error');
    });
    $('#' + id + 'Select').focusout(function () {
        $('#' + id + '_errors').text('');
        $('#' + id).removeClass('input_error');
        let detail = $('#' + id + 'Select').val();
        $('#' + id + 'Value').val(detail);
    });
}

function convertDate(dateString) {
    if (dateString) {
        var list = dateString.split('-');
        return list[2] + '/' + list[1] + '/' + list[0];
    } else {
        return '';
    }

}

function setAdmin(flag) {
    let staffEmail = $('#staffEmail');
    let staffDepartmentValue = $('#staffDepartmentValue');
    let staffDepartmentSelection = $('#staffDepartment');
    let staffName = $('#staffName');
    let staffId = $('#staffId');
    if (flag) {
        var mailRequest = {};
        mailRequest["email"] = "admin@ominext.com";
        $.ajax({
                type: 'POST',
                contentType: 'application/json',
                url: "/staff/getemail",
                dataType: 'JSON',
                data: JSON.stringify(mailRequest),
                cache: false,
                timeout: 600000,
                success: function (data) {
                    staffName.val(data.staff.name);
                    staffName.attr('disabled', flag);
                    staffName.removeClass('input_error');
                    staffDepartmentValue.val(data.staff.department);
                    staffDepartmentSelection.val(data.staff.department);
                    staffDepartmentSelection.attr('disabled', flag);
                    staffDepartmentSelection.removeClass('input_error');
                    staffEmail.val(data.staff.email);
                    staffEmail.attr('disabled', flag);
                    staffEmail.removeClass('input_error');
                    $('#staffId').val(data.staff.id);
                    $('#staffEmail_errors').text('');
                    $('#staffDepartment_errors').text('');
                    $('#staffName_errors').text('');
                }
            }
        );
    } else {
        staffName.val('');
        staffName.attr('disabled', flag);
        staffName.removeClass('input_error');
        staffDepartmentValue.val('');
        staffDepartmentSelection.val('');
        staffDepartmentSelection.attr('disabled', flag);
        staffDepartmentSelection.removeClass('input_error');
        staffEmail.val('');
        staffEmail.attr('disabled', flag);
        staffEmail.removeClass('input_error');
        $('#staffId').val('');
        $('#staffEmail_errors').text('');
        $('#staffDepartment_errors').text('');
        $('#staffName_errors').text('');
    }
}

function convertText(id) {
    var value = $('#' + id).val();
    value = value == '' ? '' : value.trim();
    $('#' + id).val(value);
    return value;
}