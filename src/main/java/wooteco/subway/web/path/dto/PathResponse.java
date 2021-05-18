package wooteco.subway.web.path.dto;

import wooteco.subway.domain.path.Path;
import wooteco.subway.domain.station.Station;
import wooteco.subway.web.station.dto.StationResponse;

import java.util.List;
import java.util.stream.Collectors;

public class PathResponse {
    private List<StationResponse> stations;
    private int distance;

    public PathResponse() {
    }

    public PathResponse(List<StationResponse> stations, int distance) {
        this.stations = stations;
        this.distance = distance;
    }

    public static PathResponse of(Path shortestPath) {
        final List<Station> path = shortestPath.getPath();
        final int distance = shortestPath.getDistance();
        return new PathResponse(makeStationResponse(path), distance);
    }

    private static List<StationResponse> makeStationResponse(List<Station> path) {
        return path.stream()
                .map(StationResponse::of)
                .collect(Collectors.toList());
    }

    public List<StationResponse> getStations() {
        return stations;
    }

    public int getDistance() {
        return distance;
    }
}
