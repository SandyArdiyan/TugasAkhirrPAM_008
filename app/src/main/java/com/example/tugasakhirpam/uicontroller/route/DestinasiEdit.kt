package com.example.tugasakhirpam.uicontroller.route

object DestinasiEdit : DestinasiNavigasi {
    override val route = "item_edit"
    override val titleRes = "Edit Antrian"
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}