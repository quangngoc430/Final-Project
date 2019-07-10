const host = 'http://localhost:8080';

function checkLogin(referenceUrl = null) {
    $.ajax({
        url: host + '/api/token?accessToken=' + window.localStorage.getItem('accessToken'),
        type: 'GET',
        contentType: 'application/json',
        success: function (token) {
            let account = {
                'email': token.account.email,
                'fullname': token.account.fullname,
                'id': token.account.id
            };

            window.localStorage.setItem('account', JSON.stringify(account));

            $('#nav-cart').attr('hidden', false);
            $('#nav-user').attr('hidden', false);
            $('#nav-logout').attr('hidden', false);

            if (referenceUrl !== null) {
                window.location.href = host + referenceUrl;
            } else {
                return true;
            }
        },
        error: function (error) {
            $('#nav-signin').attr("hidden", false);
            $('#nav-signup').attr("hidden", false);

            return false;
        }
    });
}

$('#nav-logout').click(() => {
    // delete token or set status token
    window.localStorage.clear();
    window.location.href = host;
});