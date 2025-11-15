import 'package:json_annotation/json_annotation.dart';
import 'package:inclusivecity_frontend/features/map/domain/entities/place_suggestion.dart';

part 'place_model.g.dart';

@JsonSerializable()
class PlaceModel extends PlaceSuggestion {
  const PlaceModel({
    required String placeId,
    required String description,
  }) : super(placeId: placeId, description: description);

  factory PlaceModel.fromJson(Map<String, dynamic> json) {
    return PlaceModel(
      placeId: json['place_id'] as String,
      description: json['description'] as String,
    );
  }

  factory PlaceModel.fromBackendJson(Map<String, dynamic> json) {
    return PlaceModel(
      placeId: json['placeId'] as String,
      description: json['address'] as String,
    );
  }

  factory PlaceModel.fromRootJson(Map<String, dynamic> json) =>
      _$PlaceModelFromJson(json);

  Map<String, dynamic> toJson() => _$PlaceModelToJson(this);
}