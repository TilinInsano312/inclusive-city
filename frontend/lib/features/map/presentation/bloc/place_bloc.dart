import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:inclusivecity_frontend/features/map/domain/entities/place_details.dart';
import 'package:inclusivecity_frontend/features/map/domain/entities/place_suggestion.dart';
import 'package:inclusivecity_frontend/features/map/domain/usecases/search_places.dart';
import 'package:stream_transform/stream_transform.dart';

part 'place_event.dart';
part 'place_state.dart';

EventTransformer<E> debounceTransformer<E>(Duration duration) {
  return (events, mapper) {
    return events.debounce(duration).switchMap(mapper);
  };
}

class PlacesBloc extends Bloc<PlacesEvent, PlacesState> {
  final SearchPlaces searchPlacesUseCase;

  PlacesBloc({required this.searchPlacesUseCase, required Object getPlaceDetailsUseCase}) : super(PlacesInitial()) {

    on<SearchPlacesEvent>(
      _onSearchPlaces,

      transformer: debounceTransformer(const Duration(milliseconds: 500)),
    );

    on<ClearSearchEvent>(_onClearSearch);
  }

  Future<void> _onSearchPlaces(
      SearchPlacesEvent event, Emitter<PlacesState> emit) async {
    if (event.query.isEmpty) {
      emit(PlacesInitial());
      return;
    }

    emit(PlacesLoading()); 

    final failureOrSuggestions = await searchPlacesUseCase(event.query);

    failureOrSuggestions.fold(
      (failure) => emit(PlacesError(failure.message)), 
      (suggestions) { 
        if (suggestions.isEmpty) {
          emit(PlacesEmpty()); 
        } else {
          emit(PlacesLoaded(suggestions));
        }
      },
    );
  }

  Future<void> _onClearSearch(
      ClearSearchEvent event, Emitter<PlacesState> emit) async {
    emit(PlacesInitial());
  }
}