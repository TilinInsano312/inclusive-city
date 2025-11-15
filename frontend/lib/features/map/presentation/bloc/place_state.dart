part of 'place_bloc.dart';

abstract class PlacesState extends Equatable {
  const PlacesState();

  @override
  List<Object> get props => [];
}

class PlacesInitial extends PlacesState {}

class PlacesLoading extends PlacesState {}

class PlacesLoaded extends PlacesState {
  final List<PlaceSuggestion> suggestions;

  const PlacesLoaded(this.suggestions);

  @override
  List<Object> get props => [suggestions];
}

class PlacesEmpty extends PlacesState {}

class PlacesError extends PlacesState {
  final String message;

  const PlacesError(this.message);

  @override
  List<Object> get props => [message];
}