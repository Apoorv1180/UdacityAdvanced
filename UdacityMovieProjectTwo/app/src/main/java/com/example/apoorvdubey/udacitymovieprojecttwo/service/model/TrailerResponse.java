package com.example.apoorvdubey.udacitymovieprojecttwo.service.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrailerResponse implements Parcelable {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("results")
@Expose
private List<Trailer> results = null;

    protected TrailerResponse(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        results = in.createTypedArrayList(Trailer.CREATOR);
    }

    public static final Creator<TrailerResponse> CREATOR = new Creator<TrailerResponse>() {
        @Override
        public TrailerResponse createFromParcel(Parcel in) {
            return new TrailerResponse(in);
        }

        @Override
        public TrailerResponse[] newArray(int size) {
            return new TrailerResponse[size];
        }
    };

    public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public List<Trailer> getResults() {
return results;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeTypedList(results);
    }
}