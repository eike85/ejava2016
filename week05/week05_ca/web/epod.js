(function() {

	var url = "ws://localhost:8080/epod/event"

	var EpodApp = angular.module("EpodApp", []);

	var EpodSvc = function($rootScope) {
		this.socket = new WebSocket(url);
		this.subscribe = function(cb) {
			this.socket.onmessage = function(evt) {
				$rootScope.$apply(function() {
					cb(JSON.parse(evt.data));
				});
			}
		}
	}

	var EpodCtrl = function(EpodSvc) {
		var epodCtrl = this;
		epodCtrl.epods = [];

		EpodSvc.subscribe(function(data) {
			epodCtrl.epods.unshift(data)
		});
	}

	EpodApp.service("EpodSvc", ["$rootScope", EpodSvc]);
	EpodApp.controller("EpodCtrl", ["EpodSvc", EpodCtrl]);

})();

