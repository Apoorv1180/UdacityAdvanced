package com.example.apoorvdubey.udacitymoviestageone.Utils;

public class Constants {

    private static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static final String API_KEY_V3 = "###########################";
    private static final String API_KEY_V4 = "###########################";
    private static final String BASE_URL_POSTER = "http://image.tmdb.org/t/p/";
    private static final String POSTER_SIZE = "w500";
    public static final String PAGE = "page";
    public static final String TOTAL_RESULTS = "total_results";
    public static final String RESULTS = "results";
    public static final String TOTAL_PAGES = "total_pages";
    public static final String VOTE_COUNT = "vote_count";
    public static final String ID = "id";
    public static final String VIDEO = "video";
    public static final String VOTE_AVERAGE = "vote_average";
    public static final String TITLE = "title";
    public static final String POPULARITY = "popularity";
    public static final String POSTER_PATH = "poster_path";
    public static final String ORIGINAL_LANGUAGE = "original_language";
    public static final String ORIGINAL_TITLE = "original_title";
    public static final String GENRE_IDS = "genre_ids";
    public static final String BACKDROP_PATH = "backdrop_path";
    public static final String ADULT = "adult";
    public static final String OVERVIEW = "overview";
    public static final String RELEASE_DATE = "release_date";
    public static final String POSITION = "position";
    public static final String RESPONSE = "response";
    public static final int LOADER_ID = 1;
    public static final int WIDTH = 500;

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String getBaseUrlPoster() {
        return BASE_URL_POSTER;
    }

    public static String getPosterSize() {
        return POSTER_SIZE;
    }

    public static String getApiKeyV3() {
        return API_KEY_V3;
    }

}