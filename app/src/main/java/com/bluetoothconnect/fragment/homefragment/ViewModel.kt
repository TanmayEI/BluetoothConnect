package com.bluetoothconnect.fragment.homefragment

class ViewModel (
    val ModelName : String ="",
    val BrandName : String ="",
    val Time : String ="",
    val image : String =""
)

{
    class GalleryViewModel (
    val Grass : String =""
    )

    data class HomeResponse(
        val `data`: List<Data>,
        val message: String,
        val status: String
    ){
        data class Data(
            val id: String,
            val image: String
        )
    }
}

