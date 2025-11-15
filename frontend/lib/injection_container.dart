import 'package:get_it/get_it.dart';
import 'package:http/http.dart' as http;
import 'package:inclusivecity_frontend/features/map/data/datasources/place_remote_data_source.dart';
import 'package:inclusivecity_frontend/features/map/data/datasources/place_remote_data_source_impl.dart';
import 'package:inclusivecity_frontend/features/map/data/repositories/place_repository_impl.dart';
import 'package:inclusivecity_frontend/features/map/domain/repositories/place_repository.dart';
import 'package:inclusivecity_frontend/features/map/domain/usecases/get_place_detail.dart';
import 'package:inclusivecity_frontend/features/map/domain/usecases/search_places.dart';
import 'package:inclusivecity_frontend/features/map/presentation/bloc/place_bloc.dart';

// Service Locator
final sl = GetIt.instance;

Future<void> init() async {
  // --- Features: Places ---

  // BLoC
  // Se registra como 'factory' porque queremos una nueva instancia cada vez
  // que se solicita, especialmente en la UI.
  sl.registerFactory(
    () => PlacesBloc(
      searchPlacesUseCase: sl(),
      getPlaceDetailsUseCase: sl(), 
    ),
  );

  // Use Cases
  // Se registra como 'lazySingleton' porque solo necesitamos una instancia
  // y solo se crea cuando se usa por primera vez.
  sl.registerLazySingleton(() => SearchPlaces(sl()));
  sl.registerLazySingleton(() => GetPlaceDetails(sl()));

  // Repository
  sl.registerLazySingleton<PlaceRepository>(
    () => PlaceRepositoryImpl(
      remoteDataSource: sl(),
      // networkInfo: sl(), // (Opcional)
    ),
  );

  // Data Sources
  // Backend como fuente principal de datos
  sl.registerLazySingleton<PlaceRemoteDataSource>(
    () => BackendPlacesDataSourceImpl(client: sl()),
  );

  // --- Core ---
  // (Opcional) Aquí registrarías tu 'NetworkInfo'
  // sl.registerLazySingleton<NetworkInfo>(() => NetworkInfoImpl(sl()));

  // --- Externas ---
  // Dependencias externas como http client o shared_preferences
  sl.registerLazySingleton(() => http.Client());
  // sl.registerLazySingleton(() => InternetConnectionChecker());
}