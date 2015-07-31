$(function(){

    var AppRouter = new (Backbone.Router.extend({
        routes: {
            "": "welcome",
            "main": "mainPage",
            "settings": "settingPage",
            "user/:userid": "userPage"
        },
        welcome: page.welcome,
        mainPage: page.mainPage,
        settingPage: page.settingPage,
        userPage: page.userPage

    }));

    Backbone.history.start();

});