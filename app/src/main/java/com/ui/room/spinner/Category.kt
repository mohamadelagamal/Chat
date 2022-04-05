package com.ui.room.spinner

import com.ui.R

data class Category(
    var id :String?=null,
    var name :String?=null,
    var imageId : Int?=null
){
    companion object{
        const val Music = "Music"
        const val Sports = "Sports"
        const val Movies = "Movies"
        fun FromID(category:String):Category{
            when(category){
                Music->{
                    return Category(Music, name = "Music", imageId = R.drawable.icon_music)
                }
                Sports->{
                    return Category(Sports, name = "Sports", imageId = R.drawable.icon_paly)
                }
                Movies->{
                    return Category(Movies, name = "Movies", imageId = R.drawable.icon_movies)
                }
                else->{
                    return Category(Sports, name = "Sports", imageId = R.drawable.icon_paly)
                }

            }
        }
        fun getCategoriesList(): List<Category>{
            return listOf(
                FromID(Music),
                FromID(Sports),
                FromID(Movies)
            )
        }
        fun itemRoomShow(cat:String):Category{
            when(cat){
                Music->{
                    return Category(Music, name = "Music", imageId = R.drawable.sports)
                }
                Sports->{
                    return Category(Sports, name = "Sports", imageId = R.drawable.sports)
                }
                else->{
                    return Category(Movies, name = "Movies", imageId = R.drawable.movies)
                }
            }
        }
    }
}
