(function() {
    var app = angular.module('videoGameStatistics', []);
    var port = ':8080';
    var host = "http://"+window.location.hostname + port;
    var requestList = Array();

    app.controller('OverviewController', function($scope, $http) {
        var that = this;
        loaded = false;
        this.videoGames = [];

        $http.get(host+'/loadoverview').success(function(data){
            that.videoGames = data;
        });

        this.imageLink = function (pImageId) {
            return (host+'/image/' + pImageId);
        };
    });


    app.controller('ItemController', function($scope, $http) {
        show = false;
        showScanResult = false;
        dark = false;
        showLoading = false;
        loadItems = [];
        this.item = {};

        this.addToRequestList = function(request){
            index = requestList.indexOf(request);
            if (index > -1){
                requestList.splice(index, 1)
            } else {
                requestList.push(request);
            }
            console.log("Request List: " + requestList);
        };

        this.scanItem = function(videoGame){
            loadItems.push(videoGame.id);
            $http.post(host+'/scanitem', videoGame).success(function(data, status, headers, config) {
                index = loadItems.indexOf(videoGame.id);
                if (index > -1) loadItems.splice(index, 1);
            });
        };

        this.showItem = function(id){
            that = this;
            $http.get(host+'/showitem?itemid=' + id).success(function(data){
                that.entity = data;
            });
            show = true;
            dark = true;
        };

        this.addItem = function() {
            var request = document.getElementById('addGame').value;
            console.log(request);
            that = this;
            $http.get(host+'/additem?request=' + request).success(function(data){
                that.entity = data;
            });
        };

        this.searchItem = function(){
            var that = this;
            this.searchItems = [];
            loadItems = [];
            showLoading = true;
            dark = true;

            $http.post(host+'/searchitem', this.item.request).success(function(data, status, headers, config) {
                that.searchItems = data;
                showScanResult = true;
                showLoading = false;
              });
              this.item = {};
        };

        this.hideItem = function(){
            show = false;
            showScanResult = false;
            dark = false;
        };

        this.isShown = function(){
            return show;
        };

        this.showScanResult = function(){
            return showScanResult;
        };

        this.isDark = function() {
            return dark;
        };

        this.showScan = function(pId){
            if(loadItems.indexOf(pId) > -1) return false;
            return true;
        };

        this.showLoad = function(pId){
            return !this.showScan(pId);
        };

        this.isLoading = function(){
            return showLoading;
        };
    });

})();
