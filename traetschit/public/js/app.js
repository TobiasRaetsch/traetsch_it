(function() {
    var app = angular.module('videoGameStatistics', []);
    var port = ':8080';
    var host = "http://"+window.location.hostname + port;

    app.controller('OverviewController', function($scope, $http) {
        var that = this;
        this.loaded = false;
        this.videoGames = [];
        this.show = false;
        this.isScanShow = false;
        this.dark = false;
        this.showLoading = false;
        this.loadItems = [];
        this.overview = {};
        this.searchItems = [];
        this.searchItemList = Array();
        this.overviewList = Array();

        this.searchItem = function(){
            var that = this;
            this.loadItems = [];
            this.showLoading = true;
            this.dark = true;

            $http.post(host+'/searchitem', this.overview.request).success(function(data, status, headers, config) {
                that.searchItems = data;
                that.isScanShow = true;
                that.showLoading = false;
              });
              this.overview = {};
        };



        $http.get(host+'/loadoverview').success(function(data){
            that.videoGames = data;
        });

        this.checkAll = function(pType){
            if(pType == "VG") this.overviewList = this.videoGames;
            if(pType == "RQ")this.searchItemList = this.searchItems;
        };

        this.uncheckAll = function(pType){
                if(pType == "VG") that.overviewList = Array();
                if(pType == "RQ") that.searchItemList = Array();
            };

        this.isVideoGameChecked = function(videoGame){
            index = that.overviewList.indexOf(videoGame);
            if (index > -1) return "checked";
            return "";
        };

        this.isGameScanChecked = function(pGameScan){
            index = that.searchItemList.indexOf(pGameScan);
            if (index > -1) return "checked";
            return "";
        };

        this.deleteVideoGame = function(pVideoGame){
            index = that.videoGames.indexOf(pVideoGame);
            if (index > -1) that.videoGames.splice(index, 1);
        };

        this.imageLink = function (pImageId) {
            return (host+'/image/' + pImageId);
        };

        this.addToSearchItemList = function(searchItem){
            index = that.searchItemList.indexOf(searchItem);
            if (index > -1){
                that.searchItemList.splice(index, 1)
            } else {
                that.searchItemList.push(searchItem);
            }
        };

        this.addToOverviewList = function(videoGame){
            index = that.overviewList.indexOf(videoGame);
            if (index > -1){
                that.overviewList.splice(index, 1)
            } else {
                that.overviewList.push(videoGame);
            }
        };

        this.scanItem = function(pVideoGame){
            this.videoGame = {};
            this.loadItems.push(pVideoGame.id);
            $http.post(host+'/scanitem', pVideoGame).success(function(data, status, headers, config) {
                index = that.loadItems.indexOf(pVideoGame.id);
                if (index > -1) that.loadItems.splice(index, 1);

                that.videoGames.push(data);
                this.videoGame = {};
            });
        };

        this.showItem = function(id){
            var that = this;
            $http.get(host+'/showitem?itemid=' + id).success(function(data){
                that.entity = data;
            });
            this.show = true;
            this.dark = true;
        };

        this.addItem = function() {
            var request = document.getElementById('addGame').value;
            var that = this;
            $http.get(host+'/additem?request=' + request).success(function(data){
                that.entity = data;
            });
        };

        this.hideItem = function(){
            this.show = false;
            this.isScanShow = false;
            this.dark = false;
        };

        this.isShown = function(){
            return this.show;
        };

        this.showScanResult = function(){
            return this.isScanShow;
        };

        this.isDark = function() {
            return this.dark;
        };

        this.showScan = function(pId){
            if(this.loadItems.indexOf(pId) > -1) return false;
            return true;
        };

        this.showLoad = function(pId){
            return !this.showScan(pId);
        };

        this.isLoading = function(){
            return this.showLoading;
        };
    });

})();
