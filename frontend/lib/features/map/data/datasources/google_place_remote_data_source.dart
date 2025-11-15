import 'dart:convert';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:http/http.dart' as http;
import 'package:inclusivecity_frontend/core/error/exception/exceptions.dart';
import 'package:inclusivecity_frontend/features/map/data/datasources/place_remote_data_source.dart';
import 'package:inclusivecity_frontend/features/map/data/models/place_model.dart';

class GooglePlacesDataSourceImpl implements PlaceRemoteDataSource {
  final http.Client client;
  final String _apiKey = dotenv.env['GOOGLE_MAPS_API_KEY']!;
  static const String _baseUrl =
      "https://maps.googleapis.com/maps/api/place/autocomplete/json";

  GooglePlacesDataSourceImpl({required this.client});

  @override
  Future<List<PlaceModel>> getPlaceSuggestions(String query) async {
    final response = await client.get(
      Uri.parse('$_baseUrl?input=$query&key=$_apiKey&types=establishment'),
      headers: {'Content-Type': 'application/json'},
    );

    if (response.statusCode == 200) {
      final data = json.decode(response.body);
      if (data['status'] == 'OK') {
        final predictions = data['predictions'] as List;
        return predictions
            .map((json) => PlaceModel.fromJson(json))
            .toList();
      } else if (data['status'] == 'ZERO_RESULTS') {
        return [];
      } else {
        throw ServerException(data['error_message'] ?? 'Error de Google Places API');
      }
    } else {
      throw ServerException('Error de servidor: ${response.statusCode}');
    }
  }
}