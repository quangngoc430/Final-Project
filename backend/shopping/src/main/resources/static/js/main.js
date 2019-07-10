const host = 'http://localhost:8080';

function checkLogin() {
    $.ajax({
        url: 'http://localhost:8080/api/token?accessToken=' + window.localStorage.getItem('accessToken'),
        type: 'GET',
        contentType: 'application/json',
        success: function (result) {
            console.log('done');
            $('#nav-cart').attr('hidden', false);
            $('#nav-user').attr('hidden', false);

            return true;
        },
        error: function (error) {
            $('#nav-signin').attr("hidden", false);
            $('#nav-signup').attr("hidden", false);

            return false;
        }
    });
}