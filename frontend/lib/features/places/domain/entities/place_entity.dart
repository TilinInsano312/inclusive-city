import 'package:equatable/equatable.dart';

class PlaceEntity extends Equatable {
  final String placeId;
  final String name;      // Renombrado de 'description' para ser más semántico
  final String? address;
  final double latitude;  // No puede ser nulo si va a ir en el mapa
  final double longitude; // No puede ser nulo si va a ir en el mapa

  const PlaceEntity({
    required this.placeId,
    required this.name,
    this.address,
    required this.latitude,
    required this.longitude,
  });

  @override
  List<Object?> get props => [placeId, name, address, latitude, longitude];
}