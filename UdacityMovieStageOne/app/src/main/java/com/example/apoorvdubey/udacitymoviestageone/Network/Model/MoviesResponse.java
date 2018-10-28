package com.example.apoorvdubey.udacitymoviestageone.Network.Model;


import android.os.Parcel;
import android.os.Parcelable;

import com.example.apoorvdubey.udacitymoviestageone.Utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesResponse implements Parcelable{

@SerializedName(Constants.PAGE)
@Expose
public Integer page;
@SerializedName(Constants.TOTAL_RESULTS)
@Expose
public Integer totalResults;
@SerializedName(Constants.TOTAL_PAGES)
@Expose
public Integer totalPages;
@SerializedName(Constants.RESULTS)
@Expose
public List<Result> results = null;

    protected MoviesResponse(Parcel in) {
        if (in.readByte() == 0) {
            page = null;
        } else {
            page = in.readInt();
        }
        if (in.readByte() == 0) {
            totalResults = null;
        } else {
            totalResults = in.readInt();
        }
        if (in.readByte() == 0) {
            totalPages = null;
        } else {
            totalPages = in.readInt();
        }
    }

    public static final Creator<MoviesResponse> CREATOR = new Creator<MoviesResponse>() {
        @Override
        public MoviesResponse createFromParcel(Parcel in) {
            return new MoviesResponse(in);
        }

        @Override
        public MoviesResponse[] newArray(int size) {
            return new MoviesResponse[size];
        }
    };

    public List<Result> getResults() {
        return results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (page == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(page);
        }
        if (totalResults == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(totalResults);
        }
        if (totalPages == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(totalPages);
        }
    }
}