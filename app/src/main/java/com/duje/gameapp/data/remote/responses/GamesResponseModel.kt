package com.duje.gameapp.data.remote.responses

import com.google.gson.annotations.SerializedName

data class GamesResponseModel(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Games>
)

data class Games(
    val id: Int,
    val slug: String,
    val name: String,
    val released: String,
    val tba: Boolean,
    @SerializedName("background_image")
    val backgroundImage: String,
    val rating: Double,
    @SerializedName("rating_top")
    val ratingTop: Int,
    val ratings: List<Rating>,
    @SerializedName("ratings_count")
    val ratingsCount: Int,
    @SerializedName("reviews_text_count")
    val reviewsTextCount: Int,
    val added: Int,
    @SerializedName("added_by_status")
    val addedByStatus: AddedByStatus,
    val metacritic: Int?,
    val playtime: Int?,
    @SerializedName("suggestions_count")
    val suggestionsCount: Int,
    val updated: String,
    @SerializedName("user_game")
    val userGame: Any?,
    @SerializedName("reviews_count")
    val reviewsCount: Int,
    @SerializedName("saturated_color")
    val saturatedColor: String,
    @SerializedName("dominant_color")
    val dominantColor: String,
    val platforms: List<Platform>,
    @SerializedName("parent_platforms")
    val parentPlatforms: List<ParentPlatform>,
    val genres: List<Genre>,
    val stores: List<Store>,
    val clip: Any?,
    val tags: List<Tag>,
    @SerializedName("esrb_rating")
    val esrbRating: EsrbRating?,
    @SerializedName("short_screenshots")
    val shortScreenshots: List<ShortScreenshot>
)

data class Rating(
    val id: Int,
    val title: String,
    val count: Int,
    val percent: Double
)

data class AddedByStatus(
    val yet: Int,
    val owned: Int,
    val beaten: Int,
    val toplay: Int,
    val dropped: Int,
    val playing: Int
)

data class Platform(
    val platform: PlatformInfo,
    @SerializedName("released_at")
    val releasedAt: String?,
    @SerializedName("requirements_en")
    val requirementsEn: Requirements?
)

data class PlatformInfo(
    val id: Int,
    val name: String,
    val slug: String,
    val image: String?,
    @SerializedName("year_end")
    val yearEnd: Int?,
    @SerializedName("year_start")
    val yearStart: Int?,
    @SerializedName("games_count")
    val gamesCount: Int,
    @SerializedName("image_background")
    val imageBackground: String
)

data class ParentPlatform(
    val platform: PlatformInfo
)

data class Genre(
    val id: Int,
    val name: String,
    val slug: String,
    @SerializedName("games_count")
    val gamesCount: Int,
    @SerializedName("image_background")
    val imageBackground: String
)

data class Store(
    val id: Int,
    val store: StoreInfo
)

data class StoreInfo(
    val id: Int,
    val name: String,
    val slug: String,
    val domain: String,
    @SerializedName("games_count")
    val gamesCount: Int,
    @SerializedName("image_background")
    val imageBackground: String
)

data class Tag(
    val id: Int,
    val name: String,
    val slug: String,
    val language: String,
    @SerializedName("games_count")
    val gamesCount: Int,
    @SerializedName("image_background")
    val imageBackground: String
)

data class EsrbRating(
    val id: Int,
    val name: String,
    val slug: String
)

data class ShortScreenshot(
    val id: Int,
    val image: String
)

data class Requirements(
    val minimum: String,
    val recommended: String
)
