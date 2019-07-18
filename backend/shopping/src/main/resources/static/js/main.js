const host = 'http://localhost:8080';
let isLogin = false;

async function checkLogin(referenceUrl = null) {
    try {
        const token = await $.ajax({
            url: host + '/api/token?accessToken=' + window.localStorage.getItem('accessToken'),
            type: 'GET',
            contentType: 'application/json'
        });

        isLogin = true;
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
    } catch (error) {
        $('#nav-signin').attr("hidden", false);
        $('#nav-signup').attr("hidden", false);

        return false;
    }
}

$('#nav-logout').click(() => {
    // delete token or set status token
    window.localStorage.clear();
    window.location.href = host;
});

