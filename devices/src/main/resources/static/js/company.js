$(document).ready(function () {
    $('#typeDevice').on('change', function (e) {
        $(this).removeClass('input_error');
        changeTypeDevice('', '', '');

    });

    $('#status').on('change', function (e) {
        $('#status_errors').text('');
        $('#status').removeClass('input_error');
        changeStatus('');
    });

    $('#create').click(function () {
        submitCompany("create");
    });

    $("#update").click(function () {
        submitCompany("update");
    });
    $('#codeDevice').keydown(function () {
        $('#codeDevice_errors').text('');
        $('#codeDevice').removeClass('input_error');
        $('#staffCode').removeClass('markValue');
        $('#staffName').removeClass('markValue');
        $('#staffDepartment').removeClass('markValue');
    });
    $('#codeDevice').focusin(function () {
        let codeDeviceOld = $(this).val();
        $('#staffCode').removeClass('markValue');
        $('#staffName').removeClass('markValue');
        $('#staffDepartment').removeClass('markValue');
    });

    XoaLoi();
});
$(document).keypress(function (e) {
    let keycode = (e.keyCode ? e.keyCode : e.which);
    if (keycode == '13') {
        if (window.location.href === "http://dms.ominext.co/companydevice/create") {
            submitCompany("create");
        } else {
            submitCompany("update");
        }
    }
});

function myFunction(val) {
    $('#detailType').removeClass('input_error');
    $('#detailType_errors').text('');
    let selectedDetailType = val.value;
    $('#detailTypeSelected').val(selectedDetailType);
}

//ChangeTypeDevice
function changeTypeDevice(statusValue, typeValue, detailTypeValue) {
    let returnDateLabel = $('#returnDateLabel');
    let view = $('#view');
    $('#typeDevice_errors').text('');
    $('#returnDate').parent().remove();
    returnDateLabel.parent().remove();
    let type = typeValue == '' ? $('#typeDevice option:selected').val() : typeValue;
    if (type == '') {
        $('#detailTypeContent').children().remove();
        $('#detailTypeLabel ').children().remove();
        returnDateLabel.parent().remove();
        $('#returnDate').parent().remove();
    } else {
        getValueMaster(type);
        $('#detailTypeValue').val(detailTypeValue);
        $('#detailType').val(detailTypeValue);
        $('#typeDevice').val(type);
        $('#typeDeviceValue').val(type);
    }


}

//Change Status
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
            setValueById('returnDate', '');
            setValueById('handTime', '');
            break;
        case'2':
            //Dang su dung
            view.children().remove();
            setAdmin(false);
            view.append('<div class="col-md-3 label_for_form"><label class="title_form" for="handTime">Ngày bàn giao</label><label class="compulsory">*</label></div>');
            view.append('<div class="col-md-7"><input type="date" id="handTime" name="handTime" class="create_and_edit_form" onchange="getValueById(\'handTime\')" autofocus="autofocus" placeholder="Ngày bàn giao" style="margin-bottom: 15px;"><label id="handTime_errors" class="error"></label></div>');

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
            setValueById('buyCost', '');
            setValueById('buyDate', '');
            setValueById('returnDate', '');
            setValueById('handTime', '');
            break;
    }

}


//Get du lieu cua device
function getDevice(code) {
    $.ajax({
        type: 'GET',
        contentType: 'application/json',
        url: "/device/get/" + code,
        dataType: 'JSON',
        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data['status'] == 2) {
                let codeOld = $('#codeDeviceOld').val();
                if (codeOld !== code) {
                    $('#codeDevice_errors').text("Thiết bị này đang được sử dụng");
                }

            } else {
                setDevice(data);
            }
        },
        error: function () {
            setValueById('status', '');
            setValueById('provideUnit', '');
            setValueById('description', '');
            setValueById('warrantyTime', '');
            setValueById('detailType', '');
            setAdmin(false);
            $('#view').children().remove();
            $('#detailTypeLabel').children().remove();
            $('#detailTypeSelect').children().remove();
            $('#detailTypeNone').children().remove();
            $('#detailTypeContent').children().remove();
            setValueById('typeDevice', '');
        }
    });

}

