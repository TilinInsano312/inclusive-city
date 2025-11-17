import 'package:equatable/equatable.dart';

class PlaceSuggestion extends Equatable {
  final String placeId;
  final String description;
  final String? address; // Dirección del lugar
  
  const PlaceSuggestion({
    required this.placeId,
    required this.description,
    this.address,
  });

  @override
  List<Object?> get props => [placeId, description, address];
}