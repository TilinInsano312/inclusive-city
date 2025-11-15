class ApiConstants {
  // Base URLs
  static const String gatewayUrl = "http://localhost:8080";
  static const String baseApiPath = "/inclusive/api/v1";
  static const String baseUrl = "$gatewayUrl$baseApiPath";

  // === LOCATION API (puerto 8070) ===
  // Places endpoints
  static const String placesBase = "$baseUrl/locations/place";
  static const String placesSearch = "$placesBase/search";
  static String placeDetails(String placeId) => "$placesBase/$placeId";
  static String placePhoto(String photoReference) => "$placesBase/photo/$photoReference";

  // Spots endpoints
  static const String spotsBase = "$baseUrl/locations/spot";
  static const String createSpot = spotsBase;
  static String userSpots(String userId) => "$spotsBase/$userId";

  // Incidence endpoints
  static const String incidenceBase = "$baseUrl/incidence";
  static const String createIncidence = incidenceBase;
  static const String allIncidences = "$incidenceBase/all";

  // === ROUTE API (puerto 8060) ===
  static const String routeBase = "$baseUrl/routes";

  // === ACCOUNT API (puerto 8090) ===
  static const String profileBase = "$baseUrl/profile";
  static const String formsBase = "$baseUrl/form";

  // === AUTHENTICATION (puerto 9090) ===
  static const String login = "$baseUrl/login";
  static const String register = "$baseUrl/register";
  static const String resetPassword = "$baseUrl/reset";

  // Headers
  static const Map<String, String> jsonHeaders = {
    'Content-Type': 'application/json',
  };

  static Map<String, String> authHeaders(String token) => {
    'Content-Type': 'application/json',
    'Authorization': 'Bearer $token',
  };
}