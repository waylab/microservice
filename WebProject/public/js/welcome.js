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

    template.RenderOne({
        target: "#content",
        tagName: "div",
        id: "chart-content",
        position: "new",
        template: ''
    });

    var chart = jui.include("chart.builder");
    var names = {
        ie: "IE",
        ff: "Fire Fox",
        chrome: "Chrome",
        safari: "Safari",
        other: "Others"
    };

    chart("#chart-content", {
        padding : 150,
        axis : {
            data : [
                { ie : 70, ff : 11, chrome : 9, safari : 6, other : 4 }
            ]
        },
        brush : {
            type : "pie",
            showText : true,
            format : function(k, v) {
                return names[k] + ": " + v;
            }
        },
        widget : [{
            type : "title",
            text : "Pie Sample"
        }, {
            type : "tooltip",
            orient : "left",
            format : function(data, k) {
                return {
                    key: names[k],
                    value: data[k]
                }
            }
        }, {
            type : "legend",
            format : function(k) {
                return names[k];
            }
        }]
    });

    template.RenderOne({
        target: "#chart-content",
        tagName: "div",
        position: "append",
        template: comm.getHtml("../templates/main-panel.html"),
        data: {
            title: "Result : This is title text.",
            greetings: "hello, this is main tab page!! (greeting message) take a look at this chart."
        }
    });

};

page.settingPage = function () {

    comm.initPage();

    template.RenderOne({
        target: "#content",
        tagName: "ul",
        className: "no-bullet-ul",
        position: "append",
        template: comm.getHtml("../templates/setting-list.html"),
        data: [
            {
                panelhead: "h1",
                panelbody: "b1 b1 b1 b1 b1 b1 b1 b1 b1 b1",
                panelfooter: "f1"
            },
            {
                panelhead: "h2",
                panelbody: "b2 b2 b2 b2 b2 b2 b2 b2 b2 b2 b2 b2 b2",
                panelfooter: "f2"
            },
            {
                panelhead: "h3",
                panelbody: "b3 b3 b3 b3 b3 b3 b3 b3 b3",
                panelfooter: "f3"
            }
        ]
    });

};

page.userPage = function (userid) {

    comm.initPage();
    alert("user page, user number is : " + userid);
};