function setDevice(data) {
    $('#typeDevice').val(data['type']);
    hideError('typeDevice');
    $('#status').val(data['status']);
    hideError('status');
    $('#description').val(data['description']);
    hideError('description');
    $('#provideUnit').val(data['provideUnit']);
    hideError('provideUnit');
    $('#warrantyTime').val(data['warrantyTime']);
    hideError('warrantyTime')
    let buyCost = data['buyCost'];
    let buyDate = data['buyDate'];
    let addressWarranty = data['addressWarranty'];
    let returnDate = data['returnDate'];
    let handTime = data['handTime'];
    changeStatus(data['status']);
    changeTypeDevice(data['status'], data['type'], data['detailType']);
    setValueById('buyCost', buyCost);
    setValueById('buyDate', buyDate);
    setValueById('addressWarranty', addressWarranty);
    setValueById('returnDate', returnDate);
    setValueById('handTime', handTime);
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

function setValueById(id, value) {
    $('#' + id).val(value);
    $('#' + id + 'Value').val(value);
}

function submitCompany(url) {
    $('label.error').text("");
    $("input").removeClass("input_error");
    let company = {};
    company['id'] = $('#id').val();
    company['codeDevice'] = convertText('codeDevice');
    // company['nameDevice'] = $('#nameDevice').val();
    company['typeDevice'] = $('#typeDevice').val();
    company['detailType'] = $('#detailTypeValue').val();
    company['description'] = convertText('description');
    company['provideUnit'] = convertText('provideUnit');
    company['buyCost'] = $('#buyCostValue').val();
    company['buyDate'] = $('#buyDateValue').val();
    company['addressWarranty'] = $('#addressWarranty').val();
    company['status'] = $('#status').val();
    company['returnDate'] = $('#returnDateValue').val();
    company['warrantyTime'] = $('#warrantyTime').val();
    // company['reasonWarranty'] = $('#reasonWarrantyValue').val();
    company['handTime'] = $('#handTimeValue').val();
    company['staffName'] = convertText('staffName');
    company['staffDepartment'] = $('#staffDepartmentValue').val();
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
        url: "/companydevice/" + url,
        dataType: 'JSON',
        data: JSON.stringify(company),
        cache: false,
        timeout: 600000,
        success: function (data) {
            let successModal = $('#successModal');
            let errorModal = $('#errorModal');
            // $("#" + url).attr("disabled", false);
            if (data.status == '200') {
                successModal.modal('show');
                setTimeout(function () {
                    window.location.href = window.location.origin + "/companydevice/list";
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

function convertDate(dateString) {
    if (dateString) {
        var list = dateString.split('-');
        return list[2] + '/' + list[1] + '/' + list[0];
    } else {
        return '';
    }

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
            let type = data['typeDevice'];
            $('#detailTypeModal').text(data['detailType']);
            $('#emailDeviceModal').text(data['staffEmail']);
            $('#departmentModal').text(data['staffDepartment']);
            $('#nameStaffModal').text(data['staffName']);
            $('#warrantyTimeModal').text(convertDate(data['warrantyTime']));
            $('#addressWarrantyModal').text(data['addressWarranty']);
            $('#boughtDateModal').text(convertDate(data['boughtDate']));
            $('#boughtCostModal').text(data['boughtCost']);
            $('#createPersonModal').text(data['createPerson']);
            $('#editPersonModal').text(data['editPerson']);
            let status = data['status'];
            $('#StatusModal').text(status);
            view.children().remove();
            if (status === 'Đang sử dụng') {
                view.append('<tr ><td class="modal_body_title">Ngày bàn giao:</td><td class="modal_body_content">' + convertDate(data['handTime']) + '</td></tr>');
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

function resetError(id) {
    let error = $('#' + id);
    error.keydown(function () {
        $('#' + id).removeClass('input_error');
        $('#' + id + '_errors').text('');
    });
    error.change(function () {
        hideError(id);
    });
}

function XoaLoi() {
    // resetError('nameDevice');
    resetError('provideUnit');
    resetError('staffName');
    resetError('staffDepartment');
    resetError('description');
    resetError('warrantyTime');
    resetError('buyCost');
    resetError('buyDate');
    resetError('addressWarranty');
    // resetError('reasonWarranty');
    resetError('handTime');
    resetError('returnDate');
    resetError('boughtDate');
    resetError('boughtCost');
}

function hideError(id) {
    $('#' + id).removeClass('input_error');
    $('#' + id + '_errors').text('');
}
