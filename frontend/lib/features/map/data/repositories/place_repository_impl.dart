import 'package:dartz/dartz.dart';
import 'package:inclusivecity_frontend/core/error/exception/exceptions.dart';

import 'package:inclusivecity_frontend/core/error/failure/failure.dart';
import 'package:inclusivecity_frontend/features/map/data/datasources/place_remote_data_source.dart';
import 'package:inclusivecity_frontend/features/map/domain/entities/place_suggestion.dart';
import 'package:inclusivecity_frontend/features/map/domain/repositories/place_repository.dart';

class PlaceRepositoryImpl implements PlaceRepository {
  
  final PlaceRemoteDataSource remoteDataSource;

  PlaceRepositoryImpl({required this.remoteDataSource});

  @override
  Future<Either<Failure, List<PlaceSuggestion>>> searchPlaces(String query) async {
    try{
      final remotePlaces = await remoteDataSource.getPlaceSuggestions(query);

      return Right(remotePlaces);
    }on ServerException catch (e){
      return Left(ServerFailure(e.message));
    }
  }


}