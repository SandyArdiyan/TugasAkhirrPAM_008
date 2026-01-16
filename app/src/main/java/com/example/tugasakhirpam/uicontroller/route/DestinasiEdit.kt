package com.example.tugasakhirpam.uicontroller.route

object DestinasiEdit : DestinasiNavigasi {
    override val route = "edit_antrian"
    override val titleRes = "Edit Antrian"
    const val idArg = "id"
    val routeWithArgs = "$route/{$idArg}"
}