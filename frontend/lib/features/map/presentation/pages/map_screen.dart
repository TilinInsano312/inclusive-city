import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:inclusivecity_frontend/constants/app_colors.dart';
import 'package:inclusivecity_frontend/features/map/presentation/bloc/place_bloc.dart';
import 'package:inclusivecity_frontend/features/map/presentation/widget/search_bottom_sheet.dart';
import 'package:inclusivecity_frontend/features/map/presentation/widget/place_details_bottom_sheet.dart';
import 'package:inclusivecity_frontend/features/map/domain/entities/place_details.dart';



class MapPage extends StatefulWidget {
  const MapPage({super.key});

  @override
  State<MapPage> createState() => _MapPageState();
}

class _MapPageState extends State<MapPage> {
  GoogleMapController? _mapController;
  final ValueNotifier<double> _sheetSizeNotifier = ValueNotifier<double>(0.15);
  final Set<Marker> _markers = {};
  PlaceDetails? _selectedPlaceDetails;
  bool _showDetailsSheet = false;

  @override
  void initState() {
    super.initState();
    // Disparar el evento para obtener la ubicación del usuario al iniciar
    context.read<PlacesBloc>().add(GetUserLocationEvent());
  }

  @override
  void dispose() {
    _mapController?.dispose();
    _sheetSizeNotifier.dispose();
    super.dispose();
  }

  // Posición inicial del mapa
  static const CameraPosition _initialPosition = CameraPosition(
    target: LatLng(0, 0), // Se actualizará a la ubicación del usuario
    zoom: 2.0,
  );

  @override
  Widget build(BuildContext context) {
    return BlocListener<PlacesBloc, PlacesState>(
      listener: (context, state) {
        if (state is UserLocationLoaded) {
          // Mover la cámara a la ubicación del usuario
          _mapController?.animateCamera(
            CameraUpdate.newLatLngZoom(
              LatLng(state.latitude, state.longitude),
              16.0,
            ),
          );
        } else if (state is PlaceDetailsLoaded) {
          // Mover la cámara al lugar seleccionado y agregar marcador
          final placeDetails = state.placeDetails;
          final latLng = LatLng(placeDetails.latitude, placeDetails.longitude);
          
          _mapController?.animateCamera(
            CameraUpdate.newLatLngZoom(latLng, 17.0),
          );
          
          // Agregar/actualizar marcador
          setState(() {
            _markers.clear();
            _markers.add(
              Marker(
                markerId: MarkerId(placeDetails.placeId),
                position: latLng,
                infoWindow: InfoWindow(
                  title: placeDetails.name,
                  snippet: placeDetails.address,
                ),
                icon: BitmapDescriptor.defaultMarkerWithHue(BitmapDescriptor.hueRed),
              ),
            );
            
            // Mostrar el bottom sheet de detalles
            _selectedPlaceDetails = placeDetails;
            _showDetailsSheet = true;
          });
        } else if (state is PlacesError) {
          // Mostrar mensaje de error
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(
              content: Text(state.message),
              backgroundColor: AppColors.error,
            ),
          );
        }
      },
      child: Scaffold(
        body: Stack(
          children: [
            //El Mapa de Google
            GoogleMap(
              initialCameraPosition: _initialPosition,
              onMapCreated: (GoogleMapController controller) {
                _mapController = controller;
              },
              markers: _markers,
              myLocationButtonEnabled: false,
              myLocationEnabled: true,
              zoomControlsEnabled: false,
            ),

            // Bottom sheet de búsqueda (solo cuando no hay detalles)
            if (!_showDetailsSheet)
              SearchBottomSheet(sheetSizeNotifier: _sheetSizeNotifier),
            
            // Bottom sheet de detalles del lugar
            if (_showDetailsSheet && _selectedPlaceDetails != null)
              PlaceDetailsBottomSheet(
                placeDetails: _selectedPlaceDetails!,
                onClose: () {
                  setState(() {
                    _showDetailsSheet = false;
                    _selectedPlaceDetails = null;
                    _markers.clear();
                  });
                  // Recargar el historial de búsqueda
                  context.read<PlacesBloc>().add(LoadSearchHistoryEvent());
                },
              ),

            ValueListenableBuilder<double>(
              valueListenable: _sheetSizeNotifier,
              builder: (context, sheetSize, child) {
                // Oculta el botón cuando el sheet es mayor al 30% o cuando se muestra el sheet de detalles
                final shouldShow = sheetSize < 0.3 && !_showDetailsSheet;
                return AnimatedPositioned(
                  duration: const Duration(milliseconds: 200),
                  top: shouldShow ? 50.0 : -100.0, // Mueve fuera de pantalla
                  left: 16.0,
                  child: AnimatedOpacity(
                    duration: const Duration(milliseconds: 200),
                    opacity: shouldShow ? 1.0 : 0.0,
                    child: FloatingActionButton(
                      heroTag: 'menuButton',
                      backgroundColor: AppColors.primaryNormal,
                      child: const Icon(Icons.menu, color: AppColors.surface),
                      onPressed: () {
                        // TODO: Implementar la apertura del Drawer/Menú lateral
                        print("Botón de menú presionado");
                      },
                    ),
                  ),
                );
              },
            ),


            Positioned(
              bottom: 180.0,
              right: 16.0,
              child: FloatingActionButton(
                heroTag: 'gpsButton',
                backgroundColor: AppColors.surface,
                child: const Icon(Icons.my_location, color: AppColors.primaryNormal),
                onPressed: () {
                  // Disparar el evento para obtener la ubicación del usuario
                  context.read<PlacesBloc>().add(GetUserLocationEvent());
                },
              ),
            ),
          ],
        ),
      ),
    );
  }
}