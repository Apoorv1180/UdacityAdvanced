package com.example.apoorvdubey.udacitymovieprojecttwo.service.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.apoorvdubey.udacitymovieprojecttwo.Utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "result")
public class DetailResult implements Parcelable {

    @SerializedName(Constants.VOTE_COUNT)
    @Expose
    public Integer voteCount;
    @SerializedName(Constants.ID)
    @Expose
    @PrimaryKey(autoGenerate = false)
    public Integer id;
    @SerializedName(Constants.VIDEO)
    @Expose
    public Boolean video;
    @SerializedName(Constants.VOTE_AVERAGE)
    @Expose
    public Double voteAverage;
    @SerializedName(Constants.TITLE)
    @Expose
    public String title;
    @SerializedName(Constants.POPULARITY)
    @Expose
    public Double popularity;
    @SerializedName(Constants.POSTER_PATH)
    @Expose
    public String posterPath;
    @SerializedName(Constants.ORIGINAL_LANGUAGE)
    @Expose
    public String originalLanguage;
    @SerializedName(Constants.ORIGINAL_TITLE)
    @Expose
    public String originalTitle;
    @Ignore
    @SerializedName(Constants.GENRE_IDS)
    @Expose
    public List<Integer> genreIds = null;
    @SerializedName(Constants.BACKDROP_PATH)
    @Expose
    public String backdropPath;
    @SerializedName(Constants.ADULT)
    @Expose
    public Boolean adult;
    @SerializedName(Constants.OVERVIEW)
    @Expose
    public String overview;
    @SerializedName(Constants.RELEASE_DATE)
    @Expose
    public String releaseDate;
    @ColumnInfo(name = "isFavourite")
    public String isFavourite = "true";
    @ColumnInfo(name = "popular")
    public String popular;

    protected DetailResult(Parcel in) {
        if (in.readByte() == 0) {
            voteCount = null;
        } else {
            voteCount = in.readInt();
        }
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        byte tmpVideo = in.readByte();
        video = tmpVideo == 0 ? null : tmpVideo == 1;
        if (in.readByte() == 0) {
            voteAverage = null;
        } else {
            voteAverage = in.readDouble();
        }
        title = in.readString();
        if (in.readByte() == 0) {
            popularity = null;
        } else {
            popularity = in.readDouble();
        }
        posterPath = in.readString();
        originalLanguage = in.readString();
        originalTitle = in.readString();
        backdropPath = in.readString();
        byte tmpAdult = in.readByte();
        adult = tmpAdult == 0 ? null : tmpAdult == 1;
        overview = in.readString();
        releaseDate = in.readString();
    }

    public static final Creator<DetailResult> CREATOR = new Creator<DetailResult>() {
        @Override
        public DetailResult createFromParcel(Parcel in) {
            return new DetailResult(in);
        }

        @Override
        public DetailResult[] newArray(int size) {
            return new DetailResult[size];
        }
    };

    public String getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(String isFavourite) {
        this.isFavourite = isFavourite;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPopular() {
        return popular;
    }

    public void setPopular(String popular) {
        this.popular = popular;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (voteCount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(voteCount);
        }
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeByte((byte) (video == null ? 0 : video ? 1 : 2));
        if (voteAverage == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(voteAverage);
        }
        parcel.writeString(title);
        if (popularity == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(popularity);
        }
        parcel.writeString(posterPath);
        parcel.writeString(originalLanguage);
        parcel.writeString(originalTitle);
        parcel.writeString(backdropPath);
        parcel.writeByte((byte) (adult == null ? 0 : adult ? 1 : 2));
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
    }

    public DetailResult(Integer voteCount, Integer id, Boolean video, Double voteAverage, String title, Double popularity, String posterPath, String originalLanguage, String originalTitle, String backdropPath, Boolean adult, String overview, String releaseDate) {
        this.voteCount = voteCount;
        this.id = id;
        this.video = video;
        this.voteAverage = voteAverage;
        this.title = title;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.backdropPath = backdropPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }
}