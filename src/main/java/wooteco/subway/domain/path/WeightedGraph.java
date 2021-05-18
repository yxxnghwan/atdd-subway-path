package wooteco.subway.domain.path;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import wooteco.subway.domain.line.Line;
import wooteco.subway.domain.section.Section;
import wooteco.subway.domain.station.Station;

import java.util.List;

public class WeightedGraph {
    private final WeightedMultigraph<Station, DefaultWeightedEdge> graph;

    private WeightedGraph(WeightedMultigraph<Station, DefaultWeightedEdge> graph) {
        this.graph = graph;
    }

    public static WeightedGraph of(List<Line> lines) {
        final WeightedMultigraph<Station, DefaultWeightedEdge> graph = makeWeightedGraph(lines);
        return new WeightedGraph(graph);
    }

    private static WeightedMultigraph<Station, DefaultWeightedEdge> makeWeightedGraph(List<Line> lines) {
        WeightedMultigraph<Station, DefaultWeightedEdge> graph
                = new WeightedMultigraph<>(DefaultWeightedEdge.class);

        lines.forEach(line -> {
            addVertex(graph, line);
            setEdgeWeight(graph, line);
        });

        return graph;
    }

    private static void addVertex(WeightedMultigraph<Station, DefaultWeightedEdge> graph, Line line) {
        final List<Station> stations = line.getStations();
        stations.forEach(graph::addVertex);
    }

    private static void setEdgeWeight(WeightedMultigraph<Station, DefaultWeightedEdge> graph, Line line) {
        final List<Section> sections = line.getSections().getSections();
        sections.forEach(section -> graph.setEdgeWeight(
                graph.addEdge(section.getUpStation(), section.getDownStation()),
                section.getDistance()
        ));
    }

    public WeightedMultigraph<Station, DefaultWeightedEdge> getGraph() {
        return graph;
    }
}
