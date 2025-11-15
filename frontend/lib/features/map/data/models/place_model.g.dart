// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'place_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

PlaceModel _$PlaceModelFromJson(Map<String, dynamic> json) => PlaceModel(
  description: json['description'] as String,
  placeId: json['place_id'] as String,
);

Map<String, dynamic> _$PlaceModelToJson(PlaceModel instance) =>
    <String, dynamic>{
      'place_id': instance.placeId,
      'description': instance.description,
    };
