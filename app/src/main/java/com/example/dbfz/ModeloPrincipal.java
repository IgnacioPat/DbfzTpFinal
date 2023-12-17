package com.example.dbfz;

public class ModeloPrincipal {
    String Nombre,Descripcion,Disponibilidad,PersUrl;
    ModeloPrincipal(){

    }
    public ModeloPrincipal(String descripcion, String disponibilidad, String nombre,String persUrl) {
        Descripcion = descripcion;
        Disponibilidad = disponibilidad;
        Nombre = nombre;
        PersUrl = persUrl;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getDisponibilidad() {
        return Disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        Disponibilidad = disponibilidad;
    }

    public String getPersUrl() {
        return PersUrl;
    }

    public void setPersUrl(String persUrl) {
        PersUrl = persUrl;
    }




}
