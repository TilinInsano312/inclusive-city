import '../models/place_model.dart';

abstract class PlaceRemoteDataSource {
  Future<List<PlaceModel>> getPlaceSuggestions(String query);
}