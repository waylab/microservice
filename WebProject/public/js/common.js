"use strict";

(function() {

    var root = this;
    var comm = root.comm = {};

}).call(this);


comm.getHtml = function (path) {

    var getFile = $.ajax({
        url: path,
        async: false,
        success: function (data) {
            return data;
        }
    });

    return getFile.responseText;
};

comm.initPage = function () {

    $("#content").html('');

    $("#header-nav ul li").removeClass('active');

    if (location.hash == '#main') {
        $("#header-nav ul li").eq(1).addClass('active');
    } else if (location.hash == '#settings') {
        $("#header-nav ul li").eq(2).addClass('active');
    } else {
        $("#header-nav ul li").eq(0).addClass('active');
    }

};