$(document).ready(function () {
    getCustomerDeviceList();

    $('#create').click(function () {
        submit("create");
    });

    $("#update").click(function () {
        submit("update");
    });
    XoaLoi();
    $(document).keypress(function (e) {
        let keycode = (e.keyCode ? e.keyCode : e.which);
        if (keycode == '13') {
            if (window.location.href === "http://dms.ominext.co/customerdevice/create") {
                submit("create");
            } else {
                submit("update");
            }
        }
    });


    function getCustomerDeviceList() {
        $.ajax({
            type: 'GET',
            contentType: 'application/json',
            url: "/customerdevice/customerdevices",
            cache: false,
            timeout: 60000,
            success: function (dataList) {
                if (dataList === undefined) {
                    return null;
                }
                let role = dataList['role'];
                let table = $('#table').DataTable(
                    {
                        "destroy": true,
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
                            "zeroRecords": "Xin lỗi, không có kết quả nào tương ứng với tìm kiếm của bạn",
                            "emptyTable": "Không tồn tại bản ghi"
                        },
                        "data": dataList.customerDeviceList,
                        "columns": [
                            {
                                "data": "id",
                                className: "hide-info"
                            },
                            {"data": "customerName"},
                            {"data": "device.code"},
                            {"data": "device.description"},
                            {"data": "staff.name"},
                            {"data": "staff.department"},
                            {"data": "staff.email"},
                            {
                                "data": "device.handTime",
                                render: function (data) {
                                    return data ? moment(data).format('DD/MM/YYYY') : '';
                                }
                            },
                            {
                                "data": "id",
                                render: function (data) {
                                    if (role == 'ROLE_ADMIN') {
                                        return '<span><a class="glyphicon glyphicon-list-alt" href="javascript:void(0)" onclick="getDetail(' + data + ')"></a>&ensp;<a class="glyphicon glyphicon-edit" role="button" href="/companydevice/edit/' + data + '"></a><a class="glyphicon glyphicon-trash" role="button" onclick="showModal(' + data + ')"></a></span>';
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
            }, error: function (e) {
                console.log(e);
                return null;
            }
        })
    }

});


function submit(url) {
    $("label.error").text("");
    $("input").removeClass("input_error");
    let customerDevice = {}
    customerDevice["id"] = $("#id").val();
    customerDevice["deviceCode"] = $("#deviceCode").val();
    customerDevice["codeOld"] = $("#device_codeOld").val();
    customerDevice['customerName'] = $("#customerName").val();
    customerDevice['deviceName'] = $("#deviceName").val();
    customerDevice['description'] = $("#description").val();
    customerDevice['staffEmail'] = $("#staffEmail").val();
    customerDevice['staffName'] = $("#staffName").val();
    customerDevice['staffDepartment'] = $("#staffDepartmentValue").val();
    customerDevice['handOverDay'] = $("#handOverDay").val();

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/customerdevice/" + url,
        data: JSON.stringify(customerDevice),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            let successModal = $('#successModal');
            let errorModal = $('#errorModal');
            if (data.status == '200') {
                successModal.modal('show');
                setTimeout(function () {
                    window.location.href = window.location.origin + "/customerdevice/list";
                }, 900);
            } else {
                let arrError = data.message;
                for (let i = 0; i < arrError.length; i++) {
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

function getDetail(id) {
    $.ajax({
        type: 'GET',
        contentType: 'application/json',
        url: "/customerdevice/get/" + id,
        dataType: 'JSON',
        cache: false,
        timeout: 600000,
        success: function (data) {
            $('#codeDeviceModal').text(data['deviceCode']);
            $('#nameDeviceModal').text(data['deviceName']);
            $('#detailDeviceModal').text(data['description']);
            $('#nameStaffModal').text(data['staffName']);
            $('#emailStaffModal').text(data['staffEmail']);
            $('#departmentModal').text(data['staffDepartment']);
            $('#handTimeModal').text(convertDate(data['handOverDay']));
            $('#customerModal').text(data['customerName']);
            $('#createPersonModal').text(data['createPerson']);
            $('#editPersonModal').text(data['editPerson']);
            $('#detailModal').modal('show');
        },
        error: function () {

        }
    });

}

function XoaLoi() {
    resetError('deviceCode');
    resetError('customerName');
    resetError('deviceName');
    resetError('description');
    resetError('staffEmail');
    resetError('staffName');
    resetError('staffDepartment');
    resetError('handOverDay');
}

