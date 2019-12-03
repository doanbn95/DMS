$(document).ready(function () {

    $('#create').click(function () {

        submitIndualDevice("create");
    });

    $("#update").click(function () {

        submitIndualDevice("update");
    });
    $(document).keypress(function (e) {
        let keycode = (e.keyCode ? e.keyCode : e.which);
        if (keycode == '13') {
            if (window.location.href === "http://dms.ominext.co/individualdevice/create") {
                submitIndualDevice("create");
            } else {
                submitIndualDevice("update");
            }
        }
    });
    XoaLoi();
    let path = window.location.pathname;
    let url;
    if (path == INDUALDEVICE) {
        url = path + '/table';
    }
    loadData(url);
});

function loadData(url) {
    if (url) {
        $.ajax({
            type: 'GET',
            contentType: 'application/json',
            url: url,
            cache: false,
            timeout: 60000,
            success: function (data) {
                if (data === undefined) {
                    return null;
                }
                let role = data['role'];
                let table = $('#table').DataTable(
                    {
                        destroy: true,
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
                        data: data.list,
                        columns: [
                            {
                                data: "id",
                                className: 'hide-info'
                            },
                            {data: "staff.name"},
                            {data: "staff.department"},
                            {data: "staff.email"},
                            {data: "codeDevice"},
                            {data: "detail"},
                            {
                                data: "startDate",
                                render: function (data) {
                                    return data ? moment(data).format('DD/MM/YYYY') : '';
                                }
                            },
                            {
                                data: "endDate",
                                render: function (data) {
                                    return data ? moment(data).format('DD/MM/YYYY') : '';
                                }
                            },
                            {
                                "data": "id",
                                render: function (data) {
                                    if (role == 'ROLE_ADMIN') {
                                        return '<span><a class="glyphicon glyphicon-list-alt" href="javascript:void(0)" onclick="getDetail(' + data + ')"></a>&ensp;<a class="glyphicon glyphicon-edit" role="button" href="/individualdevice/edit/' + data + '"></a><a class="glyphicon glyphicon-trash" role="button" onclick="showModal(' + data + ')"></a></span>';
                                    } else {
                                        return '<span><span><a class="glyphicon glyphicon-list-alt" href="javascript:void(0)" onclick="getDetail(' + data + ')"></a></span>'
                                    }
                                }
                            }
                        ],
                        "deferRender": true,
                        "order": [0, "desc"]

                    }
                );
                $("#text_search").on("keyup", function () {
                    table.search(this.value).draw();
                });
            }

        });
    }

}

function submitIndualDevice(url) {
    $('label.error').text("");
    $("input").removeClass("input_error");
    var indualDevice = {};
    indualDevice["id"] = $('#id').val();
    indualDevice["codeDeviceOld"] = $("#codeDeviceOld").val() == '' ? '' : $("#codeDeviceOld").val().trim();
    indualDevice["staffId"] = $("#staffId").val() == '' ? '' : $("#staffId").val().trim();
    indualDevice["staffName"] = convertText('staffName');
    indualDevice["staffDepartment"] = convertText('staffDepartmentValue')
    indualDevice["staffEmail"] = convertText('staffEmail');
    indualDevice["codeDevice"] = convertText('codeDevice');
    indualDevice["detail"] = convertText('detail');
    indualDevice["startDate"] = $("#startDate").val();
    indualDevice["endDate"] = $("#endDate").val();
    indualDevice["reason"] = convertText('reason');
    indualDevice['editPerson'] = $('#editPerson').val();
    indualDevice['createPerson'] = $('#createPerson').val();
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: "/individualdevice/" + url,
        dataType: 'JSON',
        data: JSON.stringify(indualDevice),
        cache: false,
        timeout: 600000,
        success: function (data) {
            let successModal = $('#successModal');
            let errorModal = $('#errorModal');
            if (data.status == '200') {
                successModal.modal('show');
                setTimeout(function () {
                    window.location.href = window.location.origin + "/individualdevice/list";
                }, 900);
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

function resetError(id) {
    $('#' + id).keydown(function () {
        $('#' + id).removeClass('input_error');
        $('#' + id + '_errors').text('');
    });
    $('#' + id).change(function () {
        hideError(id);
    });
}

function XoaLoi() {
    // resetError('nameDevice');
    resetError('provideUnit');
    resetError('staffName');
    resetError('staffDepartment');
    resetError('codeDevice');
    resetError('detail');
    resetError('startDate');
    resetError('endDate');
    resetError('reason');
}

function hideError(id) {
    $('#' + id).removeClass('input_error');
    $('#' + id + '_errors').text('');
}

function getDetail(id) {
    $.ajax({
        type: 'GET',
        contentType: 'application/json',
        url: "/individualdevice/get/" + id,
        dataType: 'JSON',
        cache: false,
        timeout: 600000,
        success: function (data) {
            $('#codeDeviceModal').text(data['codeDevice']);
            $('#detailDeviceModal').text(data['detail']);
            $('#nameStaffModal').text(data['staffName']);
            $('#emailStaffModal').text(data['staffEmail']);
            $('#departmentModal').text(data['staffDepartment']);
            $('#startDateModal').text(convertDate(data['startDate']));
            $('#endDateModal').text(convertDate(data['endDate']));
            $('#reasonModal').text(data['reason']);
            $('#createPersonModal').text(data['createPerson']);
            $('#editPersonModal').text(data['editPerson']);
            $('#detailModal').modal('show');
        },
        error: function () {

        }
    });

}

function getValueById(id) {
    let error = $('#' + id);
    error.removeClass('input_error');
    $('#' + id + '_errors').text('');
    error.keydown(function () {
        $('#' + id).removeClass('input_error');
        $('#' + id + '_errors').text('');
    });
    $('#' + id + 'Value').val('');
    error.focusout(function () {
        $('#' + id + 'Value').val($(this).val());
    });
    error.change(function () {
        $('#' + id + 'Value').val($(this).val());
    });
}


