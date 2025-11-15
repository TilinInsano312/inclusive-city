import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:inclusivecity_frontend/constants/app_colors.dart';
import 'package:inclusivecity_frontend/features/map/domain/entities/place_suggestion.dart';
import 'package:inclusivecity_frontend/features/map/presentation/bloc/place_bloc.dart';

/// Un widget deslizable (bottom sheet) que imita el comportamiento de búsqueda
/// de Waze o Google Maps.
///
/// Contiene el campo de búsqueda, atajos (Casa, Trabajo), y muestra
/// los resultados del BLoC o contenido por defecto (Recientes, Mis Listas).
class SearchBottomSheet extends StatefulWidget {
  final ValueNotifier<double>? sheetSizeNotifier;
  
  const SearchBottomSheet({super.key, this.sheetSizeNotifier});

  @override
  State<SearchBottomSheet> createState() => _SearchBottomSheetState();
}

class _SearchBottomSheetState extends State<SearchBottomSheet> {
  final _searchController = TextEditingController();
  final _focusNode = FocusNode();
  final _sheetController = DraggableScrollableController();

  @override
  void initState() {
    super.initState();
    _focusNode.addListener(_onFocusChange);
    _sheetController.addListener(_onSheetSizeChange);
  }

  void _onFocusChange() {
    if (_focusNode.hasFocus) {
      _sheetController.animateTo(
        0.9, // Expande al 90% de la pantalla
        duration: const Duration(milliseconds: 300),
        curve: Curves.easeInOut,
      );
    }
  }

  void _onSheetSizeChange() {
    widget.sheetSizeNotifier?.value = _sheetController.size;
    
    if (_sheetController.size < 0.3) {
      _focusNode.unfocus();
    }
  }

  @override
  void dispose() {
    _searchController.dispose();
    _focusNode.removeListener(_onFocusChange);
    _focusNode.dispose();
    _sheetController.removeListener(_onSheetSizeChange);
    _sheetController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return DraggableScrollableSheet(
      controller: _sheetController,
      initialChildSize: 0.15,
      minChildSize: 0.15,
      maxChildSize: 0.9,
      builder: (BuildContext context, ScrollController scrollController) {
        return BlocBuilder<PlacesBloc, PlacesState>(
          builder: (context, state) {
            return Container(
              decoration: const BoxDecoration(
                color: AppColors.surface,
                borderRadius: BorderRadius.vertical(top: Radius.circular(20.0)),
                boxShadow: [
                  BoxShadow(
                    color: Colors.black26,
                    blurRadius: 10.0,
                    offset: Offset(0, -2),
                  ),
                ],
              ),
              child: ListView(
                controller: scrollController,
                padding: EdgeInsets.zero,
                children: [
                  _buildGrabber(),
                  
                  //Barra de búsqueda
                  _buildSearchTextField(state),
                  
                  _buildShortcutButtons(),
                  
                  if (state is PlacesLoading)
                    const SizedBox(
                      height: 4.0,
                      child: LinearProgressIndicator(
                        color: AppColors.primaryNormal,
                        backgroundColor: AppColors.neutralLight,
                      ),
                    ),

                  const Divider(height: 1, thickness: 1, color: AppColors.neutralLight),
                  _buildContent(state),
                ],
              ),
            );
          },
        );
      },
    );
  }

  /// Construye el widget que se muestra basado en el estado del BLoC
  Widget _buildContent(PlacesState state) {
    if (state is PlacesLoaded) {
      return _buildResultsList(state.suggestions);
    }
    if (state is PlacesEmpty) {
      return _buildInfoMessage(Icons.search_off, "No se encontraron resultados");
    }
    if (state is PlacesError) {
      return _buildInfoMessage(Icons.error_outline, state.message, isError: true);
    }
    return _buildDefaultContent();
  }

  /// Barra gris para indicar que el sheet es deslizable
  Widget _buildGrabber() {
    return Container(
      width: 40.0,
      height: 5.0,
      margin: const EdgeInsets.symmetric(vertical: 10.0),
      decoration: BoxDecoration(
        color: AppColors.neutralNormal,
        borderRadius: BorderRadius.circular(2.5),
      ),
    );
  }

