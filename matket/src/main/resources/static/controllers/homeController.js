app.controller('homeController',['$scope', '$location', '$mdDialog', 'companyService','authenticationService', function($scope, $location, $mdDialog, companyService,authenticationService){
	$scope.user = authenticationService.getUser();
	$scope.authService = authenticationService;
	 
	$scope.goToPregledPreduzeca = function() {
		$location.path("/pregledPreduzeca");
	}
	
	$scope.goToFaktura = function() {
		$location.path("/faktura");
	}
	
	$scope.goToSveFakture = function() {
		$location.path("/listaSvihFaktura");
	}
	
	$scope.goToSveNarudzbenice = function() {
		$location.path("/listaSvihNarudzbenica");
	}
	
	$scope.goToNarudzbenica = function() {
		$location.path("/narudzbenica");
	}
	
	$scope.goToPrimka = function() {
		$location.path("/primka");
	}
	
	$scope.dodajCenovnik = function() {
		$location.path("/cenovnik");
	}
	
	$scope.goToPreduzece = function() {
		$location.path("/preduzece");
	}
	
	$scope.dodajMagacin = function() {
		$location.path("/magacin");
	}
	$scope.pregledCenovnika = function() {
		$location.path("/pregledCenovnika");
	}
	
	$scope.pregledMagacina = function() {
		$location.path("/pregledMagacina");
	}
	
	$scope.goToStopa = function() {
		$location.path("/pregledStopa");
	}
	$scope.goToVrsta = function() {
		$location.path("/pregledVrstiPDVa");
	}
	$scope.pregledajGrupe = function() {
		$location.path("/grupeArtikala");
	}
	$scope.goToJediniceMere = function() {
		$location.path("/jediniceMere");
	}
	$scope.poslovnaGodina = function() {
		$location.path("/poslovnaGodina");
	}
	
	$scope.logOut = function(){
	        $scope.user.username = "";
	        $scope.user.password = "";
	           $location.path('/');
	        
	   
	}
	 
	 
}]);