import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:inclusivecity_frontend/constants/app_colors.dart';
import 'package:inclusivecity_frontend/features/map/presentation/widget/search_bottom_sheet.dart';



class MapPage extends StatefulWidget {
  const MapPage({super.key});

  @override
  State<MapPage> createState() => _MapPageState();
}

class _MapPageState extends State<MapPage> {
  final ValueNotifier<double> _sheetSizeNotifier = ValueNotifier<double>(0.15);

  @override
  void dispose() {
    _sheetSizeNotifier.dispose();
    super.dispose();
  }

  // Posición inicial del mapa (ej. Temuco, Chile)
  static const CameraPosition _initialPosition = CameraPosition(
    target: LatLng(-38.7396, -72.5980), // TODO: Cambiar a la ubicación real del usuario
    zoom: 14.0,
  );

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: [
          //El Mapa de Google
          const GoogleMap(
            initialCameraPosition: _initialPosition,
            myLocationButtonEnabled: false,
            myLocationEnabled: true,
            zoomControlsEnabled: false,
          ),

          SearchBottomSheet(sheetSizeNotifier: _sheetSizeNotifier),

          ValueListenableBuilder<double>(
            valueListenable: _sheetSizeNotifier,
            builder: (context, sheetSize, child) {
              // Oculta el botón cuando el sheet es mayor al 30%
              final shouldShow = sheetSize < 0.3;
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
              heroTag: 'gpsButton', // Tag único para el Hero
              backgroundColor: AppColors.surface,
              child: const Icon(Icons.my_location, color: AppColors.primaryNormal),
              onPressed: () {
                // TODO: Implementar la lógica para centrar el mapa en la ubicación del usuario
                print("Botón de centrar presionado");
              },
            ),
          ),
        ],
      ),
    );
  }
}