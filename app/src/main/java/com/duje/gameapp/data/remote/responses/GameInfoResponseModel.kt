package com.duje.gameapp.data.remote.responses

data class GameInfoResponseModel(
    val id: Int,
    val slug: String,
    val name: String,
    val name_original: String,
    val description: String,
    val metacritic: Int,
    val metacritic_platforms: List<MetacriticPlatform>,
    val released: String,
    val tba: Boolean,
    val updated: String,
    val background_image: String,
    val background_image_additional: String,
    val website: String,
    val rating: Double,
    val rating_top: Int,
    val ratings: List<Ratings>,
    val reactions: Map<String, Int>,
    val added: Int,
    val added_by_status: AddedByStatuss,
    val playtime: Int,
    val screenshots_count: Int,
    val movies_count: Int,
    val creators_count: Int,
    val achievements_count: Int,
    val parent_achievements_count: Int,
    val reddit_url: String,
    val reddit_name: String,
    val reddit_description: String,
    val reddit_logo: String,
    val reddit_count: Int,
    val twitch_count: Int,
    val youtube_count: Int,
    val reviews_text_count: Int,
    val ratings_count: Int,
    val suggestions_count: Int,
    val alternative_names: List<String>,
    val metacritic_url: String,
    val parents_count: Int,
    val additions_count: Int,
    val game_series_count: Int,
    val user_game: Any?,
    val reviews_count: Int,
    val saturated_color: String,
    val dominant_color: String,
    val parent_platforms: List<ParentPlatforms>,
    val platforms: List<Platforms>,
    val stores: List<Stores>,
    val developers: List<Developer>,
    val genres: List<Genres>,
    val tags: List<Tags>,
    val publishers: List<Publisher>,
    val esrb_rating: EsrbRatings,
    val clip: Any?,
    val description_raw: String
)

data class MetacriticPlatform(
    val metascore: Int,
    val url: String,
    val platform: PlatformInfos
)

data class PlatformInfos(
    val platform: Int,
    val name: String,
    val slug: String
)

data class Ratings(
    val id: Int,
    val title: String,
    val count: Int,
    val percent: Double
)

data class AddedByStatuss(
    val yet: Int,
    val owned: Int,
    val beaten: Int,
    val toplay: Int,
    val dropped: Int,
    val playing: Int
)

data class ParentPlatforms(
    val platform: PlatformInfos
)

data class Platforms(
    val platform: PlatformInfos,
    val released_at: String,
    val requirements: Requirementss
)

data class Requirementss(
    val minimum: String,
    val recommended: String
)

data class Stores(
    val id: Int,
    val url: String,
    val store: StoreInfos
)

data class StoreInfos(
    val id: Int,
    val name: String,
    val slug: String,
    val domain: String,
    val games_count: Int,
    val image_background: String
)

data class Developer(
    val id: Int,
    val name: String,
    val slug: String,
    val games_count: Int,
    val image_background: String
)

data class Genres(
    val id: Int,
    val name: String,
    val slug: String,
    val games_count: Int,
    val image_background: String
)

data class Tags(
    val id: Int,
    val name: String,
    val slug: String,
    val language: String,
    val games_count: Int,
    val image_background: String
)

data class Publisher(
    val id: Int,
    val name: String,
    val slug: String,
    val games_count: Int,
    val image_background: String
)

data class EsrbRatings(
    val id: Int,
    val name: String,
    val slug: String
)
