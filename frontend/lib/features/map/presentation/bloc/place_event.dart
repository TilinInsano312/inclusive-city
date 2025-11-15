part of 'place_bloc.dart';

abstract class PlacesEvent extends Equatable {
  const PlacesEvent();

  @override
  List<Object> get props => [];
}

class SearchPlacesEvent extends PlacesEvent {
  final String query;

  const SearchPlacesEvent(this.query);

  @override
  List<Object> get props => [query];
}

class ClearSearchEvent extends PlacesEvent {}