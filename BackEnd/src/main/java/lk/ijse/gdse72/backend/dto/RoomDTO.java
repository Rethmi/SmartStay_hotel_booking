package lk.ijse.gdse72.backend.dto;

import java.util.List;

public class RoomDTO {
    private Long id;
    private String roomType;
    private double price;
    private String available;
    private Long roomNumber;
    private String image1;
    private String image2;
    private String image3;
    private Long hotelID;
    private List<Long> bookingIds;

    public RoomDTO(Long id, String roomType, double price, String available, Long roomNumber, String image1, String image2, String image3, Long hotelID, List<Long> bookingIds) {
        this.id = id;
        this.roomType = roomType;
        this.price = price;
        this.available = available;
        this.roomNumber = roomNumber;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.hotelID = hotelID;
        this.bookingIds = bookingIds;
    }

    public RoomDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public Long getHotelID() {
        return hotelID;
    }

    public void setHotelID(Long hotelID) {
        this.hotelID = hotelID;
    }

    public List<Long> getBookingIds() {
        return bookingIds;
    }

    public void setBookingIds(List<Long> bookingIds) {
        this.bookingIds = bookingIds;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public Long getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Long roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public String toString() {
        return "RoomDTO{" +
                "id=" + id +
                ", roomType='" + roomType + '\'' +
                ", price=" + price +
                ", available='" + available + '\'' +
                ", roomNumber=" + roomNumber +
                ", image1='" + image1 + '\'' +
                ", image2='" + image2 + '\'' +
                ", image3='" + image3 + '\'' +
                ", hotelID=" + hotelID +
                ", bookingIds=" + bookingIds +
                '}';
    }
}
