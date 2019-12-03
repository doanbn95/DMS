var TypeDeviceEnum = {
    "3": "Thiết bị test",
    "2": "Thiết bị làm việc",
    "4": "Thiết bị văn phòng"
};

$(document).ready(function () {
    $('#example').DataTable({
        "lengthChange": false,
        "pageLength": 10,
        "info": false,
        language: {
            "paginate": {
                "next": ">>",
                "previous": "<<"
            }
        }
    });

    $('#DeleteButton').click(function () {
        var id = $('#ModalDelete').data('id');
        deleteDevice(id);
    });


});

function myFunction(type) {
    $.ajax({
            type: 'GET',
            contentType: 'application/json',
            url: "listDevice/" + type,
            dataType: 'JSON',
            cache: false,
            timeout: 600000,
            success: function (data) {
                $("#example").DataTable().clear();
                $.each(data, function (a, b) {
                    $('#example').dataTable().fnAddData([
                        b.code,
                        b.detailType,
                        b.description,
                        TypeDeviceEnum[b.type]
                    ]);

                });
                $('#myModal').modal('show');
            },
            error: function (e) {
            }
        }
    );
}

