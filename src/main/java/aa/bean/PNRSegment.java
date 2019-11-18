package aa.bean;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
@SpaceClass
public class PNRSegment {

    String locator;
    Integer flightId;
    String departureDateTime;
    String board;
    String off;
    String arrivalDateTim;

    public String getArrivalDateTim() {
        return arrivalDateTim;
    }

    public void setArrivalDateTim(String arrivalDateTim) {
        this.arrivalDateTim = arrivalDateTim;
    }
    @SpaceId
    public String getLocator() {
        return locator;
    }

    public void setLocator(String locator) {
        this.locator = locator;
    }

    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    public String getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(String departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getOff() {
        return off;
    }

    public void setOff(String off) {
        this.off = off;
    }

    public PNRSegment(String locator, Integer flightId, String departureDateTime, String board, String off, String arrivalDateTim) {
        this.locator = locator;
        this.flightId = flightId;
        this.departureDateTime = departureDateTime;
        this.board = board;
        this.off = off;
        this.arrivalDateTim = arrivalDateTim;
    }

    public PNRSegment() {
    }

    @Override
    public String toString() {
        return "PNRSegment{" +
                "locator='" + locator + '\'' +
                ", flightId=" + flightId +
                ", departureDateTime='" + departureDateTime + '\'' +
                ", board='" + board + '\'' +
                ", off='" + off + '\'' +
                ", arrivalDateTim='" + arrivalDateTim + '\'' +
                '}';
    }
}
