'use strict';

let btnUpload = $('#btnUpload');
let singleFileUploadInput = $('#singleFileUploadInput');
let singleFileUploadError = $('#singleFileUploadError');
let singleFileUploadSuccess = $('#singleFileUploadSuccess');


function uploadSingleFile(file) {
    let formData = new FormData();
    formData.append("file", file);

    let xhr = new XMLHttpRequest();
    xhr.open("POST", "/uploadFile");

    xhr.onload = function() {
        console.log(xhr.responseText);
        let response = JSON.parse(xhr.responseText);
        if(xhr.status == 200) {
            singleFileUploadError.attr('hidden', true);
            $('#imageUploadUrl').val(response.fileDownloadUri);
            singleFileUploadSuccess.attr('hidden', false);
        } else {
            singleFileUploadSuccess.attr('hidden', true)
            singleFileUploadError.innerHTML = (response && response.message) || "Some Error Occurred";
            singleFileUploadError.attr('hidden', false);
        }
    }

    xhr.send(formData);
}

btnUpload.click((event) => {
    let files = singleFileUploadInput[0].files;
    if(files.length === 0) {
        singleFileUploadError.innerHTML = "Please select a file";
        singleFileUploadError.style.display = "block";
    }
    uploadSingleFile(files[0]);
    event.preventDefault();
});

$(".custom-file-input").on("change", function() {
    var fileName = $(this).val().split("\\").pop();
    $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
});

