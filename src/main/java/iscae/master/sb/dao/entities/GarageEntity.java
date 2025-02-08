//package iscae.master.sb.dao.entities;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name = "garage", schema = "public", catalog = "activite1")
//@Data
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//public class GarageEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    @Column(name = "nom", length = 100, nullable = false)
//    private String nom;
//
//    @Column(name = "address", length = 200)
//    private String address;
//
//    @Column(name = "telephone", length = 20)
//    private String telephone;
//
//    @Column(name = "email", length = 100)
//    private String email;
//
//    @Column(name = "capacite")
//    private Integer capacite;
//}