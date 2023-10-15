package ch.walica.sqlite_szablon.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Car implements Parcelable {

    private int id;
    private String title;
    private String body;
    private Long createdDate;

    public Car(int id, String title, String body, Long createdDate) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.createdDate = createdDate;
    }

    public Car(String title, String body) {
        this.id = -1;
        this.title = title;
        this.body = body;
        this.createdDate = new Date().getTime();
    }

    protected Car(Parcel in) {
        id = in.readInt();
        title = in.readString();
        body = in.readString();
        if (in.readByte() == 0) {
            createdDate = null;
        } else {
            createdDate = in.readLong();
        }
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(body);
        if (createdDate == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(createdDate);
        }
    }
}
