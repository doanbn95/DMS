$(document).ready(function () {
    $('#create').click(function () {
        submitTest("create");
    });

    $("#update").click(function () {
        submitTest("update");
    });

    $('#status').on('change', function (e) {
        $('#status_errors').text('');
        $('#status').removeClass('input_error');
        changeStatus('');
    });
    XoaLoi();
});
$(document).keypress(function (e) {
    let keycode = (e.keyCode ? e.keyCode : e.which);
    if (keycode == '13') {
        if (window.location.href === "http://dms.ominext.co/testdevice/create") {
            submitTest("create");
        } else {
            submitTest("update");
        }
    }
});

function changeStatus(statusValue) {
    $('#staffEmail').removeClass('markValue');
    $('#staffName').removeClass('markValue');
    $('#staffDepartment').removeClass('markValue');
    let view = $('#view');
    let status = statusValue == '' ? $('#status option:selected').val() : statusValue.toString();
    setAdmin(false);
    switch (status) {
        case'1':
            //Moi
            view.children().remove();
            setAdmin(true);
            setValueById('buyCost', '');
            setValueById('buyDate', '');
            // setValueById('addressWarranty', '');
            setValueById('returnDate', '');
            // setValueById('reasonWarranty', '');
            setValueById('handTime', '');
            break;
        case'2':
            //Dang su dung
            view.children().remove();
            setAdmin(false);
            view.append('<div class="col-md-3 label_for_form"><label class="title_form" for="handTime">Ngày bàn giao</label><label class="compulsory">*</label></div>');
            view.append('<div class="col-md-7"><input type="date" id="handTime" name="handTime" class="create_and_edit_form" onchange="getValueById(\'handTime\')" autofocus="autofocus" placeholder="Ngày bàn giao" style="margin-bottom: 15px;"><label id="handTime_errors" class="error"></label></div>');
            view.append('<div class="col-md-3 label_for_form"><label id="returnDateLabel" class="title_form" for="returnDate">Ngày trả</label><label class="compulsory">*</label></div>');
            view.append('<div class="col-md-7"><input type="date" id="returnDate" name="returnDate" class="create_and_edit_form" onchange="getValueById(\'returnDate\')"  autofocus="autofocus" placeholder="Ngày trả" style="margin-bottom: 15px;"><label id="returnDate_errors" class="error"></label></div>');
            break;
        case '3':
            //Luu kho
            view.children().remove();
            setAdmin(true);
            setValueById('buyCost', '');
            setValueById('buyDate', '');
            // setValueById('addressWarranty', '');
            setValueById('returnDate', '');
            setValueById('reasonWarranty', '');
            setValueById('handTime', '');
            break;
        case'4':
            //Bao hanh
            view.children().remove();
            setAdmin(true);
            break;
        case'5':
            //Thanh ly
            view.children().remove();
            setAdmin(true);
            view.append('<div class="col-md-3 label_for_form"><label class="title_form" for="buyDate">Ngày thanh lý</label><label class="compulsory">*</label></div>');
            view.append('<div class="col-md-7"><input type="date" id="buyDate" name="buyDate" onchange="getValueById(\'buyDate\')"  class="create_and_edit_form" autofocus="autofocus" placeholder="Ngày thanh lý" style="margin-bottom: 15px;"><label id="buyDate_errors" class="error"></label></div>');
            view.append('<div class="col-md-3 label_for_form"><label class="title_form" for="buyCost">Giá thanh lý</label><label class="compulsory">*</label></div>');
            view.append('<div class="col-md-7"><input type="text" id="buyCost" name="buyCost" class="create_and_edit_form" onkeydown="getValueById(\'buyCost\')" autofocus="autofocus" placeholder="Giá thanh lý" style="margin-bottom: 15px;"><label id="buyCost_errors" class="error"></label></div>');
            break;
        default:
            view.children().remove();
            // setValueById('typeDevice', '');
            setValueById('buyCost', '');
            setValueById('buyDate', '');
            // setValueById('addressWarranty', '');
            setValueById('returnDate', '');
            // setValueById('reasonWarranty', '');
            setValueById('handTime', '');
            break;
    }

}

