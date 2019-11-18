package aa.bean;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;

@SpaceClass
public class SegLegMap {

    Integer flightId;
    String segDepartureDateTime;
    String segBoard;
    String segOff;
    String legNo;
    String legDepartureDateTime;
    String legFlight;
    String legBoard;
    String legOff;
    @SpaceId
    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    public String getSegDepartureDateTime() {
        return segDepartureDateTime;
    }

    public void setSegDepartureDateTime(String segDepartureDateTime) {
        this.segDepartureDateTime = segDepartureDateTime;
    }

    public String getSegBoard() {
        return segBoard;
    }

    public void setSegBoard(String segBoard) {
        this.segBoard = segBoard;
    }

    public String getSegOff() {
        return segOff;
    }

    public void setSegOff(String segOff) {
        this.segOff = segOff;
    }

    public String getLegNo() {
        return legNo;
    }

    public void setLegNo(String legNo) {
        this.legNo = legNo;
    }

    public String getLegDepartureDateTime() {
        return legDepartureDateTime;
    }

    public void setLegDepartureDateTime(String legDepartureDateTime) {
        this.legDepartureDateTime = legDepartureDateTime;
    }

    public String getLegFlight() {
        return legFlight;
    }

    public void setLegFlight(String legFlight) {
        this.legFlight = legFlight;
    }

    public String getLegBoard() {
        return legBoard;
    }

    public void setLegBoard(String legBoard) {
        this.legBoard = legBoard;
    }

    public String getLegOff() {
        return legOff;
    }

    public void setLegOff(String legOff) {
        this.legOff = legOff;
    }

    public SegLegMap(Integer flightId, String segDepartureDateTime, String segBoard, String segOff, String legNo, String legDepartureDateTime, String legFlight, String legBoard, String legOff) {
        this.flightId = flightId;
        this.segDepartureDateTime = segDepartureDateTime;
        this.segBoard = segBoard;
        this.segOff = segOff;
        this.legNo = legNo;
        this.legDepartureDateTime = legDepartureDateTime;
        this.legFlight = legFlight;
        this.legBoard = legBoard;
        this.legOff = legOff;
    }

    public SegLegMap() {
    }

    @Override
    public String toString() {
        return "SegLegMap{" +
                "flightId=" + flightId +
                ", segDepartureDateTime='" + segDepartureDateTime + '\'' +
                ", segBoard='" + segBoard + '\'' +
                ", segOff='" + segOff + '\'' +
                ", legNo='" + legNo + '\'' +
                ", legDepartureDateTime='" + legDepartureDateTime + '\'' +
                ", legFlight='" + legFlight + '\'' +
                ", legBoard='" + legBoard + '\'' +
                ", legOff='" + legOff + '\'' +
                '}';
    }
}
