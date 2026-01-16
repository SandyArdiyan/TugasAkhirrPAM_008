package com.example.tugasakhirpam.uicontroller.route

object DestinasiDetail : DestinasiNavigasi {
    override val route = "item_detail"
    override val titleRes = "Detail Antrian"
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}