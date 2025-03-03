package iscae.master.sb.dao.entities;

public enum VehiculeCategorie {
    VEHICULES_LEGERS("Véhicules légers"),
    VEHICULES_UTILITAIRES("Véhicules utilitaires"),
    CAMIONS("Camions"),
    VEHICULES_ELECTRIQUES_HYBRIDES("Véhicules électriques et hybrides"),
    MOTOS_SCOOTERS("Motos et scooters"),
    ENGINS_AGRICOLES("Engins agricoles"),
    VEHICULES_CHANTIER("Véhicules de chantier");

    private final String label;

    VehiculeCategorie(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}