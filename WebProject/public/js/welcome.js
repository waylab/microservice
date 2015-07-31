"use strict";

page.welcome = function () {

    comm.initPage();

    var datas = {

        tableheaders : [
            'id', 'name', 'age', 'address'
        ],
        tabledatas: [
            {
                id: 'number.one',
                name: 'ONE',
                age: '11',
                address: 'Sangsu-dong, Mapo-gu'
            },{
                id: 'number.two',
                name: 'TWO',
                age: '22',
                address: 'Yangjae-dong, Seocho-gu'
            },{
                id: 'number.three',
                name: 'THREE',
                age: '33',
                address: 'Sadang-dong, Dongjak-gu'
            },{
                id: 'number.four',
                name: 'FOUR',
                age: '44',
                address: 'Myeong-dong, Jung-gu'
            }
        ]
    }; // RESTful api callback data example


    template.RenderOne({
        target: "#content",
        tagName: "div",
        className: "welcome",
        id: "welcome-content",
        position: "new",
        template: comm.getHtml("../templates/welcome.html"),
        data: datas,
        events: {
            "click .welcome": function () {
                alert("welcome!");
            }
        }
    });
};

page.mainPage = function () {

    comm.initPage();
    alert("welcome to main page");
};

page.settingPage = function () {

    comm.initPage();
    alert("settingPage");
};

page.userPage = function (userid) {

    comm.initPage();
    alert("user page, user number is : " + userid);
};
