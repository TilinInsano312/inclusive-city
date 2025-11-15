import 'package:dartz/dartz.dart';
import 'package:inclusivecity_frontend/core/error/exception/exceptions.dart';

import 'package:inclusivecity_frontend/core/error/failure/failure.dart';
import 'package:inclusivecity_frontend/features/map/data/datasources/place_remote_data_source.dart';
import 'package:inclusivecity_frontend/features/map/domain/entities/place_details.dart';
import 'package:inclusivecity_frontend/features/map/domain/entities/place_suggestion.dart';
import 'package:inclusivecity_frontend/features/map/domain/repositories/place_repository.dart';

class PlaceRepositoryImpl implements PlaceRepository {
  final PlaceRemoteDataSource remoteDataSource;

  PlaceRepositoryImpl({required this.remoteDataSource});

  @override
  Future<Either<Failure, List<PlaceSuggestion>>> searchPlaces(String query) async {
    try {
      final places = await remoteDataSource.getPlaceSuggestions(query);
      return Right(places);
    } on ServerException {
      return Left(ServerFailure("Error al obtener los lugares. Por favor, inténtalo de nuevo más tarde."));
    }
  }

  @override
  Future<Either<Failure, PlaceDetails>> getPlaceDetails(String placeId) async {
    try {
      final details = await remoteDataSource.getPlaceDetails(placeId);
      return Right(details);
    } on ServerException {
      return Left(ServerFailure("No se pudieron obtener los resultados de la búsqueda. Verifica tu conexión o inténtalo de nuevo."));
    }
  }

  @override
  Future<Either<Failure, List<PlaceDetails>>> getNearbyPlaces(double lat, double lng, {int radius = 5000}) async {
    try {
      final places = await remoteDataSource.getNearbyPlaces(lat, lng, radius);
      return Right(places);
    } on ServerException {
      return Left(ServerFailure("Error de comunicación con el servidor. Por favor, revisa tu conexión a internet."));
    }
  }
}