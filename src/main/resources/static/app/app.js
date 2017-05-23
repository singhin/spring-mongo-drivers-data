// Creating angular Application with module name "DriversApp"
var app = angular.module('DriversApp', []);

// If we implement the basic security in spring boot then the response will
// contains the header 'WWW-Authenticate: Basic'. So the browser will popup a
// alert to get the user credentials. To avoid that we have to set a header in
// every request we are making using AngularJs.
app.config(['$httpProvider',
				function($httpProvider) {$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
				}]);

// Creating the Angular Controller
app.controller('AppCtrl', function($http, $scope) {
					// method for login
					$scope.login = function() {
						// creating base64 encoded String from username and
						// password
						var base64Credential = btoa($scope.username + ':'
								+ $scope.password);

						// calling GET request for getting the user details
						$http.get('user', {headers : {
												// setting the Authorization
												// Header
												'Authorization' : 'Basic '
														+ base64Credential
											}
										})
								.success(
										function(res) {
											$scope.password = null;
											if (res.authenticated) {
												$scope.message = '';
												// setting the same header value
												// for all request calling from
												// this app
												$http.defaults.headers.common['Authorization'] = 'Basic '
														+ base64Credential;
												$scope.user = res;
												$scope.role = $scope.user.authorities[0].authority;
												
											} else {
												$scope.message = 'Authetication Failed !';
											}
										}).error(function(error) {
									$scope.message = 'Authetication Failed !';
								});

					};
					
					
					// method for getting a User Resource
					$scope.getUserResouce = function(userId) {
						
						$scope.error = null;
						$scope.journeyDate = "Journey Date -";
						$scope.journeyData = null;
						$scope.user_id = "User ID - " + userId;
						$http.get('http://localhost:8080/drivers/user/'+ userId)
								.success(
										function(response) {
											$scope.resource = response;

											$scope.journeys = [];
											$scope.journeyDetails = [];

											angular.forEach(response.journeys,function(journey,index) {
																$scope.journeys.push(journey);
													angular.forEach(journey.journeyDetails,function(journeyDetail,index) {
																					$scope.journeyDetails
																							.push(journeyDetail);
													});
											});

											$scope.convertTo = function(arr,
													key, dayWise) {
												var groups = {};
												for (var i = 0; l = arr.length,
														i < l; i++) {
													if (dayWise) {
														arr[i][key] = (new Date(
																arr[i][key]))
																.toLocaleDateString();
													} else {
														arr[i][key] = arr[i][key]
																.toTimeString();
													}
													groups[arr[i][key]] = groups[arr[i][key]]
															|| [];
													groups[arr[i][key]]
															.push(arr[i]);
												}
												return groups;
											};

											$scope.testData = {};
											angular.copy($scope.data, $scope.testData);

											$scope.testData.messages = $scope.convertTo($scope.journeyDetails,'time', true);
										}).error(function(error) {
									$scope.error = error;
								});

						$scope.setContent = function(value) {
								$scope.journeyData = value;
							}
					};
					// method for logout
					$scope.logout = function() {
						// clearing the authorization header
						$http.defaults.headers.common['Authorization'] = null;
						// clearing all data
						$scope.user = null;
						$scope.message = 'Successfully logged out';
						$scope.resource = null;
						$scope.testData = null;
						$$scope.journeyData = null;
					};

				});