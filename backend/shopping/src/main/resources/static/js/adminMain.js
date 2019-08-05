// const host = 'https://9af009a5.ngrok.io';
    const host = 'http://localhost:8080';
let isLogin = false;

const blackList = ['/account',
                   '/item',
                   '/order'];

async function checkLogin(referenceUrl = null) {
    try {
        const token = await $.ajax({
            url: host + '/api/token?accessToken=' + window.localStorage.getItem('adminAccessToken'),
            type: 'GET',
            contentType: 'application/json'
        });

        isLogin = true;
        let account = {
            'email': token.account.email,
            'fullname': token.account.fullname,
            'id': token.account.id
        };

        window.localStorage.setItem('adminAccount', JSON.stringify(account));

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

        for (let url of blackList) {
            if (window.location.href.includes(url)) {
                window.location.href = host + '/admin/login';
                return;
            }
        }

        if (window.location.href.endsWith('/admin')) {
            window.location.href = host + '/admin/login';
            return;
        }

        return false;
    }
}

$('#nav-logout').click(() => {
    // delete token or set status token
    window.localStorage.clear();
    window.location.href = host;
});