  /// El campo de texto para la búsqueda
  Widget _buildSearchTextField(PlacesState state) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 16.0),
      child: TextField(
        controller: _searchController,
        focusNode: _focusNode,
        decoration: InputDecoration(
          hintText: "¿Adónde vas?",
          hintStyle: const TextStyle(color: AppColors.neutralDarkNormal),
          filled: true,
          fillColor: AppColors.neutralLight,
          prefixIcon: const Icon(Icons.search, color: AppColors.primaryNormal),
          suffixIcon: IconButton(
            // TODO: Implementar lógica de reconocimiento de voz
            icon: const Icon(Icons.mic, color: AppColors.primaryNormal),
            onPressed: () {
              // TODO: Implementar navegación a la vista de búsqueda por voz
            },
          ),
          border: OutlineInputBorder(
            borderRadius: BorderRadius.circular(30.0),
            borderSide: BorderSide.none,
          ),
          contentPadding: const EdgeInsets.symmetric(vertical: 14.0),
        ),
        onChanged: (query) {
          // Dispara el evento de búsqueda
          context.read<PlacesBloc>().add(SearchPlacesEvent(query));
        },
      ),
    );
  }

  /// Los botones de atajo (Casa, Trabajo, Añadir)
  Widget _buildShortcutButtons() {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 12.0),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: [
          _buildChipButton(
            label: "Casa",
            icon: Icons.home,
            onPressed: () {
              // TODO: Implementar navegación a la ruta guardada como "Casa"
              print("Ir a Casa");
            },
          ),
          _buildChipButton(
            label: "Trabajo",
            icon: Icons.work,
            onPressed: () {
              // TODO: Implementar navegación a la ruta guardada como "Trabajo"
              print("Ir a Trabajo");
            },
          ),
          _buildChipButton(
            label: "Añadir",
            icon: Icons.add,
            onPressed: () {
              // TODO: Implementar navegación para añadir un nuevo lugar
              print("Añadir lugar");
            },
          ),
        ],
      ),
    );
  }

  /// Un botón de atajo individual
  Widget _buildChipButton({
    required String label,
    required IconData icon,
    required VoidCallback onPressed,
  }) {
    return ElevatedButton.icon(
      icon: Icon(icon, color: AppColors.primaryNormal, size: 20),
      label: Text(
        label,
        style: const TextStyle(
          color: AppColors.neutralDarkDarker,
          fontWeight: FontWeight.w600,
        ),
      ),
      onPressed: onPressed,
      style: ElevatedButton.styleFrom(
        backgroundColor: AppColors.neutralLight,
        elevation: 0,
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(20.0),
        ),
        padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 10),
      ),
    );
  }

  /// Muestra el contenido por defecto (Recientes, Mis Listas)
  Widget _buildDefaultContent() {
    return Padding(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
        // --- Sección Recientes ---
        Text(
          "Recientes",
          style: TextStyle(
            fontWeight: FontWeight.bold,
            fontSize: 16,
            color: AppColors.neutralDarkDarker,
          ),
        ),
        const SizedBox(height: 10),
        _buildListTile(
          icon: Icons.history,
          title: "Lugar reciente 1",
          onTap: () {
            // TODO: Implementar lógica para seleccionar lugar reciente
          },
        ),
        _buildListTile(
          icon: Icons.history,
          title: "Lugar reciente 2",
          onTap: () {
            // TODO: Implementar lógica para seleccionar lugar reciente
          },
        ),
        const Divider(height: 32),

        // --- Sección Mis Listas ---
        Text(
          "Mis listas",
          style: TextStyle(
            fontWeight: FontWeight.bold,
            fontSize: 16,
            color: AppColors.neutralDarkDarker,
          ),
        ),
        const SizedBox(height: 10),
        _buildListTile(
          icon: Icons.star,
          title: "Destacados",
          subtitle: "2 lugares",
          iconColor: AppColors.yellowNormal,
          onTap: () {
            // TODO: Implementar navegación a la vista "Destacados"
          },
        ),
        _buildListTile(
          icon: Icons.favorite,
          title: "Favoritos",
          subtitle: "1 lugares",
          iconColor: AppColors.accentNormal,
          onTap: () {
            // TODO: Implementar navegación a la vista "Favoritos"
          },
        ),
        _buildListTile(
          icon: Icons.add,
          title: "Agregar lista",
          onTap: () {
            // TODO: Implementar lógica para crear una nueva lista
          },
        ),
      ],
    ),
    );
  }

  /// Muestra la lista de resultados de búsqueda
  Widget _buildResultsList(List<PlaceSuggestion> suggestions) {
    return Column(
      children: List.generate(suggestions.length, (index) {
        final suggestion = suggestions[index];
        return _buildListTile(
          icon: Icons.location_on,
          title: suggestion.description,
          onTap: () {
            // Acción al seleccionar un lugar de la búsqueda
            print("Lugar seleccionado: ${suggestion.placeId}");
            _focusNode.unfocus(); // Ocultar teclado
            _sheetController.animateTo(
              0.15,
              duration: const Duration(milliseconds: 300),
              curve: Curves.easeInOut,
            );
            context.read<PlacesBloc>().add(ClearSearchEvent()); // Limpia resultados
            
            // TODO: Aquí moverías el mapa, mostrarías detalles, etc.
          },
        );
      }),
    );
  }

  /// Un widget reutilizable para las filas de la lista
  Widget _buildListTile({
    required IconData icon,
    required String title,
    String? subtitle,
    Color? iconColor,
    required VoidCallback onTap,
  }) {
    return ListTile(
      leading: Icon(icon, color: iconColor ?? AppColors.neutralDarkNormal),
      title: Text(
        title,
        style: const TextStyle(
          color: AppColors.neutralDarkDarker,
          fontWeight: FontWeight.w500,
        ),
      ),
      subtitle: subtitle != null
          ? Text(subtitle, style: const TextStyle(color: AppColors.neutralDarkNormal))
          : null,
      onTap: onTap,
      contentPadding: const EdgeInsets.symmetric(horizontal: 8.0),
    );
  }

  /// Muestra un mensaje de "No hay resultados" o "Error"
  Widget _buildInfoMessage(IconData icon, String message, {bool isError = false}) {
    final color = isError ? AppColors.error : AppColors.neutralDarkNormal;
    return Center(
      child: Padding(
        padding: const EdgeInsets.all(32.0),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            Icon(icon, size: 40, color: color),
            const SizedBox(height: 16),
            Text(
              message,
              textAlign: TextAlign.center,
              style: TextStyle(
                fontSize: 16,
                color: color,
              ),
            ),
          ],
        ),
      ),
    );
  }
}