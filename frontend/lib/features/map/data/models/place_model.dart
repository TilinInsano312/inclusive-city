import 'package:inclusivecity_frontend/features/map/domain/entities/place_suggestion.dart';

class PlaceModel extends PlaceSuggestion {
  const PlaceModel({
    required String placeId,
    required String description,
    String? address,
  }) : super(
    placeId: placeId, 
    description: description,
    address: address,
  );

  // Factory para backend (location-API)
  factory PlaceModel.fromBackendJson(Map<String, dynamic> json) {
    return PlaceModel(
      placeId: json['placeId'] as String,
      description: json['name'] as String,
      address: json['address'] as String?,
    );
  }

  // Factory para cache local (SharedPreferences)
  factory PlaceModel.fromJson(Map<String, dynamic> json) {
    return PlaceModel(
      placeId: json['placeId'] as String,
      description: json['description'] as String,
      address: json['address'] as String?,
    );
  }

  // Serialización para cache local
  Map<String, dynamic> toJson() {
    return {
      'placeId': placeId,
      'description': description,
      'address': address,
    };
  }
}