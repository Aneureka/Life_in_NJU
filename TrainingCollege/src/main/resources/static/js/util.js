let checkUserLogin = () => {
    checkLogin('user');
};

let checkInstituteLogin = () => {
    checkLogin('institute');
};

let checkManagerLogin = () => {
    checkLogin('manager');
};

let checkLogin = (role) => {
    $.ajax({
        type: 'GET',
        url: '/api/' + role + 's/checkLogin',
        data: {},
        success: (response) => {
            if (!response.success) {
                window.location.href = 'login.html';
                alert('未在登录状态，请登录！');
            }
        },
        error: (XMLHttpRequest) => {
            alert(XMLHttpRequest.status + ' ' + XMLHttpRequest.responseText);
        }
    })
};

let setWelcomeUserName = () => {
    $('#name').text(window.localStorage.getItem('name'));
}