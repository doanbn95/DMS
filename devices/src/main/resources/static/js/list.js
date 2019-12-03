var statusEnum = {
    1: "Mới",
    2: "Đang sử dụng",
    3: "Lưu kho",
    4: "Đang bảo hành",
    5: "Thanh lý"
}
var TypeDeviceEnum = {
    "3": "Thiết bị test",
    "2": "Thiết bị làm việc",
    "4": "Thiết bị văn phòng"
};
$(document).ready(function () {
    let path = window.location.pathname;
    let url;
    if (path == "/testdevice/list") {
        url = path + '/table';
        getList(url);
    } else if (path == '/companydevice/list') {
        url = path + '/table';
        getList(url);
    }


    function getList(url) {
        $.ajax({
            type: 'GET',
            contentType: 'application/json',
            url: url,
            cache: false,
            timeout: 60000,
            success: function (dataList) {
                if (dataList === undefined) {
                    return null;
                }
                let role = dataList['role'];
                var table = $('#table').DataTable(
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
                        "data": dataList.list,
                        "columns": [
                            {
                                "data": "id",
                                className: "hide-info"
                            },
                            {"data": "device.code"},
                            {
                                "data": "device.status",
                                render: function (data) {
                                    return statusEnum[data];
                                }
                            },
                            {"data": "device.provideUnit"},
                            {"data": "device.detailType"},
                            {
                                "data": "device.type",
                                render: function (data) {
                                    return TypeDeviceEnum[data];
                                }
                            },
                            {"data": "device.description"},
                            {
                                "data": "device.boughtDate",
                                className: "hide-info", render: function (data) {
                                    return data ? moment(data).format('DD/MM/YYYY') : '';
                                }
                            },
                            {
                                "data": "device.boughtCost",
                                className: "hide-info"
                            },
                            {
                                "data": "staff.name",
                                className: "hide-info"
                            },
                            {
                                "data": "staff.department",
                                className: "hide-info"
                            },
                            {"data": "staff.email"},
                            {
                                "data": "device.handTime",
                                className: "hide-info",
                                render: function (data) {
                                    return data ? moment(data).format('DD/MM/YYYY') : '';
                                }
                            },
                            {
                                "data": "device.buyDate",
                                className: "hide-info",
                                render: function (data) {
                                    return data ? moment(data).format('DD/MM/YYYY') : '';
                                }
                            },
                            {
                                "data": "device.buyCost",
                                className: "hide-info",

                            },
                            {
                                "data": "device.returnDate",
                                className: "hide-info",
                                render: function (data) {
                                    return data ? moment(data).format('DD/MM/YYYY') : '';
                                }
                            },
                            {
                                "data": "id",
                                render: function (data) {
                                    if (role == 'ROLE_ADMIN') {
                                        if (path == '/companydevice/list') {
                                            return '<span><a class="glyphicon glyphicon-list-alt" href="javascript:void(0)" onclick="getDetail(' + data + ')"></a>&ensp;<a class="glyphicon glyphicon-edit" role="button" href="/companydevice/edit/' + data + '"></a><a class="glyphicon glyphicon-trash" role="button" onclick="showModal(' + data + ')"></a></span>';
                                        } else if (path == '/testdevice/list') {
                                            return '<span><a class="glyphicon glyphicon-list-alt" href="javascript:void(0)" onclick="getDetail(' + data + ')"></a>&ensp;<a class="glyphicon glyphicon-edit" role="button" href="/testdevice/edit/' + data + '"></a><a class="glyphicon glyphicon-trash" role="button" onclick="showModal(' + data + ')"></a></span>';
                                        }
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

                $('input:checkbox').on('change', function () {

                    var status = $('input:checkbox[name="status"]:checked').map(function () {
                        return this.value;
                    }).get().join('|');

                    table.column(2).search(status, true, false, false).draw(false);
                });

                $("#text_search").on("keyup", function () {
                    table.search(this.value).draw();
                });
                var buttons = new $.fn.dataTable.Buttons(table, {
                    buttons: [
                        {
                            extend: 'excelHtml5',
                            text: 'EXPORT',
                            className: 'btn-primary',
                            exportOptions: {
                                columns: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14]
                            },
                            autoFilter: true
                        }

                    ]
                }).container().appendTo($('#export-btn'));
            }, error: function (e) {
                console.log(e);
                return null;
            }
        });
    }
});