package com.esspl.hemendra.mapdemo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hemendra on 25-07-2016.
 */
public class DirectionResults {
    @SerializedName("routes")
    private List<Route> routes;

    public List<Route> getRoutes() {
        return routes;
    }

    @SerializedName("status")
    private String status;

    public String getStatus() {
        return status;
    }

    @SerializedName("error_message")
    private String error_message;

    public String getError_message() {
        return error_message;
    }
}

class Route {
    @SerializedName("overview_polyline")
    private OverviewPolyLine overviewPolyLine;

    private List<Legs> legs;

    public OverviewPolyLine getOverviewPolyLine() {
        return overviewPolyLine;
    }

    public List<Legs> getLegs() {
        return legs;
    }
}

class Legs {
    private List<Steps> steps;

    public List<Steps> getSteps() {
        return steps;
    }

    private Distance distance;

    public Distance getDistance() {
        return distance;
    }

    private Duration duration;

    public Duration getDuration() {
        return duration;
    }

    private EndLocation end_location;

    public EndLocation getEnd_location() {
        return end_location;
    }

    private StartLocation start_location;

    public StartLocation getStart_location() {
        return start_location;
    }

    private String end_address;
    private String start_address;

    public String getEnd_address() {
        return end_address;
    }

    public String getStart_address() {
        return start_address;
    }
}

class StartLocation{
    private double lat;
    private double lng;

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}

class EndLocation{
    private double lat;
    private double lng;

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}

class Duration {
    private String text;
    private String value;

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }
}

class Distance{
    private String text;
    private String value;

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }
}

class Steps {
    private Location start_location;
    private Location end_location;
    private OverviewPolyLine polyline;

    public Location getStart_location() {
        return start_location;
    }

    public Location getEnd_location() {
        return end_location;
    }

    public OverviewPolyLine getPolyline() {
        return polyline;
    }
}

class OverviewPolyLine {

    @SerializedName("points")
    public String points;

    public String getPoints() {
        return points;
    }
}

class Location {
    private double lat;
    private double lng;

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
