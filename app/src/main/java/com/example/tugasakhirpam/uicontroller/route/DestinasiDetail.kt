package com.example.tugasakhirpam.uicontroller.route

object DestinasiDetail : DestinasiNavigasi {
    override val route = "detail_antrian"
    override val titleRes = "Detail Antrian"
    const val idArg = "id"
    val routeWithArgs = "$route/{$idArg}"
}