import 'package:inclusivecity_frontend/features/map/domain/entities/place_suggestion.dart';

class PlaceModel extends PlaceSuggestion {
  const PlaceModel({
    required String placeId,
    required String description,
  }) : super(placeId: placeId, description: description);

  // Factory para backend (location-API)
  factory PlaceModel.fromBackendJson(Map<String, dynamic> json) {
    return PlaceModel(
      placeId: json['placeId'] as String,
      description: json['name'] as String, // Backend usa 'name' en search
    );
  }
}