function submitTest(url) {
    $('label.error').text("");
    $("input").removeClass("input_error");
    let company = {};
    company['id'] = $('#id').val();
    company['codeDevice'] = convertText('codeDevice');
    company['typeDevice'] = '3';
    company['detailType'] = convertText('detailTypeValue');
    company['description'] = convertText('description');
    company['provideUnit'] = convertText('provideUnit');
    company['buyCost'] = $('#buyCostValue').val();
    company['buyDate'] = $('#buyDateValue').val();
    company['addressWarranty'] =convertText('addressWarranty');
    company['status'] = $('#status').val();
    company['returnDate'] = $('#returnDateValue').val();
    company['warrantyTime'] = $('#warrantyTime').val();
    // company['reasonWarranty'] = $('#reasonWarrantyValue').val();
    company['handTime'] = $('#handTimeValue').val();
    // company['staffCode'] = convertText('staffCode');
    company['staffName'] = convertText('staffName');
    company['staffDepartment'] = convertText('staffDepartmentValue');
    company['staffEmail'] = convertText('staffEmail');
    company['codeDeviceOld'] = $('#codeDeviceOld').val();
    company['staffId'] = $('#staffId').val();
    company['boughtCost'] = $('#boughtCost').val();
    company['boughtDate'] = $('#boughtDate').val();
    company['createPerson'] = $('#createPerson').val();
    company['editPerson'] = $('#editPerson').val();
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: "/testdevice/" + url,
        dataType: 'JSON',
        data: JSON.stringify(company),
        cache: false,
        timeout: 600000,
        success: function (data) {
            let successModal = $('#successModal');
            let errorModal = $('#errorModal');
            if (data.status == '200') {
                successModal.modal('show');
                setTimeout(function () {
                    window.location.href = window.location.origin + "/testdevice/list";
                }, 900);
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

function XoaLoi() {
    resetError('codeDevice');
    resetError('provideUnit');
    resetError('staffCode');
    resetError('staffName');
    resetError('staffDepartment');
    resetError('description');
    resetError('warrantyTime');
    resetError('buyCost');
    resetError('buyDate');
    resetError('addressWarranty');
    resetError('handTime');
    resetError('returnDate');
    resetError('boughtDate');
    resetError('boughtCost');
}


function setValueById(id, value) {
    $('#' + id).val(value);
    $('#' + id + 'Value').val(value);
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

function getDetail(id) {
    $.ajax({
        type: 'GET',
        contentType: 'application/json',
        url: "/companydevice/detail/" + id,
        dataType: 'JSON',
        cache: false,
        timeout: 600000,
        success: function (data) {
            let view = $('#view');
            $('#codeDeviceModal').text(data['codeDevice']);
            // $('#nameDeviceModal').text(data['nameDevice']);
            $('#typeModal').text(data['typeDevice']);
            $('#detailDeviceModal').text(data['description']);
            $('#staffCodeModal').text(data['staffCode']);
            let type = data['typeDevice'];
            $('#detailTypeModal').text(data['detailType']);
            $('#emailDeviceModal').text(data['staffEmail']);
            $('#departmentModal').text(data['staffDepartment']);
            $('#nameStaffModal').text(data['staffName']);
            let status = data['status'];
            $('#StatusModal').text(status);
            $('#addressWarrantyModal').text(data['addressWarranty']);
            $('#warrantyTimeModal').text(convertDate(data['warrantyTime']));
            $('#boughtDateModal').text(convertDate(data['boughtDate']));
            $('#boughtCostModal').text(data['boughtCost']);
            $('#createPersonModal').text(data['createPerson']);
            $('#editPersonModal').text(data['editPerson']);
            view.children().remove();
            if (status === 'Đang sử dụng') {
                view.append('<tr ><td class="modal_body_title">Ngày bàn giao:</td><td class="modal_body_content">' + convertDate(data['handTime']) + '</td></tr>');
                view.append('<tr><td class="modal_body_title" >Ngày trả:</td><td class="modal_body_content">' + convertDate(data['returnDate']) + '</td></tr>');
            } else if (status === 'Đang bảo hành') {

            } else if (status === 'Thanh lý') {
                view.append('<tr><td class="modal_body_title" >Ngày thanh lý:</td><td class="modal_body_content">' + convertDate(data['buyDate']) + '</td></tr>');
                view.append('<tr><td class="modal_body_title" >Giá thanh lý:</td><td class="modal_body_content">' + data['buyCost'] + '</td></tr>');

            }
            $('#detailModal').modal('show');
        },
        error: function () {

        }
    });

}